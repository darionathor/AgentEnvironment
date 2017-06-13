package agents;

import javax.ejb.Stateful;

import data.ACLMessage;
import data.Agent;
import data.DataHolder;
import data.Performative;

@Stateful
public class Pong extends Agent {
	private int counter=0;
	 DataHolder data=DataHolder.getInstance();

	@Override
	public void handleMessage(ACLMessage message) {
		// TODO Auto-generated method stub
		sendMessage("Message to Pong: " + message.content+" counter: "+counter);
		System.out.println("Message to Pong: " + message.content+" counter: "+counter);
		ACLMessage reply = message.makeReply(Performative.INFORM);
		reply.userArgs.put("pongCounter", ++counter);
		
		post(reply);
		
	}

}
