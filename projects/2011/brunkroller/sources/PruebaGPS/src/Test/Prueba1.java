package Test;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.addon.CompassSensor;



public class Prueba1 implements ButtonListener{

	static boolean enable=false;
	
	public static void main(String args[]){
		
		boolean state=false;
		CompassSensor cSensor;
		
		System.out.println("Linking GPS..   please wait");
		GPSSensor gps= new GPSSensor(SensorPort.S3);
		
		while(!state) {
			//Waiting for connection
			if(gps.linkStatus()){
				Sound.beep();
				state=true;
			}else // Connection success
			{
				System.out.println(gps.getUTC());
				System.out.println("Waiting for GPS");
				
				
			}
			try {Thread.sleep(10000);} //Wait 10.000 miliseconds
			catch (InterruptedException e) {	}			
		}
		
		System.out.println("     GPS ON");
		cSensor= new CompassSensor(SensorPort.S2);
		System.out.println("Compass ON");
		gps.setLongitude(-3779521);
		gps.setLatitude(40661968);
		
		
		
		int longitude = gps.getLongitude();
		int latitude = gps.getLatitude();
		int head= gps.getHeading();
		while(!enable)
		{
			longitude = gps.getLongitude();
			latitude = gps.getLatitude();
			head= gps.getHeading();
			System.out.println("----------------");
			System.out.println(longitude);
			System.out.println(latitude);
			//System.out.println(head);
			System.out.println(cSensor.getDegrees());
			System.out.println(gps.getAngleToDest());
			System.out.println(gps.getDistanceToDest());
					
			ButtonListener listener = null;
			Button.ENTER.addButtonListener(listener);
			Button.RIGHT.addButtonListener(listener);
			Button.LEFT.addButtonListener(listener);
			Button.waitForPress(); 
			
		}
		
		
		System.out.println("----------------");
		System.out.println(longitude);
		System.out.println(latitude);
		
		
	}
	public void buttonPressed(Button b) {}

	
	public void buttonReleased(Button b) {
		switch(b.getId()){
		case Button.ID_ENTER:
			
			break;
		case Button.ID_RIGHT:
			enable=!enable;
			break;
		case Button.ID_ESCAPE:
			System.exit(0);
			break;
		case Button.ID_LEFT:
			break;
		}
	
	
	
	}
}
