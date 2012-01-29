package principal;

import lejos.nxt.*;
import lejos.nxt.addon.CompassSensor;
import modulos.GPSSensor;	 //GPS
import java.io.*;			//FILE
import java.util.Properties;
import java.util.Vector;

public class Principal implements ButtonListener {

	//Variables Globals
	Boolean enable=false;
		
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
	
	public static void main(String args[]){
		
		//Variables
		Principal listener = new Principal();
		int latitude=0;
		int longitude=0;
		String nfichero= "DATOS.txt";
		UltrasonicSensor uss1;
		UltrasonicSensor uss2;
		int se = 0;
		CompassSensor cS;
		Navegar nav = new Navegar(4,4);
		
		
		//--File
		File datos= null;
		Writer wt=null;
		Reader rd=null;
		BufferedWriter bw=null;
		//--Boolean
		Boolean state=false;
		
		
		
		//0-Initialize Buttons
		Button.ENTER.addButtonListener(listener);
		Button.RIGHT.addButtonListener(listener);
		Button.LEFT.addButtonListener(listener);
		System.out.println("ENTER = set");
		System.out.println("RIGHT = begin");
		System.out.println("ESCAPE = exit");
		
		
		//1- Initialize sensors y motors
		
		//Initialize GPS
		GPSSensor gps= new GPSSensor(SensorPort.S3);
		state=gps.linkStatus();
		if(!state)
		{
			listener.anyerror(1);
			System.exit(0); //EXIT
			
		}
		else{
			latitude=gps.getLatitude();
			longitude=gps.getLongitude();
			
		}

		//Initialize Ultrasound Sensor
		uss1= new UltrasonicSensor(SensorPort.S1);
		se=uss1.setMode(uss1.MODE_CONTINUOUS);
		if(se!=0)
		{
			listener.anyerror(2);
		}
		
		
		//Initialize Compass
		cS= new CompassSensor(SensorPort.S2);
		
		//---------------------------------------------ALGORITM---------------------------------------------
		
		//Load file with destination properties
		Properties p = new Properties();
		File f = new File("destination");
		if(f.exists())
		{
			FileInputStream in;
			try{
				in = new FileInputStream(f);
				p.load(in);
				in.close();
			}catch(Exception e){
				System.err.println("Failed to load destination");
				System.err.println(e.getMessage());
				Button.waitForPress();
				System.exit(0);
			}
		}
		else{
			System.out.println("No destination found");
			Button.waitForPress();
			System.exit(0);
		}
		//Get destination coordinates
		int lat = Integer.parseInt(p.getProperty("lat"));
		int lng = Integer.parseInt(p.getProperty("long"));
		//Set destination coordinates
		gps.setLatitude(lat);
		gps.setLongitude(lng);
		
		//Get Angel to destination
		float angel_dest = gps.getAngleToDest();
		if(angel_dest!=0)
		{
			System.err.println("Failed to get angel to destination");
			Button.waitForPress();
		}
		float angel_curent = cS.getDegreesCartesian(); //falta control de error

		
		//Get actual position	
		int latAct;
		int lngAct;
				
		latAct=gps.getLatitude();
		lngAct=gps.getLongitude();
		
		
		//Distans to target
		int distTarg;
		distTarg=gps.getDistanceToDest();
		
		//Waypoint set
		//int WayPnt0=(int) Math.sqrt(Math.pow((lat-latAct), 2)+Math.pow((lng-lngAct), 2));
		
		int WayPnt1= distTarg/4;
		int WayPnt2= distTarg/2;
		int WayPnt3= 3*distTarg/4;
		int WayPnt4= 0;
		
		while(gps.getDistanceToDest()!=WayPnt1)
		{
			nav.forward();
			if(uss1.getDistance()<100)
			{
				nav.esquivar();
			}
		}
		
		
		
		
		
		
	}//END OF MAIN

	
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
	
}
