package pic.simulator.model;

import java.io.File;
import java.io.IOException;
import java.util.function.IntConsumer;

import pic.simulator.cpu.CallStack;
import pic.simulator.cpu.InstructionDecoder;
import pic.simulator.cpu.ProgramCounter;
import pic.simulator.io.ProgramLoader;
import pic.simulator.io.ProgramLoader.Program;
import pic.simulator.memory.DataMemory;

/**
 * Central simulation model. Owns the CPU components (program counter, call
 * stack, data memory and instruction decoder) as well as the program memory
 * (ROM). The UI only talks to this class and never touches CPU internals
 * directly.
 */
public class Simulator {

    private final ProgramCounter pc;
    private final CallStack stack;
    private final DataMemory memory;
    private final InstructionDecoder decoder;

    private int[] programMemory = new int[ProgramLoader.PROGRAM_SIZE];
    private boolean programLoaded = false;

    public Simulator() {
        pc = new ProgramCounter();
        stack = new CallStack();
        memory = new DataMemory(pc, stack);
        pc.connect(memory);
        decoder = new InstructionDecoder(memory, pc, stack);
    }

    public void loadProgram(File file) throws IOException {
        Program program = ProgramLoader.load(file);
        this.programMemory = program.getOpcodes();
        this.programLoaded = true;
    }

    public void loadProgram(Program program) {
        this.programMemory = program.getOpcodes();
        this.programLoaded = true;
    }

    /** Executes the instruction currently pointed to by the program counter. */
    public void step() {
        decoder.execute(programMemory[pc.get()]);
    }

    public void reset() {
        memory.reset();
    }

    public boolean isProgramLoaded() {
        return programLoaded;
    }

    public int getProgramCounter() {
        return pc.get();
    }

    public int getWorkingRegister() {
        return decoder.getWorkingRegister();
    }

    public int getElapsedMicros() {
        return memory.getElapsedMicros();
    }

    public DataMemory getMemory() {
        return memory;
    }

    public CallStack getStack() {
        return stack;
    }

    public void setTimerListener(IntConsumer timerListener) {
        memory.setTimerListener(timerListener);
    }
}
