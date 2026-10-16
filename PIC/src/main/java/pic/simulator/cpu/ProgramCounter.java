package pic.simulator.cpu;

import pic.simulator.memory.DataMemory;

/**
 * Program counter (13 bit) of the emulated PIC 16F84.
 * Writes the lower 8 bits back to the PCL register whenever it changes.
 */
public class ProgramCounter {

    public static final int ADDRESS_SPACE = 1024;

    private DataMemory memory;
    private int value = 0;

    /**
     * Connects the program counter to the data memory so that PCL stays in sync.
     * Needed because ProgramCounter and DataMemory reference each other.
     */
    public void connect(DataMemory memory) {
        this.memory = memory;
    }

    public int get() {
        return value;
    }

    public int next() {
        value++;
        if (value > ADDRESS_SPACE - 1) {
            value = 0;
        }
        writePcl();
        return value;
    }

    public void jump(int address) {
        value = address;
        writePcl();
    }

    public void overwrite(int address) {
        value = address;
    }

    public void reset() {
        value = 0;
    }

    private void writePcl() {
        if (memory != null) {
            memory.write(2, value & 0xFF);
        }
    }
}
