package jab.le.ch9.magellan;

import javax.microedition.location.Coordinates;
import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationException;
import javax.microedition.location.LocationProvider;
import javax.microedition.location.QualifiedCoordinates;

import lejos.nxt.addon.CompassMindSensor;

/**
 * 
 * This class use some ideas from other Open Source projects:
 * http://code.google.com/p/robomagellan/source/browse/dev/gps.c
 * http://code.google.com/p/robomagellan/source/browse/ANA/via/src/drivers/gps.c
 * 
 * @author jabrena
 *
 */
public class Magellan {

	private static LocationProvider lp = null;
	private static Location l = null;
	private static Coordinates current = null;
	
	private Criteria criteria = null;
	private boolean LPEnabled = false;
	
	public Magellan(Criteria _criteria){
		criteria = _criteria;
		
		// Get an instance of the provider:
		try {
			lp = LocationProvider.getInstance(criteria);
			LPEnabled = true;
			
		}catch(LocationException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public final double getDistance(){
		double distance = 0f;
		
		//double latitude, longitude = 0;
		//float altitude, horizontalAccuracy, verticalAccuracy = 0;
		//QualifiedCoordinates qc = new QualifiedCoordinates(latitude, longitude, altitude, horizontalAccuracy, verticalAccuracy);
		//Location from = new Location(qc, distance, distance, 0, 0, null);
		
		/*
		double latFrom = 0, lonFrom = 0;
		double latTo = 0, lonTo = 0;
		
		double[] madrid = {40.41705754418463, -3.703717589378357};
		double[] london = {51.499513113816974, -0.12516260147094727};
		
		Coordinates from = new Coordinates(madrid[0],madrid[1]);
		Coordinates to = new Coordinates(london[0],london[1]);
		
		distance = from.distance(to);
		*/
		
		double[] madrid = {40.41705754418463, -3.703717589378357};
		Coordinates to = new Coordinates(madrid[0],madrid[1]);	
		
		try {
			l = lp.getLocation(-1);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		//Get a coordinate object
		current = l.getQualifiedCoordinates();
		
		distance = current.distance(to);
		
		return distance;
	}
	
	private double getAzimuthTo(){
		
		double azimuth = 0f;
		
		double[] madrid = {40.41705754418463, -3.703717589378357};
		Coordinates to = new Coordinates(madrid[0],madrid[1]);	
		
		try {
			l = lp.getLocation(-1);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		//Get a coordinate object
		current = l.getQualifiedCoordinates();
		
		azimuth = current.azimuthTo(to);
		
		return azimuth;
	}
	
	public final long getTimestamp(){
		
		long timestamp = 0;
		
		try {
			l = lp.getLocation(-1);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		timestamp = l.getTimestamp();
		
		return timestamp;
	}
	
	public final double getRelativeHeading(CompassMindSensor compass ){
		
		double compassDegrees = 90;
		double target = this.getAzimuthTo();
		
	    double result = target - compassDegrees;
	    if (result < -180)
	    result += 360;
	    if (result > 180)
	    result -= 360;
	    
	    return result;
		
	}
	
}
