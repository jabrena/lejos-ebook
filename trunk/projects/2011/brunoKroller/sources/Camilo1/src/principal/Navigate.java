package principal;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;



public class Navigate extends DifferentialPilot{

	public Navigate(double wheeDiam, double tracKWid, RegulatedMotor leftMot, RegulatedMotor rightMot)
	{
		super(wheeDiam,tracKWid,leftMot,rightMot);
	}
	
	
	public void Doodge(UltrasonicSensor uSsensor) 
	{
		ScanArea sa = new ScanArea();
		
		int rot=0;
				
		this.stop();
		
		
		rot=sa.barrido(uSsensor);
		
		this.rotate(-rot-15);
				
	}
	
}
