package agents;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;

import data.ACLMessage;
import data.AID;
import data.Agent;
import data.AgentCenter;
import data.AgentType;
import data.DataHolder;
import data.Performative;

public class Ping extends Agent {
	DataHolder data=DataHolder.getInstance();

	@Override
	public void handleMessage(ACLMessage message) {
		// TODO Auto-generated method stub
		System.out.println("Message to Ping: " + message.content);
		if (message.performative == Performative.REQUEST) { // inital request
			// send a request to the Pong agent
			AgentType agClass = new AgentType("pong", "pong");
			AgentCenter agCenter= data.agentCenter;
			AID pongAid = new AID(message.content,agCenter, agClass);
			ACLMessage msgToPong = new ACLMessage();
			msgToPong.performative=Performative.REQUEST;
			msgToPong.sender = getId();
			msgToPong.receivers.add(pongAid);
			post(msgToPong);
		} else if (message.performative == Performative.INFORM) {
			// wait for the message
			// ACLMessage msgFromPong = receiveWait(0);
			ACLMessage msgFromPong = message;
			HashMap<String, Object> args = new HashMap<>(msgFromPong.userArgs);
			//args.put("pingCreatedOn", nodeName);
			//args.put("pingWorkingOn", getNodeName());

			// print info
			//LOG.info("Ping-Pong interaction details:");
			System.out.println("Ping-Pong interaction details: ");
			
			// reply to the original sender (if any)
			if (message.sender!=null || message.inReplyTo!=null) {
				ACLMessage reply = message.makeReply(Performative.INFORM);
				reply.userArgs = args;
				
				post(reply);
			}
		}
	}

}
