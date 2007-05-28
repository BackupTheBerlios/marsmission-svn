package base;

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

}