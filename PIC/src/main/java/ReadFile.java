import src.main.java.GUI_Practice;

public class ReadFile {

    public static void main(String[] args){

        GUI_Practice g = new GUI_Practice();						//neu
        g.main();													//neu

        while(GUI_Practice.fileLoaded == false) {					//neu
            System.out.println("");
        }

        GUI_Practice.updateTabelle();

        while(GUI_Practice.runProgramm == false) {
            System.out.println("");
        }
    }
}

