import src.main.java.GUI_Practice;

public class ReadFile {

    public static void main(String[] args){

        GUI_Practice g = new GUI_Practice();
        g.main();

        while(GUI_Practice.fileLoaded == false) {
            System.out.println("");
        }

        GUI_Practice.updateTabelle();

        while(GUI_Practice.runProgramm == false) {
            System.out.println("");
        }
    }
}

