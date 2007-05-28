package communication;

import base.Configuration;

import net.jxta.rendezvous.RendezVousService;
import net.jxta.rendezvous.RendezvousEvent;
import net.jxta.rendezvous.RendezvousListener;

public class Communication extends Configuration {

	RendezVousService rendezvousService = null;
	
	class rendezvousListener implements RendezvousListener {
		public void rendezvousEvent(RendezvousEvent arg0) {}
	}

	public void blubb () {
		outputPipe = null;
	}
	
}
