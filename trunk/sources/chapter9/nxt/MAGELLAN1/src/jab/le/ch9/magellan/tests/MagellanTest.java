package jab.le.ch9.magellan.tests;

import jab.le.ch9.magellan.Magellan;

import javax.microedition.location.Coordinates;
import javax.microedition.location.Criteria;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassMindSensor;
import lejos.util.Delay;

public class MagellanTest {

	private static long iteration = 0;
	
	private static final double KM = 1000;
	
	private static final int oneSecond = 1000;
	
	public static void main(String[] args){
		
		Criteria criteria = new Criteria();
		
		double[] madrid = {40.41705754418463, -3.703717589378357};
		double[] london = {51.499513113816974, -0.12516260147094727};
		double[] paris = {48.87401614213272, 2.295691967010498};
		double[] berlin = {52.54318996285548, 13.405380249023438};
		double[] taipei = {25.149790941461944, 121.78018569946289};
		double[] tokyo = {35.41535532818056, 139.62318420410156};
		double[] sanfrancisco = {37.812767557570204, -122.47824668884277};
		double[] seychelles = {-4.64760483755757, 55.5523681640625};
		double[] mauritius = {-20.34462694382967, 57.23876953125};

		Coordinates[] waypoints = new Coordinates[9];
		waypoints[0] = new Coordinates(madrid[0],madrid[1]);
		waypoints[1] = new Coordinates(london[0],london[1]);
		waypoints[2] = new Coordinates(paris[0],paris[1]);
		waypoints[3] = new Coordinates(berlin[0],berlin[1]);
		waypoints[4] = new Coordinates(taipei[0],taipei[1]);
		waypoints[5] = new Coordinates(tokyo[0],tokyo[1]);
		waypoints[6] = new Coordinates(sanfrancisco[0],sanfrancisco[1]);
		waypoints[7] = new Coordinates(seychelles[0],seychelles[1]);
		waypoints[8] = new Coordinates(mauritius[0],mauritius[1]);		
		
		Magellan magellan = new Magellan(criteria);
		
		double distance = 0f;
		double heading = 0f;
		
		CompassMindSensor compass;
		compass = new CompassMindSensor(SensorPort.S1);
		
		while(!Button.ESCAPE.isDown()){
			
			iteration ++;

			distance = magellan.getDistance();
			distance = (distance/KM);
			
			System.out.println("Iteration: " + iteration);
			System.out.println("Distance: " + distance);
			
			heading = magellan.getRelativeHeading(compass);
			
			System.out.println("Heading: " + heading);
			
			System.out.println("TS: " + magellan.getTimestamp());

			Delay.msDelay(oneSecond);
			
			LCD.clearDisplay();			

		}
		
	}
}
