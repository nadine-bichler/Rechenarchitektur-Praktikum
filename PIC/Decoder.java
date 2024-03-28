package PIC;
public class Decoder{

    //Working Register
    int W = 0;

    Flags flags = new Flags();
    Datenspeicher datenspeicher = new Datenspeicher();

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
                        ADDWF(dBit, fAdr);
                        break;
                    case 0b000101:
                        ANDWF(dBit, fAdr);
                        break;
                    case 0b000001:
                        //CRLF oder CRLW
                        break;
                    case 0b001001:
                        COMF(dBit, fAdr);
                        break;
                    case 0b000011:
                        DECF(dBit, fAdr);
                        break;
                    case 0b001011:
                        DECFSZ(dBit, fAdr);
                        break;
                    case 0b001010:
                        INCF(dBit, fAdr);
                        break;
                    case 0b001111:
                        INCFSZ(dBit, fAdr);
                        break;
                    case 0b000100:
                        IORWF(dBit, fAdr);
                        break;
                    case 0b001000:
                        MOVF(dBit, fAdr);
                        break;
                    case 0b000000:
                        //MOVWF, NOP, CRLWDT, RETFIE, RETURN, oder SLEEP
                        break;
                    case 0b001101:
                        RLF(dBit, fAdr);
                        break;
                    case 0b001100:
                        RRF(dBit, fAdr);
                        break;
                    case 0b000010:
                        SUBWF(dBit, fAdr);
                        break;
                    case 0b001110:
                        SWAPF(dBit, fAdr);
                        break;
                    case 0b000110:
                        XORWF(dBit, fAdr);
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
        int X = W;
        W += k;
        flags.C(X, W);
        flags.DC(X, W);
        flags.Z(W);
    }

    public void ANDLW(int k){
        W &= k;
        flags.Z(W);
    }

    public void IORLW(int k){
        W |= k;
        flags.Z(W);
    }

    public void MOVLW(int k){
        W = k;
    }

    //später
    public void RETLW(int k){
        
    }

    public void SUBLW(int k){
        int X = W;
        W -= k;
        if (W < 0){
            W *= (-1);
            flags.CFlag = 1;
        }else if(W == 0){
            flags.CFlag = 1;
        } else {
            flags.CFlag = 0;
        }
        flags.DC(X, W);
        flags.Z(W);
        
    }

    public void XORLW(int k){
        W ^= k;
        flags.Z(W);
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
    public void ADDWF(int dBit, int fAdr) {
        if(dBit == 0){
            W = W + datenspeicher.PullDataFromRegister(fAdr);
        }
        else{
            datenspeicher.PushDataToBank(fAdr, W + datenspeicher.PullDataFromRegister(fAdr));
        }
    }

    public void ANDWF(int dBit, int fAdr) {
       if(dBit == 0){
            W = W & datenspeicher.PullDataFromRegister(fAdr);
       }
       else{
        datenspeicher.PushDataToBank(fAdr, W & datenspeicher.PullDataFromRegister(fAdr));
       }
    }
    public void COMF(int dBit, int fAdr) {
       
    }
    public void DECF(int dBit, int fAdr) {
       
    }
    public void DECFSZ(int dBit, int fAdr) {
       
    }
    public void INCF(int dBit, int fAdr) {
       
    }
    public void INCFSZ(int dBit, int fAdr) {
       
    }
    public void IORWF(int dBit, int fAdr) {
       
    }
    public void MOVF(int dBit, int fAdr) {
       
    }
    public void RLF(int dBit, int fAdr) {
       
    }
    public void RRF(int dBit, int fAdr) {
       
    }
    public void SUBWF(int dBit, int fAdr) {
       
    }
    public void SWAPF(int dBit, int fAdr) {
       
    }
    public void XORWF(int dBit, int fAdr) {
       
    }

    public void displayStatus(){
        System.out.println("W:" + W + " C:" + flags.CFlag + " DC:" + flags.DCFlag + " Z:" + flags.ZFlag);
    }
}