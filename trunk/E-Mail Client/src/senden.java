import java.io.*;
import java.net.*;

public class senden{
  
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
  
  
  //Hauptprogramm
  public static void main(String[] args){
    
    try{
      System.out.println("Dies ist ein Programm zum Senden von Mails mittels Sockets");
      
      //Initialisierung der benutzten Namen 
      String absenderadresse = "";
      String betreff = "";
      String nachricht = "";
      String server = "";
      String entsch = "";
      String msg = "";
      int entsch2;
      String[] empfaenger;
      String anzahlString = "";
      int anzahl;

      do{
        //Eingabe der wichtigsten Größen
        System.out.println("Bitte geben Sie den SMTP-Server an");
        server = eingabe(server);
        System.out.println("Bitte geben Sie die Absenderadresse ein");
        absenderadresse = eingabe(absenderadresse);
        
        //Empfaengerarray wird angelegt
        System.out.println("An wieviele Empfaenger wollen Sie senden?");
        anzahlString = eingabe(anzahlString);
        anzahl = Integer.parseInt(anzahlString);
        empfaenger = new String[anzahl];
        
        for(int i = 0; i < empfaenger.length; i++){
          System.out.println("Bitte geben Sie die Empfaengeradressen ein");
          empfaenger[i] = eingabe(empfaenger[i]);
        }
      
        //Verbindung zum Server
        System.out.println("Verbindungsaufbau...");
        Socket mysocket = new Socket(server,25);
        OutputStream smtpout = mysocket.getOutputStream();
        BufferedReader smtpin = new BufferedReader(new InputStreamReader(mysocket.getInputStream()));
      
        //Nachschauen, ob Verbindung besteht
        String status = smtpin.readLine();
        if(!status.startsWith("220")){
          System.out.println("Keine Verbindung zu SMTP-Server herstellbar");
          msg = "QUIT\n";
          smtpout.write(msg.getBytes());
          mysocket.close();
        return;
        }
        System.out.println("Verbunden");
      
        //Vorstellen
        msg = "HELO " + InetAddress.getLocalHost().getHostAddress() + "\n";
        smtpout.write(msg.getBytes());
        status = smtpin.readLine();
        if(!status.startsWith("250")){
          System.out.println("Inkorrekter Host-Name");
          smtpout.write(msg.getBytes());
          mysocket.close();
          return;
        }
          
        //Absenderadresse eingeben
        msg = "MAIL FROM: " + absenderadresse + "\n";
        smtpout.write(msg.getBytes());
        status = smtpin.readLine();
        if(!status.startsWith("250")){
          System.out.println("Absenderadresse existiert nicht");
          msg = "QUIT\n";
          smtpout.write(msg.getBytes());
          mysocket.close();
          return;
        }
      
        //Empfaengeradresse wird an socket uebergeben
        for(int j = 0; j < empfaenger.length; j++){
          msg = "RCPT TO: " + empfaenger[j] + "\n";
          smtpout.write(msg.getBytes());
          status = smtpin.readLine();
          if(!status.startsWith("250")){
            System.out.println("Empfaengeradresse existiert nicht");
            msg = "QUIT\n";
            smtpout.write(msg.getBytes());
            mysocket.close();
            return;
          }
        }
      
        //Betreff eingeben
        System.out.println("Bitte Betreff eingeben");
        betreff = eingabe(betreff);
        System.out.println("Bitte Nachricht eingeben und mit 'Ende' beenden");
      
        //Nachricht wird eingegeben und abgeschickt
        msg = "DATA\n";
        smtpout.write(msg.getBytes());
        status = smtpin.readLine();
        if(!status.startsWith("354")){
          System.out.println("Es ist ein Fehler aufgetreten");
          msg = "QUIT\n";
          smtpout.write(msg.getBytes());
          mysocket.close();
          return;
        }
      
        smtpout.write(("Subject: " + betreff + "\n").getBytes());
      
        do{
          nachricht = eingabe(nachricht);
          smtpout.write((nachricht + "\n").getBytes());

        } while(!nachricht.startsWith("Ende"));
      
        smtpout.write(("\n.\n").getBytes());
        status = smtpin.readLine();
        if(!status.startsWith("250")){
          System.out.println("Es ist ein Fehler bei der Nachrichtenuebermittlung aufgetreten");
          smtpout.write("QUIT\n".getBytes());
          mysocket.close();
          return;
        }
        
        System.out.println("Email(s) wurde(n) versandt!");
        
        //Auswahl, ob Beenden oder nicht
        System.out.println("Wollen Sie das Programm beenden? Druecken Sie 1 fuer <j> oder 2 fuer <n>");
        entsch = eingabe(entsch);
        entsch2 = Integer.parseInt(entsch);
        
        if(entsch2 == 1){
          msg = "QUIT\n";
          smtpout.write(msg.getBytes());
          status = smtpin.readLine();
          if(!status.startsWith("221")){
            System.out.println("Verbindung konnte aufgrund eines Fehlers nicht beendet werden");
            msg = "QUIT\n";
            smtpout.write(msg.getBytes());
            mysocket.close();
            return;
          }
          System.out.println("Das Programm wird beendet!");
          break;
        }
        mysocket.close();
      }while(entsch2 == 2);
    }
    catch(Exception ausnahme){
      System.out.println("Es ist ein Fehler aufgetreten. Das Programm wird beendet");
    }   
  }
}