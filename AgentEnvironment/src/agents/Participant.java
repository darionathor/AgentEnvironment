package agents;

import javax.ejb.Stateful;

import data.ACLMessage;
import data.Agent;
import data.Performative;

@Stateful
public class Participant extends Agent {

	@Override
	public void handleMessage(ACLMessage message) {
		// TODO Auto-generated method stub
		ACLMessage reply = message.makeReply(Performative.ACCEPT_PROPOSAL);
		post(reply);
	}

}
