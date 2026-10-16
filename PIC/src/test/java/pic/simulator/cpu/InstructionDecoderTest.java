package pic.simulator.cpu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pic.simulator.memory.DataMemory;

class InstructionDecoderTest {

    private DataMemory memory;
    private ProgramCounter pc;
    private CallStack stack;
    private InstructionDecoder decoder;

    @BeforeEach
    void setUp() {
        pc = new ProgramCounter();
        stack = new CallStack();
        memory = new DataMemory(pc, stack);
        pc.connect(memory);
        decoder = new InstructionDecoder(memory, pc, stack);
    }

    @Test
    void movlwLoadsLiteralIntoWorkingRegisterAndAdvancesPc() {
        decoder.execute(0x3005); // MOVLW 0x05
        assertEquals(5, decoder.getWorkingRegister());
        assertEquals(1, pc.get());
    }

    @Test
    void addlwAddsLiteralToWorkingRegister() {
        decoder.execute(0x300F); // MOVLW 0x0F
        decoder.execute(0x3E01); // ADDLW 0x01
        assertEquals(0x10, decoder.getWorkingRegister());
    }

    @Test
    void movwfStoresWorkingRegisterInFileRegister() {
        decoder.execute(0x3042); // MOVLW 0x42
        decoder.execute(0x008C); // MOVWF 0x0C
        assertEquals(0x42, memory.read(12));
    }

    @Test
    void addwfStoresResultInFileRegisterWhenDestinationBitSet() {
        decoder.execute(0x3005); // MOVLW 0x05
        memory.setRegister(0, 12, 3);
        decoder.execute(0x078C); // ADDWF 0x0C, 1
        assertEquals(8, memory.read(12));
    }

    @Test
    void gotoJumpsToAbsoluteAddress() {
        decoder.execute(0x2804); // GOTO 0x004
        assertEquals(4, pc.get());
    }

    @Test
    void callPushesReturnAddressAndJumps() {
        decoder.execute(0x2004); // CALL 0x004
        assertEquals(4, pc.get());
        assertEquals(1, stack.getSize());
        assertEquals(1, stack.top());
    }

    @Test
    void returnPopsReturnAddress() {
        decoder.execute(0x2004); // CALL 0x004
        decoder.execute(0x0008); // RETURN
        assertEquals(1, pc.get());
        assertEquals(0, stack.getSize());
    }

    @Test
    void btfscSkipsNextInstructionWhenBitIsClear() {
        memory.setRegister(0, 12, 0b0000_0000);
        decoder.execute(0x180C); // BTFSC 0x0C, 0
        assertEquals(2, pc.get());
    }

    @Test
    void btfssSkipsNextInstructionWhenBitIsSet() {
        memory.setRegister(0, 12, 0b0000_0001);
        decoder.execute(0x1C0C); // BTFSS 0x0C, 0
        assertEquals(2, pc.get());
    }

    @Test
    void bsfSetsBitWithoutAdvancingTwice() {
        decoder.execute(0x140C); // BSF 0x0C, 0
        assertEquals(1, memory.read(12) & 0b1);
        assertEquals(1, pc.get());
    }
}
