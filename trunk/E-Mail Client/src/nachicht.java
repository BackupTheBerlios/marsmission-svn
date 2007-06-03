import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.mail.*;

public class nachicht {
	
	private static String betreff = "";
	
	private static String absender = "";
	
	private static String nachricht = "";
	
	public static void speichern(Message nachrichtenid, String betreff, String absender, String nachricht) {
		try {
			FileWriter file = new FileWriter((new Integer(nachrichtenid.toString()).toString()));
			file.write("betreff="+betreff+"\n");
			file.write("absender="+absender+"\n");
			file.write("nachricht="+nachricht+"\n");
			file.close();
		} catch (IOException ioe) {
			System.err.println("Fehler beim Schreiben der Nachicht auf den Datenträger.");
			ioe.printStackTrace();
		}
	}
		
	public static void laden(Message nachrichtenid) {
		try {
			betreff = "";
			absender = "";
			nachricht = "";
			FileReader file = new FileReader(nachrichtenid.toString());
			BufferedReader buffer = new BufferedReader(file);
			betreff = buffer.readLine();
			absender = buffer.readLine();
			boolean eof = false;
			while (!eof) {
				String line = buffer.readLine();
				if (line == null)
					eof = true;
				else
					nachricht = nachricht + line;
			}
			buffer.close();
			betreff = betreff.substring(betreff.indexOf("=")+1);
			absender = absender.substring(absender.indexOf("=")+1);
			nachricht = nachricht.substring(nachricht.indexOf("=")+1);
		} catch (IOException ioe) {
			System.err.println("Fehler beim Laden der Nachicht vom Datenträger.");
			ioe.printStackTrace();
			betreff = "";
			absender = "";
			nachricht = "";			
		}
	}

	public static String getBetreff() {
		return betreff;
	}

	public static String getAbsender() {
		return absender;
	}

	public static String getNachricht() {
		return nachricht;
	}
	
}
