import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


public class senden_API {

    //Methode zum Einlesen von Strings
    public static String eingabe (String text){

      BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
      try {
        text = userIn.readLine();
      }
      catch (IOException ausnahme){
        System.out.println("Es ist ein Fehler aufgetreten. Schliessen Sie das Programm und starten Sie es erneut");
      }
      return text;
    }

//  Hauptprogramm zum Senden von Mails via JavaMailAPI
    public static void main(String[] args) {
//    	Initialisierung der Variablen
    	String internetadresse = "";
    	String antwort = "";
    	String [] empfaenger;
    	String anzahlempfaenger = "";
    	int anzahl;
    	String absender = "";
    	String subject = "";
    	String text = "";
    	String eingebenerText = "";
    	int i;

    	System.out.println("Dies ist ein Programm zum Senden von Mails mittels der JavaMailAPI \n");

    	try {
//    		Eingabe des SMTP Hosts
    		System.out.println("Geben Sie bitte Ihren SMTP Host ein:");
    		internetadresse = eingabe(internetadresse);
    		
    		//Festlegen eines geeigneten SMTP Hosts
    		Properties props = new Properties();
    		props.put("mail.smtp.host",  internetadresse);
    		Session session = Session.getDefaultInstance(props);
    		Message msg = new MimeMessage(session);

    		//Eingabe der EMailadresse des Abenders
    		System.out.println("Nennen Sie bitte Ihre E-Mail Adresse:");
    		absender = eingabe(absender);
    		//Festlegen der Abenderadresse
    		InternetAddress addressFrom = new InternetAddress(absender);
    		msg.setFrom(addressFrom);
    		System.out.println("Absender: " + absender);
        
    		do {
    			//definieren der Anzahl der Empfänger
    			System.out.println("An wieviele Empfaenger soll die E- Mail geschickt werden?");
    			anzahlempfaenger = eingabe(anzahlempfaenger);
    			anzahl = Integer.parseInt(anzahlempfaenger);
    			empfaenger = new String[anzahl];
        
    			//Eingabe der Adressen der Empfänger
    			for (i = 0; i < empfaenger.length; i++) {
    				System.out.println("An wen möchten Sie die E- Mail versenden? Geben Sie bitte die Empfaengeradresse ein:");
    				empfaenger [i]= eingabe(empfaenger[i]);
    			}
    		
    			//Ausgabe der Adressen der Empfänger
    			for (i = 0; i < empfaenger.length; i++) {
    				System.out.println("eingegebene Empfaenger: " + empfaenger[i] + "/n");
    			}
    			
    			//Eingabe des Betreffs der Nachricht
    			System.out.println("Geben Sie bitte den Betreff für die Nachricht ein:");
    			subject = eingabe(subject);
    			msg.setSubject(subject);
    			System.out.println("Subject: " + subject);

    			//Eingabe der Nachricht (auch mehrzeilig)
    			System.out.println("Geben Sie bitte Ihre Nachricht ein (Sie beenden indem Sie ENDE eingeben):");
    			do {
    				text = eingabe(text);
    				eingebenerText = eingebenerText + " \n " + text;
    			} while(!eingebenerText.endsWith("ENDE"));
    			msg.setText( eingebenerText );
    			System.out.println("Ihre Nachricht:" + eingebenerText);
                       
    			//Absenden der EMail die die vorher eingegebenen Empfänger
    			for (i = 0; i < empfaenger.length; i++) {
    				InternetAddress addressTo = new InternetAddress( empfaenger [i]);
    				msg.setRecipient(Message.RecipientType.TO, addressTo);
    				Transport.send(msg);
    			}
    			System.out.println("Ihre Nachricht wurde versendet.");
      
//          	Abfrage, was als nächstes getan werden soll
    			System.out.println("Moechten Sie beenden (1) oder weitere EMails lesen(2)?");
    			antwort = eingabe(antwort);
    		} while (!antwort.equals("1"));
    	
    //Hier folgt nun noch ExceptionHandling
    } catch (AddressException ausnahme){
    		System.out.println("AddressException");
    		ausnahme.printStackTrace();
    	} catch (MessagingException ausnahme){
    		System.out.println("MessagingException");
    		ausnahme.printStackTrace();
    	}
   	}
}