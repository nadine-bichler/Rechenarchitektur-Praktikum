package PIC;

public class PC {
    private static int pc = 0;

    public static int get(){
        return pc;
    }

    public static int next(){
        pc += 1;
        Datenspeicher.PushDataToRegister(2, (pc & 0xFF));
        Datenspeicher.PushDataToRegister(10, ((pc & 0x1F00) >> 8));
        return pc;
    }

    public static int jump (int spot){
        pc = spot;
        Datenspeicher.PushDataToRegister(2, (pc & 0xFF));
        Datenspeicher.PushDataToRegister(10, ((pc & 0x1F00) >> 8));
        return pc;
    }
}
