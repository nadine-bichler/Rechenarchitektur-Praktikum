package PIC; 
public class Datenspeicher {

    private static int[] bank0 = new int[128];  
    private static int[] bank1 = new int[128];
    
    
    static {
        for(int i = 0; i < bank0.length; i++) {     //Alle Register auf 0 setzen
            bank0[i] = 0;
            bank1[i] = 0;
        }
            
        bank1[5] = 255;     //Tris-Register auf 0xFF
        bank1[6] = 255;
    
        bank0[11] = 255;    //Intcon-Register auf 0xFF
        bank1[11] = 255;    
            
        bank0[3] = 24;      //Status-Register auf 0x18
        bank1[3] = 24;
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
        if((bank0[3] & 0b100000) == 0) {
            return 0;
        }else {
            return 1;
        }
    }
   
    public static void PushDataToRegister(int Register, int Ergebnis) {   //Port und Tris syncen ?
        if(whichBank() == 0) {
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
}
