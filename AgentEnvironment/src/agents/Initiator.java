package agents;

import java.util.ArrayList;
import java.util.Arrays;

import javax.ejb.Stateful;

import data.ACLMessage;
import data.AID;
import data.Agent;
import data.Performative;

@Stateful
public class Initiator extends Agent {
	private int pendingProposals;
	@Override
	public void handleMessage(ACLMessage message) {
		// TODO Auto-generated method stub
		switch (message.performative) {
		case REQUEST:
			sendMessage("sending proposals");
			System.out.println("sending proposals");
			AID[] activeAgents = getActiveAgents();
			ArrayList<AID> participants= new ArrayList<>();
			for(AID a:activeAgents){
				if(a.getType().getName().equals("participant"))
					participants.add(a);
			}
			sendCfps( participants.toArray(new AID[1]));
			pendingProposals = participants.size();
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
