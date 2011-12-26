package jab.le.ch7.gps.ideas;

import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.addon.GPSSensor;

public class DGPSTest {

	static GPSSensor gps;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		gps = new GPSSensor(SensorPort.S1);
		
		while(true) {
			if(gps.linkStatus()){
				Sound.beep();
			}else{
				System.out.println(gps.getUTC());
			}
			try {Thread.sleep(10000);} catch (InterruptedException e) {	}			
		}
	}

}
