package base;

/**
 * 
 * @author Torsten Burschka
 * 
 */

import java.io.File;

public class Utilities {

	public static String toURI (String path) {
		File f = new File(path); 
        if (path.endsWith("\\") && !path.substring(0,path.length()-1).endsWith(":"))
        	return f.toURI().toString() + "/";
        else
        	return f.toURI().toString();
	}
	
	public static String toPath (String URI) {
		File f = new File(URI);
	    if (URI.endsWith("\\")&& !URI.substring(0,URI.length()-1).endsWith(":"))		
	    	return f.getPath() + "\\";
	    else 
	    	return f.getPath();
	}
}
