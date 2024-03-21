package PIC;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ReadFile {

    public static void main(String[] args){

        //Programmspeicher (Programmzähler = Index)
        int ROM [] = new int[1024];

        //Working Register
        int W = 0;;

        Decoder decoder = new Decoder();

        int x = 0;

        try {

            FileReader fileReader = new FileReader("C:/Users/nbich/Documents/2/Rechnerarchitektur/TestProg_PicSim/TPicSim1.LST"); //C:/Users/thuyl/Desktop/TestProg_PicSim_20230413/TPicSim1.LST
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
        for (int i = 0; i < x; i++){
            System.out.println(ROM[i]);
        }
    }
}

