package pic.simulator.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads a compiled PIC listing file (".lst"). Each non-indented line starts with
 * a 4 digit hex address followed by the 4 digit hex opcode, e.g. "0000 0C05".
 * Indented lines (e.g. source comments or line numbers) are kept for display but
 * do not contribute an opcode.
 */
public class ProgramLoader {

    public static final int PROGRAM_SIZE = 1024;

    /** Result of parsing a listing file. */
    public static final class Program {
        private final int[] opcodes;
        private final List<String> lines;

        Program(int[] opcodes, List<String> lines) {
            this.opcodes = opcodes;
            this.lines = lines;
        }

        public int[] getOpcodes() {
            return opcodes;
        }

        public List<String> getLines() {
            return lines;
        }
    }

    private ProgramLoader() {
    }

    public static Program load(File file) throws IOException {
        int[] opcodes = new int[PROGRAM_SIZE];
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                if (line.startsWith(" ") || line.length() < 9) {
                    continue;
                }
                int address = Integer.parseInt(line.substring(0, 4), 16);
                int opcode = Integer.parseInt(line.substring(5, 9), 16);
                opcodes[address] = opcode;
            }
        }

        return new Program(opcodes, lines);
    }
}
