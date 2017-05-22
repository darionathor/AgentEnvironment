package agents;

import data.ACLMessage;
import data.Agent;
import data.Performative;

public class Participant extends Agent {

	@Override
	public void handleMessage(ACLMessage message) {
		// TODO Auto-generated method stub
		ACLMessage reply = message.makeReply(Performative.ACCEPT_PROPOSAL);
		post(reply);
	}

}
