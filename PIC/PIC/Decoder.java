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
                        PC.next();
                        break;
                    case 0b111001:
                        ANDLW(k);
                        PC.next();
                        break;
                    case 0b111000:
                        IORLW(k);
                        PC.next();
                        break;
                    case 0b110000:
                        MOVLW(k);
                        PC.next();
                        break;
                    case 0b110100:
                        RETLW(k);
                        PC.next();
                        break;
                    case 0b111100:
                        SUBLW(k);
                        PC.next();
                        break;
                    case 0b111010:
                        XORLW(k);
                        PC.next();
                        break;
                }
                break;
            //Bit-oriented Operations
            case 0b01:

            int bit = (bcode & 0x3FF) >> 7;
            

                switch(bcode >> 10){
                    case 0b0100:
                        BCF(bit, fAdr);
                        PC.next();
                        break;
                    case 0b0101:
                        BSF(bit, fAdr);
                        PC.next();
                        break;
                    case 0b0110:
                        BTFSC(bit, fAdr);
                        PC.next();
                        break;
                    case 0b0111:
                        BTFSS(bit, fAdr);
                        PC.next();
                        break;
                }
            //Byte-oriented operations
            case 0b00:

            int dBit = bcode & 0x80;

                switch (bcode >> 8){
                    case 0b000111:
                        ADDWF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b000101:
                        ANDWF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b000001:      //CRLF oder CRLW
                        switch(bcode & 0xFF){
                            case 0b00000000:
                                CLRW(dBit, fAdr);
                            default:
                                CLRF(dBit, fAdr);
                        }
                        
                        break;
                    case 0b001001:
                        COMF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b000011:
                        DECF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b001011:
                        DECFSZ(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b001010:
                        INCF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b001111:
                        INCFSZ(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b000100:
                        IORWF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b001000:
                        MOVF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b000000:      //MOVWF, NOP, CRLWDT, RETFIE, RETURN, oder SLEEP
                        switch(bcode & 0xFF){
                            case 0b00000000:
                                NOP(dBit, fAdr);
                            case 0b01100100:
                                CLRWDT(dBit, fAdr);
                            case 0b00001001:
                                RETFIE(dBit, fAdr);
                            case 0b00001000:
                                RETURN(dBit, fAdr);
                            case 0b01100011:
                                SLEEP(dBit, fAdr);
                            default:
                                MOVWF(dBit, fAdr);
                        }
                        
                        break;
                    case 0b001101:
                        RLF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b001100:
                        RRF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b000010:
                        SUBWF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b001110:
                        SWAPF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b000110:
                        XORWF(dBit, fAdr);
                        PC.next();
                        break;
                }
            //Control operations
            case 0b10:

            k = bcode & 0x7FF;

                switch(bcode >> 11){
                    case 0b100:
                        CALL(k);
                        PC.next();
                        break;
                    case 0b101:
                        GOTO(k);
                        PC.next();
                        break;
                }

        }
    }

//case 0b11:
    //Literal operations
    public int ADDLW(int k) {
        W += k;
        W %= 256;
        return W;
    }

    public int ANDLW(int k){
        W &= k;
        W %= 256;
        return W;
    }

    public int IORLW(int k){
        W |= k;
        W %= 256;
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
            Datenspeicher.set1(3, 0);
        } else {
            Datenspeicher.set0(3, 0);
        }
        int DCW = W & 0xF;
        if (DCW >= 0){
            Datenspeicher.set1(3, 1);
        }else {
            Datenspeicher.set0(3, 1);
        }
        return W;
    }

    public int XORLW(int k){
        W ^= k;
        W %= 256;
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
    public void CALL(int k){
        Stack.push(PC.next());
        PC.jump(k);
    }

    public void GOTO(int k){
        PC.jump(k);
    }

//case 0b00:
    public void ADDWF(int dBit, int fAdr) {    //C, DC, ZFlag werden manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        if(dBit == 0){
            W = W + save;           
        }
        else{
            Datenspeicher.PushDataToRegister(fAdr, W + Datenspeicher.PullDataFromRegister(fAdr));
        }
    }

    public void ANDWF(int dBit, int fAdr) {
        int save = Datenspeicher.PullDataFromRegister(fAdr);
       if(dBit == 0){
            W = W & save;
       }
       else{
        Datenspeicher.PushDataToRegister(fAdr, W & save);
       }
    }

    public void COMF(int dBit, int fAdr) {  //ZFlag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        int mask = 0xFF;

       if(dBit == 0){
            W = mask ^ save;
       }
       else{
            Datenspeicher.PushDataToRegister(fAdr, mask ^ save);
       }
    }
    
    public void DECF(int dBit, int fAdr) {      //ZFlag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        save = save - 1;
 
        if(dBit == 0){
             W = save;
        }
        else{
             Datenspeicher.PushDataToRegister(fAdr, save);
        }
     }

    public void DECFSZ(int dBit, int fAdr) {    //Keine Flag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        save = save - 1 ;

        if(dBit == 0){
            W = save;
            if(W == 0){
//                PC++;     naechster Befehl wird uebersprungen
            }
       }
       else{
            Datenspeicher.PushDataToRegister(fAdr, save);
       }
       
    }
    public void INCF(int dBit, int fAdr) {      //Z-Flag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        save = save - 1;

        if(dBit == 0){
             W = save;
        }
        else{
             Datenspeicher.PushDataToRegister(fAdr, save);
        }
    }

    public void INCFSZ(int dBit, int fAdr) {    //Keine Flag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        save = save + 1 ;

        if(dBit == 0){
            W = save;
            if(W == 0){
//                PC++;                                                         naechster Befehl wird uebersprungen
            }
       }
       else{
            Datenspeicher.PushDataToRegister(fAdr, save);
       }
    }
    public void IORWF(int dBit, int fAdr) { //ZFlag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);

        if(dBit == 0){
            W = W | save;
        }
        else{
            Datenspeicher.PushDataToRegister(fAdr, W | save);
           }
    }
    
    public void MOVF(int dBit, int fAdr) {      //Z-Flag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);

       if(dBit == 0){
            W = save;
            W = save;  
       }
       else{
            Datenspeicher.PushDataToRegister(fAdr, save);       
       }

    }

    public void RLF(int dBit, int fAdr) {   //CFlag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        int mask = 0x80;
        int storage = mask & save;

        if(storage == 0){
        }
        else{
        }

        if(dBit == 0){
            W = save << 1;
        }
        else{
            Datenspeicher.PushDataToRegister(fAdr, save << 1);
        }
    }

    public void RRF(int dBit, int fAdr) {   //CFlag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        int mask = 0x01;
        int storage = mask & save;

        if(storage == 0){
        }
        else{
        }

        if(dBit == 0){
            W = save >>> 1;
        }
        else{
            Datenspeicher.PushDataToRegister(fAdr, save >>> 1);
        }
    }

    public void SUBWF(int dBit, int fAdr) { //C, DC, ZFlag werden manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);
//    	    CFlag(W, save);                                                         Flags muessen noch erledigt werden
//            DCFlag(W, save);

        if(dBit == 0){
            W = save - W;           
        }
        else{  
            Datenspeicher.PushDataToRegister(fAdr, save - W);
        }
    }

    public void SWAPF(int dBit, int fAdr) { //Keine Flag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);
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
            Datenspeicher.PushDataToRegister(fAdr, storage2 ^ storage1);
        }
       
    }
    public void XORWF(int dBit, int fAdr) { //Keine Flag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);

        if(dBit == 0){
            W = W ^ save;
        }
        else{
            Datenspeicher.PushDataToRegister(fAdr, W ^ save);
           }
    }

    public void CLRF (int dBit, int fAdr) { //ZFlag wird manipuliert
        Datenspeicher.PushDataToRegister(fAdr, 0);
    }

    public void CLRW (int dBit, int fAdr) { //ZFlag wird manipuliert
        W = 0;
    }

    public void CLRWDT (int dBit, int fAdr) {   //|TO und|PD wird manipuliert
//      WDT = 0;        
    }

    public void RETFIE (int dBit, int fAdr) {   //Keine Flags werden manipuliert

    }

    public void RETURN (int dBit, int fAdr) {   //Keine Flags werden manipuliert
        
    }

    public void SLEEP (int dBit, int fAdr) {    //|TO und|PD wird manipuliert
        
    }

    public void MOVWF (int dBit, int fAdr) {    //Keine Flags werden manipuliert
        Datenspeicher.PushDataToRegister(fAdr, W);
    }

    public void NOP (int dBit, int fAdr) {  //Keine Flags werden manipuliert
        
    }
}