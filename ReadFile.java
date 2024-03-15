import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ReadFile {

    public static void main(String[] args){

        ArrayList<String> ROM = new ArrayList<String>();

        try {

            FileReader fileReader = new FileReader("C:/Users/nbich/Documents/2/Rechnerarchitektur/TestProg_PicSim/TPicSim1.LST");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith(" ")){
                    continue;
                }
                System.out.println(line);
                ROM.add(line.substring(5,9));
            }

            bufferedReader.close();
            } 
        catch (IOException e) {
            e.printStackTrace();
            }
        
        Iterator<String> iter = ROM.iterator();
        while (iter.hasNext()){
            String command = iter.next();
            System.out.print(command + " ");
            System.out.println(operator(command) + " " + adress(command));
        }
    }

    public static int operator (String x){
        String hex = x.substring(0,2);
        int command = Integer.parseInt(hex, 16);
        switch (command){
            case 48:
                System.out.print("movlw ");
                break;
            case 57:
                System.out.print("andlw ");
                break;
            case 56:
                System.out.print("iorlw ");
                break;
            case 60:
                System.out.print("sublw ");
                break;
            case 58:
                System.out.print("xorlw ");
                break;
            case 62:
                System.out.print("addlw ");
                break;
            case 40:
                System.out.print("goto ");
                break;
        }
        return command;
    }

    public static int adress (String x){
        String hex = x.substring(2,4);
        int adress = Integer.parseInt(hex, 16);
        return adress;
    }
}

