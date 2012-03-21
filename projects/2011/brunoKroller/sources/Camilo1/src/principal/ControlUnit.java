/*
 * Name:ControlUnit
 * Description: 
 * Author: Bruno Kröller da Silva
 * Date: 2011-2012
*/

package principal;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
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
		Navigate nav = new Navigate(1, 4, Motor.A, Motor.C);
				
		boolean fstate = false;
		int latitude = 0;
		int longitude = 0;
		int se = 0;
		double distance=0;
		int i = 0 ;
		//0-Initialize Buttons
		/*
		Button.ENTER.addButtonListener(listener);
		Button.RIGHT.addButtonListener(listener);
		Button.LEFT.addButtonListener(listener);
		System.out.println("ENTER = set");
		System.out.println("RIGHT = begin");
		System.out.println("ESCAPE = exit");
		*/
	
		//16 caracteres por linea en pantalla
		
		Button.LEFT.addButtonListener(listener);
		System.out.println("Hellow");
		
		/*
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
		System.out.println(gps.getUTC());
		System.out.println("----------------");
		*/
		
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
		
		/*LOG
		File data = new File("log.dat");
		int i2= 0;
		try{
			
			OutputStream osw = new FileOutputStream (data);
			DataOutputStream dos = new DataOutputStream(osw);
			System.out.println("FILE OK");
			
		}
		catch(IOException ioe)
		{
			System.err.println("FILE Error");
		}
		
		latitude=gps.getLatitude();
		longitude=gps.getLongitude();
		
		*/
		
		//---------------------------------------------ALGORITM---------------------------------------------
		
		
				
		while(!fstate)
		{
			nav.backward();
			distance = uSsensor.getDistance();
			if(distance<60)
			{
				nav.stop();
				//state=true;
				nav.Doodge(uSsensor);
				i++;
			}
			if(i==4)
				fstate=true;
		}
		
		
		Button.waitForPress(); 
		nav.stop();
	
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
