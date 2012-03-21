package principal;


import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;

public class ScanArea {
	
	
	//array to store the distances
	private  int a=0;
	private int b=0;
	private int i=0;
	private int grads=-90;
	
	
	public ScanArea(){
		
	}
	 
	public int barrido(UltrasonicSensor uss)
	{
		//Motor turn the ultrasonic sensor left
		Motor.B.rotateTo(grads);
		
		//Start Scanning the area to see the distances
		
		for(i=1;i<11;i++)
		{
				b=a;
				a=uss.getDistance();
				waits(1);
				Motor.B.rotate(20);
			if(a >225)
			{
				break;
			}
	
								
		}

			
		//Goes back to initial angel	
		Motor.B.rotateTo(0);
		//Gives back the degrees to turn
		switch(i)
		{
		case 0: return -90;
		case 1: return -70;
		case 2: return -50;
		case 3: return -30;
		case 4: return -10;
		case 5: return 10;
		case 6: return 30;
		case 7: return 50;
		case 8: return 70;
		case 9: return 90;
		case 10: return 110;
		default: return 0;
		}
	}
		
	public  void waits (int n)
	{
	        long t0,t1;
	        t0=System.currentTimeMillis();
	        do{
	            t1=System.currentTimeMillis();
	        }
	        while (t1-t0<1000);
	}
		
		
	

	
}
