package agents;

import java.util.Arrays;

import data.ACLMessage;
import data.AID;
import data.Agent;
import data.Performative;

public class Initiator extends Agent {
	private int pendingProposals;
	@Override
	public void handleMessage(ACLMessage message) {
		// TODO Auto-generated method stub
		switch (message.performative) {
		case REQUEST:
			sendMessage("sending proposals");
			System.out.println("sending proposals");
			AID[] participants = (AID[]) message.contentObj;
			sendCfps(participants);
			pendingProposals = participants.length;
			break;
		case ACCEPT_PROPOSAL:
			sendMessage("recieved an accept");
			System.out.println("recieved an accept");
			--pendingProposals;
			if (pendingProposals == 0)
				;// agm().stopAgent(myAid);
			break;
		default:
			sendMessage("Message not understood");
			System.out.println("Message not understood");
		}
	}
	private void sendCfps(AID[] participants) {
		ACLMessage msg = new ACLMessage();
		msg.performative=Performative.CALL_FOR_PROPOSAL;
		msg.sender = getId();
		msg.receivers.addAll(Arrays.asList(participants));
		post(msg);
	}
}
