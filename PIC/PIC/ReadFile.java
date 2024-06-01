package PIC;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {

    static int[] commandRow = new int[1024];						//neu

    public static void main(String[] args){

        //Programmspeicher
        int ROM [] = new int[1024];

        GUI_Practice g = new GUI_Practice();						//neu
        g.main();													//neu

        int y = -1;													//neu

        //CALL & GOTO auch in Programmspeicher?

        Decoder decoder = new Decoder();

        int x = 0;

        GUI_Practice.setContentToZero();							//neu
        while(GUI_Practice.fileLoaded == false) {					//neu
            System.out.println("");
        }

        try {
            //FileReader fileReader = new FileReader("//C:/Users/nbich/Documents/2/Rechnerarchitektur/TestProg_PicSim/TPicSim7.LST");
            FileReader fileReader = new FileReader(GUI_Practice.filepath);
            //D:/Rechenarchitekturen/TestProg_PicSim_20230413/TPicSim6.LST
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            //
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                y++;

                if (line.startsWith(" ")){
                    continue;
                }
                System.out.println(line);
                ROM [x] = Integer.parseInt(line.substring(5,9), 16);
                commandRow[x] = y;									//neu
                x++;

            }

            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //Test ob richtig in Array gespeichert

        //btfss Befehl, wieso fertig wenn timer = 0?

        while(GUI_Practice.runProgramm == false) {
            System.out.println("");
        }
        

        while(true){

            GUI_Practice.updateRow(commandRow[PC.get()]);	//nach oben verschoben
        	
        	while(GUI_Practice.runProgramm == false || GUI_Practice.checkBreakpoint(commandRow[PC.get()], 0) == true) {		//muss noch verändert werden
        		System.out.println("");
            }
            
            decoder.execute(ROM[PC.get()]);
            GUI_Practice.updateTabelle();
            
        }
    }
}

