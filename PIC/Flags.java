package PIC;

public class Flags {
    int ZFlag;
    int CFlag;
    int DCFlag;
    
    public void DC(int output){
        DCFlag = (input <= 0xF && output > 0xF) ? 1 : 0;
    }

    public void C(int output){
        CFlag = (output > 0xFF) ? 1 : 0;
    }

    //?
    public void Z (int output){
        ZFlag = (output == 0) ? 1 : 0;
    }
}
