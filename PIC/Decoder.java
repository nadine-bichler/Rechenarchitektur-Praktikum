package PIC;
public class Decoder{

    //Working Register
    int W = 0;

    public void execute (int bcode){

        int fAdr = bcode & 0x7F;
    
        switch (bcode >> 12){
            //Literal operations
            case 0b11:

            int k = bcode & 0xFF;

                switch (bcode >> 8){
                    case 0b111110:
                        ADDLW(k);
                        break;
                    case 0b111001:
                        ANDLW(k);
                        break;
                    case 0b111000:
                        IORLW(k);
                        break;
                    case 0b110000:
                        MOVLW(k);
                        break;
                    case 0b110100:
                        RETLW(k);
                        break;
                    case 0b111100:
                        SUBLW(k);
                        break;
                    case 0b111010:
                        XORLW(k);
                        break;
                }
            //Bit-oriented Operations
            case 0b01:

            int bit = (bcode & 0x3FF) >> 7;
            

                switch(bcode >> 10){
                    case 0b0100:
                        BCF(bit, fAdr);
                        break;
                    case 0b0101:
                        BSF(bit, fAdr);
                        break;
                    case 0b0110:
                        BTFSC(bit, fAdr);
                        break;
                    case 0b0111:
                        BTFSS(bit, fAdr);
                        break;
                }
            //Byte-oriented operations
            case 0b00:

            int dBit = bcode & 0x80;

                switch (bcode >> 8){
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
                switch(bcode >> 11){
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
    public void ADDLW(int k) {
       W += k;
    }

    public void ANDLW(int k){
        W &= k;
    }

    public void IORLW(int k){
        W |= k;
    }

    public void MOVLW(int k){
        W = k;
    }

    //später
    public void RETLW(int k){
        
    }

    public void SUBLW(int k){
        W -= k;
    }

    public void XORLW(int k){
        W ^= k;
    }


    //bit-oriented operations
    public void BCF(int bit, int fAdr){

    }

    public void BSF(int bit, int fAdr){

    }

    public void BTFSC(int bit, int fAdr){

    }

    public void BTFSS(int bit, int fAdr){

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