import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


public class sendenAPI {

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
      String internetadresse = "";
      String [] empfaenger;
      String anzahlempfaenger = "";
      int anzahl;
      //String [] empfaengermenge;
      String absender = "";
      String subject = "";
      String text = "";
      int i;
      int a = 1;

      System.out.println("JavaMail API \n");
      System.out.println("Dies ist ein Programm zum Senden von Mails mittels der JavaMailAPI \n");

      try {
        Properties props = new Properties();

        System.out.println("Geben Sie bitte Ihren SMTP Host ein:");
        //internetadresse = eingabe(internetadresse);
        internetadresse = "mailgate.minet.uni-jena.de";
        props.put( "mail.smtp.host",  internetadresse );
        Session session = Session.getDefaultInstance( props );
        Message msg = new MimeMessage( session );

        System.out.println("Nennen Sie bitte Ihre E-Mail Adresse:");
        absender = "ijunold@minet.uni-jena.de";
        //absender = eingabe(absender);
        InternetAddress addressFrom = new InternetAddress( absender );
        msg.setFrom( addressFrom );
        System.out.println("Absender: " + absender);
        
        System.out.println("An wieviele Empfaenger soll die E- Mail geschickt werden?");
        anzahlempfaenger = eingabe(anzahlempfaenger);
        anzahl = Integer.parseInt(anzahlempfaenger);
        empfaenger = new String[anzahl];

        for (i = 0; i < empfaenger.length; i++) {

            System.out.println("An wen möchten Sie die E- Mail versenden? Geben Sie bitte die Empfaengeradresse ein:");
            //empfaenger = "ijunold@googlemail.com";
            empfaenger [i]= eingabe(empfaenger[i]);
            /*InternetAddress addressTo = new InternetAddress( empfaenger [i]);
            msg.setRecipient( Message.RecipientType.TO, addressTo );    */


        }
        for (i = 0; i < empfaenger.length; i++) {
            System.out.println("eingegebene Empfaenger: " + empfaenger[i] + "/n");
        }
        

            System.out.println("Geben Sie bitte den Betreff für die Nachricht ein:");
            subject = eingabe(subject);
            msg.setSubject( subject );
            System.out.println("Subject: " + subject);


                  String eingebenerText = "";
            System.out.println("Geben Sie bitte Ihre Nachricht ein (Sie beenden indem Sie ENDE eingeben):");
            //a = 1;
            do {

               text = eingabe(text);
            //text = "Hier ist die Nachricht";
              //  a++;
              eingebenerText = eingebenerText + " \n " + text;
              
              
              //System.out.println("Ihre Nachricht:" + eingebenerText + "/n");
              
              
            } while(!eingebenerText.endsWith("ENDE"));
            
            
            msg.setText( eingebenerText );
            
            System.out.println("Ihre Nachricht:" + eingebenerText);
            
            
            for (i = 0; i < empfaenger.length; i++) {
                InternetAddress addressTo = new InternetAddress( empfaenger [i]);
            msg.setRecipient( Message.RecipientType.TO, addressTo );
            
            
              // for (i = 0; i < a; i++) {
            //msg.setText( text [i] );
              //    }
                              
            Transport.send( msg );
            
            }
            System.out.println("Ihre Nachricht wurde versendet.");
      }
      //Hier folgt nun noch ExceptionHandling
      catch (AddressException ausnahme){
        System.out.println("AddressException");
        ausnahme.printStackTrace();
      }
      catch (MessagingException ausnahme){
        System.out.println("MessagingException");
        ausnahme.printStackTrace();
      }
    }

}

