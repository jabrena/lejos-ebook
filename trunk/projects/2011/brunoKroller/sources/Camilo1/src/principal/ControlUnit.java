package principal;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassSensor;
import modulos.GPSSensor;

public class ControlUnit implements ButtonListener{
	//Global Variables
	boolean enable = false;
	
	
	//Auxiliary classes
	void anyerror(int xcase){
		switch(xcase){
			case 1:
				System.err.println("Failed to connect GPS");
				Button.waitForPress();
				break;
			case 2:
				System.err.println("Ultrasonic Sensor failed to put in continuos mode");
				Button.waitForPress();
				break;
		}
	}
	
	//------------------------------ MAIN ---------------------------------
	public static void main(String args[]){
	
		//Variables
		
		ControlUnit listener = new ControlUnit();
		UltrasonicSensor uSsensor;
		CompassSensor cSensor;
		boolean state = false;
		int latitude = 0;
		int longitude = 0;
		int se = 0;
		
		//0-Initialize Buttons
		
		Button.ENTER.addButtonListener(listener);
		Button.RIGHT.addButtonListener(listener);
		Button.LEFT.addButtonListener(listener);
		System.out.println("ENTER = set");
		System.out.println("RIGHT = begin");
		System.out.println("ESCAPE = exit");
		
		
		/*
		GPSSensor gps= new GPSSensor(SensorPort.S3);
		int i=0;
		while(!state & i<100)
		{
			state=gps.linkStatus();
			i++;
		}
		if(!state)
		{
			listener.anyerror(1);
			System.exit(0); //EXIT
			
		}
		else{
			System.out.println("GPS ON");
			latitude=gps.getLatitude();
			longitude=gps.getLongitude();
			
		}
		*/
		System.out.println("-------");
		
		//Initialize Ultrasonic Sensor
		uSsensor= new UltrasonicSensor(SensorPort.S1);
		se=uSsensor.setMode(UltrasonicSensor.MODE_CONTINUOUS);
		if(se!=0)
		{
			listener.anyerror(2);
		}
		System.out.println("UltraSonic Sensor ON");
		Button.waitForPress(); 
		
		//Initialize Compass
		cSensor= new CompassSensor(SensorPort.S2);
		System.out.println("Compass ON");
		Button.waitForPress(); 
		//---------------------------------------------ALGORITM---------------------------------------------
		
		
		
		
		
	
	}//END MAIM
	
	
	
	//Listeners
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
		}
	
	
	
	}
	
}//END
