package base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Operations with the rendezvous list
 * 
 * @author Torsten Burschka
 * @author Steffen Boettcher
 */

public class RendezvousList {

	/**
	 * Path to rendezvous list file
	 */
	private static String rendezvousListHomePath = "";
	
	/**
	 * Name of the rendezvous list file
	 */
	private static String rendezvousListName = "rdv.lst";
	
	/**
	 * Transfer protocol
	 */
	private static String rendezvousProtocol = "tcp";

	/**
	 * Transfer port
	 */
	private static int rendezvousPort = 9701;
	
	/**
	 * Set the home path for the rendezvous list file
	 * @param homePath
	 *            home path
	 */
	public static void setHomePath (String homePath) {
		rendezvousListHomePath = homePath;
	}

	/**
	 * Set the rendezvous list file name
	 * @param name
	 * 			  name, default: "rdv.lst"
	 */
	public static void setRendezvousListName (String name) {
		rendezvousListName = name;
	}
	
	/**
	 * Set the protocol, which is used
	 * @param protocol
	 *            protocol, default: "tcp"
	 */
	public static void setProtocol (String protocol) {
		rendezvousProtocol = protocol;
	}
	
	/**
	 * Set the port, that is used
	 * @param port
	 *            port, default: "9701";
	 */
	public static void setPort (int port) {
		rendezvousPort = port;
	}
	
	/**
	 * Returns the actual home path
	 * @return
	 *      homePath
	 */
	public static String getHomePath() {
		return rendezvousListHomePath;
	}
	
	/**
	 * This will reset rendezvousListName, rendezvousProtocol and rendezvousPort to defaults.
	 */
	public static void resetDefaults() {
		rendezvousListName = "rdv.lst";
		rendezvousProtocol = "tcp";
		rendezvousPort     = 9701;
	}
	
	/**
	 * Returns the actual rendezvous list name
	 * @return
	 *      rendezvousListName
	 */
	public String getRendezvousListName() {
		return rendezvousListName;
	}

	/**
	 * Returns the actual protocol
	 * @return
	 *      protocol
	 */
	public String getProtocol() {
		return rendezvousProtocol;
	}
	
	/**
	 * Returns the actual port
	 * @return
	 *      port
	 */
	public int getPort() {
		return rendezvousPort;
	}
	
	/**
	 * Format an IPv4 or IPv6 address into a rendezvous
	 * @param adress
	 *        an IPv4 or IPv6 address
	 * @param protocol
	 *        the protocol, that is used
	 * @param port
	 *        the port, that is used       
	 * @return returns a rendezvous
	 */
	private static String addressToRendezvous (String address, String protocol, int port) {
		if (address.indexOf(":") == -1)
			return protocol + "://" + address + ":" + port;   // IPv4 
		else
			return protocol + "://[" + address + "]:" + port; // IPv6
	}

	/**
	 * Adds a list of the connected networks, useful to create a local rendezvous list. Using the stored protocol and port.
	 */
	public static void addHostAdresses () {
		try {
			// get the hostname
			String localHost = InetAddress.getLocalHost().getHostName();
			// for every network
			for (InetAddress ia : InetAddress.getAllByName(localHost))
				add(addressToRendezvous(ia.getHostAddress(),rendezvousProtocol,rendezvousPort));
		} catch (Exception e) {
			System.err.println("addHostAdresses: Fehler beim Auslesen der Adresse(n)!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a list of the connected networks, useful to create a local rendezvous list
	 * @param protocol
	 *        the protocol, that is used
	 * @param port
	 *        the port, that is used       
	 */
	public static void addHostAdresses (String protocol, int port) {
		try {
			// get the hostname
			String localHost = InetAddress.getLocalHost().getHostName();
			// for every network
			for (InetAddress ia : InetAddress.getAllByName(localHost))
				add(addressToRendezvous(ia.getHostAddress(),protocol,port));
		} catch (Exception e) {
			System.err.println("addHostAdresses: Fehler beim Auslesen der Adresse(n)!");
			e.printStackTrace();
		}
	}

	/**
	 * Overwrite an existing rendezvous list file or create a new one
	 */
	public static void overwrite () {
		try {
			FileWriter file = new FileWriter(Configuration.getProjectPath()+Configuration.rendezvousFile);
			file.close();
		} catch (IOException ioe) {
			System.err.println("createRenzezvousList: Fehler beim Überschreiben der Rendezvous Liste!");
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Create a new or add to an existing rendezvous list file the given rendezvous
	 * @param rendezvous
	 *            A list of rendezvous, parted with \n
	 */
	public static void add (String rendezvous) {
		if (find(rendezvous) == -1)
			try {
				FileWriter file = new FileWriter(Configuration.getProjectPath()+Configuration.rendezvousFile, true);
				file.write(rendezvous.toString()+"\n");
				file.close();
			} catch (IOException ioe) {
				System.err.println("addRendezvous: Fehler beim Schreiben in die Rendezvous Liste!");
				ioe.printStackTrace();
			}
	}
	
	/**
	 * Return the rendezvous list from the rendezvous list file
	 * @return Return a rendezvous list, or if no rendezvous list file exists, return ""
	 */
	public static String getAll () {
		String returnString = "";
		try {
			FileReader file = new FileReader(Configuration.getProjectPath()+Configuration.rendezvousFile);
			BufferedReader buff = new BufferedReader(file);
			boolean eof = false;
			while (!eof) {
				String line = buff.readLine();
				if (line == null)
					eof = true;
				else {
					returnString += line;
					returnString += "\n";
				} 
			}
			buff.close();
		} catch (IOException ioe) {
			// do nothing
		}
		return returnString;
	}

	/**
	 * Search a rendezvous in the rendezvousList and give back the line 
	 * @param rendezvous
	 *        the rendezvous you seaching for
	 * @return the line, if it wasn't found, -1 will be returned
	 */
	public static int find(String rendezvous) {
		int counter = 0;
		try {
			FileReader file = new FileReader(Configuration.getProjectPath()+Configuration.rendezvousFile);
			BufferedReader buff = new BufferedReader(file);
			boolean eof = false;
			while (!eof) {
				String line = buff.readLine();
				if (line == null)
					eof = true;
				else {
					if (line.compareTo(rendezvous) == 0)
						return counter;
					else
						counter++;
				} 
			}
			buff.close();
		} catch (IOException ioe) {
			ioe.toString();
		}
		return -1;
	}
	
	/**
	 * Delete a rendezvous in the given line
	 * @param line
	 *        the line of the rendezvous to delete
	 * @return returns true, if the rendezvous was deleted, else it will returns false 
	 */
	public static boolean delete(int line) {
		if (line >= 0) {
			String newList = "";
			int counter = 0;
			try {
				FileReader file = new FileReader(Configuration.getProjectPath()+Configuration.rendezvousFile);
				BufferedReader buff = new BufferedReader(file);
				boolean eof = false;
				while (!eof) {
					String rowNumber = buff.readLine();
					if (rowNumber == null)
						eof = true;
					else {
						if (counter != line)
							newList += rowNumber;
						counter++;
					} 
				}
				buff.close();
				
				FileWriter writer = new FileWriter(Configuration.getProjectPath()+Configuration.rendezvousFile);
				writer.write(newList.toCharArray());
				writer.close();
			} catch (IOException ioe) {
				ioe.toString();
				return false;
			}
			
			return true;
		} else {
			return false;
		}
	}
}