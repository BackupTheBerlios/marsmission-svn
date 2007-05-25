package node;

import net.jxta.rendezvous.RendezVousService;
import net.jxta.rendezvous.RendezvousEvent;
import net.jxta.rendezvous.RendezvousListener;

public class Communication {

	RendezVousService rendezvousService = null;
	
	class rendezvousListener implements RendezvousListener {
		public void rendezvousEvent(RendezvousEvent arg0) {}
	}
	
	
}
