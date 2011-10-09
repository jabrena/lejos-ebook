package jab.le.ch7.io;


import java.io.IOException;

import lejos.nxt.Motor;
import lejos.nxt.remote.NXTCommand;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTConnector;

public class NXTManager {

	public static final int FORWARD = 1;
	public static final int BACKWARD = 2;
	public static final int STOP = 3;
	public static final int DISCONNECT = 4;
	public static final int TURN_LEFT = 5;
	public static final int TURN_RIGHT = 6;
	
	private NXTConnector conn;
	
	public NXTManager(){
	    conn = new NXTConnector();
	}
	
	public void connect(String NXTBrick){
	    if (!conn.connectTo(NXTBrick, NXTComm.LCP)) {
		      System.err.println("Connection failed");
		      System.exit(1);
		    }
		    NXTCommand.getSingleton().setNXTComm(conn.getNXTComm());
	}
	
	public void calibrateRobot(){
	    Motor.A.stop();
	    Motor.C.stop();
	    Motor.A.resetTachoCount();
	    Motor.C.resetTachoCount();
	}
	
	public void sendCommand(int command){
		if(command == NXTManager.FORWARD){
			
			System.out.println("Forward");
			
		    Motor.A.forward();
		    Motor.C.forward();
		    
		}else if(command == NXTManager.BACKWARD){
			
			System.out.println("Backward");
			
		    Motor.A.backward();
		    Motor.C.backward();
		    
		    
		}else if(command == NXTManager.STOP){
			
			System.out.println("Stop");
			
		    Motor.A.stop();
		    Motor.C.stop();
		    
		}else if(command == NXTManager.TURN_LEFT){
			
		    Motor.A.stop();
		    Motor.C.forward();	
		    
		}else if(command == NXTManager.TURN_RIGHT){
		    Motor.A.forward();
		    Motor.C.stop();	
		    
		}else if(command == NXTManager.DISCONNECT){
			disconnect();

		}else{
			System.out.println("Command not available: " + command);
		}
	}
	
	public void disconnect(){
		try {

			NXTCommand.getSingleton().close();
			conn.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
