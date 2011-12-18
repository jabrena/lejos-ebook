package modulos;

import lejos.nxt.*;
import javax.microedition.location.*;
import java.io.*;
import java.util.Properties;

public class WaypointFollower implements ProximityListener {

	public static final int MAX_SPEED = 900;
	public static final int MED_SPEED = 700;
	public static final int MIN_SPEED = 500;
	
	private static boolean waypointReached = false;
	
	public static void main(String[] args) {
		powerSteer(MAX_SPEED, MAX_SPEED);
		WaypointFollower proximityListener = new WaypointFollower();
		
		// Get an instance of the provider:
		LocationProvider lp = null;
		try {
			System.err.println("Connecting.. ");
			lp = LocationProvider.getInstance(null);
			System.err.println("CONNECTED");
		} catch(LocationException e) {
			System.err.println(e.getMessage());
			Button.waitForPress();
			System.exit(0);
		}
		
		// Load properties:
		Properties p = new Properties();
		File f = new File("waypoints.prop");
		if(f.exists()) {
			FileInputStream in;
			try {
				in = new FileInputStream(f);
				p.load(in);
				in.close();
			} catch (Exception e) {
				System.err.println("Failed to load waypoints");
				System.err.println(e.getMessage());
				Button.waitForPress();
				System.exit(0);
			}
		} else {
			System.out.println("No waypoints found");
			while(!Button.ESCAPE.isPressed()) {Thread.yield();}
			System.exit(0);
		}

		int waypointCount = Integer.parseInt(p.getProperty("waypoints"));
		for(int i=0;i<waypointCount;i++) {
			waypointReached = false;
			double latitude = Double.parseDouble(p.getProperty("lat" + i));
			double longitude = Double.parseDouble(p.getProperty("long" + i));
			Coordinates waypoint = new Coordinates(latitude, longitude);
			
			try {
				LocationProvider.addProximityListener(proximityListener, waypoint, 3);
			} catch (LocationException e) {
				System.out.println(e.getMessage());
				while(!Button.ESCAPE.isPressed()) {Thread.yield();}
				System.exit(0);
			}
			
			while(!waypointReached) {
				Location l;
				try {
					l = lp.getLocation(-1);
				} catch (Exception e) {
					System.err.println(e.getMessage());
					continue;
				}
				
				Coordinates current = l.getQualifiedCoordinates();
				double az = current.azimuthTo(waypoint);
				LCD.clearDisplay();
				LCD.drawString("LAT:" + current.getLatitude(), 0, 0);
				LCD.drawString("LONG:" + current.getLongitude(), 0, 1);
				LCD.drawString("TLAT:" + latitude, 0, 2);
				LCD.drawString("TLONG:" + longitude, 0, 3);
				LCD.drawString("AZ:" + az, 0, 4);
				double course = l.getCourse();
				LCD.drawString("COURSE:" + course, 0, 5);
				
				double diff = az - course;
				if (diff > 180) diff -= 360;
			    if (diff < -180) diff += 360;
			    LCD.drawString("DIFF:" + diff, 0, 6);
			    LCD.drawString("DIST:" + current.distance(waypoint), 0, 7);
			    LCD.refresh();
			    
			    if(diff < -90) {
			    	powerSteer(MIN_SPEED, MAX_SPEED);
			    } else if(diff < -10) {
			    	powerSteer(MED_SPEED, MAX_SPEED);
			    } else if(diff > 90) {
			    	powerSteer(MAX_SPEED, MIN_SPEED);
			    } else if(diff > 10) {
			    	powerSteer(MAX_SPEED, MED_SPEED);
			    } else {
			    	powerSteer(MAX_SPEED, MAX_SPEED);
			    }
			    // TODO: A LocationListener would get coordinates as
			    // soon as available. Less lag and resulting disorientation?
				try {Thread.sleep(500);} 
				catch(InterruptedException e){}
			}
		}
	}

	public static void powerSteer(int left, int right) {
		Motor.A.setSpeed((left + right)/2);
		Motor.B.setSpeed(right);
		Motor.C.setSpeed(left);
		
		Motor.A.backward();
		Motor.B.forward();
		Motor.C.forward();
	}
	
	public void monitoringStateChanged(boolean arg0) {}

	public void proximityEvent(Coordinates arg0, Location arg1) {
		Sound.beepSequenceUp();
		waypointReached = true;
	}	
}