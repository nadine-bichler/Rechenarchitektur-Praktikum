package PIC;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {

    public static void main(String[] args){

        //Programmspeicher
        int ROM[] = new int[1024];

        //CALL & GOTO auch in Programmspeicher?

        Decoder decoder = new Decoder();

        int x = 0;
        try {

            FileReader fileReader = new FileReader("C:/Users/nbich/Documents/2/Rechnerarchitektur/TestProg_PicSim/TPicSim4.LST"); //C:/Users/thuyl/Desktop/TestProg_PicSim_20230413/TPicSim1.LST
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
        while(true){
            decoder.execute(ROM[PC.get()]);
            System.out.println(decoder.W);
            Datenspeicher.Flags();
        }
    }
}

