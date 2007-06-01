import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class standard extends Frame 
{
	TextField eingabe;
	Label     ausgabe, hinweis, hinweis2;
	
	public standard () 
	{
		//Fenstertitel setzen  
		setTitle("Ein selbstprogrammierter Email-Client");  
		//Fenstergröße einstellen
		setSize(500,250);                            
		//EventListener für das Fenster hinzufügen
		//notwendig, damit das Fenster geschlossen werden kann
		addWindowListener(new TestWindowListener()); 		
		
		//Hinweis ausgeben
		hinweis = new Label("Dies ist ein Programm zum Senden und Empfangen von Mails");
		add( BorderLayout.NORTH, hinweis );
		setMenuBar(getMenuBar());
		
		//Fenster und Inhalt sichtbar machen
		setVisible(true);
	}
	

	
	
	public MenuBar getMenuBar(){
		MenuBar menueleiste = new MenuBar();
		Menu fileMenu = new Menu("E-Mails");
		MenuItem abrufen = new MenuItem("... abrufen von Konto xy");
		fileMenu.add(abrufen);
		abrufen.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getActionCommand().equals("... abrufen von Konto xy")){
					hinweis = new Label("Abrufen vom Konto XY");
					add( BorderLayout.CENTER, hinweis );
					//Empfang.main(new String[0]);
					setVisible(true);
			}}});
		MenuItem saveItem = new MenuItem("... senden");
		fileMenu.add(saveItem);
		fileMenu.add("... Löschen");
		fileMenu.addSeparator();
		MenuItem quit = new MenuItem("Programm schliessen", new MenuShortcut('q'));
		fileMenu.add(quit);
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			if(e.getActionCommand().equals("Programm schliessen")){
				setVisible(false);
				dispose();
				System.exit(0);
			}}});
		
		Menu kontoinfos = new Menu("Kontoinformationen");
		MenuItem allgemein = new MenuItem("allgemein");
		kontoinfos.add(allgemein);
		allgemein.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getActionCommand().equals("allgemein")){
					
					kontoinfosallgemein();
					
				}
			}});
		MenuItem pop3 = new MenuItem("POP3");
		kontoinfos.add(pop3);
		//pop3.addActionListener();
		MenuItem smtp = new MenuItem("SMTP");
		kontoinfos.add(smtp);
		//smtp.addActionListener(this);
		menueleiste.add(fileMenu);
		menueleiste.add(kontoinfos);
		return menueleiste;
	}
	
	public void kontoinfosallgemein(){
		//neuen Frame erstellen 
		Frame allg = new Frame("irgendwas");
		allg.setTitle("Allgemeine Kontoinformationen");  
		//Fenstergröße einstellen
		allg.setSize(500,250);    
		eingabe = new TextField(5);
		allg.add(BorderLayout.NORTH,eingabe);

		ausgabe = new Label();
		 allg.add( BorderLayout.SOUTH,  ausgabe );
		eingabe.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ausgabe.setText( "Der eingelesene Text lautet: " + eingabe.getText() );
				
			}});
		allg.setVisible(true);
		//EventListener für das Fenster hinzufügen
		//notwendig, damit das Fenster geschlossen werden kann
		allg.addWindowListener(new AktFensterSchliessen());
		
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
		new standard ();
	}
}
