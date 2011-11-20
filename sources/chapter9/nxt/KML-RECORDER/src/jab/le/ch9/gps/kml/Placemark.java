package jab.le.ch9.gps.kml;
//import java.lang.StringBuffer;

public class Placemark {
	
	private String name ="";
	private String description  = "";
	private double latitude = 0;
	private double longitude = 0;
	private double altitude = 0;
	public static final String CRLF = "\r\n";
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
	public Placemark(){
		
	}

	public Placemark(final String name,final String description,final double latitude,final double longitude){
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Placemark(final String name, final String description, final double latitude, final double longitude,final double altitude){
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}


	public void setName(final String pName){
		name = pName;
	}
	
	public String getName(){
		return name;
	}

	public void setDescription(final String pDescription){
		description = pDescription;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setLatitude(final double pLatitude){
		latitude = pLatitude;
	}

	public double getLatitude(){
		return latitude;
	}
	
	public void setLongitude(final double pLongitude){
		longitude = pLongitude;
	}
	
	public double getLongitude(){
		return longitude;
	}
	
	public void setAltitude(final double pAltitude){
		altitude = pAltitude;
	}
	
	public double getAltitude(){
		return altitude;
	}
	
	public String toString(){
		final StringBuffer xmlNode = new StringBuffer(264);
		xmlNode.append("<Placemark>"+ CRLF);
		xmlNode.append("<name>" + this.name + "</name>"+CRLF);
		xmlNode.append("<description>" + this.description + "</description>"+CRLF);
		xmlNode.append("<Point><coordinates>" + this.longitude + "," + this.latitude + "," + this.altitude + "</coordinates></Point>"+CRLF);
		xmlNode.append("</Placemark>"+CRLF);
		return xmlNode.toString();
	}
}
