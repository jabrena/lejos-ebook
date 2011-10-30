package jab.le.ch3;

import lejos.nxt.Button;

public class HelloWorld {

	private static String message = "Hello World";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(message);
		Button.waitForAnyPress();
	}

}
