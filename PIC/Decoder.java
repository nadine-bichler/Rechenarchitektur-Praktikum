package PIC;
public class Decoder{

    //Working Register
    int W = 0;
    
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
                break;
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
    public int ADDLW(int k) {
        W += k;
        W %= 256;
        ZFlag(W);
        DCFlag(W, k);
        CFlag(W, k);
        return W;
    }

    public int ANDLW(int k){
        W &= k;
        W %= 256;
        ZFlag(W);
        return W;
    }

    public int IORLW(int k){
        W |= k;
        W %= 256;
        ZFlag(W);
        return W;
    }

    public int MOVLW(int k){
        W = k;
        W %= 256;
        return W;
    }

    //später
    public void RETLW(int k){
        
    }

    public int SUBLW(int k){
        W ^= 0xFF;
        W += 1;
        W += k;
        W %= 256;
        if (W >= 0){
            datenspeicher.set1(3, 0);
        } else {
            datenspeicher.set0(3, 0);
        }
        int DCW = W & 0xF;
        if (DCW >= 0){
            datenspeicher.set1(3, 1);
        }else {
            datenspeicher.set0(3, 1);
        }
        return W;
    }

    public int XORLW(int k){
        W ^= k;
        W %= 256;
        ZFlag(W);
        return W;
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
    public void ADDWF(int dBit, int fAdr) {    //C, DC, ZFlag werden manipuliert
        int save = datenspeicher.PullDataFromRegister(fAdr);
    	    CFlag(W, save);
            DCFlag(W, save);
            ZFlag(W + save); 

        if(dBit == 0){
            W = W + save;           
        }
        else{  
            datenspeicher.PushDataToRegister(fAdr, W + save);
        }
    }

    public void ANDWF(int dBit, int fAdr) {     //ZFlag wird manipuliert
        int save = datenspeicher.PullDataFromRegister(fAdr);
        ZFlag(W & save);

       if(dBit == 0){
            W = W & save;
       }
       else{
        datenspeicher.PushDataToRegister(fAdr, W & save);
       }
    }
    public void COMF(int dBit, int fAdr) {  //ZFlag wird manipuliert
        int save = datenspeicher.PullDataFromRegister(fAdr);
        int mask = 0xFF;
        ZFlag(mask ^ save);

       if(dBit == 0){
            W = mask ^ save;
       }
       else{
            datenspeicher.PushDataToRegister(fAdr, mask ^ save);
       }
    }

    public void DECF(int dBit, int fAdr) {      //ZFlag wird manipuliert
       int save = datenspeicher.PullDataFromRegister(fAdr);
       save = save - 1;
       ZFlag(save);
       
       if(dBit == 0){
            W = save;
       }
       else{
            datenspeicher.PushDataToRegister(fAdr, save);
       }
    }
    public void DECFSZ(int dBit, int fAdr) {    //Keine Flag wird manipuliert
        int save = datenspeicher.PullDataFromRegister(fAdr);
        save = save - 1 ;
        ZFlag(save);

        if(dBit == 0){
            W = save;
            if(W == 0){
//                PC++;     naechster Befehl wird uebersprungen
            }
       }
       else{
            datenspeicher.PushDataToRegister(fAdr, save);
       }
       
    }
    public void INCF(int dBit, int fAdr) {      //Z-Flag wird manipuliert
        int save = datenspeicher.PullDataFromRegister(fAdr);
        ZFlag(save - 1);
        save = save - 1;

        if(dBit == 0){
             W = save;
        }
        else{
             datenspeicher.PushDataToRegister(fAdr, save);
        }
    }

    public void INCFSZ(int dBit, int fAdr) {    //Keine Flag wird manipuliert
        int save = datenspeicher.PullDataFromRegister(fAdr);
        save = save + 1 ;
        ZFlag(save);

        if(dBit == 0){
            W = save;
            if(W == 0){
//                PC++;                                                         naechster Befehl wird uebersprungen
            }
       }
       else{
            datenspeicher.PushDataToRegister(fAdr, save);
       }
    }
    public void IORWF(int dBit, int fAdr) { //ZFlag wird manipuliert
        int save = datenspeicher.PullDataFromRegister(fAdr);
        ZFlag(W | save);

        if(dBit == 0){
            W = W | save;
        }
        else{
            datenspeicher.PushDataToRegister(fAdr, W | save);
           }
    }

    public void MOVF(int dBit, int fAdr) {      //Z-Flag wird manipuliert
        int save = datenspeicher.PullDataFromRegister(fAdr);
        ZFlag(W);

       if(dBit == 0){
            W = save;  
       }
       else{
            datenspeicher.PushDataToRegister(fAdr, save);       
       }

    }
    public void RLF(int dBit, int fAdr) {   //CFlag wird manipuliert
        int save = datenspeicher.PullDataFromRegister(fAdr);
        int mask = 0x80;
        int storage = mask & save;

        if(storage == 0){
            CFlag(255, 1);
        }
        else{
            CFlag(0, 0);
        }

        if(dBit == 0){
            W = save << 1;
        }
        else{
            datenspeicher.PushDataToRegister(fAdr, save << 1);
        }
    }

    public void RRF(int dBit, int fAdr) {   //CFlag wird manipuliert
        int save = datenspeicher.PullDataFromRegister(fAdr);
        int mask = 0x01;
        int storage = mask & save;

        if(storage == 0){
            CFlag(255, 1);
        }
        else{
            CFlag(0, 0);
        }

        if(dBit == 0){
            W = save >>> 1;
        }
        else{
            datenspeicher.PushDataToRegister(fAdr, save >>> 1);
        }
    }

    public void SUBWF(int dBit, int fAdr) { //C, DC, ZFlag werden manipuliert
        int save = datenspeicher.PullDataFromRegister(fAdr);
//    	    CFlag(W, save);                                                         Flags muessen noch erledigt werden
//            DCFlag(W, save);
            ZFlag(save - W); 

        if(dBit == 0){
            W = save - W;           
        }
        else{  
            datenspeicher.PushDataToRegister(fAdr, save - W);
        }
    }

    public void SWAPF(int dBit, int fAdr) { //Keine Flag wird manipuliert
        int save = datenspeicher.PullDataFromRegister(fAdr);
        int mask1 = 0xF0;
        int mask2 = 0x0F;
        int storage1;
        int storage2;

        storage1 = (mask1 & save) >>> 4;
        storage2 = (mask2 & save) << 4;

        if(dBit == 0){
            W = storage2 ^ storage1;
        }
        else{
            datenspeicher.PushDataToRegister(fAdr, storage2 ^ storage1);
        }
       
    }
    public void XORWF(int dBit, int fAdr) { //Keine Flag wird manipuliert
        int save = datenspeicher.PullDataFromRegister(fAdr);
        ZFlag(W ^ save);

        if(dBit == 0){
            W = W ^ save;
        }
        else{
            datenspeicher.PushDataToRegister(fAdr, W ^ save);
           }
    }


    public void ZFlag(int output){
        if (output == 0){
            datenspeicher.set1(3, 2);
        }else {
            datenspeicher.set0(3, 2);
        }
    }

    public void DCFlag (int output, int k){
        output &= 0xF;
        k &= 0xF;
        if (output + k >= 16){
            datenspeicher.set1(3, 1);
        }else {
            datenspeicher.set0(3, 1);
        }
    }

    public void CFlag (int output, int k){
        output &= 0xFF;
        if (output + k >= 256){
            datenspeicher.set1(3, 0);
        }else {
            datenspeicher.set0(3, 0);
        }
    }

    public void Flags(){
        System.out.println("C: " + datenspeicher.get(3, 0) +" DC: " + datenspeicher.get(3, 1) + " Z: " + datenspeicher.get(3, 2));
    }
}