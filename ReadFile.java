import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ReadFile {

    public static void main(String[] args){

        ArrayList<Integer> ROM = new ArrayList<Integer>();

        try {

            FileReader fileReader = new FileReader("C:/Users/nbich/Documents/2/Rechnerarchitektur/TestProg_PicSim/TPicSim1.LST");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith(" ")){
                    continue;
                }
                System.out.println(line);
                String hex = line.substring(5,9);
                ROM.add(Integer.parseInt(hex, 16));
            }

            bufferedReader.close();
            } 
        catch (IOException e) {
            e.printStackTrace();
            }
        
        Iterator<Integer> iter = ROM.iterator();
        while (iter.hasNext()){
            System.out.print(iter.next() + " ");
        }
    }
}

