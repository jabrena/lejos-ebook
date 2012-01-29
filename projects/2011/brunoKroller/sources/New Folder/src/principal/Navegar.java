package principal;

import lejos.nxt.*;
import lejos.robotics.navigation.*;

public class Navegar extends DifferentialPilot{
	
	//Variables globales
	private DifferentialPilot pilot;
	
	
	//constructor
	public Navegar(double dr1,double dr2){
		super(dr1,dr1,Motor.A,Motor.C,true);
				
	}
	
	 public void esquivar()
	 {
		 pilot.rotateRight(); //Turn Right
		 pilot.travel(100); //In cm
		 pilot.stop();
		 
	 }
	
	
	

}
