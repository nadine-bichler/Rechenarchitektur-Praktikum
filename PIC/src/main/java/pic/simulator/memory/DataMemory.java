package pic.simulator.memory;

import java.util.function.IntConsumer;

import pic.simulator.cpu.CallStack;
import pic.simulator.cpu.ProgramCounter;

/**
 * Data memory of the PIC 16F84: two register banks, the special function
 * registers, status flags, timer/prescaler logic, port handling and interrupts.
 */
public class DataMemory {

    private static final int BANK_SIZE = 128;

    private final int[] bank0 = new int[BANK_SIZE];
    private final int[] bank1 = new int[BANK_SIZE];

    private final ProgramCounter pc;
    private final CallStack stack;

    private boolean indirect = false;
    private boolean forceBankZero = false;

    private int psa;
    private int ps2;
    private int prescaler = 0xFF;
    private final int[] dataLatch = {15, 15};
    private int elapsedMicros = 0;

    private IntConsumer timerListener;

    public DataMemory(ProgramCounter pc, CallStack stack) {
        this.pc = pc;
        this.stack = stack;

        for (int i = 0; i < BANK_SIZE; i++) {
            bank0[i] = 0;
            bank1[i] = 0;
        }

        bank1[5] = 0x1F;    // TRISA
        bank1[6] = 0xFF;    // TRISB

        bank0[3] = 0x18;    // STATUS register
        bank1[3] = 0x18;

        bank1[1] = 0xFF;    // OPTION register

        psa = getBit(1, 1, 3);
        ps2 = bank1[1] & 0b111;
    }

    /**
     * Notified with the elapsed execution time (in microseconds) after every tick.
     */
    public void setTimerListener(IntConsumer timerListener) {
        this.timerListener = timerListener;
    }

    public void setIndirect(boolean indirect) {
        this.indirect = indirect;
    }

    /**
     * When set, reading/writing registers always targets bank 0. Used while the
     * user manipulates port pins directly in the UI.
     */
    public void setForceBankZero(boolean forceBankZero) {
        this.forceBankZero = forceBankZero;
    }

    public int getBit(int register, int bit) {
        int mask = 0b1 << bit;
        if (currentBank() == 0) {
            return (bank0[register] & mask) == 0 ? 0 : 1;
        } else {
            return (bank1[register] & mask) == 0 ? 0 : 1;
        }
    }

    public int getBit(int bank, int register, int bit) {
        int mask = 0b1 << bit;
        if (bank == 0) {
            return (bank0[register] & mask) == 0 ? 0 : 1;
        } else {
            return (bank1[register] & mask) == 0 ? 0 : 1;
        }
    }

    public void setBit(int register, int bit) {
        if (getBit(register, bit) == 0) {
            int mask = 0b1 << bit;
            if (isSpecialFunction(register, 127)) {
                bank0[register] |= mask;
                bank1[register] |= mask;
            } else if (currentBank() == 0) {
                bank0[register] |= mask;
            } else {
                bank1[register] |= mask;
            }
        }
    }

    public void clearBit(int register, int bit) {
        if (getBit(register, bit) == 1) {
            int mask = 0b1 << bit;
            if (isSpecialFunction(register, 79)) {
                bank0[register] ^= mask;
                bank1[register] ^= mask;
            } else if (currentBank() == 0) {
                bank0[register] ^= mask;
            } else {
                bank1[register] ^= mask;
            }
        }
    }

    public void setRegister(int bank, int register, int value) {
        if (bank == 0) {
            bank0[register] = value;
        } else {
            bank1[register] = value;
        }
    }

    public int getRegister(int bank, int register) {
        if (bank == 0) {
            return bank0[register];
        } else {
            return bank1[register];
        }
    }

    /**
     * Returns the currently selected bank. Indirect addressing is signalled with
     * 10/11 so callers can distinguish it from direct bank access.
     */
    public int currentBank() {
        if (indirect) {
            int content = bank0[4];
            int seventhBit = content >> 7;
            indirect = false;

            return seventhBit == 0 ? 10 : 11;
        }

        if ((bank0[3] & 0b100000) == 0 || forceBankZero) {
            return 0;
        }
        return 1;
    }

    public void write(int register, int value) {
        if (currentBank() == 0 || currentBank() == 10) {
            writeBothOrBank0(register, value, true);
        } else {
            writeBothOrBank0(register, value, false);
        }
    }

    public int read(int register) {
        if (currentBank() == 0) {
            return bank0[register];
        }
        return bank1[register];
    }

    public void increment(int bank, int register) {
        if (bank == 0) {
            bank0[register]++;
        } else {
            bank1[register]++;
        }
    }

    public void updateZeroFlag(int output) {
        if (output == 0) {
            setBit(3, 2);
        } else {
            clearBit(3, 2);
        }
    }

    public void updateDigitCarryFlag(int output, int k) {
        output &= 0xF;
        k &= 0xF;
        if (output + k > 15) {
            setBit(3, 1);
        } else {
            clearBit(3, 1);
        }
    }

    public void updateCarryFlag(int output, int k) {
        output &= 0xFF;
        if (output + k > 255) {
            setBit(3, 0);
        } else {
            clearBit(3, 0);
        }
    }

    public void updatePrescaler(boolean force) {
        if (psa != getBit(1, 1, 3) || ps2 != (bank1[1] & 0b111) || force) {
            psa = getBit(1, 1, 3);
            ps2 = bank1[1] & 0b111;
            if (getBit(1, 1, 3) == 0) {
                prescaler = (int) Math.pow(2.0, ps2 + 1);
            } else {
                prescaler = (int) Math.pow(2.0, ps2);
            }
        }
    }

    public void handleTimerWrite(int address) {
        if ((currentBank() == 0) & (address == 1)) {
            updatePrescaler(true);
        }
    }

    public int getPrescaler() {
        return prescaler;
    }

    public void decrementPrescaler() {
        prescaler--;
    }

    public void tickTimer() {
        int t0cs = (bank1[1] & 0b100000) >> 5;
        int psa = (bank1[1] & 0b1000) >> 3;

        updatePrescaler(false);

        if (t0cs == 0) {
            if (psa == 0) {
                prescaler--;
            } else {
                bank0[1]++;
            }
        }
        handlePrescalerOverflow();

        if (bank0[1] > 0xFF) {
            setBit(11, 2);
            bank0[1] = 0;
            updatePrescaler(true);
        }

        elapsedMicros++;
        notifyTimerChanged();
    }

    public void checkInterrupts() {
        tmr0Interrupt();
        rb0Interrupt();
        rbPortChangeInterrupt();
    }

    public void handlePclWrite(int address) {
        if (address == 2) {
            pc.overwrite((read(10) << 8) | read(2));
        }
    }

    public void handlePrescalerOverflow() {
        if (prescaler <= 0) {
            updatePrescaler(true);
            bank0[1]++;
        }
    }

    public void writePort(int before, int address) {
        int portIndex = 0;
        int newPortVal = read(address);
        int tris = getRegister(1, address);
        if (currentBank() == 0 && (address == 5 || address == 6)) {
            if (address == 6) {
                portIndex = 1;
            }
            dataLatch[portIndex] = newPortVal;
            newPortVal = (newPortVal & ~tris) | (before & tris);
            write(address, newPortVal);
        }
    }

    public void syncPortFromLatch(int address) {
        int portIndex = 0;
        int newPortVal = getRegister(0, address);
        int tris = getRegister(1, address);
        if (currentBank() == 1 && (address == 5 || address == 6)) {
            if (address == 6) {
                portIndex = 1;
            }
            newPortVal = (dataLatch[portIndex] & ~tris) | (newPortVal & tris);
        }
        setRegister(0, address, newPortVal);
    }

    public int getElapsedMicros() {
        return elapsedMicros;
    }

    public void reset() {
        pc.reset();
        stack.reset();
        prescaler = 0xFF;
        dataLatch[0] = 15;
        dataLatch[1] = 15;

        setRegister(0, 2, 0);
        setRegister(0, 3, getRegister(0, 3) & 0b11111);
        setRegister(0, 10, 0);
        setRegister(0, 11, getBit(0, 11, 0));

        setRegister(1, 1, 0xFF);
        setRegister(1, 2, 0);
        setRegister(1, 3, getRegister(1, 3) & 0b11111);
        setRegister(1, 5, 0x1F);
        setRegister(1, 6, 0xFF);
        setRegister(1, 8, 0);
        setRegister(1, 10, 0);
        setRegister(1, 11, getBit(1, 11, 0));

        elapsedMicros = 0;
        notifyTimerChanged();
    }

    private boolean isSpecialFunction(int register, int upperBound) {
        return register == 0 || register == 2 || register == 3 || register == 4
                || register == 10 || register == 11
                || (register >= 12 && register <= upperBound);
    }

    private void writeBothOrBank0(int register, int value, boolean bankZero) {
        if (isSpecialFunction(register, 79)) {
            bank0[register] = value;
            bank1[register] = value;
        } else if (bankZero) {
            bank0[register] = value;
        } else {
            bank1[register] = value;
        }
    }

    private void notifyTimerChanged() {
        if (timerListener != null) {
            timerListener.accept(elapsedMicros);
        }
    }

    private void tmr0Interrupt() {
        if (getBit(11, 2) == 1 && getBit(11, 7) == 1 && getBit(11, 5) == 1) {
            stack.push(pc.get());
            pc.jump(4);
            clearBit(11, 7);
        }
    }

    private void rb0Interrupt() {
        // RB0/INT triggers based on the INTEDG bit of the OPTION register
        if (getBit(11, 7) == 1 && getBit(11, 4) == 1 && getBit(11, 1) == 1) {
            stack.push(pc.get());
            pc.jump(4);
            clearBit(11, 7);
        }
    }

    private void rbPortChangeInterrupt() {
        if (getBit(11, 7) == 1 && getBit(11, 3) == 1 && getBit(11, 0) == 1) {
            stack.push(pc.get());
            pc.jump(4);
            clearBit(11, 7);
        }
    }
}
