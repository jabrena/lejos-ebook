package jab.le.ch9.io.tests;
import lejos.nxt.*;


import jab.le.ch9.gps.kml.KML;
import jab.le.ch9.gps.kml.Placemark;
import jab.le.ch9.io.FileManager3;

import java.io.*;
import java.lang.StringBuffer;
import java.util.Random;

/**
 * 
 * Test of leJOS NXT File System.
 * 
 * The example creates a file into leJOS NXT File System. 
 * In this case the file, is a KML file used by Google Earth.
 * If you use the command nxjbrowse, you could
 * download that file and to use with Google Earth.
 * 
 * 2008/04/18
 * Current version has problems when increase the size of the file.
 * 
 * @author Juan Antonio Brenha Moral, JAB
 *
 */

public class FileTest3 {

	public static void main(String[] args)throws Exception {
    	String fileName = "test.kml";
    	String text = "";

        LCD.drawString("Testing leJOS FS",0,0);
        LCD.refresh();

        String name = "KML Sample generated by NXT Brick";
        String description = "NXJ Example to use leJOS NXT File System";
        String folderName = "Placemarks";
        String folderDescription = "Placemarks generated by my NXT Brick";

        KML k = new KML(name,description,folderName,folderDescription);

    	Random r = new Random();
    	fileName = "gps_" + r.nextInt(1000) + ".kml";
    	fileName ="lrw.kml";

        FileManager3 fm;
        fm = new FileManager3(fileName);
        fm.delete();

        //System.exit(0);
        
        LCD.drawString("KML Header",0,3);
        LCD.refresh();
        fm.open();
        text = k.getKMLHeader();
        fm.add(text);
        fm.close();

        for(int i=0;i<=500;i++){
            LCD.drawString("Waypoint #"+i,0,3);
            LCD.refresh();
	        fm.open();
	        Placemark p = new Placemark("waypoint"+i,"waypoint"+i,4027.8076,328.69948);
	        text = p.toString();
	        fm.add(text);
	        fm.close();
        }

        LCD.drawString("                     ",0,3);
        LCD.drawString("KML Footer",0,3);
        LCD.refresh();
        fm.open();
        text = k.getKMLFooter();
        fm.add(text);
        fm.close();

        LCD.drawString("File created",0,5);
        LCD.drawString("Test finished",0,6);
        Thread.sleep(1000);
    }
}