package PIC;

import java.util.Timer;
import java.util.TimerTask;

public class Laufzeitzaehler {
	private static Timer t1;
	private static int sek = 0;
	private static int min = 0;
	private static int std = 0;
	
	private static String sekAsString = "";
	private static String minAsString = "";
	private static String stdAsString = "";
	
	public static String time = "";
	
	public static void startTimer() {
		t1 = new Timer();
		
		t1.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				sek++;
				
				if(sek % 60 == 0) {
					min++;
					sek = 0;
					
					if(min % 60 == 0) {
						std++;
						min = 0;
					}
				}
				
				if(sek < 10) {
					sekAsString = "0" + sek;
				}
				else {
					sekAsString = sek + "";
				}
				
				if(min < 10) {
					minAsString = "0" + min;
				}
				else {
					minAsString = min + "";
				}
				if(std < 10) {
					stdAsString = "0" + std;
				}
				else {
					stdAsString = std + "";
				}
				
				
				time = stdAsString + ":" + minAsString + ":" + sekAsString;
				//System.out.println(time);
				GUI_Practice.timerLabel.setText(time);
			}
			
		}, 0, 1000);
	}
}


