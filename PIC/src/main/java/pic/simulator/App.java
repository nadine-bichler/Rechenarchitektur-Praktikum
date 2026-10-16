package pic.simulator;

import javax.swing.SwingUtilities;

import pic.simulator.ui.SimulatorGui;

/**
 * Application entry point. Schedules the Swing UI on the event dispatch thread.
 */
public final class App {

    private App() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimulatorGui().setVisible(true));
    }
}
