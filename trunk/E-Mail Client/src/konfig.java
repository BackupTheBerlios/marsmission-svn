import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Klasse zum Laden und Speichern der Konfiguration
 * 
 * @author Ines Junold
 * @author Corinne Klimkeit
 */
public class konfig {

	/**
	 * Lokale Variable für die Konfigurationsdatei
	 */
	private static String fileName = "konfig.txt";
	
	/**
	 * Lokale Variable für den Benutzernamen
	 */
	private static String username = "";
	
	/**
	 * Lokale Variable für das Password
	 */
	private static String password = "";

	/**
	 * Lokale Variable für den POP Server
	 */
	private static String popserver = "";
	
	/**
	 * Lokale Variable für den SMTP Server
	 */
	private static String smtpserver = "";
		
	/**
	 * Speichert die Konfiguration in die Konfigurationsdatei
	 * @param username
	 *        Benutername
	 * @param password
	 *        Password
	 * @param popserver
	 *        POP Server
	 * @param smtpserver
	 *        SMTP Server
	 */
	public static void saveToFile(String username, String password, String POPserver, String SMTPserver) {
		try {
			FileWriter file = new FileWriter(fileName);
			// Daten in die Datei schreiben
			file.write("username="+username+"\n");
			file.write("password="+password+"\n");
			file.write("popserver="+POPserver+"\n");
			file.write("smtpserver="+SMTPserver+"\n");
			file.close();
		} catch (IOException ioe) {
			System.err.println("Fehler beim Schreiben der Konfiguration!");
			ioe.printStackTrace();
		}
	}

	/**
	 * Laden der Konfiguration in die lokalen Variablen
	 */
	private static void loadFromFile() {
		// Array zum Speichern der eingelesenen Strings
		String[] currentLine = new String[4];
		try {
			FileReader file = new FileReader(fileName);
			BufferedReader buffer = new BufferedReader(file);
			for (int i=0; i < 3; i++) {
				// Auslesen aus der Datei
				currentLine[i] = buffer.readLine();
			}
			buffer.close();
			for (int i=0; i<3; i++) {
				// Strings auf die richtige Länge kürzen 
				currentLine[i] = currentLine[i].substring(currentLine[i].indexOf("=")+1);
			}
			// Strings an die lokalen Variablen übergeben
			username   = currentLine[0];
			password   = currentLine[1];
			popserver  = currentLine[2];
			smtpserver = currentLine[3];
		} catch (IOException ioe) {
			System.err.println("Fehler beim laden der Konfiguration!");
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Gibt den Benutzernamen zurück
	 * @return Benutzername
	 */
	public static String getUserName() {
		loadFromFile();
		return username;
	}

	/**
	 * Gibt das Password zurück
	 * @return Password
	 */	
	public static String getPassword() {
		loadFromFile();
		return password;
	}
	
	/**
	 * Gibt den POP Server zurück
	 * @return POP Server
	 */
	public static String getPOPServer() {
		loadFromFile();
		return popserver;
	}
	
	/**
	 * Gibt den SMTP Server zurück
	 * @return SMTP Server
	 */
	public static String getSMTPServer() {
		loadFromFile();
		return smtpserver;
	}
}