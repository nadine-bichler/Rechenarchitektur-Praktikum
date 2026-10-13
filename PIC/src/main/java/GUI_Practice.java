package src.main.java;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.SwingConstants;
import java.awt.Font;

public class GUI_Practice extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static int lastRow = -1;
	public static boolean fileLoaded = false;
	public static boolean runProgramm = false;
	public static boolean isPort = false;
	public static String filepath = "";
	public static JLabel timerLabel;
	static int[] commandRow = new int[1024];
	public static int ROM [] = new int[1024];
	Thread thread;


	Decoder decoder = new Decoder();

	static String[] columnSPR = new String[] {
			"Adresse", "Bank0", "Bank1"
	};

	static String[] columnsGPR = new String[] {
			"Adresse", "Bank0", "Bank1"
	};

	static String[] columnStack = new String[] {
			"Stack", "Wert"
	};

	static String[] columnLST = new String[] {
			"", "", "File content"
	};

	public static Object[][] data = new Object [][] {
			{"0x00", 0, 0},
			{"0x01", 0, 0},
			{"0x02", 0, 0},
			{"0x03", 0, 0},
			{"0x04", 0, 0},
			{"0x05", 0, 0},
			{"0x06", 0, 0},
			{"0x07", 0, 0},
			{"0x08", 0, 0},
			{"0x09", 0, 0},
			{"0x0A", 0, 0},
			{"0x0B", 0, 0}
	};

	public static Object[][] data2 = new Object[][] {
			{"0x0C", 0, 0},
			{"0x0D", 0, 0},
			{"0x0E", 0, 0},
			{"0x0F", 0, 0},
			{"0x10", 0, 0},
			{"0x11", 0, 0},
			{"0x12", 0, 0},
			{"0x13", 0, 0},
			{"0x14", 0, 0},
			{"0x15", 0, 0},
			{"0x16", 0, 0},
			{"0x17", 0, 0},
			{"0x18", 0, 0},
			{"0x19", 0, 0},
			{"0x1A", 0, 0},
			{"0x1B", 0, 0},
			{"0x1C", 0, 0},
			{"0x1D", 0, 0},
			{"0x1E", 0, 0},
			{"0x1F", 0, 0},
			{"0x20", 0, 0},
			{"0x21", 0, 0},
			{"0x22", 0, 0},
			{"0x23", 0, 0},
			{"0x24", 0, 0},
			{"0x25", 0, 0},
			{"0x26", 0, 0},
			{"0x27", 0, 0},
			{"0x28", 0, 0},
			{"0x29", 0, 0},
			{"0x2A", 0, 0},
			{"0x2B", 0, 0},
			{"0x2C", 0, 0},
			{"0x2D", 0, 0},
			{"0x2E", 0, 0},
			{"0x2F", 0, 0},
			{"0x30", 0, 0},
			{"0x31", 0, 0},
			{"0x32", 0, 0},
			{"0x33", 0, 0},
			{"0x34", 0, 0},
			{"0x35", 0, 0},
			{"0x36", 0, 0},
			{"0x37", 0, 0},
			{"0x38", 0, 0},
			{"0x39", 0, 0},
			{"0x3A", 0, 0},
			{"0x3B", 0, 0},
			{"0x3C", 0, 0},
			{"0x3D", 0, 0},
			{"0x3E", 0, 0},
			{"0x3F", 0, 0},
			{"0x40", 0, 0},
			{"0x41", 0, 0},
			{"0x42", 0, 0},
			{"0x43", 0, 0},
			{"0x44", 0, 0},
			{"0x45", 0, 0},
			{"0x46", 0, 0},
			{"0x47", 0, 0},
			{"0x48", 0, 0},
			{"0x49", 0, 0},
			{"0x4A", 0, 0},
			{"0x4B", 0, 0},
			{"0x4C", 0, 0},
			{"0x4D", 0, 0},
			{"0x4E", 0, 0},
			{"0x4F", 0, 0},
			{"0x50", 0, 0},
			{"0x51", 0, 0},
			{"0x52", 0, 0},
			{"0x53", 0, 0},
			{"0x54", 0, 0},
			{"0x55", 0, 0},
			{"0x56", 0, 0},
			{"0x57", 0, 0},
			{"0x58", 0, 0},
			{"0x59", 0, 0},
			{"0x5A", 0, 0},
			{"0x5B", 0, 0},
			{"0x5C", 0, 0},
			{"0x5D", 0, 0},
			{"0x5E", 0, 0},
			{"0x5F", 0, 0},
			{"0x60", 0, 0},
			{"0x61", 0, 0},
			{"0x62", 0, 0},
			{"0x63", 0, 0},
			{"0x64", 0, 0},
			{"0x65", 0, 0},
			{"0x66", 0, 0},
			{"0x67", 0, 0},
			{"0x68", 0, 0},
			{"0x69", 0, 0},
			{"0x6A", 0, 0},
			{"0x6B", 0, 0},
			{"0x6C", 0, 0},
			{"0x6D", 0, 0},
			{"0x6E", 0, 0},
			{"0x6F", 0, 0},
			{"0x70", 0, 0},
			{"0x71", 0, 0},
			{"0x72", 0, 0},
			{"0x73", 0, 0},
			{"0x74", 0, 0},
			{"0x75", 0, 0},
			{"0x76", 0, 0},
			{"0x77", 0, 0},
			{"0x78", 0, 0},
			{"0x79", 0, 0},
			{"0x7A", 0, 0},
			{"0x7B", 0, 0},
			{"0x7C", 0, 0},
			{"0x7D", 0, 0},
			{"0x7E", 0, 0},
			{"0x7F", 0, 0}

	};

	public static Object[][] data3 = new Object[][] {
			{"Stack 0", "-"},
			{"Stack 1", "-"},
			{"Stack 2", "-"},
			{"Stack 3", "-"},
			{"Stack 4", "-"},
			{"Stack 5", "-"},
			{"Stack 6", "-"},
			{"Stack 7", "-"}
	};

	public static Object[][] data4 = new Object[1024][3];

	static JTable tableSPR = new JTable(data, columnSPR);
	static JTable table = new JTable(data2, columnsGPR);
	static JTable tableStack = new JTable(data3, columnStack);
	static JTable tableLST;
	static String fileInput = "";
	static boolean isLoaded = false;

//-----------------------------------------------------------------------------------

	/**
	 * Launch the application.
	 */
	public void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Practice frame = new GUI_Practice();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

//-----------------------------------------------------------------------------------

	/**
	 * Create the frame.
	 */
	public GUI_Practice() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

//-----------------------------------------------------------------------------------
		JScrollPane spSPR = new JScrollPane();
		spSPR.setBounds(1197, 61, 265, 218);
		spSPR.setViewportView(tableSPR);
		contentPane.add(spSPR);

//-----------------------------------------------------------------------------------
		JScrollPane spGPR = new JScrollPane();
		spGPR.setBounds(1197, 312, 265, 477);
		spGPR.setViewportView(table);
		contentPane.add(spGPR);

//-----------------------------------------------------------------------------------

		JScrollPane spStack = new JScrollPane();
		spStack.setBounds(26, 636, 222, 153);
		spStack.setViewportView(tableStack);
		contentPane.add(spStack);

//-----------------------------------------------------------------------------------

		DefaultTableModel model5 = new DefaultTableModel(null, columnLST) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 0) {
					return Boolean.class;
				}
				else if(columnIndex == 1 || columnIndex == 2) {
					return String.class;
				}
				return super.getColumnClass(columnIndex);
			}
		};

		tableLST = new JTable(model5);
		JScrollPane spLST = new JScrollPane();
		spLST.setBounds(284, 61, 878, 728);

		tableLST.setModel(model5);

		tableLST.getColumnModel().getColumn(0).setPreferredWidth(30);
		tableLST.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableLST.getColumnModel().getColumn(2).setPreferredWidth(3000);

		tableLST.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent event0) {

				int row = tableLST.getSelectedRow();
				int col = tableLST.getSelectedColumn();
				int counter = 0;

				if(row >= 0 && col == 0) {
					int number = (int) tableLST.getValueAt(row, col);
					System.out.println("number: " + number);
					counter++;
					System.out.println(counter);

					if(number == 0 && counter == 1) {
						tableLST.setValueAt(1, row, col);
						System.out.println("set 1");
						System.out.println(row + " " + col);
						System.out.println(counter);
					}

					else if(number == 1 && counter == 2) {
						tableLST.setValueAt(0, row, col);
						System.out.println("set 0");
						System.out.println();
						System.out.println(counter);
						counter = 0;
						System.out.println(counter);
					}

				}


			}

		});



		spLST.setViewportView(tableLST);
		contentPane.add(spLST);




//-----------------------------------------------------------------------------------

		JButton runButton = new JButton("Run");
		runButton.setBounds(284, 27, 89, 23);
		contentPane.add(runButton);

		runButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event1) {
				if(fileLoaded) {
					runProgramm = true;
					thread = new Thread(()->{
						while(!checkBreakpoint() && runProgramm) {
							stepProgramm();
						}
					});
					thread.start();
				}
			}
		});

//-----------------------------------------------------------------------------------

		JButton stopButton = new JButton("Stop");
		stopButton.setBounds(383, 27, 89, 23);
		contentPane.add(stopButton);

		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event2) {
				if(fileLoaded) {
					runProgramm = false;
				}
			}
		});

//-----------------------------------------------------------------------------------

		JButton stepButton = new JButton("Step");
		stepButton.setBounds(482, 27, 89, 23);
		contentPane.add(stepButton);

		stepButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event3) {
				if(fileLoaded) {
					runProgramm = true;
					stepProgramm();
					runProgramm = false;
				}
			}
		});

//-----------------------------------------------------------------------------------

		JButton resetButton = new JButton("Reset");
		resetButton.setBounds(581, 27, 89, 23);
		contentPane.add(resetButton);

		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent resetEvent) {
				Datenspeicher.reset();
				updateRow(PC.get());
				GUI_Practice.updateTabelle();
			}
		});

//-----------------------------------------------------------------------------------

		final JButton loadButton = new JButton("Load");
		loadButton.setBounds(1073, 27, 89, 23);
		contentPane.add(loadButton);

		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event5) {
				if(event5.getSource() == loadButton) {
					JFileChooser filechooser = new JFileChooser();
					int i = filechooser.showOpenDialog(loadButton);

					if(i == JFileChooser.APPROVE_OPTION) {
						File file = filechooser.getSelectedFile();
						filepath = file.getPath();
						int y = -1;													//neu
						int x = 0;

						try {
							FileReader filereader = new FileReader(filepath);
							BufferedReader bufferedReader = new BufferedReader(filereader);

							String line = "";
							while ((line = bufferedReader.readLine()) != null) {
								y++;
								model5.addRow(new Object[] {false, "", line});
								if (line.startsWith(" ")){
									continue;
								}
								System.out.println(line);
								ROM [Integer.parseInt(line.substring(0,4), 16)] = Integer.parseInt(line.substring(5,9), 16);
								commandRow[Integer.parseInt(line.substring(0,4), 16)] = y;									//neu

							}

							bufferedReader.close();
							fileLoaded = true;

						}
						catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			}
		});

//-----------------------------------------------------------------------------------

		final JCheckBox ra0CheckBox = new JCheckBox("RA0");
		ra0CheckBox.setBounds(26, 175, 67, 23);
		contentPane.add(ra0CheckBox);

		ra0CheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(ra0CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(5, 0);
					updateColumnAtRow(5);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(5, 0);
					updateColumnAtRow(5);
					isPort = false;

				}

			}
		});

//-----------------------------------------------------------------------------------

		final JCheckBox ra1CheckBox = new JCheckBox("RA1");
		ra1CheckBox.setBounds(26, 224, 67, 23);
		contentPane.add(ra1CheckBox);

		ra1CheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(ra1CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(5, 1);
					updateColumnAtRow(5);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(5, 1);
					updateColumnAtRow(5);
					isPort = false;

				}

			}
		});

//-----------------------------------------------------------------------------------

		final JCheckBox ra2CheckBox = new JCheckBox("RA2");
		ra2CheckBox.setBounds(26, 271, 67, 23);
		contentPane.add(ra2CheckBox);

		ra2CheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(ra2CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(5, 2);
					updateColumnAtRow(5);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(5, 2);
					updateColumnAtRow(5);
					isPort = false;

				}

			}
		});

//-----------------------------------------------------------------------------------

		final JCheckBox ra3CheckBox = new JCheckBox("RA3");
		ra3CheckBox.setBounds(26, 325, 67, 23);
		contentPane.add(ra3CheckBox);

		ra3CheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(ra3CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(5, 3);
					updateColumnAtRow(5);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(5, 3);
					updateColumnAtRow(5);
					isPort = false;

				}

			}
		});

//-----------------------------------------------------------------------------------

		final JCheckBox ra4CheckBox = new JCheckBox("RA4");
		ra4CheckBox.setBounds(26, 379, 67, 23);
		contentPane.add(ra4CheckBox);

		ra4CheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(ra4CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(5, 4);
					updateColumnAtRow(5);
					handleRA4Change(true);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(5, 4);
					updateColumnAtRow(5);
					handleRA4Change(false);
					isPort = false;
				}

			}
		});

//-----------------------------------------------------------------------------------

		final JCheckBox rb0CheckBox = new JCheckBox("RB0");
		rb0CheckBox.setBounds(181, 175, 67, 23);
		contentPane.add(rb0CheckBox);

		rb0CheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(rb0CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(6, 0);
					updateColumnAtRow(6);
					handleRB0Change(true);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(6, 0);
					updateColumnAtRow(6);
					handleRB0Change(false);
					isPort = false;

				}
			}
		});

//-----------------------------------------------------------------------------------

		final JCheckBox rb1CheckBox = new JCheckBox("RB1");
		rb1CheckBox.setBounds(181, 224, 67, 23);
		contentPane.add(rb1CheckBox);

		rb1CheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(rb1CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(6, 1);
					updateColumnAtRow(6);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(6, 1);
					updateColumnAtRow(6);
					isPort = false;

				}

			}
		});

//-----------------------------------------------------------------------------------

		final JCheckBox rb2CheckBox = new JCheckBox("RB2");
		rb2CheckBox.setBounds(181, 271, 67, 23);
		contentPane.add(rb2CheckBox);

		rb2CheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(rb2CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(6, 2);
					updateColumnAtRow(6);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(6, 2);
					updateColumnAtRow(6);
					isPort = false;

				}

			}
		});

//-----------------------------------------------------------------------------------

		final JCheckBox rb3CheckBox = new JCheckBox("RB3");
		rb3CheckBox.setBounds(181, 325, 67, 23);
		contentPane.add(rb3CheckBox);

		rb3CheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(rb3CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(6, 3);
					updateColumnAtRow(6);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(6, 3);
					updateColumnAtRow(6);
					isPort = false;

				}

			}
		});

//-----------------------------------------------------------------------------------

		final JCheckBox rb4CheckBox = new JCheckBox("RB4");
		rb4CheckBox.setBounds(181, 379, 67, 23);
		contentPane.add(rb4CheckBox);


		rb4CheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(rb4CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(6, 4);
					updateColumnAtRow(6);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(6, 4);
					updateColumnAtRow(6);
					isPort = false;

				}
				handleRB4to7Change(4);
			}
		});
//-----------------------------------------------------------------------------------

		final JCheckBox rb5CheckBox = new JCheckBox("RB5");
		rb5CheckBox.setBounds(181, 433, 67, 23);
		contentPane.add(rb5CheckBox);

		rb5CheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(rb5CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(6, 5);
					updateColumnAtRow(6);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(6, 5);
					updateColumnAtRow(6);
					isPort = false;

				}
				handleRB4to7Change(5);
			}
		});

//-----------------------------------------------------------------------------------

		final JCheckBox rb6CheckBox = new JCheckBox("RB6");
		rb6CheckBox.setBounds(181, 487, 67, 23);
		contentPane.add(rb6CheckBox);

		rb6CheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(rb6CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(6, 6);
					updateColumnAtRow(6);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(6, 6);
					updateColumnAtRow(6);
					isPort = false;

				}
				handleRB4to7Change(6);
			}
		});

//-----------------------------------------------------------------------------------

		final JCheckBox rb7CheckBox = new JCheckBox("RB7");
		rb7CheckBox.setBounds(181, 541, 67, 23);
		contentPane.add(rb7CheckBox);


		rb7CheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(rb7CheckBox.isSelected()) {
					isPort = true;
					Datenspeicher.set1(6, 7);
					updateColumnAtRow(6);
					isPort = false;
				}
				else {
					isPort = true;
					Datenspeicher.set0(6, 7);
					updateColumnAtRow(6);
					isPort = false;

				}
				handleRB4to7Change(7);
			}
		});
//-----------------------------------------------------------------------------------

		timerLabel = new JLabel("00 µs");
		timerLabel.setFont(new Font("Courier", Font.PLAIN, 20));
		timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timerLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		timerLabel.setBounds(37, 61, 211, 49);
		contentPane.add(timerLabel);
	}

//-----------------------------------------------------------------------------------

	public static void updateTabelle() {

		for(int i = 0; i < data.length; i++) {
			int number = Datenspeicher.getRegister(0, i);
			String binaryStr = Integer.toBinaryString(number);

			while(binaryStr.length() < 8) {
				binaryStr = "0" + binaryStr;
			}

			tableSPR.setValueAt(binaryStr, i, 1);

		}

		for(int i = 0; i < data.length; i++) {
			int number = Datenspeicher.getRegister(1, i);
			String binaryStr = Integer.toBinaryString(number);

			while(binaryStr.length() < 8) {
				binaryStr = "0" + binaryStr;
			}

			tableSPR.setValueAt(binaryStr, i, 2);

		}

		int counter = 12;

		for(int i = 0; i < data2.length; i++) {
			int number = Datenspeicher.PullDataFromRegister(counter);
			String hexStr = Integer.toHexString(number);

			while(hexStr.length() < 2) {
				hexStr = "0" + hexStr;
			}

			hexStr = "0x" + hexStr;

			table.setValueAt(hexStr, i, 1);
			table.setValueAt(hexStr, i, 2);
			counter++;
		}

		if(Stack.currentSize > 0) {
			int number = Stack.top();
			String hexStr = Integer.toHexString(number);

			while(hexStr.length() < 2) {
				hexStr = "0" + hexStr;
			}

			hexStr = "0x" + hexStr;

			tableStack.setValueAt(hexStr, Stack.currentSize - 1, 1);
		}

	}

//-----------------------------------------------------------------------------------

	public static void updateTabelleStackRemove() {
		tableStack.setValueAt("-", Stack.currentSize, 1);
	}

//-----------------------------------------------------------------------------------

	public static void updateRow(int row) {

		if(lastRow == -1) {
			tableLST.setValueAt("----->", row, 1);
			lastRow = row;
		}
		else {
			tableLST.setValueAt("", lastRow, 1);
			tableLST.setValueAt("----->", row, 1);
			lastRow = row;
		}

	}

	public static void updateColumnAtRow(int register) {
		int number = Datenspeicher.getRegister(0, register);
		String binaryStr = Integer.toBinaryString(number);

		while(binaryStr.length() < 8) {
			binaryStr = "0" + binaryStr;
		}

		tableSPR.setValueAt(binaryStr, register, 1);
	}

	private static void handleRA4Change(boolean isSelected) {
		boolean T0SE = (Datenspeicher.get(1, 1, 4)) != 0; // Check if T0SE (bit 4) is set
		if (Datenspeicher.get(1, 1, 5) == 1) { // Check if T0CS (bit 5) is set
			if(Datenspeicher.get(1, 1, 3) == 1) {
				if ((T0SE && !isSelected) || (!T0SE && isSelected)) {
					Datenspeicher.incrementRegister(0, 1);
				}
			}else {
				if ((T0SE && !isSelected) || (!T0SE && isSelected)) {
					Datenspeicher.prescaler--;
				}
			}
		}
	}

	private static void handleRB0Change(boolean isSelected) {
		boolean INTEDG = (Datenspeicher.get(1, 1, 6)) != 0;
		//falls INTEDG = 1 -> interrupt bei steigender Flanke; falls INTEDG = 0 -> interrupt bei sinkender Flanke
		if ((INTEDG && isSelected) || (!INTEDG && !isSelected)) {
			Datenspeicher.set1(11,1);
		}
	}

	private static void handleRB4to7Change(int pin) {
		if(Datenspeicher.get(1, 6, pin) == 1) {
			Datenspeicher.set1(11, 0);
		}
	}

	public static boolean checkBreakpoint() {
		// Gebe true zurück, wenn die CheckBox gesetzt ist, sonst false
		boolean isBreakpointSet = (boolean) tableLST.getValueAt(commandRow[PC.get()], 0);
		return isBreakpointSet;
	}

	public void stepProgramm() {
		while(GUI_Practice.runProgramm == false) {
			System.out.println("");
		}
		decoder.execute(ROM[PC.get()]);
		GUI_Practice.updateRow(commandRow[PC.get()]);
		GUI_Practice.updateTabelle();
	}

}