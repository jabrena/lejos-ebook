package modulos;

import lejos.nxt.*;
import modulos.GPSSensor;

public class GeoCache implements ButtonListener {
	static GPSSensor gps;
	static boolean enabled = false;
	static float start_dist;
	
	static final int LO = 200;
	static final int HI = 2800;
	
	public static void main(String[] args) throws Exception {
		gps = new GPSSensor(SensorPort.S1);
		GeoCache listener = new GeoCache();
		Button.ENTER.addButtonListener(listener);
		Button.RIGHT.addButtonListener(listener);
		Button.ESCAPE.addButtonListener(listener);
		System.out.println("ENTER = set");
		System.out.println("RIGHT = begin");
		System.out.println("ESCAPE = exit");
		
		while(true) {
			if(enabled) {
				float distance = gps.getDistanceToDest();
				float percent = (distance/start_dist);
				int freq = (int)(LO + (HI - (percent * HI)));
				System.out.println("Dist:" + distance);
				Sound.playTone(freq, 200);
			}
			Thread.sleep(1100);			
		}		
	}

	public void buttonPressed(Button b) {}

	public void buttonReleased(Button b) {
		switch(b.getId()) {
		case Button.ID_ENTER:	
			gps.setLatitude(gps.getLatitude());
			gps.setLongitude(gps.getLongitude());
			System.out.println("Target set!");
			break;
		case Button.ID_RIGHT:
			start_dist = gps.getDistanceToDest();
			enabled = !enabled;
			break;
		case Button.ID_ESCAPE:
			System.exit(0);			
		}
	}
}