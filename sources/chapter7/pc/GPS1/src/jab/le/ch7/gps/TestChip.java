package jab.le.ch7.gps;

import jab.lejos.gps.GPS;

//import java.io.IOException;
//import java.util.Vector;
//import javax.bluetooth.*;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import java.io.*;

public class TestChip {

	public static void main(String[] args) {
		
		String BTMAC  = "000B0D00031A";
		System.out.println("Connecting");
		
		try{
			String connectionURL=null;
			connectionURL = "btspp://" + BTMAC + ":1";
	        //connect to the server and send a line of text
	        StreamConnection streamConnection=(StreamConnection)Connector.open(connectionURL);
	        System.out.println("Connected");
	        
	        InputStream dis = streamConnection.openInputStream();
	        
	        GPS gps = new GPS(dis);
	        gps.updateValues(true);
	        gps.start();
        
		}catch(Exception e){
			System.out.println(e.getMessage());
		}  
        System.out.println("FINISH");
	}
}
