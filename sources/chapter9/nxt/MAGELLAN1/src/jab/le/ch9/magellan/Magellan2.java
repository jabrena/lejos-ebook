package jab.le.ch9.magellan;

import javax.microedition.location.Coordinates;
import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationException;
import javax.microedition.location.LocationProvider;


/**
 * 
 * Ideas from:
 * http://www.deanandara.com/Argonaut/Sensors/Gps/GettingData.html
 * 
 * @author jabrena
 *
 */
public class Magellan2 {

	private LocationProvider lp = null;
	private Location l = null;
	private Coordinates current = null;
	
	//private Criteria criteria = null;
	//private boolean LPEnabled = false;
	
	public static final double MERCATOR = 6378137.0;
	
	public Magellan2(Criteria criteria) throws LocationException{
		//criteria = _criteria;
		
		// Get an instance of the provider:
		//try {
			lp = LocationProvider.getInstance(criteria);
			//LPEnabled = true;
			
		//}catch(LocationException e) {
			//throws new LocationException();
			//System.err.println(e.getMessage());
		//}
	}
	
	private Coordinates getCurrentPosition(){
		try {
			l = lp.getLocation(-1);
		} catch (Exception e) {
			//System.err.println(e.getMessage());
		}
		
		//Get a coordinate object
		current = l.getQualifiedCoordinates();
		
		return current;
	}
	
	/*
	def calcBearing(lat1, lon1, lat2, lon2):
	    dLon = lon2 - lon1
	    y = math.sin(dLon) * math.cos(lat2)
	    x = math.cos(lat1) * math.sin(lat2) \
	        - math.sin(lat1) * math.cos(lat2) * math.cos(dLon)
	    return math.atan2(y, x)
	*/
	
	public final double getBearing(final double lat2, final double lon2){
		
		double bearing = 0;
		
		Coordinates currentPosition = getCurrentPosition();
		double lat1 = currentPosition.getLatitude();
		double lon1 = currentPosition.getLongitude();
		
	    double dLon = lon2 - lon1;
	    double y = Math.sin(dLon) * Math.cos(lat2);
	    double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon);
	    
	    bearing = Math.atan2(y, x);
		
		return bearing;
	}
	
	//(6378137.0 + 1579.0)
	
	/*
	def havDistance(lat1, lon1, lat2, lon2):
	    dLat = lat2 - lat1
	    dLon = lon2 - lon1
	    a = math.sin(dLat / 2) * math.sin(dLat / 2) \
	        + math.cos(lat1) * math.cos(lat2) \
	        * math.sin(dLon / 2) * math.sin(dLon / 2);
	    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
	    return R * c
	*/
	
	private double getHarversineDistance(double lat1,double lon1,double lat2,double lon2){
		
		double distance = 0;
		
	    double dLat = lat2 - lat1;
	    double dLon = lon2 - lon1;
	    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
	        + Math.cos(lat1) * Math.cos(lat2)
	        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    
	    double R = MERCATOR + 1579.0;
	    distance = R * c;
	    
	    return distance;
	}

	/*
	def slcDistance(lat1, lon1, lat2, lon2):
	    return math.acos(math.sin(lat1) * math.sin(lat2)
	                     + math.cos(lat1) * math.cos(lat2)
	                     * math.cos(lon2 - lon1)) * R
	*/
	
	private double getSLCDistance(double lat1,double lon1,double lat2,double lon2){
		
		double distance = 0;
		
	    double R = MERCATOR + 1579.0;
	    distance = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.cos(lon2 - lon1)) * R;
	    
	    return distance;
	}
	
	public final double getDistance(double lat2, double lon2){
		
		double distance = 0;
		

		Coordinates currentPosition = getCurrentPosition();
		double lat1 = currentPosition.getLatitude();
		double lon1 = currentPosition.getLongitude();
		
		//Using Harvesine formula
		//http://en.wikipedia.org/wiki/Haversine_formula
	    distance = getHarversineDistance(lat1, lon1, lat2, lon2);
		
		return distance;
		
	}
	
	/*
	def calcTurn(Btarget, Bcurrent):
	    """Returns tuple (turn angle [rads], turn dir [+1 == right, -1 == left])."""

	    diff = Btarget - Bcurrent
	    neg = diff < 0
	    big = abs(diff) > PI

	    if not neg and not big: theta = diff; lr = +1
	    if not neg and big: theta = 2*PI - diff; lr = -1
	    if neg and not big: theta = abs(diff); lr = -1
	    if neg and big: theta = 2*PI - abs(diff); lr = +1

	   return (theta, lr)
	*/
	
	public final double getTurnAngle(int bearingCurrent, int bearingTarget){
		
		double theta = 0;
		int lr;
		
	    int diff = bearingTarget - bearingCurrent;
	    boolean neg = diff < 0;
	    boolean big = Math.abs(diff) > Math.PI;

	    if ((!neg) && (!big)){ 
	    	theta = diff; 
	    	lr = +1;
	    }
	    if ((!neg) && (big)){
	    	theta = 2*Math.PI - diff;
	    	lr = -1;
	    }
	    if ((neg) && (!big)){
	    	theta = Math.abs(diff); 
	    	lr = -1;
	    }
	    if (neg && big){
	    	theta = 2*Math.PI - Math.abs(diff); 
	    	lr = +1;
	    }
		
		return theta;
		
	}
	
}
