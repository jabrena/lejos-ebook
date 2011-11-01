package jab.le.ch7.gps.kml;
import java.lang.StringBuffer;

public class Placemark {
	
	private String name ="";
	private String description  = "";
	private double latitude = 0;
	private double longitude = 0;
	private double altitude = 0;
	private StringBuffer sb;
	private final String CRLF = "\r\n";
	//http://code.google.com/intl/es-ES/apis/kml/documentation/kmlreference.html
	//<extrude>0</extrude> 
	//<altitudeMode>clampToGround</altitudeMode>  <!-- kml:altitudeModeEnum: clampToGround, relativeToGroundo absolute -->

	/*
	 *       <description>
        <![CDATA[
          <h1>CDATA Tags are useful!</h1>
          <p><font color="red">Text is <i>more readable</i> and
          <b>easier to write</b> when you can avoid using entity
          references.</font></p>
        ]]>
      </description>

      <Icon>
        <href>http://code.google.com/apis/kml/documentation/etna.jpg</href>
      </Icon>
      http://kml-samples.googlecode.com/svn/trunk/interactive/index.html
	 */
	

	public Placemark(String name,String description,double latitude,double longitude){
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Placemark(String name,String description,double latitude,double longitude,double altitude){
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}

	public Placemark(){
		
	}

	public void setName(String pName){
		name = pName;
	}

	public void setDescription(String pDescription){
		description = pDescription;
	}
	
	public void setLatitude(double pLatitude){
		latitude = pLatitude;
	}

	public void setLongitude(double pLongitude){
		longitude = pLongitude;
	}
	
	public String toString(){
		sb = new StringBuffer();
		sb.append("<Placemark>"+CRLF);
		sb.append("<name>" + this.name + "</name>"+CRLF);
		sb.append("<description>" + this.description + "</description>"+CRLF);
		sb.append("<Point><coordinates>" + this.longitude + "," + this.latitude + "," + this.altitude + "</coordinates></Point>"+CRLF);
		sb.append("</Placemark>"+CRLF);
		return sb.toString();
	}
}
