package pic.simulator.ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import pic.simulator.io.ProgramLoader;
import pic.simulator.io.ProgramLoader.Program;
import pic.simulator.memory.DataMemory;
import pic.simulator.model.Simulator;

/**
 * Swing user interface of the simulator. The view only communicates with the
 * {@link Simulator} model and never touches CPU internals (memory, stack,
 * program counter) directly except through the model's public API.
 */
public class SimulatorGui extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final String[] COLUMNS_BANK = {"Adresse", "Bank0", "Bank1"};
    private static final String[] COLUMNS_STACK = {"Stack", "Wert"};
    private static final String[] COLUMNS_LISTING = {"", "", "File content"};

    private static final int[] PORT_A_ROWS = {175, 224, 271, 325, 379};
    private static final int[] PORT_B_ROWS = {175, 224, 271, 325, 379, 433, 487, 541};

    private final Simulator simulator = new Simulator();

    private final JPanel contentPane = new JPanel();
    private final JLabel timerLabel = new JLabel("00 µs");
    private final JTable tableSfr;
    private final JTable tableGpr;
    private final JTable tableStack;
    private final JTable tableListing;

    private final int[] commandRow = new int[1024];
    private boolean running = false;
    private int lastRow = -1;

    public SimulatorGui() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1500, 900);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        tableSfr = new JTable(buildBankData(0, 12), COLUMNS_BANK);
        tableGpr = new JTable(buildBankData(12, 128), COLUMNS_BANK);
        tableStack = new JTable(buildStackData(), COLUMNS_STACK);
        tableListing = createListingTable();

        addScrollPane(tableSfr, 1197, 61, 265, 218);
        addScrollPane(tableGpr, 1197, 312, 265, 477);
        addScrollPane(tableStack, 26, 636, 222, 153);
        addScrollPane(tableListing, 284, 61, 878, 728);

        contentPane.add(createButton("Run", 284, 27, e -> onRun()));
        contentPane.add(createButton("Stop", 383, 27, e -> running = false));
        contentPane.add(createButton("Step", 482, 27, e -> onStep()));
        contentPane.add(createButton("Reset", 581, 27, e -> onReset()));
        contentPane.add(createButton("Load", 1073, 27, e -> onLoad()));

        createPortCheckBoxes("RA", 5, 26, PORT_A_ROWS);
        createPortCheckBoxes("RB", 6, 181, PORT_B_ROWS);

        timerLabel.setFont(new Font("Courier", Font.PLAIN, 20));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        timerLabel.setBounds(37, 61, 211, 49);
        contentPane.add(timerLabel);

        simulator.setTimerListener(micros ->
                SwingUtilities.invokeLater(() -> timerLabel.setText(micros + " µs")));
        simulator.getStack().setPopListener(() ->
                SwingUtilities.invokeLater(this::clearTopStackRow));

        refreshTables();
    }

    // --- UI construction helpers -------------------------------------------------

    private void addScrollPane(JTable table, int x, int y, int width, int height) {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(x, y, width, height);
        scrollPane.setViewportView(table);
        contentPane.add(scrollPane);
    }

    private JButton createButton(String text, int x, int y, ActionListener action) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 89, 23);
        button.addActionListener(action);
        contentPane.add(button);
        return button;
    }

    private JTable createListingTable() {
        DefaultTableModel model = new DefaultTableModel(null, COLUMNS_LISTING) {
            private static final long serialVersionUID = 1L;

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class;
                }
                return String.class;
            }
        };
        JTable table = new JTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(3000);
        return table;
    }

    private void createPortCheckBoxes(String prefix, int portRegister, int x, int[] rows) {
        for (int pin = 0; pin < rows.length; pin++) {
            final int portPin = pin;
            JCheckBox checkBox = new JCheckBox(prefix + pin);
            checkBox.setBounds(x, rows[pin], 67, 23);
            checkBox.addActionListener(e -> onPortPinChanged(portRegister, portPin, checkBox.isSelected()));
            contentPane.add(checkBox);
        }
    }

    // --- Event handlers ----------------------------------------------------------

    private void onRun() {
        if (!simulator.isProgramLoaded()) {
            return;
        }
        running = true;
        Thread thread = new Thread(() -> {
            while (running && !isBreakpointReached()) {
                step();
            }
        });
        thread.start();
    }

    private void onStep() {
        if (!simulator.isProgramLoaded()) {
            return;
        }
        running = true;
        step();
        running = false;
    }

    private void onReset() {
        simulator.reset();
        updateRow(simulator.getProgramCounter());
        refreshTables();
    }

    private void onLoad() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            loadListing(fileChooser.getSelectedFile());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void loadListing(File file) throws IOException {
        Program program = ProgramLoader.load(file);
        DefaultTableModel model = (DefaultTableModel) tableListing.getModel();
        model.setRowCount(0);

        List<String> lines = program.getLines();
        for (int row = 0; row < lines.size(); row++) {
            String line = lines.get(row);
            model.addRow(new Object[] {false, "", line});
            if (!line.startsWith(" ") && line.length() >= 9) {
                commandRow[Integer.parseInt(line.substring(0, 4), 16)] = row;
            }
        }

        simulator.loadProgram(program);
    }

    private void onPortPinChanged(int portRegister, int pin, boolean selected) {
        DataMemory memory = simulator.getMemory();
        memory.setForceBankZero(true);
        if (selected) {
            memory.setBit(portRegister, pin);
        } else {
            memory.clearBit(portRegister, pin);
        }
        refreshBankColumn(portRegister);
        memory.setForceBankZero(false);

        if (portRegister == 5 && pin == 4) {
            handleRA4Change(selected);
        } else if (portRegister == 6 && pin == 0) {
            handleRB0Change(selected);
        } else if (portRegister == 6 && pin >= 4) {
            handleRB4to7Change(pin);
        }
    }

    // --- Simulation stepping -----------------------------------------------------

    private void step() {
        simulator.step();
        SwingUtilities.invokeLater(() -> {
            updateRow(simulator.getProgramCounter());
            refreshTables();
        });
    }

    private boolean isBreakpointReached() {
        int row = commandRow[simulator.getProgramCounter()];
        if (row < 0 || row >= tableListing.getRowCount()) {
            return false;
        }
        Object value = tableListing.getValueAt(row, 0);
        return Boolean.TRUE.equals(value);
    }

    // --- View refresh ------------------------------------------------------------

    private void refreshTables() {
        DataMemory memory = simulator.getMemory();

        for (int i = 0; i < 12; i++) {
            tableSfr.setValueAt(toBinary(memory.getRegister(0, i)), i, 1);
            tableSfr.setValueAt(toBinary(memory.getRegister(1, i)), i, 2);
        }

        for (int i = 0; i < 128 - 12; i++) {
            String hex = toHex(memory.read(12 + i));
            tableGpr.setValueAt(hex, i, 1);
            tableGpr.setValueAt(hex, i, 2);
        }

        if (simulator.getStack().getSize() > 0) {
            tableStack.setValueAt(toHex(simulator.getStack().top()),
                    simulator.getStack().getSize() - 1, 1);
        }
    }

    private void refreshBankColumn(int register) {
        tableSfr.setValueAt(toBinary(simulator.getMemory().getRegister(0, register)), register, 1);
    }

    private void clearTopStackRow() {
        tableStack.setValueAt("-", simulator.getStack().getSize(), 1);
    }

    private void updateRow(int row) {
        if (row < 0 || row >= tableListing.getRowCount()) {
            return;
        }
        if (lastRow >= 0 && lastRow < tableListing.getRowCount()) {
            tableListing.setValueAt("", lastRow, 1);
        }
        tableListing.setValueAt("----->", row, 1);
        lastRow = row;
    }

    // --- Port / timer side effects ----------------------------------------------

    private void handleRA4Change(boolean selected) {
        DataMemory memory = simulator.getMemory();
        boolean t0se = memory.getBit(1, 1, 4) != 0;
        if (memory.getBit(1, 1, 5) == 1) {
            boolean edge = (t0se && !selected) || (!t0se && selected);
            if (edge) {
                if (memory.getBit(1, 1, 3) == 1) {
                    memory.increment(0, 1);
                } else {
                    memory.decrementPrescaler();
                }
            }
        }
    }

    private void handleRB0Change(boolean selected) {
        DataMemory memory = simulator.getMemory();
        boolean intedg = memory.getBit(1, 1, 6) != 0;
        if ((intedg && selected) || (!intedg && !selected)) {
            memory.setBit(11, 1);
        }
    }

    private void handleRB4to7Change(int pin) {
        DataMemory memory = simulator.getMemory();
        if (memory.getBit(1, 6, pin) == 1) {
            memory.setBit(11, 0);
        }
    }

    // --- Table data --------------------------------------------------------------

    private static Object[][] buildBankData(int from, int to) {
        Object[][] data = new Object[to - from][];
        for (int i = from; i < to; i++) {
            data[i - from] = new Object[] {String.format("0x%02X", i), 0, 0};
        }
        return data;
    }

    private static Object[][] buildStackData() {
        Object[][] data = new Object[8][];
        for (int i = 0; i < 8; i++) {
            data[i] = new Object[] {"Stack " + i, "-"};
        }
        return data;
    }

    private static String toBinary(int value) {
        String binary = Integer.toBinaryString(value);
        while (binary.length() < 8) {
            binary = "0" + binary;
        }
        return binary;
    }

    private static String toHex(int value) {
        String hex = Integer.toHexString(value);
        while (hex.length() < 2) {
            hex = "0" + hex;
        }
        return "0x" + hex;
    }
}
