package jab.le.ch7;

import java.io.IOException;

import lejos.pc.comm.*;

public class BluetoothBrickSearcher {

	public static void main(String[] args) {

        NXTComm nxtComm;
        
		try {
			nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
       
	        NXTInfo[] nxtInfo = null;
	       
	        try {
	              nxtInfo = nxtComm.search(null, NXTCommFactory.BLUETOOTH);
	        } catch (NXTCommException e) {
	              System.out.println("Failure in search");
	        }
	       
	        if (nxtInfo.length == 0) {
	              System.out.println("NXT not found");
	              System.exit(1);
	        }
	
	        try {
	              nxtComm.open(nxtInfo[0]);
	              
	        	  System.out.println("Connecting to " + nxtInfo[0].connectionState);
	              
	        } catch (NXTCommException e) {
	              System.out.println("Failure in connection");
	        }
	        
            try {
                nxtComm.close();
            } catch (IOException ioe) {}
        
		} catch (NXTCommException e1) {
			e1.printStackTrace();
			System.out.println("Error");
		}
	}

}
