package principal;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import lejos.nxt.Button;

public class LoadDesination {

	public LoadDesination()
	{
		
	}
	
	public Properties Destino()
	{
		//Load file with destination properties
		Properties p = new Properties();
		File f = new File("destination");
		if(f.exists())
		{
			FileInputStream in;
			try{
				in = new FileInputStream(f);
				p.load(in);
				in.close();
			}catch(Exception e){
				System.err.println("Failed to load destination");
				System.err.println(e.getMessage());
				Button.waitForPress();
				System.exit(0);
			}
		}
		else{
			System.out.println("No destination found");
			Button.waitForPress();
			System.exit(0);
		}
		
		return p;
	}
	
	
	
}
