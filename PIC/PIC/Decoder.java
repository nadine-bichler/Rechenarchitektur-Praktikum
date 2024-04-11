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
                        System.out.println("ADDLW");
                        ADDLW(k);
                        PC.next();
                        break;
                    case 0b111001:
                        System.out.println("ANDLW");
                        ANDLW(k);
                        PC.next();
                        break;
                    case 0b111000:
                        System.out.println("IORLW");
                        IORLW(k);
                        PC.next();
                        break;
                    case 0b110000:
                        System.out.println("MOVLW");
                        MOVLW(k);
                        PC.next();
                        break;
                    case 0b110100:
                        System.out.println("RETLW");
                        RETLW(k);
                        break;
                    case 0b111100:
                        System.out.println("SUBLW");
                        SUBLW(k);
                        PC.next();
                        break;
                    case 0b111010:
                        System.out.println("XORLW");
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
                        System.out.println("BCF");
                        BCF(bit, fAdr);
                        PC.next();
                        break;
                    case 0b0101:
                        BSF(bit, fAdr);
                        System.out.println("BSF");
                        PC.next();
                        break;
                    case 0b0110:
                        System.out.println("BTFSC");
                        BTFSC(bit, fAdr);
                        PC.next();
                        break;
                    case 0b0111:
                        System.out.println("BTFSS");
                        BTFSS(bit, fAdr);
                        PC.next();
                        break;
                }
            //Byte-oriented operations
            case 0b00:

            int dBit = bcode & 0x80;

                switch (bcode >> 8){
                    case 0b000111:
                        System.out.println("ADDWF");
                        ADDWF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b000101:
                        System.out.println("ANDWF");
                        ANDWF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b000001:      //CRLF oder CRLW
                        switch(bcode & 0xFF){
                            case 0b00000000:
                                System.out.println("CLRW");
                                CLRW(dBit, fAdr);
                                PC.next();
                                break;
                            default:
                                System.out.println("CRLF");
                                CLRF(dBit, fAdr);
                                PC.next();
                        }
                        break;
                    case 0b001001:
                        System.out.println("COMF");
                        COMF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b000011:
                        System.out.println("DECF");
                        DECF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b001011:
                        System.out.println("DECFSZ");
                        DECFSZ(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b001010:
                        System.out.println("INCF");
                        INCF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b001111:
                        System.out.println("INCFSZ");
                        INCFSZ(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b000100:
                        System.out.println("IORWF");
                        IORWF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b001000:
                        System.out.println("MOVF");
                        MOVF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b000000:      //MOVWF, NOP, CRLWDT, RETFIE, RETURN, oder SLEEP
                        switch(bcode & 0xFF){
                            case 0b00000000:
                                System.out.println("NOP");
                                NOP(dBit, fAdr);
                                PC.next();
                                break;
                            case 0b01100100:
                                System.out.println("CLRWDT");
                                CLRWDT(dBit, fAdr);
                                PC.next();
                                break;
                            case 0b00001001:
                                System.out.println("RETFIE");
                                RETFIE(dBit, fAdr);
                                PC.next();
                                break;
                            case 0b00001000:
                                System.out.println("RETURN");
                                RETURN(dBit, fAdr);
                                break;
                            case 0b01100011:
                                System.out.println("SLEEP");
                                SLEEP(dBit, fAdr);
//                                PC.next();                wird der Programmzähler erhöht, was geschieht bei dem Befehl
                                PC.next();
                                break;
                            default:
                                System.out.println("MOVWF");
                                MOVWF(dBit, fAdr);
                                PC.next();
                                break;
                        }
                        break;
                    case 0b001101:
                        System.out.println("RLF");
                        RLF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b001100:
                        System.out.println("RRF");
                        RRF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b000010:
                        System.out.println("SUBWF");
                        SUBWF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b001110:
                        System.out.println("SWAPF");
                        SWAPF(dBit, fAdr);
                        PC.next();
                        break;
                    case 0b000110:
                        System.out.println("XORWF");
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
                        break;
                    case 0b101:
                        GOTO(k);
                        break;
                }

        }
    }

//case 0b11:
    //Literal operations
    public void ADDLW(int k) {
        W += k;
        W %= 256;
        Datenspeicher.CFlag(W, k);
        Datenspeicher.DCFlag(W, k);
        Datenspeicher.ZFlag(W);
    }

    public void ANDLW(int k){
        W &= k;
        W %= 256;
        Datenspeicher.ZFlag(W);
    }

    public void IORLW(int k){
        W |= k;
        W %= 256;
        Datenspeicher.ZFlag(W);
    }

    public void MOVLW(int k){
        W = k;
        W %= 256;
    }

    //später
    public void RETLW(int k){
        W = k;
        PC.jump(Stack.pop());
    }

    public void SUBLW(int k){
        W ^= 0xFF;
        W += 1;
        W += k;
        W %= 256;

        //Carry Flag
        if (W >= 0){
            Datenspeicher.set1(3, 0);
        } else {
            Datenspeicher.set0(3, 0);
        }
        //Digitcarry Flag
        int DCk = k & 0xF;
        int DCW = W & 0xF;
        int DC = DCk - DCW;
        if (DC >= 0){
            Datenspeicher.set1(3, 1);
        }else {
            Datenspeicher.set0(3, 1);
        }
        //ZFlag
        Datenspeicher.ZFlag(W);
    }

    public void XORLW(int k){
        W ^= k;
        W %= 256;
        Datenspeicher.ZFlag(W);
    }


    //bit-oriented operations
    public void BCF(int bit, int fAdr){
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        int mask = 0b1 << bit;
        save = save | mask;
        Datenspeicher.PushDataToRegister(fAdr, save);
        
    }

    public void BSF(int bit, int fAdr){
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        int mask = 0b1 << bit;
        int mask2 = mask ^ 0b0;
        save = save & mask2;
        Datenspeicher.PushDataToRegister(fAdr, save);
    }

    public void BTFSC(int bit, int fAdr){
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        int mask = 0b1 << bit;
        save = save & mask;

        if(save == 0){
            PC.jump(PC.next() + 1);     //wenn das Bit an Stelle 0 ist soll es auf den übernächsten Befehl überspringen
        }
        else{                           //wenn das Bit an Stelle 1 ist soll es den nächsten Befehl ausführen
            PC.next();
        }
    }

    public void BTFSS(int bit, int fAdr){
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        int mask = 0b1 << bit;
        save = save & mask;

        if(save == 0){
            PC.next();                  //wenn das Bit an Stelle 1 ist soll es den nächsten Befehl ausführen
        }
        else{                           
            PC.jump(PC.next() + 1);     //wenn das Bit an Stelle 0 ist soll es auf den übernächsten Befehl überspringen
        }
    }

    //control operations
    public void CALL(int k){
        Stack.push(PC.next());
        int jumpaddress = ((Datenspeicher.PullDataFromRegister(10) >> 3) << 11) | k; 
        PC.jump(jumpaddress);
    }

    public void GOTO(int k){
        int jumpaddress = ((Datenspeicher.PullDataFromRegister(10) >> 3) << 11) | k; 
        PC.jump(jumpaddress);
    }

//case 0b00 (byte operations):
    public void ADDWF(int dBit, int fAdr) {    //C, DC, ZFlag werden manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);

        Datenspeicher.CFlag(W, save);
        Datenspeicher.DCFlag(W, save);

        int result = W + save;
        result %= 256;
        if(dBit == 0){
            W = result;           
        }
        else{
            Datenspeicher.PushDataToRegister(fAdr, result);
            System.out.println(Datenspeicher.PullDataFromRegister(fAdr));
        }
        Datenspeicher.ZFlag(W);
    }

    public void ANDWF(int dBit, int fAdr) { //ZFlag wird maniuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);
       if(dBit == 0){
            W = W & save;
            Datenspeicher.ZFlag(W);
       }
       else{
            Datenspeicher.PushDataToRegister(fAdr, W & save);
            Datenspeicher.ZFlag(Datenspeicher.PullDataFromRegister(fAdr));
            System.out.println(Datenspeicher.PullDataFromRegister(fAdr));
       }
    }

    public void COMF(int dBit, int fAdr) {  //ZFlag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        int mask = 0xFF;

       if(dBit == 0){
            W = mask ^ save;
            Datenspeicher.ZFlag(W);
       }
       else{
            Datenspeicher.PushDataToRegister(fAdr, mask ^ save);
            Datenspeicher.ZFlag(Datenspeicher.PullDataFromRegister(fAdr));
            System.out.println(Datenspeicher.PullDataFromRegister(fAdr));
       }
    }
    
    public void DECF(int dBit, int fAdr) {      //ZFlag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        save--;
        if (save < 0){
            save += 256;
        }
 
        if(dBit == 0){
             W = save;
             Datenspeicher.ZFlag(W);
        }
        else{
             Datenspeicher.PushDataToRegister(fAdr, save);
             Datenspeicher.ZFlag(Datenspeicher.PullDataFromRegister(fAdr));
             System.out.println(Datenspeicher.PullDataFromRegister(fAdr));
        }
     }

    public void DECFSZ(int dBit, int fAdr) {    //Keine Flag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        save--;
        if (save < 0){
            save += 256;
        }else if(save == 0){
            PC.jump(PC.next());
        }

        if(dBit == 0){
            W = save;
       }else{
            Datenspeicher.PushDataToRegister(fAdr, save);
            System.out.println(Datenspeicher.PullDataFromRegister(fAdr));
       }
       
    }

    public void INCF(int dBit, int fAdr) {      //Z-Flag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        save++;
        save %= 256;

        if(dBit == 0){
             W = save;
             Datenspeicher.ZFlag(W);
        }
        else{
             Datenspeicher.PushDataToRegister(fAdr, save);
             Datenspeicher.ZFlag(Datenspeicher.PullDataFromRegister(fAdr));
             System.out.println(Datenspeicher.PullDataFromRegister(fAdr));
        }
    }

    public void INCFSZ(int dBit, int fAdr) {    //Keine Flag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        save++;
        save %= 256;
        if(save == 0){
            PC.jump(PC.next());
        }

        if(dBit == 0){
            W = save;
       }
       else{
            Datenspeicher.PushDataToRegister(fAdr, save);
            System.out.println(Datenspeicher.PullDataFromRegister(fAdr));
       }
    }

    public void IORWF(int dBit, int fAdr) { //ZFlag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);

        if(dBit == 0){
            W = W | save;
            Datenspeicher.ZFlag(W);
        }
        else{
            Datenspeicher.PushDataToRegister(fAdr, W | save);
            Datenspeicher.ZFlag(Datenspeicher.PullDataFromRegister(fAdr));
            System.out.println(Datenspeicher.PullDataFromRegister(fAdr));
        }
    }
    
    public void MOVF(int dBit, int fAdr) {      //Z-Flag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);

       if(dBit == 0){
            W = save;
       }
       else{
            Datenspeicher.PushDataToRegister(fAdr, save);   
            System.out.println(Datenspeicher.PullDataFromRegister(fAdr));    
       }
       Datenspeicher.ZFlag(Datenspeicher.PullDataFromRegister(fAdr));

    }

    public void RLF(int dBit, int fAdr) {   //CFlag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        int mask1 = 0x80;
        int storage = mask1 & save;
        int storage1 = Datenspeicher.get(3, 0);

        if(storage == 0){
            Datenspeicher.set0(3, 0);
        }
        else{
            Datenspeicher.set1(3, 0);
        }
        save = save << 1;
        save &= 0xFF;
        save |= storage1; 

        if(dBit == 0){
            W = save;
        }
        else{
            Datenspeicher.PushDataToRegister(fAdr, save);
            System.out.println(Datenspeicher.PullDataFromRegister(fAdr));
        }
    }

    public void RRF(int dBit, int fAdr) {   //CFlag wird manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);
        int mask1 = 0x01;
        int storage = mask1 & save;
        int storage1 = (Datenspeicher.get(3, 0)) << 7;

        if(storage == 0){
            Datenspeicher.set1(3, 0);
        }
        else{
            Datenspeicher.set1(3, 0);
        }

        save = save >>> 1;
        save |= storage1; 

        if(dBit == 0){
            W = save;
        }
        else{
            Datenspeicher.PushDataToRegister(fAdr, save);
            System.out.println(Datenspeicher.PullDataFromRegister(fAdr));
        }
    }

    public void SUBWF(int dBit, int fAdr) { //C, DC, ZFlag werden manipuliert
        int save = Datenspeicher.PullDataFromRegister(fAdr);

        //Carry
        int result = save - W;

        if (result >= 0){
            Datenspeicher.set1(3, 0);
        } else {
            Datenspeicher.set0(3, 0);
        }

        //DC Carry
        int DCsave = save & 0xF;
        int DCW = W & 0xF;
        int DC = DCsave - DCW;

        if (DC >= 0){
            Datenspeicher.set1(3, 1);
        }else {
            Datenspeicher.set0(3, 1);
        }

        //Z Flag
        Datenspeicher.ZFlag(result);

        //Ergebnis
        if (result < 0){
            result += 256;
        }

        if(dBit == 0){
            W = result;  
        }
        else{  
            Datenspeicher.PushDataToRegister(fAdr, result);
            System.out.println(Datenspeicher.PullDataFromRegister(fAdr));
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
            System.out.println(Datenspeicher.PullDataFromRegister(fAdr));

        }
       
    }
    public void XORWF(int dBit, int fAdr) { //ZFlag
        int save = Datenspeicher.PullDataFromRegister(fAdr);

        if(dBit == 0){
            W = W ^ save;
            Datenspeicher.ZFlag(W);
        }
        else{
            Datenspeicher.PushDataToRegister(fAdr, W ^ save);
            Datenspeicher.ZFlag(Datenspeicher.PullDataFromRegister(fAdr));
            System.out.println(Datenspeicher.PullDataFromRegister(fAdr));
           }
    }

    public void CLRF (int dBit, int fAdr) { //ZFlag wird manipuliert
        Datenspeicher.PushDataToRegister(fAdr, 0);
        Datenspeicher.ZFlag(Datenspeicher.PullDataFromRegister(fAdr));
        System.out.println(Datenspeicher.PullDataFromRegister(fAdr));
    }

    public void CLRW (int dBit, int fAdr) { //ZFlag wird manipuliert
        W = 0;
        Datenspeicher.ZFlag(W);
    }

    public void CLRWDT (int dBit, int fAdr) {   //|TO und|PD wird manipuliert
//      WDT = 0;        
    }

    public void RETFIE (int dBit, int fAdr) {   //Keine Flags werden manipuliert
        
    }

    public void RETURN (int dBit, int fAdr) {   //Keine Flags werden manipuliert
        PC.jump(Stack.pop());
    }

    public void SLEEP (int dBit, int fAdr) {    //|TO und|PD wird manipuliert
        
    }

    public void MOVWF (int dBit, int fAdr) {    //Keine Flags werden manipuliert
        Datenspeicher.PushDataToRegister(fAdr, W);
        System.out.println(Datenspeicher.PullDataFromRegister(fAdr));
    }

    public void NOP (int dBit, int fAdr) {  //Keine Flags werden manipuliert
        return;
    }
}