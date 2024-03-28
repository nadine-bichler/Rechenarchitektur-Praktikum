import PIC.Datenspeicher;

public class Test {
    static void main(){
        Datenspeicher speicher = new Datenspeicher();

        speicher.PushDataToBank(3, 0x0);
        System.out.println(speicher.get(3, 5));
        //speicher.PushDataToBank(3, 32);
        //System.out.println(speicher.get(3, 5));
        speicher.set1(3, 5);
        System.out.println(speicher.get(3, 5));
        System.out.println(Integer.toBinaryString(speicher.PullDataFromRegister(3)));
        speicher.set0(3, 5);
        System.out.println(Integer.toBinaryString(speicher.PullDataFromRegister(0)));

    }
}
