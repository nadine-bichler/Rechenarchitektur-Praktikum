package PIC;

public class PC {
    private static int pc = 0;

    public static int get(){
        return pc;
    }

    public static int next(){
        pc += 1;

        return pc;
    }

    public static int jump (int spot){
        pc = spot;
        return pc;
    }
}
