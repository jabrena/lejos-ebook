package jab.le.ch7.gps;


import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import lejos.addon.gps.*;

public class TestChip2 {

	public static double latitude;

	public static void main(String[] args) {
		
		String BTMAC  = "000B0D00031A";
		
		BTMAC = "000B0D8E05C7";
		
		System.out.println("Connecting");
		
		int errorCount = 0;
		
		try{
			String connectionURL=null;
			connectionURL = "btspp://" + BTMAC + ":1";
	        StreamConnection streamConnection=(StreamConnection)Connector.open(connectionURL);
	        System.out.println("Connected");
	        
	        InputStream dis = streamConnection.openInputStream();
	        
	        GPS gps = new GPS(dis);
	        //gps.updateValues(true);
	        gps.start();
	        latitude = gps.getLatitude();
	        
	        System.out.println(latitude);
        
		}catch(Exception e){
			
			errorCount++;
			System.err.println(e.getMessage());
			System.err.println(errorCount);
		}  
        System.out.println("FINISH");
	}
}

