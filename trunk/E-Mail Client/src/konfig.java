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
	 * Lokale Variable f�r die emailAdressee
	 */
	private static String emailAdresse = "";
	
	/**
	 * Lokale Variable f�r den Benutzernamen
	 */
	private static String username = "";
	
	/**
	 * Lokale Variable f�r das passwort
	 */
	private static String passwort = "";

	/**
	 * Lokale Variable f�r den POP Server
	 */
	private static String pop3server = "";
	
	/**
	 * Lokale Variable f�r den SMTP Server
	 */
	private static String smtpserver = "";
	
	public static boolean exists() {
		File file = new File(fileName);
		return file.exists();
	}
	
	/**
	 * Speichert die Konfiguration in die Konfigurationsdatei
	 * @param name
	 *        Name des Anwenders
	 * @param emailAdresse
	 *        emailAdresse
	 * @param username
	 *        Benutzername
	 * @param passwort
	 *        passwort
	 * @param pop3server
	 *        POP Server
	 * @param smtpserver
	 *        SMTP Server
	 */
	public static void saveToFile(String name, String emailAdresse, String username, String passwort, String pop3server, String SMTPserver) {
		try {
			FileWriter file = new FileWriter(fileName);
			// Daten in die Datei schreiben
			file.write("name="+name+"\n");
			file.write("emailAdresse="+emailAdresse+"\n");
			file.write("username="+username+"\n");
			file.write("passwort="+passwort+"\n");
			file.write("pop3server="+pop3server+"\n");
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
		String[] currentLine = new String[6];
		try {
			FileReader file = new FileReader(fileName);
			BufferedReader buffer = new BufferedReader(file);
			for (int i=0; i <= 5; i++) {
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
			emailAdresse = currentLine[1];
			username   = currentLine[2];
			passwort   = currentLine[3];
			pop3server  = currentLine[4];
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
	 * Gibt die emailAdressee zur�ck
	 * @return emailAdressee
	 */
	public static String getemailAdresse() {
		loadFromFile();
		return emailAdresse;
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
	 * Gibt das passwort zur�ck
	 * @return passwort
	 */	
	public static String getpasswort() {
		loadFromFile();
		return passwort;
	}
	
	/**
	 * Gibt den POP Server zur�ck
	 * @return POP Server
	 */
	public static String getpop3server() {
		loadFromFile();
		return pop3server;
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