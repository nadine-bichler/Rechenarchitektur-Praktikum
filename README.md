# PIC 16F84 Simulator

**Project Note:** This project was created in the summer of 2024 as part of the Computer Architecture module at the beginning of our studies. From today's perspective, we would definitely design the software architecture differently—especially regarding clean code principles, avoiding excessive static methods/variables, and implementing a stricter separation between the backend and GUI (MVC pattern). This refactoring turned those lessons into practice: the code base now uses a proper package structure, an instance-based CPU model, an MVC separation and a test suite. Nonetheless, this project remains an important milestone for us: it significantly sharpened our understanding of hardware-related processes and was a great learning accelerator for agile teamwork and problem-solving.

---

## Project Description
The goal of this project was to develop a realistic software simulator for the PIC 16F84 microcontroller. The simulator emulates the core components of the chip in a pure software environment and makes it possible to visually trace the operation and data management of the microcontroller.

### Key Features:
- **Processor Emulation:** Replication of the instruction set (including commands such as `BTFSC`, `CALL`, `MOVF`, `RRF`, `SUBWF`, `DECFSZ`, `XORLW`).
- **Memory & Register Management:** Correct mapping of Bank 0 and Bank 1, including the hardware-level logic of the `TRIS` register (input/output control via data latch).
- **Interrupt Handling:** Complete implementation of interrupt conditions (e.g., `TMR0Interrupt`, `RB0Interrupt`, and `RBPortChangeInterrupt`), taking into account the GIE bit and the stack for return address safeguarding.
- **Interactive Debugger:** Execution of test programs in single-step mode (`Step`) or continuous mode (`Run`) with the ability to dynamically set breakpoints in the code.
- **Runtime Counter:** Integrated timer to measure program execution time in microseconds (µs).

---

## Tech Stack
- **Programming Language:** Java 17
- **GUI Framework:** Java Swing
- **Build Tool:** Maven
- **Testing:** JUnit 5
- **Development Basis:** Official datasheets of the "PIC 16F84 Data Sheet"

---

## Architecture & Program Structure
The code is organized into packages that separate model, CPU and view. The UI (`SimulatorGui`) only talks to the `Simulator` model and never reaches into CPU internals directly.

```
src/main/java/pic/simulator/
├── App.java                       # entry point, launches the Swing UI
├── model/
│   └── Simulator.java             # central model: owns CPU, memory and ROM
├── cpu/
│   ├── ProgramCounter.java        # 13-bit program counter, keeps PCL in sync
│   ├── CallStack.java             # 8 entry hardware call stack
│   └── InstructionDecoder.java    # decodes and executes 14-bit instructions
├── memory/
│   └── DataMemory.java            # banks, SFRs, flags, timer, ports, interrupts
├── io/
│   └── ProgramLoader.java         # reads .lst listing files into ROM
└── ui/
    └── SimulatorGui.java          # Swing user interface (view)
```

Class responsibilities:
- `InstructionDecoder`: Parses and executes binary instruction codes (one instruction per call).
- `DataMemory`: Manages registers, banks, flags (Zero, Digit Carry and Carry), timer/prescaler, ports and interrupts.
- `ProgramCounter` & `CallStack`: Control the program counter and return addresses for subroutines (`CALL`) or interrupts.
- `ProgramLoader`: Reads and loads the assembler test programs (`.lst` files).
- `SimulatorGui`: Interactive user interface for visualizing register contents (binary/hexadecimal), pins and the runtime counter.

---

## Build & Run

This project is structured as a Maven project. To build and test it via the terminal, navigate to the directory containing the `pom.xml`:

1. Navigate to the project directory:
   ```bash
   cd PIC
   ```
2. Clean, compile and run the tests:
   ```bash
   mvn clean test
   ```
3. Create the runnable package (JAR):
   ```bash
   mvn clean package
   ```
4. Run:
   ```bash
   java -jar target/pic16f84-simulator-1.0.0.jar
   ```

---

## Authors
This project was created jointly in June 2024 by:
- Thuy Linh Trinh
- Nadine Bichler

*Developed at Offenburg University in the AI degree program (subject: Computer Architecture).*
