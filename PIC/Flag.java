package PIC;

public class Flag {
    public int DC(int input, int output){
        if (input >= 0xF && output > 0xF){
            return 1;
        } else {
            return 0;
        }
    }

    public int C(int input, int output){
        if (input >= 0xFF && output > 0xFF){
            return 1;
        } else {
            return 0;
        }
    }

    public int Z (int bit){
        if (bit == 0){
            return 1;
        } else {
            return 0;
        }
    }
}
