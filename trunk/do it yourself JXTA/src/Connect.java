import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import net.jxta.platform.NetworkManager;
import net.jxta.platform.NetworkManager.ConfigMode;
import net.jxta.rendezvous.RendezvousEvent;
import net.jxta.rendezvous.RendezvousListener;

public class Connect implements RendezvousListener {
	
	public void rendezvousEvent(RendezvousEvent event) {
        if (event.getType() == RendezvousEvent.RDVCONNECT ||
            event.getType() == RendezvousEvent.RDVRECONNECT ||
            event.getType() == RendezvousEvent.BECAMERDV) {
      		System.out.println("Rendezvous Event");
        }
    }
 
	
	public Connect(String nodeName) {
		NetworkManager manager = null;

		try {
			System.setProperty("net.jxta.logging.Logging", "FINEST");
			System.setProperty("net.jxta.level", "FINEST");
			System.setProperty("java.util.logging.config.file", "logging.properties");
			File home = new File("."+nodeName);
	        manager = new NetworkManager(ConfigMode.RENDEZVOUS,nodeName,home.toURI());
	        System.out.println(nodeName+": Starting RENDEZVOUS");
	        manager.startNetwork();
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.exit(-1);
	    }
		
	}
	
	public static void main(String[] args) {
		BufferedReader in
		   = new BufferedReader(new InputStreamReader(System.in));
		try {
			Connect connection = new Connect(in.readLine());
			in.readLine();
		} catch (IOException ioe)
		{
		}
	}
}
