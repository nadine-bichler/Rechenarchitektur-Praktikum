package PIC;
public class Datenspeicher {

    private static int[] bank0 = new int[128];
    private static int[] bank1 = new int[128];

    static {
        for(int i = 0; i < bank0.length; i++) {     //Alle Register auf 0 setzen
            bank0[i] = 0;
            bank1[i] = 0;
        }

        bank1[5] = 0xFF;    //Tris A
        bank1[6] = 0xFF;    //Tris B

        bank0[3] = 0x18;    //Status-Register
        bank1[3] = 0x18;

        bank1[1] = 0xFF;    //Option-Register
    }
    public static int PSA = get(1, 1, 3);
    public static int PS2 = bank1[1] & 0b111;
    public static int prescaler = 0xFF;
    public static int[] dataLath = {15, 15};

    public static int get (int register,int k){
        int mask = 0b1 << k;
        int result;
        if (whichBank() == 0){
            result = ((bank0[register] & mask) == 0) ? 0 : 1;
        } else{
            result = ((bank1[register] & mask) == 0) ? 0 : 1;
        }
        return result;
    }

    public static int get (int bank, int register, int k){
        int mask = 0b1 << k;
        int result;
        if(bank == 0){
            result = ((bank0[register] & mask) == 0) ? 0 : 1;
        }else{
            result = ((bank1[register] & mask) == 0) ? 0 : 1;
        }
        return result;
    }

    public static void set1(int register,int k){
        if (get(register, k) == 0){
            int mask = 0b1 << k;
            if((register == 0) || (register == 2) || (register == 3) || (register == 4) || (register == 10) || (register == 11) || ((register >= 12) && (register <= 127))) {
                bank0[register] |= mask;
                bank1[register] |= mask;
            } else if (whichBank() == 0){
                bank0[register] |= mask;
            } else{
                bank1[register] |= mask;
            }
        }
    }


    public static void set0(int register, int k){
        if(get(register, k) == 1){
            int mask = 0b1 << k;
            if((register == 0) || (register == 2) || (register == 3) || (register == 4) || (register == 10) || (register == 11) || ((register >= 12) && (register <= 79))) {
                bank0[register] ^= mask;
                bank1[register] ^= mask;
            } else if (whichBank() == 0){
                bank0[register] ^= mask;
            } else{
                bank1[register] ^= mask;
            }
        }
    }


    public static void setRegister(int bank, int register, int k){
        if(bank == 0) {
            bank0[register] = k;
        }else {
            bank1[register] = k;
        }
    }


    public static int whichBank() {
        if(Decoder.isIndirect) {
            int content = bank0[4];
            int seventhBit = content >> 7;
            Decoder.isIndirect = false;												//hinzugefügt

            if(seventhBit == 0) {
                return 10;
            }
            else {
                return 11;
            }
        }

        else {
            if((bank0[3] & 0b100000) == 0 || GUI_Practice.isPort == true) {			//geändert
                return 0;
            }else {
                return 1;
            }


        }//zusatzbedingung für isIndirect Parameter
    }

    public static void PushDataToRegister(int Register, int Ergebnis) {   //Port und Tris syncen ?
        if(whichBank() == 0 || whichBank() == 10) {
            if((Register == 0) || (Register == 2) || (Register == 3) || (Register == 4) || (Register == 10) || (Register == 11) || ((Register >= 12) && (Register <= 79))) {
                bank0[Register] = Ergebnis;
                bank1[Register] = Ergebnis;
            }
            else {
                bank0[Register] = Ergebnis;
            }

        }else {
            if((Register == 0) || (Register == 2) || (Register == 3) || (Register == 4) || (Register == 10) || (Register == 11) || ((Register >= 12) && (Register <= 79))) {
                bank0[Register] = Ergebnis;
                bank1[Register] = Ergebnis;
            }
            else {
                bank1[Register] = Ergebnis;
            }
        }
    }

    public static int PullDataFromRegister(int Register) {
        if(whichBank() == 0) {
            return bank0[Register];
        }
        else {
            return bank1[Register];
        }
    }

    public static int getRegister(int bank, int register){
        if(bank == 0){
            return bank0[register];
        }else{
            return bank1[register];
        }
    }

    public static void incrementRegister(int bank, int register){
        if(bank == 0){
            bank0[register]++;
        }else{
            bank1[register]++;
        }
    }


    public static void ZFlag(int output){
        if (output == 0){
            set1(3, 2);
        }else {
            set0(3, 2);
        }
    }

    public static void DCFlag (int output, int k){
        output &= 0xF;
        k &= 0xF;
        if (output + k > 15){
            set1(3, 1);
        }else {
            set0(3, 1);
        }
    }

    public static void CFlag (int output, int k){
        output &= 0xFF;
        if (output + k > 255){
            set1(3, 0);
        }else {
            set0(3, 0);
        }
    }

    public static void Flags(){
        System.out.println("C: " + get(3, 0) +" DC: " + get(3, 1) + " Z: " + get(3, 2));
    }

    public static void prescaler(boolean change) {
        if(PSA != get(1, 1, 3) || PS2 != (bank1[1] & 0b111) || change) {
            PSA = get(1, 1, 3);
            PS2 = bank1[1] & 0b111;
            if(get(1, 1, 3) == 0) {
                prescaler = (int)Math.pow(2.0, (double)(PS2 + 1));
            }else {
                prescaler = (int)Math.pow(2.0, PS2);
            }
        }
    }

    public static void overwritingTimer(int fadr){
        if(whichBank() == 0 & fadr == 1){
            prescaler(true);
        }
    }

    public static void Timer(){
        int T0CS = (bank1[1] & 0b100000) >> 5;
        int PSA = (bank1[1] & 0b1000) >> 3;

        prescaler(false);

        if (T0CS == 0){
            if(PSA == 0){
                prescaler--;
            }else{
                bank0[1]++;
            }
        }
        prescalerReachedMax();

        if(bank0[1] > 0xFF){
            set1(11, 2);
            bank0[1] = 0;
            prescaler(true);
        }
    }

    public static void Interrupts(){
        TMR0Interrupt();
        RB0Interrupt();
        RBPortChangeInterrupt();
    }

    public static void TMR0Interrupt(){
        if(get(11, 2) == 1 && get(11, 7) == 1 && get(11, 5) == 1){
            Stack.push(PC.get());
            PC.jump(4);
            set0(11, 7);
        }
    }

    public static void RB0Interrupt(){
        //RB0(Intcon-reg.) sperrt/öffnet interrupts; ob steigende oder sinkende Flanke(mithilfe Option-Reg -> INTEDG)
        if(get(11, 7) == 1 && get(11, 4) == 1 && get(11, 1) == 1){
            Stack.push(PC.get());
            PC.jump(4);
            set0(11, 7);
        }
    }

    public static void RBPortChangeInterrupt(){
        if(get(11, 7) == 1 && get(11, 3) == 1 && get(11, 0) == 1){
            Stack.push(PC.get());
            PC.jump(4);
            set0(11, 7);
        }
    }

    public static void overwritingPCL(int fadr){
        if(fadr == 2){
            PC.overwritePc((PullDataFromRegister(10) << 8) | Datenspeicher.PullDataFromRegister(2));
        }
    }

    public static void prescalerReachedMax() {
        if (prescaler <= 0){
            prescaler(true);
            bank0[1]++;
        }
    }

    public static void setPort(int before, int fAdr){
        int AorB = 0;
        int newPortVal = PullDataFromRegister(fAdr);
        int Tris = getRegister(1, fAdr);
        if(whichBank() == 0 && (fAdr == 5 || fAdr == 6)){
            if(fAdr == 6){
                AorB = 1;
            }
            dataLath[AorB] = newPortVal;
            newPortVal = (newPortVal & ~Tris) | (before & Tris);
            PushDataToRegister(fAdr, newPortVal);
        }
    }

    public static void dataToPort(int fAdr){
        int AorB = 0;
        int newPortVal = getRegister(0, fAdr);
        int Tris = getRegister(1, fAdr);
        if(whichBank() == 1 && (fAdr == 5 || fAdr == 6)){
            if(fAdr == 6){
                AorB = 1;
            }
            newPortVal = (dataLath[AorB] & ~Tris) | (newPortVal & Tris);
        }
        setRegister(0, fAdr, newPortVal);
    }

    public static void reset(){
        PC.reset();
        Stack.reset();
        prescaler = 0xFF;
        dataLath[0] = 15;
        dataLath[1] = 15;
        //bank0
        setRegister(0, 2, 0);
        setRegister(0, 3, getRegister(0, 3) & 0b1);
        setRegister(0, 10, 0);
        setRegister(0, 11, get(0, 11, 0));

        //bank1
        setRegister(1, 1, 0xFF);
        setRegister(1, 2, 0);
        setRegister(0, 3, getRegister(0, 3) & 0b1);
        setRegister(1, 5, 0x1F);
        setRegister(1, 6, 0xFF);
        setRegister(1, 8, 0);
        setRegister(1, 10, 0);
        setRegister(1, 11, get(0, 11, 0));
    }
}

