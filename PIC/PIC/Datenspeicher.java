package PIC; 
public class Datenspeicher {

    private static int[] bank0 = new int[128];  
    private static int[] bank1 = new int[128];

    private static int WDT = 128;
    
    
    static {
        for(int i = 0; i < bank0.length; i++) {     //Alle Register auf 0 setzen
            bank0[i] = 0;
            bank1[i] = 0;
        }
            
        bank1[5] = 0x1F;    //Tris A
        bank1[6] = 0xFF;    //Tris B
    
        bank0[11] = 255;    //Intcon-Register auf 0xFF  //?
        bank1[11] = 255;    
            
        bank0[3] = 0x18;    //Status-Register
        bank1[3] = 0x18;

        bank1[1] = 0xFF;    //Option-Register
    }


    public static int get (int register,int k){
        int mask = 0b1 << k;
        int result;
        if (whichBank() == 0){
            result = ((bank0[register] & mask) == 0) ? 0 : 1;
            return result;
        } else{
            result = ((bank1[register] & mask) == 0) ? 0 : 1;
            return result;
        }
    }

    public static void set1(int register,int k){
        if (get(register, k) == 0){
            int mask = 0b1 << k;
            if((register == 0) || (register == 2) || (register == 3) || (register == 4) || (register == 10) || (register == 11) || ((register >= 12) && (register <= 79))) {
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
    
    public static int whichBank() {
        if((Decoder.isIndirect) == true) {
            int content = bank0[4];
            int seventhBit = content >> 7;
            
            if(seventhBit == 0) {
                return 10;
            }
            else {
                return 11;
            }
        }
        
        else {
            if((bank0[3] & 0b100000) == 0) {
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

    public static void Timer(){
        int T0CS = (bank1[1] & 0b100000) >> 5;
        int PSA = (bank1[1] & 0b1000) >> 3;
        int RA4 = (bank0[5] & 0b10000) >> 4;
        
        if (PSA == 1){
            if(T0CS == 0){
                bank0[1] += 1;
            }else if(T0CS == 1 && RA4 == 1){
                bank0[1] += 1;
            }
        }
    }
}
