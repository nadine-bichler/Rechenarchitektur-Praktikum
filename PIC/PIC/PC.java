package PIC;

public class PC {
    private static int pc = 0;

    public static int get(){
        return pc;
    }

    public static int next(){
        pc++;
        if (pc > 1023){
            pc = 0;
        }
        //PCL
        Datenspeicher.PushDataToRegister(2, (pc & 0xFF));
        return pc;
    }

    public static void jump (int spot){
        pc = spot;
        //PCL
        Datenspeicher.PushDataToRegister(2, (pc & 0xFF));
    }

    public static void overwritePc (int val){
        pc = val;
    }

    public static void reset(){
        pc = 0;
    }
}
