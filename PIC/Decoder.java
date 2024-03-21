package PIC;
public class Decoder{
    public void operator (int bcode){
        int operation = bcode >> 12;
        int command = bcode >> 8;
        int argument = bcode & 0xFF;
        int adress = bcode & 0x7F;
        int destinationBit = bcode & 0x80;
    
        switch (operation){
            case 0b11:
                switch (command){
                    case 0b111110:
                        ADDLW(argument);
                        break;
                    case 0b111001:
                        ANDLW(argument);
                        break;
                    case 0b111000:
                        IORLW(argument);
                        break;
                    case 0b110000:
                        MOVLW(argument);
                        break;
                    case 0b110100:
                        RETLW(argument);
                        break;
                    case 0b111100:
                        SUBLW(argument);
                        break;
                    case 0b111010:
                        XORLW(argument);
                        break;
                }
            case 0b01:
            case 0b00:
                switch (command){
                    case 0b000111:
                        ADDWF(argument);
                        break;
                    case 0b000101:
                        ANDWF(argument);
                        break;
                    case 0b000001:
                        //CRLF oder CRLW
                        break;
                    case 0b001001:
                        COMF(argument);
                        break;
                    case 0b000011:
                        DECF(argument);
                        break;
                    case 0b001011:
                        DECFSZ(argument);
                        break;
                    case 0b001010:
                        INCF(argument);
                        break;
                    case 0b001111:
                        INCFSZ(argument);
                        break;
                    case 0b000100:
                        IORWF(argument);
                        break;
                    case 0b001000:
                        MOVF(argument);
                        break;
                    case 0b000000:
                        //MOVWF, NOP, CRLWDT, RETFIE, RETURN, oder SLEEP
                        break;
                    case 0b001101:
                        RLF(argument);
                        break;
                    case 0b001100:
                        RRF(argument);
                        break;
                    case 0b000010:
                        SUBWF(argument);
                        break;
                    case 0b001110:
                        SWAPF(argument);
                        break;
                    case 0b000110:
                        XORWF(argument);
                        break;
                }
            case 0b10:

        }
    }

//case 0b11:
    public void ADDLW(int argument) {
       
    }

    public void MOVLW(int argument){
        
    }

    public void ANDLW(int argument){
        
    }

    public void IORLW(int argument){
        
    }

    public void RETLW(int argument){
        
    }

    public void SUBLW(int argument){
        
    }

    public void XORLW(int argument){
        
    }

//case 0b00:
    public void ADDWF(int argument) {
       
    }
}