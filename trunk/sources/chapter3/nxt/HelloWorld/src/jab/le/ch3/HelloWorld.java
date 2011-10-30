package jab.le.ch3;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class HelloWorld {

	private static String message = "Hello World";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(message);
		Button.waitForAnyPress();
		credits(2);
		System.exit(0);
	}

	private static void credits(int seconds){
		LCD.clear();
		LCD.drawString("LEGO Mindstorms",0,1);
		LCD.drawString("NXT Robots  ",0,2);
		LCD.drawString("run better with",0,3);
		LCD.drawString("Java leJOS",0,4);
		LCD.drawString("www.lejos.org",0,6);
		LCD.refresh();
		try {Thread.sleep(seconds*1000);} catch (Exception e) {}
	}
}
