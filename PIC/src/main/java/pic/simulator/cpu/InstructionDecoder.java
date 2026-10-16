package pic.simulator.cpu;

import pic.simulator.memory.DataMemory;

/**
 * Decodes and executes a single 14 bit instruction of the PIC 16F84.
 * Instruction mnemonics are kept as method names on purpose: they map 1:1 to
 * the datasheet and make the emulation easy to compare with it.
 */
public class InstructionDecoder {

    private final DataMemory memory;
    private final ProgramCounter pc;
    private final CallStack stack;

    // Working register
    private int w = 0;
    private int fAdr = 0;

    public InstructionDecoder(DataMemory memory, ProgramCounter pc, CallStack stack) {
        this.memory = memory;
        this.pc = pc;
        this.stack = stack;
    }

    public int getWorkingRegister() {
        return w;
    }

    private void markIndirect(int address) {
        if (address == 0) {
            fAdr = memory.read(4);
            memory.setIndirect(true);
        }
    }

    public void execute(int bcode) {
        fAdr = bcode & 0x7F;
        int before = memory.read(fAdr);

        switch (bcode >> 12) {
            // Literal operations
            case 0b11:

                int k = bcode & 0xFF;

                switch (bcode >> 8) {
                    case 0b111110:
                        ADDLW(k);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        pc.next();
                        break;
                    case 0b111001:
                        ANDLW(k);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        pc.next();
                        break;
                    case 0b111000:
                        IORLW(k);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        pc.next();
                        break;
                    case 0b110000:
                        MOVLW(k);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        pc.next();
                        break;
                    case 0b110100:
                        RETLW(k);
                        memory.tickTimer();
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                    case 0b111100:
                        SUBLW(k);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        pc.next();
                        break;
                    case 0b111010:
                        XORLW(k);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        pc.next();
                        break;
                }
                break;

            // Bit-oriented operations
            case 0b01:

                int bit = (bcode & 0x3FF) >> 7;

                switch (bcode >> 10) {
                    case 0b0100:
                        markIndirect(fAdr);
                        BCF(bit, fAdr);
                        memory.writePort(before, fAdr);
                        memory.syncPortFromLatch(fAdr);
                        memory.handlePclWrite(fAdr);
                        pc.next();
                        memory.handleTimerWrite(fAdr);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                    case 0b0101:
                        markIndirect(fAdr);
                        BSF(bit, fAdr);
                        memory.writePort(before, fAdr);
                        memory.syncPortFromLatch(fAdr);
                        memory.handlePclWrite(fAdr);
                        pc.next();
                        memory.handleTimerWrite(fAdr);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                    case 0b0110:
                        markIndirect(fAdr);
                        BTFSC(bit, fAdr);
                        pc.next();
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                    case 0b0111:
                        markIndirect(fAdr);
                        BTFSS(bit, fAdr);
                        pc.next();
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                }

            // Byte-oriented operations
            case 0b00:

                int dBit = bcode & 0x80;

                switch (bcode >> 8) {
                    case 0b000111:
                        markIndirect(fAdr);
                        ADDWF(dBit, fAdr);
                        memory.writePort(before, fAdr);
                        memory.syncPortFromLatch(fAdr);
                        memory.handlePclWrite(fAdr);
                        pc.next();
                        memory.handleTimerWrite(fAdr);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                    case 0b000101:
                        markIndirect(fAdr);
                        ANDWF(dBit, fAdr);
                        memory.writePort(before, fAdr);
                        memory.syncPortFromLatch(fAdr);
                        memory.handlePclWrite(fAdr);
                        pc.next();
                        memory.handleTimerWrite(fAdr);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                    case 0b000001:      // CLRW or CLRF
                        switch (bcode & 0xFF) {
                            case 0b00000000:
                                markIndirect(fAdr);
                                CLRW(dBit, fAdr);
                                pc.next();
                                memory.tickTimer();
                                memory.checkInterrupts();
                                break;
                            default:
                                markIndirect(fAdr);
                                CLRF(dBit, fAdr);
                                memory.writePort(before, fAdr);
                                memory.syncPortFromLatch(fAdr);
                                memory.handlePclWrite(fAdr);
                                pc.next();
                                memory.handleTimerWrite(fAdr);
                                memory.tickTimer();
                                memory.checkInterrupts();
                        }
                        break;
                    case 0b001001:
                        markIndirect(fAdr);
                        COMF(dBit, fAdr);
                        memory.writePort(before, fAdr);
                        memory.syncPortFromLatch(fAdr);
                        memory.handlePclWrite(fAdr);
                        pc.next();
                        memory.handleTimerWrite(fAdr);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                    case 0b000011:
                        markIndirect(fAdr);
                        DECF(dBit, fAdr);
                        memory.writePort(before, fAdr);
                        memory.syncPortFromLatch(fAdr);
                        memory.handlePclWrite(fAdr);
                        pc.next();
                        memory.handleTimerWrite(fAdr);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                    case 0b001011:
                        markIndirect(fAdr);
                        DECFSZ(dBit, fAdr);
                        memory.writePort(before, fAdr);
                        memory.syncPortFromLatch(fAdr);
                        memory.handlePclWrite(fAdr);
                        pc.next();
                        memory.handleTimerWrite(fAdr);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                    case 0b001010:
                        markIndirect(fAdr);
                        INCF(dBit, fAdr);
                        memory.writePort(before, fAdr);
                        memory.syncPortFromLatch(fAdr);
                        memory.handlePclWrite(fAdr);
                        pc.next();
                        memory.handleTimerWrite(fAdr);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                    case 0b001111:
                        markIndirect(fAdr);
                        INCFSZ(dBit, fAdr);
                        memory.writePort(before, fAdr);
                        memory.syncPortFromLatch(fAdr);
                        memory.handlePclWrite(fAdr);
                        pc.next();
                        memory.handleTimerWrite(fAdr);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                    case 0b000100:
                        markIndirect(fAdr);
                        IORWF(dBit, fAdr);
                        memory.writePort(before, fAdr);
                        memory.syncPortFromLatch(fAdr);
                        memory.handlePclWrite(fAdr);
                        pc.next();
                        memory.handleTimerWrite(fAdr);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                    case 0b001000:
                        markIndirect(fAdr);
                        MOVF(dBit, fAdr);
                        if (bcode == 0b10000001) {
                            memory.writePort(before, fAdr);
                            memory.syncPortFromLatch(fAdr);
                            memory.handlePclWrite(fAdr);
                            memory.handleTimerWrite(fAdr);
                        }
                        pc.next();
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                    case 0b000000:      // MOVWF, NOP, CLRWDT, RETFIE, RETURN or SLEEP
                        switch (bcode & 0xFF) {
                            case 0b00000000:
                                NOP(dBit, fAdr);
                                pc.next();
                                memory.tickTimer();
                                memory.checkInterrupts();
                                break;
                            case 0b01100100:
                                markIndirect(fAdr);
                                CLRWDT(dBit, fAdr);
                                pc.next();
                                memory.tickTimer();
                                memory.checkInterrupts();
                                break;
                            case 0b00001001:
                                markIndirect(fAdr);
                                RETFIE(dBit, fAdr);
                                memory.tickTimer();
                                memory.tickTimer();
                                memory.checkInterrupts();
                                break;
                            case 0b00001000:
                                markIndirect(fAdr);
                                RETURN(dBit, fAdr);
                                memory.tickTimer();
                                memory.tickTimer();
                                memory.checkInterrupts();
                                break;
                            case 0b01100011:
                                markIndirect(fAdr);
                                SLEEP(dBit, fAdr);
                                memory.tickTimer();
                                memory.checkInterrupts();
                                pc.next();
                                break;
                            default:
                                markIndirect(fAdr);
                                MOVWF(dBit, fAdr);
                                memory.writePort(before, fAdr);
                                memory.syncPortFromLatch(fAdr);
                                memory.handlePclWrite(fAdr);
                                pc.next();
                                memory.handleTimerWrite(fAdr);
                                memory.tickTimer();
                                memory.checkInterrupts();
                                break;
                        }
                        break;
                    case 0b001101:
                        markIndirect(fAdr);
                        RLF(dBit, fAdr);
                        memory.writePort(before, fAdr);
                        memory.syncPortFromLatch(fAdr);
                        memory.handlePclWrite(fAdr);
                        pc.next();
                        memory.handleTimerWrite(fAdr);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                    case 0b001100:
                        markIndirect(fAdr);
                        RRF(dBit, fAdr);
                        memory.writePort(before, fAdr);
                        memory.syncPortFromLatch(fAdr);
                        memory.handlePclWrite(fAdr);
                        pc.next();
                        memory.handleTimerWrite(fAdr);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                    case 0b000010:
                        markIndirect(fAdr);
                        SUBWF(dBit, fAdr);
                        pc.next();
                        memory.tickTimer();
                        break;
                    case 0b001110:
                        markIndirect(fAdr);
                        SWAPF(dBit, fAdr);
                        pc.next();
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                    case 0b000110:
                        markIndirect(fAdr);
                        XORWF(dBit, fAdr);
                        memory.writePort(before, fAdr);
                        memory.syncPortFromLatch(fAdr);
                        memory.handlePclWrite(fAdr);
                        pc.next();
                        memory.handleTimerWrite(fAdr);
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                }

            // Control operations
            case 0b10:

                k = bcode & 0x7FF;

                switch (bcode >> 11) {
                    case 0b100:
                        CALL(k);
                        memory.tickTimer();
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                    case 0b101:
                        GOTO(k);
                        memory.tickTimer();
                        memory.tickTimer();
                        memory.checkInterrupts();
                        break;
                }
                break;
        }
    }

    // Literal operations

    public void ADDLW(int k) {
        w += k;
        w %= 256;
        memory.updateCarryFlag(w, k);
        memory.updateDigitCarryFlag(w, k);
        memory.updateZeroFlag(w);
    }

    public void ANDLW(int k) {
        w &= k;
        w %= 256;
        memory.updateZeroFlag(w);
    }

    public void IORLW(int k) {
        w |= k;
        w %= 256;
        memory.updateZeroFlag(w);
    }

    public void MOVLW(int k) {
        w = k;
        w %= 256;
    }

    public void RETLW(int k) {
        w = k;
        pc.jump(stack.pop());
    }

    public void SUBLW(int k) {
        w ^= 0xFF;
        w += 1;
        w += k;
        w %= 256;

        if (w >= 0) {
            memory.setBit(3, 0);
        } else {
            memory.clearBit(3, 0);
        }

        int dck = k & 0xF;
        int dcw = w & 0xF;
        int dc = dck - dcw;
        if (dc >= 0) {
            memory.setBit(3, 1);
        } else {
            memory.clearBit(3, 1);
        }

        memory.updateZeroFlag(w);
    }

    public void XORLW(int k) {
        w ^= k;
        w %= 256;
        memory.updateZeroFlag(w);
    }

    // Bit-oriented operations

    public void BCF(int bit, int fAdr) {
        memory.clearBit(fAdr, bit);
    }

    public void BSF(int bit, int fAdr) {
        memory.setBit(fAdr, bit);
    }

    public void BTFSC(int bit, int fAdr) {
        int save = memory.read(fAdr);
        int mask = 0b1 << bit;
        save = save & mask;

        if (save == 0) {
            pc.next();
        }
    }

    public void BTFSS(int bit, int fAdr) {
        int save = memory.read(fAdr);
        int mask = 0b1 << bit;
        save = save & mask;

        if (save != 0) {
            pc.next();
        }
    }

    // Control operations

    public void CALL(int k) {
        stack.push(pc.next());
        int jumpaddress = ((memory.read(10) >> 3) << 11) | k;
        pc.jump(jumpaddress);
    }

    public void GOTO(int k) {
        int jumpaddress = ((memory.read(10) >> 3) << 11) | k;
        pc.jump(jumpaddress);
    }

    // Byte-oriented operations

    public void ADDWF(int dBit, int fAdr) {
        int save = memory.read(fAdr);

        memory.updateCarryFlag(w, save);
        memory.updateDigitCarryFlag(w, save);

        int result = w + save;
        result %= 256;
        if (dBit == 0) {
            w = result;
        } else {
            memory.write(fAdr, result);
        }
        memory.updateZeroFlag(w);
    }

    public void ANDWF(int dBit, int fAdr) {
        int save = memory.read(fAdr);
        if (dBit == 0) {
            w = w & save;
            memory.updateZeroFlag(w);
        } else {
            memory.write(fAdr, w & save);
            memory.updateZeroFlag(memory.read(fAdr));
        }
    }

    public void COMF(int dBit, int fAdr) {
        int save = memory.read(fAdr);
        int mask = 0xFF;

        if (dBit == 0) {
            w = mask ^ save;
            memory.updateZeroFlag(w);
        } else {
            memory.write(fAdr, mask ^ save);
            memory.updateZeroFlag(memory.read(fAdr));
        }
    }

    public void DECF(int dBit, int fAdr) {
        int save = memory.read(fAdr);
        save--;
        if (save < 0) {
            save += 256;
        }

        if (dBit == 0) {
            w = save;
            memory.updateZeroFlag(w);
        } else {
            memory.write(fAdr, save);
            memory.updateZeroFlag(memory.read(fAdr));
        }
    }

    public void DECFSZ(int dBit, int fAdr) {
        int save = memory.read(fAdr);
        save--;
        if (save < 0) {
            save += 256;
        } else if (save == 0) {
            pc.next();
        }

        if (dBit == 0) {
            w = save;
        } else {
            memory.write(fAdr, save);
        }
    }

    public void INCF(int dBit, int fAdr) {
        int save = memory.read(fAdr);
        save++;
        save %= 256;

        if (dBit == 0) {
            w = save;
            memory.updateZeroFlag(w);
        } else {
            memory.write(fAdr, save);
            memory.updateZeroFlag(memory.read(fAdr));
        }
    }

    public void INCFSZ(int dBit, int fAdr) {
        int save = memory.read(fAdr);
        save++;
        save %= 256;
        if (save == 0) {
            pc.next();
        }

        if (dBit == 0) {
            w = save;
        } else {
            memory.write(fAdr, save);
        }
    }

    public void IORWF(int dBit, int fAdr) {
        int save = memory.read(fAdr);

        if (dBit == 0) {
            w = w | save;
            memory.updateZeroFlag(w);
        } else {
            memory.write(fAdr, w | save);
            memory.updateZeroFlag(memory.read(fAdr));
        }
    }

    public void MOVF(int dBit, int fAdr) {
        int save = memory.read(fAdr);

        if (dBit == 0) {
            w = save;
        } else {
            memory.write(fAdr, save);
        }
        memory.updateZeroFlag(memory.read(fAdr));
    }

    public void RLF(int dBit, int fAdr) {
        int save = memory.read(fAdr);
        int mask1 = 0x80;
        int storage = mask1 & save;
        int storage1 = memory.getBit(3, 0);

        if (storage == 0) {
            memory.clearBit(3, 0);
        } else {
            memory.setBit(3, 0);
        }
        save = save << 1;
        save &= 0xFF;
        save |= storage1;

        if (dBit == 0) {
            w = save;
        } else {
            memory.write(fAdr, save);
        }
    }

    public void RRF(int dBit, int fAdr) {
        int save = memory.read(fAdr);
        int mask1 = 0x01;
        int storage = mask1 & save;
        int storage1 = (memory.getBit(3, 0)) << 7;

        if (storage == 0) {
            memory.clearBit(3, 0);
        } else {
            memory.setBit(3, 0);
        }

        save = save >>> 1;
        save |= storage1;

        if (dBit == 0) {
            w = save;
        } else {
            memory.write(fAdr, save);
        }
    }

    public void SUBWF(int dBit, int fAdr) {
        int save = memory.read(fAdr);

        int result = save - w;

        if (result >= 0) {
            memory.setBit(3, 0);
        } else {
            memory.clearBit(3, 0);
        }

        int dcsave = save & 0xF;
        int dcw = w & 0xF;
        int dc = dcsave - dcw;

        if (dc >= 0) {
            memory.setBit(3, 1);
        } else {
            memory.clearBit(3, 1);
        }

        memory.updateZeroFlag(result);

        if (result < 0) {
            result += 256;
        }

        if (dBit == 0) {
            w = result;
        } else {
            memory.write(fAdr, result);
        }
    }

    public void SWAPF(int dBit, int fAdr) {
        int save = memory.read(fAdr);
        int mask1 = 0xF0;
        int mask2 = 0x0F;

        int storage1 = (mask1 & save) >>> 4;
        int storage2 = (mask2 & save) << 4;

        if (dBit == 0) {
            w = storage2 ^ storage1;
        } else {
            memory.write(fAdr, storage2 ^ storage1);
        }
    }

    public void XORWF(int dBit, int fAdr) {
        int save = memory.read(fAdr);

        if (dBit == 0) {
            w = w ^ save;
            memory.updateZeroFlag(w);
        } else {
            memory.write(fAdr, w ^ save);
            memory.updateZeroFlag(memory.read(fAdr));
        }
    }

    public void CLRF(int dBit, int fAdr) {
        memory.write(fAdr, 0);
        memory.updateZeroFlag(memory.read(fAdr));
    }

    public void CLRW(int dBit, int fAdr) {
        w = 0;
        memory.updateZeroFlag(w);
    }

    public void CLRWDT(int dBit, int fAdr) {
        // Watchdog timer is not emulated
    }

    public void RETFIE(int dBit, int fAdr) {
        pc.jump(stack.pop());
        memory.setBit(11, 7);
    }

    public void RETURN(int dBit, int fAdr) {
        pc.jump(stack.pop());
    }

    public void SLEEP(int dBit, int fAdr) {
        // Sleep mode is not emulated
    }

    public void MOVWF(int dBit, int fAdr) {
        memory.write(fAdr, w);
    }

    public void NOP(int dBit, int fAdr) {
        // No operation
    }
}
