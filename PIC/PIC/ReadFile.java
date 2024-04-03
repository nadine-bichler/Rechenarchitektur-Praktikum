package PIC;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class ReadFile {

    public static void main(String[] args){

        //Programmspeicher (Programmzähler = Index)
        int ROM [] = new int[1024];

        //CALL & GOTO auch in Programmspeicher?

        ArrayStack stack = new ArrayStack(8);
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
            decoder.execute(ROM[i]);
            System.out.println(decoder.W);
            decoder.Flags();
        }

        stack.push(0x10011010);
        System.out.println(stack.top());
        stack.pop();
        System.out.println(stack.top());
    }
}

