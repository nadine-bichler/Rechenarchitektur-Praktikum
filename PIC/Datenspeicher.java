package PIC;
public class Datenspeicher {
    int[] bank0;
    int[] bank1;
    
    public Datenspeicher() {    //Register auf default Werte setzen
        
        bank0 = new int[128];
        bank1 = new int[128];
        
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
    
    public boolean whichBank(int[] bank0, int[] bank1) {
        if(((bank0[3] & 0b10000) == 1)) {
            return true;
        }
        else if((bank1[3] & 0b10000) == 1) {
            return true;
        }
        else {
            return false;
        }
        
    }
   
    public void PushDataToBank(int Register, int Ergebnis) {   //Port und Tris syncen ?
        if(whichBank(bank0, bank1) == true) {
            if((Register == 0) || (Register == 2) || (Register == 3) || (Register == 4) || (Register == 10) || (Register == 11) || ((Register >= 12) && (Register <= 79))) {
                bank0[Register] = Ergebnis;
                bank1[Register] = Ergebnis;
            }
            else {
                bank1[Register] = Ergebnis;
            }
            
        }
        else {
            if((Register == 0) || (Register == 2) || (Register == 3) || (Register == 4) || (Register == 10) || (Register == 11) || ((Register >= 12) && (Register <= 79))) {
                bank0[Register] = Ergebnis;
                bank1[Register] = Ergebnis;
            }
            else {
                bank0[Register] = Ergebnis;
            }
        }
        
    }
    
    public int PullDataFromRegister(int Register) {
        if(whichBank(bank0, bank1) == true) {
            return bank1[Register];
        }
        else {
            return bank0[Register];
        }
    }
}
