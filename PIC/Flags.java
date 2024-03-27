package PIC;

public class Flags {
    int ZFlag;
    int CFlag;
    int DCFlag;
    
    public void DC(int input, int output){
        DCFlag = ((input % 15) + (output % 15) >= 15) ? 1 : 0;
    }

    public void C(int input, int output){
        CFlag = ((input % 255) + (output % 255) >= 255) ? 1 : 0;
    }

    //?
    public void Z (int output){
        ZFlag = (output == 0) ? 1 : 0;
    }
}
