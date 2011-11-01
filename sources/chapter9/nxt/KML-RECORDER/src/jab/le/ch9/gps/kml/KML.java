package jab.le.ch9.gps.kml;

import java.util.Vector;

public class KML {
	private Vector al;
	private StringBuffer sb;
	private String fileName;
	private String fileDescription;
	private String folderName;
	private String folderDescription;
	private final String CRLF = "\r\n";
	

	
	public KML(String _fileName,String _fileDescription,String _folderName,String _folderDescription){
		al = new Vector();

		fileName = _fileName;
		fileDescription = _fileDescription;
		folderName = _folderName;
		folderDescription = _folderDescription;
	}

	public String getKMLHeader(){
		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version='1.0' encoding='UTF-8'?>"+CRLF);
        sb.append("<kml xmlns='http://earth.google.com/kml/2.2'>"+CRLF);
        sb.append("<Document>"+CRLF);
        sb.append("<name>" + fileName + "</name>"+CRLF);
        sb.append("<open>1</open>"+CRLF);
        sb.append("<description>" + fileDescription + "</description>"+CRLF);
        sb.append("<Folder>"+CRLF);
        sb.append("<name>" + folderName + "</name>"+CRLF);
        sb.append("<description>" + folderDescription + "</description>"+CRLF);
        
        return sb.toString();
	}

	public void addPlacemark(Placemark _p){
		al.addElement(_p);
	}
	
	public void addPlacemark(String name,String description,double latitude,double longitude){
		Placemark p = new Placemark();
		p.setName(name);
		p.setDescription(description);
		p.setLatitude(latitude);
		p.setLongitude(longitude);
		al.addElement(p);
	}

	public String getKMLFooter(){
		StringBuffer sb = new StringBuffer();
		
        sb.append("</Folder>"+CRLF);
        sb.append("</Document>"+CRLF);
        sb.append("</kml>"+CRLF);

        return sb.toString();
	}

	public String getPlacemarks(){
		Placemark p;
		StringBuffer sb = new StringBuffer();
		
		//Add Placemarks to KML File
		if(al.size() > 0){
			for(int i=0; i< al.size();i++){
				p = (Placemark) al.elementAt(i);
				sb.append(p.toString());
			}
		}
		
		return sb.toString();
	}

	public String toString(){

		StringBuffer sb = new StringBuffer();

		sb.append(this.getKMLHeader());
		sb.append(this.getPlacemarks());

		//Close
		sb.append(this.getKMLFooter());

		return sb.toString();
	}
}
