package dummy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import net.jxta.exception.PeerGroupException;
import net.jxta.peergroup.NetPeerGroupFactory;
import net.jxta.peergroup.PeerGroup;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.rendezvous.RendezVousService;
import net.jxta.rendezvous.RendezvousEvent;
import net.jxta.rendezvous.RendezvousListener;


public class dummyserver {
	public static void main(String[] args) {

		RendezVousService rendezvousService = null;
		
		class rendezvousListener implements RendezvousListener {
			public void rendezvousEvent(RendezvousEvent arg0) {
				System.out.println("New Event: "+arg0);
			}
		}
	  
	  // Set up the information we need to create the peer group.
    dummyserver runner = new dummyserver();
    runner.makeConfig();
    PeerGroup pg = runner.startJxta();
    System.out.println();
    System.out.println("PeerGroupName:       "+pg.getPeerGroupName());
    System.out.println("PeerName:            "+pg.getPeerName());
    System.out.println("PeerGroupID:         "+pg.getPeerGroupID());
    System.out.println("PeerID:              "+pg.getPeerID());
    System.out.println("");
 
      System.out.println("Server ready!");
		rendezvousService = pg.getRendezVousService();
		rendezvousService.addListener(new rendezvousListener());
		BufferedReader in
		   = new BufferedReader(new InputStreamReader(System.in));
		try {
			in.readLine();
		} catch (IOException ioe)
		{
			
		}
	System.out.println("Server stopping!");
    pg.stopApp();
  }

  private void makeConfig() {
    NetworkConfigurator config = new NetworkConfigurator();
    if (config.exists())
      try {
        config.load();
      } catch (Exception e) {
        System.err.println(e.toString());
      }
    // Create a new configuration with a new name, principal, and
    // pass
    config.setName("server");
    config.setPrincipal("server");
    config.setPassword("mars2007");
    config.setHttpEnabled(true);
    config.setHttpIncoming(true);
    config.setHttpOutgoing(true);
    config.setHttpPort(9700);
    config.setTcpEnabled(true);
    config.setTcpIncoming(true);
    config.setTcpOutgoing(true);
    config.setUseMulticast(false);
    config.setTcpPort(9701);
    config.setUseOnlyRendezvousSeeds(false);
    config.setUseOnlyRelaySeeds(false);
    config.setTcpEndPort(9701);
    config.setTcpStartPort(9701);
    List<String> seedList = new LinkedList<String>();
    config.setRendezvousSeedURIs(seedList);
    config.setRelaySeedURIs(seedList);
    config.setMode(NetworkConfigurator.RDV_SERVER
        +NetworkConfigurator.TCP_SERVER+NetworkConfigurator.TCP_CLIENT
        +NetworkConfigurator.RELAY_SERVER+NetworkConfigurator.HTTP_SERVER
        +NetworkConfigurator.HTTP_CLIENT);
    try {
      // persist it
      config.save();
    } catch (Exception io) {
      System.err.println("Could not write config!");
    }
  }

  private PeerGroup startJxta() {
    try { // create and start the default JXTA
      NetPeerGroupFactory netPeerGroupFactory = new NetPeerGroupFactory();
      return netPeerGroupFactory.getInterface();
    } catch (PeerGroupException e) {
      System.out.println("Fatal error : group creation failure.");
      e.printStackTrace();
      System.exit(1);
      return null;
    }
  }
}
