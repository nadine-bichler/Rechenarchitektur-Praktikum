package PIC;
public class Decoder{

    //Working Register
    int W = 0;;

    public void execute (int bcode){
        //Bitoperation
        int operation = bcode >> 12;

        //Bitbefehl
        int command = bcode >> 8;

        //Bitargument
        int argument = bcode & 0xFF;
        int fadress = bcode & 0x7F;
        int dBit = bcode & 0x80;
    
        switch (operation){
            //Literal operations
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
            //Bit-oriented Operations
            case 0b01:
                switch(command >> 2){
                    case 0b0100:
                        BCF(argument);
                        break;
                    case 0b0101:
                        BSF(argument);
                        break;
                    case 0b0110:
                        BTFSC(argument);
                        break;
                    case 0b0111:
                        BTFSS(argument);
                        break;
                }
            //Byte-oriented operations
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
            //Control operations
            case 0b10:
                switch(command >> 3){
                    case 0b100:
                        CALL();
                        break;
                    case 0b101:
                        GOTO();
                        break;
                }

        }
    }

//case 0b11:
    //Literal operations
    public void ADDLW(int argument) {
       W += argument;
    }

    public void ANDLW(int argument){
        W &= argument;
    }

    public void IORLW(int argument){
        W |= argument;
    }

    public void MOVLW(int argument){
        W = argument;
    }

    //später
    public void RETLW(int argument){
        
    }

    public void SUBLW(int argument){
        W -= argument;
    }

    public void XORLW(int argument){
        W ^= argument;
    }


    //bit-oriented operations
    public void BCF(int argument){

    }

    public void BSF(int argument){

    }

    public void BTFSC(int argument){

    }

    public void BTFSS(int argument){

    }

    //control operations
    public void CALL(){

    }

    public void GOTO(){

    }

//case 0b00:
    public void ADDWF(int argument) {
       
    }
}