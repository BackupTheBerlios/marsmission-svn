package base;

import java.io.IOException;
import java.util.logging.Logger;

import database.DatabaseServer;
import net.jxta.discovery.DiscoveryService;
import net.jxta.peergroup.PeerGroup;
import net.jxta.pipe.InputPipe;
import net.jxta.pipe.OutputPipe;
import net.jxta.pipe.PipeService;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;
import net.jxta.protocol.PipeAdvertisement;

/**
 * 
 * @author Torsten Burschka
 *
 */

public class Configuration {

	/**
	 * Project name
	 */
	protected String project = "MarsMissionII";
	
	/**
	 * Node name 
	 */
	protected String nodeName = "Node";
	
	/**
	 * Node principal
	 */
	protected String principal = nodeName;
	
	/**
	 * Node password
	 */
	protected String password = "mars2007";
	
	protected static String rendezvousFile = "rdv.lst";
	
	protected static String relayFile = "rly.lst";
	
	/**
	 * PeerGroup
	 */
	protected PeerGroup netPeerGroup = null;

	/**
	 * Stores the configuration
	 */
	protected NetworkConfigurator configuration = null;

	/**
	 * Pipe Advertisement
	 */
	protected PipeAdvertisement pipeAdv = null;
	
	/**
	 * Pipe Service
	 */
	protected PipeService pipeServ = null;
	
	/**
	 * Discovery Service
	 */
	protected DiscoveryService discServ = null;
	
	/**
	 * NetworkManager
	 */
	protected NetworkManager manager; 
	
	/**
	 * Input pipe
	 */
	protected InputPipe inputPipe = null;
	
	/**
	 * Output pipe
	 */
	protected OutputPipe outputPipe = null;

	/**
	 * Message container for outgoing messages
	 */
	protected net.jxta.endpoint.Message message_out = null;

	/**
	 * Time to wait for a rendezvous connection in msec
	 */
	protected long waitTime = 10000;	
	
	public void Configuration (String profile) {
		this.nodeName = profile;
		System.setProperty("JXTA_HOME","."+this.project+"_"+this.nodeName);
		this.configuration = new NetworkConfigurator();
		if (!configuration.exists()) {
			createConfig();
		} else {
			System.out.println(nodeName+": Configuration found.");
			rendezvousList.addRendezvous(rendezvousList.getHostAdresses("tcp", 9701));
		}
		DatabaseServer.connect();
	}
	
	private void createConfig() {
		rendezvousList.setHomePath(getGlobalPath());
		rendezvousList.createRendezvousList(rendezvousList.getHostAdresses("tcp", 9701));
		// Create a new configuration with a new name, principal, and pass
		System.out.println("\n"+this.nodeName+": No configuration found. Autogenerate a new configuration.");
		configuration.setName(nodeName);
		configuration.setPrincipal(nodeName);
		configuration.setPassword("mars2007");
		configuration.setDescription("Autogenerated Platform Config Advertisement.");
		configuration.setTcpEnabled(true);
		configuration.setTcpIncoming(true);
		configuration.setTcpOutgoing(true);
		configuration.setHttpEnabled(false);
		configuration.setHttpIncoming(false);
		configuration.setHttpOutgoing(false);
		configuration.setUseMulticast(false);
		configuration.setUseOnlyRendezvousSeeds(true);
		Logger.getAnonymousLogger().info(toURI(rendezvousList.getHomePath()+rendezvousList.getRendezvousListName()));
		configuration.addRdvSeedingURI(toURI(rendezvousList.getHomePath()+rendezvousList.getRendezvousListName()));
		configuration.setTcpPort(9702);
		configuration.setTcpEndPort(9702);
		configuration.setTcpStartPort(9702);
		configuration.setUseOnlyRelaySeeds(false);
		configuration.setMode(NetworkConfigurator.TCP_CLIENT
				+ NetworkConfigurator.TCP_SERVER);
		try {
			// persist it
			configuration.save();

		} catch (IOException ioe) {
			System.err.println("\n"+this.nodeName+": Could not save the configuration!");
			System.err.println("\n"+this.nodeName+": Error Message:");
			ioe.printStackTrace();
			System.err.println();
		}
	}
	
}