package node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import message.Message;

/**
 * 
 */

/**
 * @author Torsten und Marian
 *
 */
public class TextBasedUserInterface {

	/**
	 * @param args
	 */
	communication.Communication com = null;
	
	BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	
    String nodeName = "";
    
	Message msg = new Message();
	
	public void TextBasedUserInteface () {
	}
	
	public void send () {
		try {
		System.out.println("\n"+this.nodeName+": Sende-System:");
		System.out.print("\n"+this.nodeName+": Empfaenger: ");
		this.msg.setSender(this.stdin.readLine());
		System.out.print("\n"+this.nodeName+": Nachicht: ");
		this.msg.setData(this.stdin.readLine());
		} catch (IOException ioe) {
			System.err.println("\n"+this.nodeName+": Fehler bei der Eingabe!");
		}
		
		System.out.println("\nNachicht:");
		System.out.println("Sender:     "+this.msg.getSender());
		System.out.println("Empfaenger: "+this.msg.getReceiver());
		System.out.println("Nachicht:   "+this.msg.getData()+"\n");
		try {
			this.com.openOutputPipe(this.msg.getReceiver());
			this.com.sendMessage(msg);
			this.com.closeOutputPipe();
			System.out.println("\n"+this.nodeName+": Nachicht erfolgreich gesendet!");
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
	
	public void sendFromFile () {
		boolean messageLoaded = true;
		try {
			String fileName = "";
			System.out.println("\n"+this.nodeName+": Nachichtendatei muss sich im Pfad "+com.getNodePath()+" befinden!");
			System.out.print("\n"+this.nodeName+": Dateiname: ");
			fileName = this.stdin.readLine();
			System.out.println("\n"+this.nodeName+": Nachicht aus "+com.getNodePath()+fileName+" laden.");
			this.msg.setTimestamp("20070516");
			this.msg.setMessageType(1);
			this.msg.setReciever(nodeName);
			this.msg.setInformation("Message myself!");
			this.msg = msg.FromFile(com.getNodePath()+fileName);
		} catch (IOException ioe) {
			System.err.println("\n"+this.nodeName+": Fehler beim laden der Nachicht!");
			messageLoaded = false;
		}
		if (messageLoaded) {
			try {
				this.com.openOutputPipe(this.msg.getReciever());
				this.com.sendMessage(msg);
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

	public void showInMessages () {
		
	}
	
	public void connect () {
		try {
			System.out.print("Knoten-Name eingeben: ");
			nodeName = this.stdin.readLine();
			this.com = new Communication(nodeName);
			com.connect();
		} catch (IOException ioe) {
			System.err.println("Fehler beim verbinden!");
			System.err.println("\n"+this.nodeName+": Error Message:");
			ioe.printStackTrace();
			System.err.println();
		}
	}
	
	public void disconnect () {
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
	}
	
	public void menue ()  {
		String tmp;
		int ui, ende;
		ui = 0;
		ende =0;
		System.out.println("Willkommen zum Kommunikationssystem Mars Mission \n");
		this.connect();
		while (ende <1) {
			if (ui == 0){
				try {
					System.out.println("1. Einzelnachrichten senden \n" + "2. Nachricht aus Datei versenden \n" + "3. Nachrichten ansehen \n" + "4. Beenden\n");
					tmp = stdin.readLine();
					ui = (int) tmp.charAt(0);
					ui = ui - 48;
			}
				catch (IOException ioe) {
					System.err.println("Fehler beim Einlesen der Menukontrollzahl");
				}
			}
			else if (ui == 1){
				this.send();
				ui = 0;				
			}
			else if (ui ==2){
				this.sendFromFile();
				ui = 0;
			}
			else if (ui ==3){
				this.showInMessages();
				ui = 0;
			}
			else if (ui == 4) {
				this.disconnect();
				ende = 1;
			}
			else {
				ui = 0;
			}
		}
	}
	
	public static void main(String[] args) {
		TextBasedUserInterface tbui = new TextBasedUserInterface();
		tbui.menue();
	}
}