package communication;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import agents.Ping;
import agents.Pong;
import data.ACLMessage;
import data.AID;
import data.Agent;
import data.AgentCenter;
import data.AgentType;
import data.DataHolder;
@Path("/")
public class ClientAgentCenterRest {
	
	   DataHolder data= DataHolder.getInstance();
	
	@GET
	@Path("/test")
	public String getTest(){
		return "test";
	}
	@GET
	@Path("/agents/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<AgentType> getClasses(){
		return data.classes;
	}
	@GET
	@Path("/agents/running")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<AID> getRunning(){
		
		ArrayList<AID> aids= new ArrayList<AID>();
		for(Agent a:data.running){
			aids.add(a.getId());
			System.out.println(a.getId().getName());
		}
		return aids;
	}
	@PUT
	@Path("/agents/running/{type}/{name}")
	public void putRunning(@PathParam("type") String type, @PathParam("name") String name){
		System.out.println(type+" agent postavljen "+name);
		if(type.equals("ping")){
			Ping ping=new Ping();
			ping.setId(new AID(name, new AgentCenter(), new AgentType("ping", "ping")));
			data.running.add(ping);
			System.out.println("PING DODAT");
		}else if(type.equals("pong")){

			Pong pong=new Pong();
			pong.setId(new AID(name, new AgentCenter(), new AgentType("pong", "pong")));
			data.running.add(pong);
		}
		
		
	}
	@DELETE
	@Path("/agents/running/{aid}")
	public void deleteRunning(@PathParam("aid") String aid){
		System.out.println("agent zaustavljan");
		for(Agent a:data.running){
			if(a.getId().getName().equals(aid)){
				data.running.remove(a);
				break;
			}
		}
	}	
	
	@POST
	@Path("/messages")
	@Consumes(MediaType.APPLICATION_JSON)
	public void postMessages(ACLMessage message){
		System.out.println("message postavljen "+message.content);
		for(Agent a:data.running){
					a.handleMessage(message);
				}
			
		
		
	}
		
	@GET
	@Path("/messages")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getMessages(){
		ArrayList<String> performatives=new ArrayList<String>();
		performatives.add("ACCEPT_PROPOSAL");
		performatives.add("AGREE");
		performatives.add("CANCEL"); 
		performatives.add("CALL_FOR_PROPOSAL"); 
		performatives.add("CONFIRM");
		performatives.add("DISCONFIRM");
		performatives.add("FAILURE");
		performatives.add("INFORM");
		performatives.add("INFORM_IF");
		performatives.add("INFORM_REF"); 
		performatives.add("NOT_UNDERSTOOD"); 
		performatives.add("PROPAGATE"); 
		performatives.add("PROPOSE"); 
		performatives.add("PROXY");
		performatives.add("QUERY_IF");
		performatives.add("QUERY_REF");
		performatives.add("REFUSE");
		performatives.add("REJECT_PROPOSAL");
		performatives.add("REQUEST");
		performatives.add("REQUEST_WHEN");
		performatives.add("REQUEST_WHENEVER"); 
		performatives.add("SUBSCRIBE");
		return performatives;
	}
}
