package dummy;

import java.io.*;

import net.jxta.document.Document;
import net.jxta.peergroup.NetPeerGroupFactory;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupFactory;
import net.jxta.exception.PeerGroupException;
import net.jxta.document.MimeMediaType;
import net.jxta.document.AdvertisementFactory;
import net.jxta.membership.MembershipService;
import net.jxta.impl.peergroup.Platform;
import net.jxta.impl.peergroup.GenericPeerGroup;
import net.jxta.id.ID;
import net.jxta.id.IDFactory;
import java.net.URL;
import java.net.MalformedURLException; 
import java.net.UnknownServiceException; 


import net.jxta.protocol.PeerGroupAdvertisement;

public class dummynode {

    static PeerGroup netPeerGroup = null,
                     howPeerGroup = null;
    private PeerGroupAdvertisement howGroupAdv = null;
    private MembershipService howGroupMembership = null;

    public static void main(String args[]) {
        dummynode myapp = new dummynode();
        System.out.println ("Launching JXTA");

        myapp.launchJXTA();
        myapp.getJXTAInfo();

        System.out.println ("Terminating Application");
        System.exit(0);
    }

    /*
     *  Default Constructor
     */
    public void Example1() { 
    }

    /*
     *  Private method for starting the JXTA platform.  
    */
    private void launchJXTA() {
        try {
        	NetPeerGroupFactory netPeerGroupFactory = new NetPeerGroupFactory();
        	netPeerGroup = netPeerGroupFactory.getWeakInterface();
        } catch (PeerGroupException e) {
            System.out.println("Unable to create PeerGroup - Failure");
            e.printStackTrace();
            System.exit(1);
        }  
    }

    public void getJXTAInfo() {
	System.out.println("This Peer's ID in the group : " + netPeerGroup.getPeerID().toString());
	System.out.println("This Peer's Name in the group : " + netPeerGroup.getPeerName());
	System.out.println("The Peergroup's ID : " + netPeerGroup.getPeerGroupID().toString());
	System.out.println("The Peergroup's name : " + netPeerGroup.getPeerGroupName());
    }
}

