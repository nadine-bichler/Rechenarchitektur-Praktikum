package PIC;
public class Decoder{
    public int operator (String x){
        String hex = x.substring(0,2);
        int command = Integer.parseInt(hex, 16);
        switch (command){
            case 0b110000:
                System.out.print("movlw ");
                break;
            case 0b111001:
                System.out.print("andlw ");
                break;
            case 0b111000:
                System.out.print("iorlw ");
                break;
            case 0b111100:
                System.out.print("sublw ");
                break;
            case 0b111010:
                System.out.print("xorlw ");
                break;
            case 0b111110:
                System.out.print("addlw ");
                break;
        }
        return command;
    }

    public int adress (String x){
        String hex = x.substring(2,4);
        int adress = Integer.parseInt(hex, 16);
        return adress;
    }
}