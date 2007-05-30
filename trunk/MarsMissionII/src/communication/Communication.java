package communication;

import message.Message;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import database.DatabaseServer;

import base.Configuration;
import net.jxta.document.AdvertisementFactory;
import net.jxta.document.MimeMediaType;
import net.jxta.endpoint.StringMessageElement;
import net.jxta.exception.PeerGroupException;
import net.jxta.peergroup.NetPeerGroupFactory;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.rendezvous.RendezVousService;
import net.jxta.rendezvous.RendezvousEvent;
import net.jxta.rendezvous.RendezvousListener;
import net.jxta.util.PipeUtilities;

public class Communication implements PipeMsgListener {

	RendezVousService rendezvousService = null;
	
	class rendezvousListener implements RendezvousListener {
		public void rendezvousEvent(RendezvousEvent arg0) {}
	}
	
	public void connect () {
		System.out.println("\n"+Configuration.profileName+": Try to connect to PeerGroup.");
		try {
			NetPeerGroupFactory netPeerGroupFactory = new NetPeerGroupFactory();
			Configuration.netPeerGroup = netPeerGroupFactory.getInterface();
			Configuration.netPeerGroup.startApp(null);
			System.out.println(Configuration.profileName+": PeerGroupName:       "
					+ Configuration.netPeerGroup.getPeerGroupName());
			System.out.println(Configuration.profileName+": PeerName:            "
					+ Configuration.netPeerGroup.getPeerName());
			System.out.println(Configuration.profileName+": PeerGroupID:         "
					+ Configuration.netPeerGroup.getPeerGroupID());
			System.out.println(Configuration.profileName+": PeerID:              "
					+ Configuration.netPeerGroup.getPeerID());
			System.out.println("");
		} catch (PeerGroupException e) {
			System.err.println("\n"+Configuration.profileName+": Unable to create a new PeerGroup!");
			e.printStackTrace();
			System.exit(1);
		}
		
		rendezvousService = Configuration.netPeerGroup.getRendezVousService();
		rendezvousService.addListener(new rendezvousListener());
		int i = 1;
		System.out.print(Configuration.profileName+": Try to connect to rendezvous server.");
		while (!rendezvousService.isConnectedToRendezVous()) {
			try {
				Thread.sleep(100L);
			} catch (Exception e) {
			}

			if (i <= Configuration.waitTime/100) {
				System.out.print(".");
				i = i+1;
			} else {
				break;
			}
		}
		if (rendezvousService.isConnectedToRendezVous()) {
			System.out.println("\n"+Configuration.profileName+": Found rendezvous server:    "+rendezvousService.getConnectedPeerIDs()+"\n");
//			openInputPipe();
		} else {
			System.out.println("\n"+Configuration.profileName+": No rendezvous server found. Node will now act as rendezvous server.");
			rendezvousService.setAutoStart(true);
			System.out.println("\n"+Configuration.profileName+": Rendezvous status: "+rendezvousService.getRendezVousStatus()+"\n");
		}
	}

	/**
	 * Pipe Message Event
	 */
	public void pipeMsgEvent (PipeMsgEvent event) {
		Message message = Message.fromXML(event.getMessage().getMessageElement("data").toString());
		Logger.getAnonymousLogger().info(event.getMessage().getMessageElement("data").toString());
		DatabaseServer.insertMessage(message);
		System.out.println("\n"+Configuration.profileName+": New Message from "+message.getSender());
	}
	
	/**
	 * Write out the own PipeAdverisement in a file
	 * 
	 * @param pipe advertisement
	 */
	public void storePipeAdvertisement(PipeAdvertisement pipeAdv) throws IOException {
		try {
			FileWriter file = new FileWriter(Configuration.globalPath+"PipeAdv_"+Configuration.profileName+".xml");
			file.write(pipeAdv.toString());
			file.close();
		} catch (IOException ioe) {
			System.err.println("\n"+Configuration.profileName+": Failed to store own pipe advertisement.");
			System.err.println("\n"+Configuration.profileName+": Error Message:");
			ioe.printStackTrace();
			System.err.println();
		}
	}
	
	/**
	 * Load the pipeAdverisement from a specified node
	 * @param nodeName
	 *            Name of the node, which pipe advertisement we want to have
	 * @return
	 *            The wanted pipe advertisement
	 */
	public PipeAdvertisement loadPipeAdverisement (String nodeName) throws IOException {
		PipeAdvertisement returnPipeAdvertisement = null;
		try {
		FileInputStream is = new FileInputStream(Configuration.globalPath+"PipeAdv_"+nodeName+".xml");
		returnPipeAdvertisement = (PipeAdvertisement)AdvertisementFactory.newAdvertisement(new MimeMediaType("text/xml"), is);
		} catch (Exception e) {
			System.out.println("\n"+Configuration.profileName+": failed to read/parse pipe advertisement");
			System.err.println("\n"+Configuration.profileName+": Error Message:");
			e.printStackTrace();
			System.err.println();
			System.exit(-1);
		}
		return returnPipeAdvertisement;
	}
	
	/**
	 * Open the Pipes to a specified node
	 * @param nodeName
	 *            Name of the node, we want to connect.
	 */
	public void openOutputPipe(String nodeName) {
		try {
			Configuration.pipeAdv = loadPipeAdverisement(nodeName);
			System.out.println("\n"+Configuration.profileName+": Pipe Advertisement of "+nodeName+" successfully loaded.");
		} catch (IOException ioe_load) {
			System.out.println("\n"+Configuration.profileName+": Could not find the pipe advertisement of "+nodeName+". Create a new one.");
			Configuration.pipeAdv  = PipeUtilities.createNewPipeAdvertisement(Configuration.netPeerGroup,PipeService.PropagateType);
			System.out.println("\n"+Configuration.profileName+": New pipe advertisement for "+nodeName+" created.");
			try {
				storePipeAdvertisement(Configuration.pipeAdv);
				System.out.println("\n"+Configuration.profileName+": Successfully stored pipe advertisement.");
			} catch (IOException ioe_store) {
				System.err.println("\n"+Configuration.profileName+": Could not store the advertisement!");
			}
		}
		try {
			/*OutputPipe*/ Configuration.outputPipe = Configuration.pipeServ.createOutputPipe(Configuration.pipeAdv,Configuration.waitTime);
			System.out.println("\n"+Configuration.profileName+": Successfully created pipes to "+nodeName+".");
		} catch (IOException ioe) {
			System.err.println("\n"+Configuration.profileName+": Pipe creation failed!");
			System.err.println("\n"+Configuration.profileName+": Error Message:");
			ioe.printStackTrace();
			System.err.println();
		}
	}
	
	public void openInputPipe() {
		try {
			Configuration.pipeAdv = loadPipeAdverisement(Configuration.profileName);
			System.out.println("\n"+Configuration.profileName+": Pipe Advertisement of "+Configuration.profileName+" successfully loaded.");
		} catch (IOException ioe_load) {
			System.out.println("\n"+Configuration.profileName+": Could not find the pipe advertisement of "+Configuration.profileName+". Create a new one.");
			Configuration.pipeAdv  = PipeUtilities.createNewPipeAdvertisement(Configuration.netPeerGroup,PipeService.PropagateType);
			System.out.println("\n"+Configuration.profileName+": New pipe advertisement for "+Configuration.profileName+" created.");
			try {
				storePipeAdvertisement(Configuration.pipeAdv);
				System.out.println("\n"+Configuration.profileName+": Successfully stored pipe advertisement.");
			} catch (IOException ioe_store) {
				System.err.println("\n"+Configuration.profileName+": Could not store the advertisement!");
			}
		}
		Configuration.pipeServ = Configuration.netPeerGroup.getPipeService();
		try {
			/*InputPipe*/ Configuration.inputPipe = Configuration.pipeServ.createInputPipe(Configuration.pipeAdv,this);
			System.out.println("\n"+Configuration.profileName+": Successfully created inputPipe.");
		} catch (IOException ioe) {
			System.err.println("\n"+Configuration.profileName+": Pipe creation failed!");
			System.err.println("\n"+Configuration.profileName+": Error Message:");
			ioe.printStackTrace();
			System.err.println();
		}		
	}
	
	/**
	 * Sending a message
	 */
	public void sendMessage(message.Message message) {
		Configuration.message_out = new net.jxta.endpoint.Message();
		
		try {
			Configuration.message_out.addMessageElement(new StringMessageElement("data",message.toString(),null));
			Configuration.outputPipe.send(Configuration.message_out);
		} catch (IOException ioe_send) {
			System.err.println("\n"+Configuration.profileName+": Could not send the Message!");
			System.err.println("\n"+Configuration.profileName+": Error Message:");
			ioe_send.printStackTrace();
			System.err.println();
		}
	}
	
	/**
	 * Close opened pipes
	 */
	public void closeOutputPipe () {
		Configuration.outputPipe.close();
	}
	
	public void disconnect () {
		Configuration.netPeerGroup.stopApp();
		rendezvousService.stopApp();
	}
}