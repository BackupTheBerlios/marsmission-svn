package node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import base.Configuration;

import communication.Communication;

import message.Message;
import message.MessageFileOperations;

/**
 * Interface for controlling the node
 * @author Marian Zoellner
 * @author Torsten Burschka
 */
public class TextBasedUserInterface {

	Configuration config = null;
	
	Communication com = null;
	
	boolean connected = false;
	
	BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	
    String nodeName = "";
    
	Message message = new Message();
	
	/**
	 * Constructor
	 */
	public TextBasedUserInterface () {
		System.out.println("Welcome to "+Configuration.project+" communication system\n");
		System.out.print("Enter a profile name: ");
		boolean error = false;
		while (!error) {
			try {
				Configuration.init(this.stdin.readLine());
				error = false;
			} catch (IOException ioe) {
				error = true;
			}
		}
	}
	
	/**
	 * Menu point "Message sending"
	 */
	private void sendMessage() {
		boolean leave = false;
		char choise = '0';
		while (!leave) {
			System.out.println("Message sending system\n");
			System.out.println("1 - Create and send a new message");
			System.out.println("2 - Send a message stored in a file");
			System.out.println("3 - Back\n");
			System.out.print("Your choise: ");
			try {
				choise = (char)stdin.read();
				
			} catch (IOException ioe) {
				choise = '0';
			}
			switch ( choise ) {
			case '1' :
				sendMessageFromInput();
				leave = false;
			case '2' :
				sendMessageFromFile();
				leave = false;
			case '3' :
				leave = true;
			default :
				System.out.println("\\Wrong input!\n");
			}
			System.out.println();
		}
	}

	/**
	 * Menu point "Create and send a new message"
	 */
	private void sendMessageFromInput () {
		System.out.println("");
		try {
			System.out.print("Receiver: ");
			message.setReceiver(stdin.readLine());
			System.out.print("Receiver group: ");
			message.setReceiverGroup(stdin.readLine());
			System.out.print("Message type: ");
			message.setType(stdin.readLine());
			System.out.print("Message data: ");
			message.setData(this.stdin.readLine());
		} catch (IOException ioe) {
			System.err.println();
		}
		System.out.println("\n\n Sending message.");
		try {
			com.sendMessage(message);
		} catch (Throwable ta) {
			System.err.println("\n"+this.nodeName+": Fehler beim Senden!");
			System.err.println("\n"+this.nodeName+": Error Message:");
			ta.printStackTrace();
			System.err.println();
		}
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException ie) {}
	}
	
	/**
	 * Menu point "Send a message stored in a file"
	 */
	private void sendMessageFromFile () {
		boolean messageLoaded = true;
		System.out.println("");
		try {
			String filePath = "";
			String fileName = "";
			System.out.print("\n"+this.nodeName+": Pfad eingeben (bei leerem Pfad wird der Standardpfad \""+Configuration.getProfilePath()+"\" genutzt:");
			filePath = this.stdin.readLine();
			System.out.print("Dateiname: ");
			fileName = this.stdin.readLine();
			System.out.println("\n"+this.nodeName+": Nachicht aus "+filePath+fileName+" laden.");
			this.message = MessageFileOperations.loadFromFile(filePath+fileName);
		} catch (IOException ioe) {
			System.err.println("\n"+this.nodeName+": Fehler beim aden der Nachicht!");
			messageLoaded = false;
		}
		if (messageLoaded) {
			try {
				this.com.openOutputPipe(this.message.getReceiver());
				this.com.sendMessage(message);
				this.com.closeOutputPipe();
				System.out.println("\n"+this.nodeName+": Nachicht erfolgreich gesendet!");
			} catch (Throwable ta) {
				System.err.println("\n"+this.nodeName+": Fehler beim Senden!");
				System.err.println("\n"+this.nodeName+": Error Message:");
				ta.printStackTrace();
				System.err.println();
			}
		}
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException ie) {}
	}

	/**
	 * Menu point "Show incoming Messages"
	 */
	private void showInMessages () {
		
	}

	/**
	 * Menu point "Connect"
	 */
	private void connect () {
		com.connect();
		connected = true;
	}
	
	/**
	 * Menu point "Disconnect"
	 */
	private void disconnect () {
		try {
			System.out.print("\n"+this.nodeName+": Trennen der Verbindung ");
			com.disconnect();
			System.out.println("erfolgreich!");
		} catch (Throwable ta) {
			System.err.println("nicht erfolgreich!");
			System.err.println("\n"+this.nodeName+": Error Message:");
			ta.printStackTrace();
			System.err.println();
		}
		connected = false;
	}

	/**
	 * Menu point "Group configuration"
	 */
	private void groupConfiguration () {
		boolean leave = false;
		char choise = '0';
		while (!leave) {
			System.out.println("Groups configuration\n");
			System.out.println("1 - Show all groups");
			System.out.println("2 - Show own groups");
			System.out.println("3 - Join a group");
			System.out.println("4 - Leave a group");
			System.out.println("5 - Back\n");
			System.out.print("Your choise: ");
			try {
				choise = (char)stdin.read();
				
			} catch (IOException ioe) {
				choise = '0';
			}
			switch ( choise ) {
			case '1' :
			case '2' :
			case '3' :
			case '4' :
			case '5' :
				leave = true;
			default :
				System.out.println("\\Wrong input!\n");
			}
		}
	}
	
	/**
	 * The main menu
	 */
	public void menu ()  {
		boolean leave = false;
		char choise = '0';
		while (!leave) {
			if (!connected) {
				System.out.println("Main menu\n");
				System.out.println("1 - Connect");
				System.out.println("2 - Show incoming messages");
				System.out.println("3 - Exit");
				try {
					choise = (char)stdin.read();

				} catch (IOException ioe) {
					choise = '0';
				}
				switch ( choise ) {
				case '1' :
					connect();
				case '2' :
					showInMessages();
				case '3' :
					leave = true;
				default :
					System.out.println("\\Wrong input!\n");
				}
			} else {
				System.out.println("Main menu\n");
				System.out.println("1 - Message sending");
				System.out.println("2 - Show incoming messages");
				System.out.println("3 - Group configuration");
				System.out.println("4 - Disconnect");
				System.out.println("5 - Exit\n");
				System.out.print("Your choise: ");
				try {
					choise = (char)stdin.read();

				} catch (IOException ioe) {
					choise = '0';
				}
				switch ( choise ) {
				case '1' :
					sendMessage();
					leave = false;
				case '2' :
					showInMessages();
					leave = false;
				case '3' :
					groupConfiguration();
					leave = false;
				case '4' :
					disconnect();
					leave = false;
				case '5' :
					leave = true;
				default :
					System.out.println("\\Wrong input!\n");
				}
			}
		}
	}
	
	/**
	 * The Main
	 * @param args
	 */
	public static void main(String[] args) {
		TextBasedUserInterface tbui = new TextBasedUserInterface();
		tbui.menu();
	}
}