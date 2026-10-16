package pic.simulator.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pic.simulator.cpu.CallStack;
import pic.simulator.cpu.ProgramCounter;

class DataMemoryTest {

    private ProgramCounter pc;
    private CallStack stack;
    private DataMemory memory;

    @BeforeEach
    void setUp() {
        pc = new ProgramCounter();
        stack = new CallStack();
        memory = new DataMemory(pc, stack);
        pc.connect(memory);
    }

    @Test
    void statusRegisterIsInitialised() {
        assertEquals(0x18, memory.getRegister(0, 3));
    }

    @Test
    void trisRegistersAreInitialised() {
        assertEquals(0x1F, memory.getRegister(1, 5));
        assertEquals(0xFF, memory.getRegister(1, 6));
    }

    @Test
    void zeroFlagIsSetWhenResultIsZero() {
        memory.updateZeroFlag(0);
        assertEquals(1, memory.getBit(3, 2));
    }

    @Test
    void zeroFlagIsClearedWhenResultIsNotZero() {
        memory.setBit(3, 2);
        memory.updateZeroFlag(5);
        assertEquals(0, memory.getBit(3, 2));
    }

    @Test
    void carryFlagIsSetOnOverflow() {
        memory.updateCarryFlag(255, 1);
        assertEquals(1, memory.getBit(3, 0));
    }

    @Test
    void digitCarryFlagIsSetOnNibbleOverflow() {
        memory.updateDigitCarryFlag(0x0F, 1);
        assertEquals(1, memory.getBit(3, 1));
    }

    @Test
    void timer0InterruptJumpsToVectorAndClearsFlag() {
        memory.setBit(11, 7);   // GIE
        memory.setBit(11, 5);   // TMR0IE
        memory.setBit(11, 2);   // TMR0IF

        memory.checkInterrupts();

        assertEquals(4, pc.get());
        assertEquals(1, stack.getSize());
        assertEquals(0, memory.getBit(11, 7));
    }

    @Test
    void noInterruptWithoutGlobalEnable() {
        memory.setBit(11, 5);
        memory.setBit(11, 2);

        memory.checkInterrupts();

        assertEquals(0, pc.get());
        assertEquals(0, stack.getSize());
    }

    @Test
    void resetRestoresInitialState() {
        memory.setRegister(0, 10, 0x55);
        memory.reset();

        assertEquals(0, memory.getRegister(0, 10));
        assertEquals(0, memory.getElapsedMicros());
        assertEquals(0x1F, memory.getRegister(1, 5));
        assertEquals(0, pc.get());
    }
}
