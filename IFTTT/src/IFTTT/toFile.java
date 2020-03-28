package IFTTT;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class toFile {
	public static void toFile(String str, String path){
		Charset charset = Charset.forName("UTF-8");
		try{
			FileOutputStream out = new FileOutputStream(path, true); 
			out.write(str.getBytes(charset)); 
			out.close();    
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
	}
}
