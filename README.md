# PIC 16F84 Simulator

**Project Note:** This project was created in the summer of 2024 as part of the Computer Architecture module at the beginning of our studies. From today's perspective, we would definitely design the software architecture differently—especially regarding clean code principles, avoiding excessive static methods/variables, and implementing a stricter separation between the backend and GUI (MVC pattern). Nonetheless, this project remains an important milestone for us: it significantly sharpened our understanding of hardware-related processes and was a great learning accelerator for agile teamwork and problem-solving.

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
- **Programming Language:** Java 
- **GUI Framework:** Java Swing 
- **Development Basis:** Official datasheets of the "PIC 16F84 Data Sheet" 

---

## Architecture & Program Structure
The class structure was closely modeled after the real hardware components of the microcontroller:
- `Decoder`: Handles parsing and decoding of binary instruction codes.
- `Datenspeicher`: Manages registers, banks, and flags (Zero, Digit Carry, and Carry flags).
- `PC` & `Stack`: Control the program counter and return addresses for subroutines (`CALL`) or interrupts.
- `ReadFile`: Responsible for reading and loading the assembler test programs.
- `GUI`: The interactive user interface for visualizing register contents (binary/hexadecimal) and pins.

---

## Project Insights & Key Takeaways
From the subsequent project reflection, we drew important lessons for future software projects:
1. **Architectural Planning:** An early separation of backend and UI saves time-consuming refactoring later on.
2. **OOP Best Practices:** Excessive use of static methods limits flexibility—upon revision, we would opt for a more object-oriented design.
3. **Team Communication:** Structured coordination and uniform development environments from day one are essential for project success.

---

## Build & Run

This project is structured as a Maven project. To build and test it via the terminal:

1. Navigate to the project directory (where the `pom.xml` is located):
   ```bash
   cd PIC
2. Clean and compile the project:
   ```bash
   mvn clean compile
3. Create the package (JAR):
   ```bash
   mvn clean package
4. Run:
   ```bash
   java -cp target/classes ReadFile

---

## Authors
This project was created jointly in June 2024 by:
- Thuy Linh Trinh
- Nadine Bichler

*Developed at Offenburg University in the AI degree program (subject: Computer Architecture) under the supervision of Prof. Stefan Lehmann.*
