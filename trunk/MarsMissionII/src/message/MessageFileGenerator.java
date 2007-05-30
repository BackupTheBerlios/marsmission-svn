package message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A simple message file generator 
 * @author Torsten Burschka
 *
 */
public class MessageFileGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String fileName = "message.txt";
		Message message = new Message();
		System.out.println("Message File Gererator\n\n");
		System.out.print("Dateiname eingeben: ");
		try {
			fileName = in.readLine();
		} catch (IOException ioe) {
			System.err.println("Fehler bei der Eingabe");
			ioe.printStackTrace();
		}
		System.out.print("Absender eingeben: ");
		try {
			message.setSender(in.readLine());
		} catch (IOException ioe) {
			System.err.println("Fehler bei der Eingabe");
			ioe.printStackTrace();
		}		
		System.out.print("Absender-Gruppe eingeben: ");
		try {
			message.setSenderGroup(in.readLine());
		} catch (IOException ioe) {
			System.err.println("Fehler bei der Eingabe");
			ioe.printStackTrace();
		}		
		System.out.print("Empfaenger eingeben: ");
		try {
			message.setReceiver(in.readLine());
		} catch (IOException ioe) {
			System.err.println("Fehler bei der Eingabe");
			ioe.printStackTrace();
		}
		System.out.print("Empfaenger-Gruppe eingeben: ");
		try {
			message.setReceiverGroup(in.readLine());
		} catch (IOException ioe) {
			System.err.println("Fehler bei der Eingabe");
			ioe.printStackTrace();
		}		
		System.out.print("Nachichtentyp eingeben ("+Message.command_type+", "+Message.data_type+" oder "+Message.text_type+"): ");
		try {
			message.setType(in.readLine());
		} catch (IOException ioe) {
			System.err.println("Fehler bei der Eingabe");
			ioe.printStackTrace();
		}
		System.out.print("Nachicht eingeben: ");
		try {
			message.setData(in.readLine());
		} catch (IOException ioe) {
			System.err.println("Fehler bei der Eingabe");
			ioe.printStackTrace();
		}
		message.refreshTimeStamp();
		MessageFileOperations.saveToFile(fileName, message);
		System.out.println("Nachicht erstellt.");
	}
}
