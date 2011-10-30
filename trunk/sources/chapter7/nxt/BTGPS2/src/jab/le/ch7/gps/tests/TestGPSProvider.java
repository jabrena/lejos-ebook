package jab.le.ch7.gps.tests;

import javax.microedition.location.Criteria;
import javax.microedition.location.LocationException;
import javax.microedition.location.LocationProvider;

public class TestGPSProvider {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Criteria criteria = new Criteria();
		
		try {
			LocationProvider.getInstance(criteria);
		} catch (LocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
