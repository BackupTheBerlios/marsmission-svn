import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Klasse zum Laden und Speichern der Konfiguration
 * 
 * @author Corinne Klimkeit
 * @author Ines Junold
 */
public class konfig {

	/**
	 * Lokale Variable f�r die Konfigurationsdatei
	 */
	private static String fileName = "konfig.txt";
	
	/**
	 * Lokale Variable f�r den Namen
	 */
	private static String name = "";
	
	/**
	 * Lokale Variable f�r die Mailadresse
	 */
	private static String mailadress = "";
	
	/**
	 * Lokale Variable f�r den Benutzernamen
	 */
	private static String username = "";
	
	/**
	 * Lokale Variable f�r das Password
	 */
	private static String password = "";

	/**
	 * Lokale Variable f�r den POP Server
	 */
	private static String popserver = "";
	
	/**
	 * Lokale Variable f�r den SMTP Server
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
	public static void saveToFile(String name, String mailadress, String username, String password, String POPserver, String SMTPserver) {
		try {
			FileWriter file = new FileWriter(fileName);
			// Daten in die Datei schreiben
			file.write("name="+name+"\n");
			file.write("mailadress="+mailadress+"\n");
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
			for (int i=0; i < 5; i++) {
				// Auslesen aus der Datei
				currentLine[i] = buffer.readLine();
			}
			buffer.close();
			for (int i=0; i<5; i++) {
				// Strings auf die richtige L�nge k�rzen 
				currentLine[i] = currentLine[i].substring(currentLine[i].indexOf("=")+1);
			}
			// Strings an die lokalen Variablen �bergeben
			name       = currentLine[0];
			mailadress = currentLine[1];
			username   = currentLine[2];
			password   = currentLine[3];
			popserver  = currentLine[4];
			smtpserver = currentLine[5];
		} catch (IOException ioe) {
			System.err.println("Fehler beim laden der Konfiguration!");
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Gibt den Namen zur�ck
	 * @return Name
	 */
	public static String getName() {
		loadFromFile();
		return name;
	}
	
	/**
	 * Gibt die Mailadresse zur�ck
	 * @return Mailadresse
	 */
	public static String getMailAdress() {
		loadFromFile();
		return mailadress;
	}
	
	/**
	 * Gibt den Benutzernamen zur�ck
	 * @return Benutzername
	 */
	public static String getUserName() {
		loadFromFile();
		return username;
	}

	/**
	 * Gibt das Password zur�ck
	 * @return Password
	 */	
	public static String getPassword() {
		loadFromFile();
		return password;
	}
	
	/**
	 * Gibt den POP Server zur�ck
	 * @return POP Server
	 */
	public static String getPOPServer() {
		loadFromFile();
		return popserver;
	}
	
	/**
	 * Gibt den SMTP Server zur�ck
	 * @return SMTP Server
	 */
	public static String getSMTPServer() {
		loadFromFile();
		return smtpserver;
	}
}