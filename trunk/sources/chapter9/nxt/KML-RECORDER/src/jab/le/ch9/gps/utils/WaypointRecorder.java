package jab.le.ch9.gps.utils;

import jab.le.ch9.gps.kml.KML;
import jab.le.ch9.gps.kml.Placemark;
import jab.le.ch9.io.FileManager3;

import java.util.Random;

import javax.microedition.location.Coordinates;

import lejos.nxt.Battery;
import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.util.Stopwatch;
import java.util.Date;

/**
 * 
 */

/**
 * @author juanantonio.bre�a
 *
 */
public class WaypointRecorder extends Thread{

	private Stopwatch sw;
	
	public static final int MODE_AUTO = 0;
	public static final int MODE_INTERACTIVE = 1;
	
	public static final String CRLF = "\r\n";
	private String name = "LeJOS Runner";
	private String description = "This file store KML Points stored in a leJOS Runner session";
	private String folderName = "LeJOS Runner Placemarks";
	private String folderDescription = "Placemarks generated in a working session";

	private KML k;

	private FileManager3 fm;
	private String text  = "";
	
	private Coordinates current;
	
	int seconds = 1000;
	int delay = 30 * seconds;//Every 60 secons (1 Minute), the system store a waypoint;

	long now = 0;
	
	int i = 0;
	
	int mode = 0;
	
	StringBuffer pDescription;
	
	public WaypointRecorder(){
		
		sw = new Stopwatch();
		k = new KML(name,description,folderName,folderDescription);

		String fileName ="lrw.kml";
		Random r = new Random();
		fileName = "lrw_" + r.nextInt(1000) + ".kml";
		fm = new FileManager3(fileName);
		fm.delete();

		text = k.getKMLHeader();
		fm.open();
		fm.add(text);
		fm.close();
		
		current = new Coordinates(0,0);
		now = 0;
	}
	
	public void setMode(int m){
		mode = m;
	}
	
	public void updateCoordinate(Coordinates c){
		current = c;
	}
	
	public void updateTimestamp(long ts){
		now = ts;
	}
	
	public final void run(){
		
		while(true){
			
			if(mode == 0){
				
				if(sw.elapsed() >= delay){

					i++;
					
					pDescription = new StringBuffer();
					pDescription.append(CRLF + "<![CDATA[" + CRLF);
					pDescription.append("<battery>");
					pDescription.append("" + Battery.getVoltageMilliVolt());
					pDescription.append("</battery>" + CRLF);
					pDescription.append("<freeMemory>");
					pDescription.append("" + System.getRuntime().freeMemory());
					pDescription.append("</freeMemory>" + CRLF);
					pDescription.append("<totalMemory>");
					pDescription.append("" + System.getRuntime().totalMemory());
					pDescription.append("</totalMemory>" + CRLF);
					pDescription.append("<time>");
					pDescription.append("" + now);
					pDescription.append("</time>" + CRLF);
					pDescription.append("]]>" + CRLF);
					
					Placemark p = new Placemark("waypoint"+i,pDescription.toString(),current.getLatitude(),current.getLongitude(),current.getAltitude());
					text = p.toString();
					
					//Update
					fm.open();
					fm.add(text);
					fm.close();

					sw.reset();
					
					Sound.beep();

				}				
			}else{
				
				//Action is done in update() method
				
			}

		}
	}
	
	public void update(){
		
		i++;
		
		pDescription = new StringBuffer();
		pDescription.append(CRLF + "<![CDATA[" + CRLF);
		pDescription.append("<battery>");
		pDescription.append("" + Battery.getVoltageMilliVolt());
		pDescription.append("</battery>" + CRLF);
		pDescription.append("<freeMemory>");
		pDescription.append("" + System.getRuntime().freeMemory());
		pDescription.append("</freeMemory>" + CRLF);
		pDescription.append("<totalMemory>");
		pDescription.append("" + System.getRuntime().totalMemory());
		pDescription.append("</totalMemory>" + CRLF);
		pDescription.append("<time>");
		pDescription.append("" + now);
		pDescription.append("</time>" + CRLF);
		pDescription.append("]]>" + CRLF);
		
		Placemark p = new Placemark("waypoint"+i,pDescription.toString(),current.getLatitude(),current.getLongitude(),current.getAltitude());
		text = p.toString();
		
		//Update
		fm.open();
		fm.add(text);
		fm.close();
		
		Sound.beepSequence();
		
	}
	
	public final void close(){
		fm.open();
		text = k.getKMLFooter();
		fm.add(text);
		fm.close();
	}
}
