package PIC;
public class Decoder{
    public void operator (int bcode){

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
            case 0b01:
            case 0b00:
            case 0b10:

        }
    }

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
}