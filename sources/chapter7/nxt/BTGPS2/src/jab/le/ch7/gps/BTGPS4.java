package jab.le.ch7.gps;

import jab.lejos.gps.GPS;
//import jab.lejos.gps.Date;

//import java.io.IOException;
import java.io.InputStream;
//import java.util.Date;
import java.util.Vector;

import javax.bluetooth.RemoteDevice;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.util.TextMenu;

public class BTGPS4 {

	//Inquire code
	private static int cod = 0; // 0 picks up every Bluetooth device regardless of Class of Device (cod).

	//Bluetooth
	private static RemoteDevice GPSDevice = null;
	private static GPS gps = null;
	private static InputStream in = null;

	//GPS Pin
	private static final byte[] pin = {(byte) '0', (byte) '0', (byte) '0', (byte) '0'};

	//GPS Data
	private static java.util.Date now;
	
	private static long tics = 0;
	
	private static int miliseconds = 2000;
	
	/**
	 * This method, show all BT Devices with BT Services enable
	 * User choose a GPS device to connect
	 * 
	 * Developer note: This method has a bug when you click in exit button twice
	 */
	static boolean discoverBTDevices(){
		boolean GPSDetected = false;
		
		LCD.clear();
		LCD.drawString("Searching...", 0, 0);
		LCD.refresh();
		//Make an BT inquire to get all Devices with BT Services enable
		Vector devList = Bluetooth.inquire(5, 10,cod);

		//If exist GPS Devices near
		if (devList.size() > 0){
			String[] names = new String[devList.size()];
			for (int i = 0; i < devList.size(); i++) {
				RemoteDevice btrd = ((RemoteDevice) devList.elementAt(i));
				names[i] = btrd.getFriendlyName(true);
			}
				
			TextMenu searchMenu = new TextMenu(names,1);
			String[] subItems = {"Connect"};
			TextMenu subMenu = new TextMenu(subItems,4);
			
			int selected;
			do {
				LCD.clear();
				LCD.drawString("Found",6,0);
				LCD.refresh();
				//Menu 1: Show all BT Devices
				selected = searchMenu.select();
				if (selected >=0){
					RemoteDevice btrd = ((RemoteDevice) devList.elementAt(selected));
					LCD.clear();
					LCD.drawString("Found",6,0);
					LCD.drawString(names[selected],0,1);
					LCD.drawString(btrd.getBluetoothAddress(), 0, 2);
					//Menu 2: Show GPS Device
					int subSelection = subMenu.select();
					if (subSelection == 0){
						GPSDetected = true;
						GPSDevice = btrd;
						break;
					}
				}
			} while (selected >= 0);
		}else{
			GPSDetected = false;
		}

		return GPSDetected;
	}
	
	/**
	 * This method connect with a RemoteDevice.
	 * If the connection has success then the method create an instance of
	 * the class GPS which manages an InputStream
	 * 
	 * @return
	 */
	static int connectGPS(){
		int result;
		Bluetooth.addDevice(GPSDevice);

		String BTMAC = "000B0D8E05C7";
		
		BTConnection btGPS = null;

		//btGPS = Bluetooth.connect(BTMAC, NXTConnection.RAW, pin);
		
		btGPS = Bluetooth.connect(GPSDevice.getDeviceAddr(), NXTConnection.RAW, pin);
		
		if(btGPS == null){
			result  = -1;//No connection
		}else{
			result = 1;//Connection Successful
		}

		try{
			in = btGPS.openInputStream();
			gps = new GPS(in);
			gps.updateValues(true);

			result = 2;//
		}catch(Exception e) {
			result = -2;
		}
		
		return result;
	}
	
	public static void main(String[] args) {

		//Detect GPS Device
		boolean GPSDetected = false;
		GPSDetected = discoverBTDevices();

		//GPSDetected = true;
		
		if(GPSDetected){
			//Connect with GPS Device
			int connectionStatus = 0;
			connectionStatus = connectGPS();

			if(connectionStatus == 2){
				//Show data from GPS Receiver
				showData();//GUI
			}else{
				if(connectionStatus == -1){
					LCD.drawString("No connection", 0, 7);
				}else if(connectionStatus == -2){
					LCD.drawString("Something goes bad", 0, 7);
				}
				try {Thread.sleep(2000);} catch (Exception e) {}
			}
			LCD.refresh();
		}else{
			LCD.drawString("No detected GPS", 0, 3);
			LCD.refresh();
			try {Thread.sleep(2000);} catch (Exception e) {}
		}
		
	}

	private static void showData(){
			
		LCD.clear();
		
		while(!Button.ESCAPE.isDown()){
						
			tics++;

			//System.out.println("ITERATION: " + tics);
			
			//showGGAUI();
			//showRMCUI();
			
			showSatUI();
			
			try {Thread.sleep(miliseconds);} catch (Exception e) {}
			
		}
		
		gps.shutDown();
	}
	
	/**
	 * Show GGA Basic Data from GPS
	 */
	private static void showGGAUI(){
		refreshSomeLCDLines();
		LCD.drawString("GGA", 0, 2);
		LCD.drawString(""+ tics, 10, 2);

		now = gps.getDate();
		LCD.drawString("Tim " + now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds() + "", 0, 3);
		LCD.drawString("Lat " + gps.getLatitude(), 0, 4);
		LCD.drawString("" + gps.getLatitudeDirection() , 15, 4);
		LCD.drawString("Lon " + gps.getLongitude(), 0, 5);
		LCD.drawString("" + gps.getLongitudeDirection() , 15, 5);
		LCD.drawString("Alt " + gps.getAltitude(), 0, 6);
		LCD.drawString("Sat " + gps.getSatellitesTracked(), 0, 7);
		//LCD.drawString("QOS " + gps.getFixMode(), 6, 7);
		LCD.refresh();
	}
	
	/**
	 * Show RMC Data from GPS
	 */
	private static void showRMCUI(){
		refreshSomeLCDLines();
		LCD.drawString("RMC", 0, 2);
		
		LCD.drawString("Dat " + now.getDay() + "/" + now.getMonth() + "/" + now.getYear() + "", 0, 3);
		LCD.drawString("Com " + gps.getCompassDegrees(), 0, 4);
		LCD.refresh();
	}	
	
	private static void showSatUI(){
		refreshSomeLCDLines();
		LCD.drawString("Sat quality data", 0, 2);

		//LCD.drawString("Mode " + gps.getSelectionType(), 0, 3);
		//LCD.drawString("Value " + gps.getFixType(), 8, 3);
		LCD.drawString("NSat " + gps.getSatellitesTracked(), 0, 4);
		LCD.drawString("PDOP " + gps.getPDOP(), 0, 5);
		LCD.drawString("HDOP " + gps.getHDOP(), 0, 6);
		LCD.drawString("VDOP " + gps.getVDOP(), 0, 7);
		LCD.refresh();
	}
	
	/**
	 * Clear some LCD lines
	 */
	private static void refreshSomeLCDLines(){
		LCD.drawString("                     ", 0, 2);
		LCD.drawString("                     ", 0, 3);
		LCD.drawString("                     ", 0, 4);
		LCD.drawString("                     ", 0, 5);
		LCD.drawString("                     ", 0, 6);
		LCD.drawString("                     ", 0, 7);
	}
	
}

