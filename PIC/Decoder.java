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
}