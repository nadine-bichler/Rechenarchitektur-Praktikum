package PIC;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {

    public static void main(String[] args){

        //Programmspeicher
        int ROM [] = new int[1024];

        //CALL & GOTO auch in Programmspeicher?

        Decoder decoder = new Decoder();

        int x = 0;
        try {

            FileReader fileReader = new FileReader("//C:/Users/nbich/Documents/2/Rechnerarchitektur/TestProg_PicSim/TPicSim7.LST");
            //FileReader fileReader = new FileReader("C:/Users/thuyl/Desktop/TestProg_PicSim_20230413/TPicSim6.LST"); //C:/Users/thuyl/Desktop/TestProg_PicSim_20230413/TPicSim1.LST
            BufferedReader bufferedReader = new BufferedReader(fileReader);
    
            //
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith(" ")){
                    continue;
                }
                System.out.println(line);
                ROM [x] = Integer.parseInt(line.substring(5,9), 16);
                x++;

            }

            bufferedReader.close();
            } 
        catch (IOException e) {
            e.printStackTrace();
            }
        
        //Test ob richtig in Array gespeichert

        //Wird Timer zurückgesetzt wenn prescaler verändert?
        while(true){
            System.out.println(PC.get());
            decoder.execute(ROM[PC.get()]);
            System.out.println("Teilerfaktor: " + Datenspeicher.Teilerfaktor);
            System.out.println("Bank: " + Datenspeicher.whichBank());
            System.out.println("Register1 Bank0 (Timer): " + Datenspeicher.getRegister(0, 1));
            System.out.println("W-Register: " + decoder.W);
            //System.out.println("fsr-Register: " + Datenspeicher.PullDataFromRegister(4));
            System.out.println("wert1: " + Datenspeicher.PullDataFromRegister(12));
            System.out.println("wert2: " + Datenspeicher.PullDataFromRegister(13));
            //System.out.println("ergebnis: " + Datenspeicher.PullDataFromRegister(14));
            //System.out.println("F10: " + Datenspeicher.PullDataFromRegister(16));
            //System.out.println("F11: " + Datenspeicher.PullDataFromRegister(17));
            //System.out.println();
            System.out.println("Prescaler: " + Datenspeicher.prescaler);
            Datenspeicher.Flags();
            System.out.println();
        }
    }
}

