import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Aufgabe extends Frame 
{
	TextField name, emailAdresse, benutzername, passwort, pop3server, smtpserver;
	Label     ausgabe, hinweis, hinweis2;
	Frame allg = new Frame("allg");
	Frame pop3frame = new Frame("pop3");
	Frame smtpframe = new Frame("smtp");
	String username, password, pop3;
	
	public Aufgabe () 
	{
		//Fenstertitel setzen  
		setTitle("Ein selbstprogrammierter Email-Client");  
		//Fenstergr��e einstellen
		setSize(500,250);                            
		//EventListener f�r das Fenster hinzuf�gen
		//notwendig, damit das Fenster geschlossen werden kann
		addWindowListener(new TestWindowListener()); 		
		//Hinweis ausgeben
		hinweis = new Label("Dies ist ein Programm zum Senden und Empfangen von Mails");
		add( BorderLayout.NORTH, hinweis );
		setMenuBar(getMenuBar());
		//Fenster und Inhalt sichtbar machen
		setVisible(true);
	}

	//Erstellung einer Men�bar und Ausf�hrung der Aktionen
	public MenuBar getMenuBar(){
		//Erstellung der Men�Bar
		MenuBar menueleiste = new MenuBar();
		//Erstellung der enzelnen Men�punkte
		Menu fileMenu = new Menu("E-Mails");
		MenuItem abrufen = new MenuItem("... abrufen von Konto xy");
		fileMenu.add(abrufen);
		
		//bei Klick auf Men�punkt "... abrufen von Konto xy" Aktion
		abrufen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getActionCommand().equals("... abrufen von Konto xy")){
					/*hinweis = new Label("Abrufen vom Konto XY");
					add( BorderLayout.CENTER, hinweis );
					//Empfang.main(new String[0]);
					setVisible(true);*/
					empfang();
			}}});
		
		//Erstellung eines Men�punktes "... senden"
		MenuItem saveItem = new MenuItem("... senden");
		fileMenu.add(saveItem);
		//Erstellung eines Men�punktes "... l�schen"
		fileMenu.add("... l�schen");
		fileMenu.addSeparator();
		
		//Erstellung eines Men�punktes "Programm schliessen"
		MenuItem quit = new MenuItem("Programm schliessen", new MenuShortcut('q'));
		fileMenu.add(quit);
		
		//Beenden des Programmes bei Klick auf "Programm schliessen"
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			if(e.getActionCommand().equals("Programm schliessen")){
				setVisible(false);
				dispose();
				System.exit(0);
			}}});
		
		//Erstellung eines Men�punktes "Kontoinformationen"
		Menu kontoinfos = new Menu("Kontoinformationen");
		MenuItem allgemein = new MenuItem("Allgemeines");
		kontoinfos.add(allgemein);
		//bei Klick auf Men�punkt "Kontoinformationen" Aktion
		allgemein.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getActionCommand().equals("Allgemeines")){
					kontoinfosallgemein();
				}
			}});
		
		//Erstellung eines Men�punktes "POP3"
		MenuItem pop3 = new MenuItem("POP3");
		kontoinfos.add(pop3);
		//bei Klick auf Men�punkt "POP3" Aktion
		pop3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getActionCommand().equals("POP3")){
					kontoinfospop3();
				}
			}});
		
		//Erstellung eines Men�punktes "SMTP"
		MenuItem smtp = new MenuItem("SMTP");
		kontoinfos.add(smtp);
		//bei Klick auf Men�punkt "SMTP" Aktion
		smtp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getActionCommand().equals("SMTP")){
					kontoinfossmtp();
				}
			}});
		
		//Anzeigen der Men�oberpunkte
		menueleiste.add(fileMenu);
		menueleiste.add(kontoinfos);
		return menueleiste;
	}
	
	//Fenster f�r die allgemeinen Kontoinformationen
	public void kontoinfosallgemein(){	
		allg.setVisible(true);
		//Festlegen des Titels des Fensters
		allg.setTitle("Allgemeine Kontoinformationen");  
		//Fenstergr��e einstellen
		allg.setSize(340,170); //350,100
		GridLayout gl = new GridLayout (7,2); //zeilenanzahl, spaltenanzahl
		allg.setLayout(gl);
		//Einf�gen der Labels und Textfelder
		allg.add(new Label("Ihr Name:"));
		name = new TextField(5);
		allg.add(name);
		
		allg.add(new Label("Ihre eMailadresse:"));
		emailAdresse = new TextField(5);
		allg.add(emailAdresse);
		
		allg.add(new Label("Username:"));
		benutzername = new TextField(5);
		allg.add(benutzername);
		
		//username = benutzername.getText();
		
		//System.out.println("Username drin: " + username);
		/*ausgabe = new Label();
		allg.add(ausgabe);
		benutzername.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//ausgabe.setText( "Text: " + benutzername.getText() );
				
				//String username = benutzername.getText();
				//System.out.println("drin: " + username);
			}});*/
		allg.add(new Label("Passwort:"));
		passwort = new TextField(5);
		allg.add(passwort);
		allg.add(new Label("bitte mit Klick auf den"));
		allg.add(new Label(""));
		allg.add(new Label("Button best�tigen"));
		Button b = new Button("OK");
		allg.add(b);
		//bei Klick auf den Button "OK" wird das Fenster "Allgemeine Kontoinformationen" geschlossen
		b.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				//allg.setVisible(false);
				/*String username = benutzername.getText();
				System.out.println("nicht drin: " + username);*/
				allg.dispose();
			 }
		});
	
		
		
		/*eingabe = new TextField(5);
		allg.add(BorderLayout.NORTH,eingabe);

		ausgabe = new Label();
		 allg.add( BorderLayout.SOUTH,  ausgabe );
		eingabe.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ausgabe.setText( "Der eingelesene Text lautet: " + eingabe.getText() );
				
			}});*/
		//allg.setVisible(true);
		//EventListener f�r das Fenster hinzuf�gen
		//notwendig, damit das Fenster geschlossen werden kann
		allg.addWindowListener(new AktFensterSchliessen());
	}

	//Fenster f�r die POP3 Kontoinformationen
	public void kontoinfospop3(){
		pop3frame.setTitle("pop3 Informationen");  
		//Fenstergr��e einstellen
		pop3frame.setSize(350,90);
		GridLayout gl = new GridLayout (3,2); //zeilenanzahl, spaltenanzahl
		pop3frame.setLayout(gl);
		
		pop3frame.add(new Label("pop3 Server:"));
		pop3server = new TextField(5);
		pop3frame.add(pop3server);
		pop3frame.add(new Label("bitte mit Klick auf den"));
		pop3frame.add(new Label(""));
		pop3frame.add(new Label("Button best�tigen"));
		Button b = new Button("OK");
		pop3frame.add(b);
		pop3frame.setVisible(true);
		//bei Klick auf den Button "OK" wird das Fenster "Allgemeine Kontoinformationen" geschlossen
		b.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				//allg.setVisible(false);
				pop3frame.dispose();
			 }
		});
		//EventListener f�r das Fenster hinzuf�gen
		//notwendig, damit das Fenster geschlossen werden kann
		pop3frame.addWindowListener(new AktFensterSchliessen());
	}

	//Fenster f�r die SMTP Kontoinformationen
	public void kontoinfossmtp(){
		smtpframe.setTitle("smtp Informationen");  
		//Fenstergr��e einstellen
		smtpframe.setSize(350,80);
		GridLayout gl = new GridLayout (3,2); //zeilenanzahl, spaltenanzahl
		smtpframe.setLayout(gl);
		
		smtpframe.add(new Label("smtp Server:"));
		smtpserver = new TextField(5);
		smtpframe.add(smtpserver);
		smtpframe.add(new Label("bitte mit Klick auf den"));
		smtpframe.add(new Label(""));
		smtpframe.add(new Label("Button best�tigen"));
		Button b = new Button("OK");
		smtpframe.add(b);
		smtpframe.setVisible(true);
		//bei Klick auf den Button "OK" wird das Fenster "Allgemeine Kontoinformationen" geschlossen
		b.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				//allg.setVisible(false);
				smtpframe.dispose();
			 }
		});
		//EventListener f�r das Fenster hinzuf�gen
		//notwendig, damit das Fenster geschlossen werden kann
		smtpframe.addWindowListener(new AktFensterSchliessen());
	}
	
	//Methode zum Einlesen von Strings
    public static String Eingabe (String text){
    
      BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
      try {
        text = userIn.readLine();
      }
      catch (IOException ausnahme){
        ausnahme.printStackTrace();
      }
      return text;
    }
    
	//Empfang der EMails
    public void empfang() {
    	//Initialisierung der Variablen
	    String antwort = "";
	    int antwort2;
	    //String username = "";
	    String internetadresse = "";
	    //String passwort = "";
	    Store mystore = null;
	    Session mysession;
	    Folder myfolder=null;
	    Folder inboxfolder=null;
	    int laenge;
	    String umwandlung;
	    
	    //  System.out.println("Dies ist ein Programm zum Abrufen von Mails mittels der JavaMailAPI");  
	       
	      //Eingabe der relevanten Gr��en
	      System.out.println("Geben Sie bitte Ihren POP3- Server ein.");
	      //internetadresse = Eingabe(internetadresse);
	      pop3 = pop3server.getText();
	      System.out.println(pop3);
	      
	      System.out.println("Geben Sie bitte Ihren Usernamen ein.");
	      username = benutzername.getText();
	      System.out.println(username);

	      System.out.println("Geben Sie bitte Ihr Passwort ein.");
	      password = passwort.getText();
	      System.out.println(password);
	      
	      try {
	        
	        //Erstellen einer Session
	        Properties props = System.getProperties();
	        mysession = Session.getInstance(props,null);
	        
	        //Festlegen einer geeigneten Store f�r den Provider
	        mystore = mysession.getStore("pop3");
	        
	        //Verbindungsaufbau zum angegebenen POP3-Server mit dem Username und dem Passwort
	          mystore.connect(pop3, username, password);
	          System.out.println("Verbindung hergestellt");
	          
	          //Navigieren in der Baumhierarchie -> Finden des INBOX-Folders 
	          myfolder=mystore.getDefaultFolder();
	          inboxfolder=myfolder.getFolder("INBOX");
	          
	          //�ffnen des Folders mit READ_ONLY (es k�nnen keine Ver�nderungen mehr vorgenommen werden
	          inboxfolder.open(Folder.READ_ONLY);
	          
	          //Nachrichten werden in ein Array "geladen"
	          Message[] msgs= inboxfolder.getMessages();
	      
	          //L�nge des Arrays wird bestimmt
	          laenge = msgs.length;
	          System.out.println("Anzahl der Nachrichten: " + laenge);
	          
	          for (int i = 0; i < laenge; i++){
	            
	            Message m = msgs[i];
	            
	            //Abrufen der Nummer der Nachrichten
	            System.out.print("Nummer: " + msgs[i].getMessageNumber() + "  ");
	            
	            //Abrufen des Betreffs
	            System.out.println("Betreff: " + msgs[i].getSubject() + "  ");
	                        
	            //Abrufen des Absenders
	            System.out.println("Absender: " + m.getFrom()[0]); 
	            
	            System.out.println("");
	            }
	                
	          //Schleife zum Abfragen, welche Nachricht gelesen werden soll 
	          //oder ob Verbindung getrennt werden soll
	          do {
	            
	            System.out.println("Welche Nachricht m�chten Sie lesen? Geben Sie bitte dazu die Nummer ein.");
	            
	            //Eingabe der Nummer und Parsen in int, damit dann Vergleich angestrebt werden kann
	            antwort = Eingabe(antwort);
	            antwort2 = Integer.parseInt(antwort);
	            
	            //Festlegung, welche Nachricht abgerufen werde soll
	            //-1, da Array von 0 bis n-1 geht, die Aufz�hlung im Programm aber bei 1 beginnt
	            Message m = msgs[antwort2-1];
	          
	            //Hier wird der Inhalt der Nachricht geladen
	            umwandlung = m.getContent().toString();
	            
	            //Test, ob Uni-Server oder zb GMX-Server, da diese unterschiedliche Aufschl�sselung haben
	            if (!umwandlung.startsWith("javax.mail.internet.MimeMultipart")){
	                
	                //if-Zweig behandelt Uni-Server
	                //hier wird einfach nur Nachricht ausgegeben
	                System.out.println("Nachricht :");
	                System.out.println(m.getContent());
	            } 
	            else {
	              
	                //else-Zweig behandelt andere Server
	                //Laden des Inhalts und Parsen in Multipart
	                Multipart mp = (Multipart) m.getContent(); 
	                
	                //mp.getCount gibt die Anzahl der eingeschlossenen BodyPart-Objekte zur�ck
	                for ( int j = 0; j < mp.getCount(); j++ ) 
	                { 
	                  
	                  //getBodPart(j) gibt einen speziellen Part zur�ck
	                  Part part = mp.getBodyPart( j );
	                  
	                  //gibt die Art des Parts zur�ck
	                  String disposition = part.getDisposition();
	                  
	                  if ( disposition == "inline" );{ 
	                  
	                    //parsen des Parts in einen MimeBodyPart- Objekt
	                    MimeBodyPart mimePart = (MimeBodyPart)part; 
	                    
	                    //wenn es Klartext ist, soll es ausgegeben werden
	                    if ( mimePart.isMimeType("text/plain")){ 
	                    
	                      BufferedReader in = new BufferedReader(new InputStreamReader(mimePart.getInputStream()) );
	                      
	                      for(String line; (line=in.readLine()) != null; ){
	                        System.out.println( line ); 
	                      }
	                    } 
	                  }
	                }
	            } 
	            
	            System.out.print("\n");
	            //Abfrage, was als n�chstes getan werden soll
	            System.out.println("Moechten Sie beenden (1) oder weitere EMails lesen(2)?");
	            antwort = Eingabe(antwort);
	            
	          } while (!antwort.equals("1"));
	          
	          //Session wird geschlossen
	          mystore.close(); 
	          System.out.println("Verbindung geschlossen");
	            
	      }
	      //Hier folgt nun noch ExceptionHandling
	      catch (ClassCastException ausnahme){
	          System.out.println("ClassCastException");
	          ausnahme.printStackTrace();
	      } 
	      catch (IOException ausnahme){
	        System.out.println("IOException");
	        ausnahme.printStackTrace();
	      } 
	      catch (NoSuchProviderException ausnahme) {
	        System.out.println("NoSuchProviderException");
	        ausnahme.printStackTrace();
	      } 
	      catch (MessagingException ausnahme) {
	        System.out.println("MessagingException");
	        ausnahme.printStackTrace();
	      } 
	      catch (ArrayIndexOutOfBoundsException ausnahme) {
	        System.out.println("ArrayIndexOutOfBoundsException");
	        ausnahme.printStackTrace();
	      } 
	    }
	

	//wenn Fenster geschlossen wird, wird auch Anwendung geschlossen
	class TestWindowListener extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			//Fenster und VM "killen"
			e.getWindow().dispose();                  
			System.exit(0);                            
		}           
	}
	
	class AktFensterSchliessen extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			e.getWindow().dispose();
		}
	}

	public static void main (String args[]) 
	{
		//im HP wird ein neuer Frame vom Typ standard erzeugt  
		new Aufgabe ();
	}
}
