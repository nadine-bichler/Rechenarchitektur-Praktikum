package PIC;
public class PC {
    int pc;

    public PC(){
        pc = 0;
    }

    public int get(){
        return pc;
    }

    public int next(){
        pc += 1;
        return pc;
    }

    public int jump (int spot){
        pc = spot;
        return pc;
    }
}
