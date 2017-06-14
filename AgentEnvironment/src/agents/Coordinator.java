package agents;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.ejb.Stateful;

import data.ACLMessage;
import data.AID;
import data.Agent;
import data.Performative;

@Stateful
public class Coordinator extends Agent {
	HashMap<String,Integer> hm= new HashMap<String, Integer>();
	ArrayList<AID> activeWorkers=new ArrayList<AID>();
	@Override
	public void handleMessage(ACLMessage message) {
		// TODO Auto-generated method stub
		if(message.performative.equals(Performative.REQUEST)){
			File f = new File(message.content);
			ArrayList<ACLMessage> me=new ArrayList<ACLMessage>();
			for(File fa:f.listFiles()){
				AID a=createAgent("worker",UUID.randomUUID().toString());
				activeWorkers.add(a);
				ACLMessage mess=new ACLMessage();
				mess.contentObj=fa;
				mess.sender=this.getId();
				ArrayList<AID> rec=new ArrayList<AID>();
				rec.add(a);
				mess.receivers=rec;
				mess.performative=Performative.REQUEST;
				me.add(mess);
			}
			for(ACLMessage poruka:me)
				post(poruka);
		}else if(message.performative.equals(Performative.INFORM)){
			HashMap<String,Integer> value= (HashMap<String, Integer>)message.contentObj;
			for(String str:value.keySet()){
				if(hm.containsKey(str)){
					hm.put(str, value.get(str)+hm.get(str));
				}else
					hm.put(str, value.get(str));
			}
			
			activeWorkers.remove(message.sender);
			if(message.sender.getType().getName().equals("worker"))
				deleteAgent(message.sender.getName());
			if(activeWorkers.size()==0){
				sendMessage("Obrada zavrsena");
				for(String str:hm.keySet()){
					sendMessage(str+": "+hm.get(str));
				}
			}
		}
	}

}
