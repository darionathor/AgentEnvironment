package communication;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import data.ACLMessage;
import data.AID;
import data.Agent;
import data.AgentType;
import data.DataHolder;
@Path("/")
public class ClientAgentCenterRest {
	
	@EJB DataHolder data;
	
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
		}
		return aids;
	}
	@PUT
	@Path("/agents/running/{type}/{name}")
	public void putRunning(@PathParam("type") String type, @PathParam("name") String name){
		System.out.println(type+" agent postavljen "+name);
	}@DELETE
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
		System.out.println("message postavljen");
		for(Agent a:data.running){
			for(AID aid:message.getRecievers()){
				if(a.getId().equals(aid)){
					a.handleMessage(message);
				}
			}
		}
		
	}
		
	@GET
	@Path("/messages")
	public void getMessages(){
		
	}
}
