package jab.le.ch7;

import jab.le.ch7.io.NXTManager;

public class TeleoperatorTest1 {

	private static final int miliseconds = 1000;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		NXTManager nm = new NXTManager();
		
		String NXTBrick = "dog";
		nm.connect(NXTBrick);
		
		nm.sendCommand(NXTManager.FORWARD);
		try {Thread.sleep(miliseconds);} catch (Exception e) {}
		nm.sendCommand(NXTManager.BACKWARD);
		try {Thread.sleep(miliseconds);} catch (Exception e) {}
		nm.sendCommand(NXTManager.STOP);
		nm.sendCommand(NXTManager.DISCONNECT);
		
	}

}
