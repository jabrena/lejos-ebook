package principal;

import lejos.nxt.*;
import lejos.robotics.navigation.*;

public class Navegar {
	
	//Variables globales
	private DifferentialPilot pilot;
	
	
	//constructor
	public Navegar(double dr1,double dr2){
		pilot=new DifferentialPilot(dr1,dr1,Motor.A,Motor.C,true);
		
		
	}
	
	
	
	

}
