package base;

/**
 * 
 * @author Torsten Burschka
 *
 */

public class Configuration {

	private String project = "MarsMissionII";
	
	private String nodeName = "Node";
	
	private String nodePath = "."+project+"_";
	
	private String peerGroup = project;
	
	private String principal = nodeName;
	
	private String password = "mars2007";
	
	private static String rendezvousFile = "rdv.lst";
	
	private static String relayFile = "rly.lst";
	
	private static String messageFile = "messages.xml";
	
	public static String getMessageFileName() {
		return messageFile;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getPeerGroup() {
		return peerGroup;
	}

	public void setPeerGroup(String peerGroup) {
		this.peerGroup = peerGroup;
	}

	public String getRendezvousFile() {
		return rendezvousFile;
	}

	public String getRelayFile() {
		return relayFile;
	}	
}