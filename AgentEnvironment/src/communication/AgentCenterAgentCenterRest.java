package communication;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import data.AID;
import data.AgentCenter;
import data.AgentType;
import data.DataHolder;

@Path("/")
public class AgentCenterAgentCenterRest {
	DataHolder data=DataHolder.getInstance();
	
	@POST
	@Path("/node")
	@Consumes(MediaType.APPLICATION_JSON)
	public void postNode(AgentCenter ac){
		System.out.println("post Node");
		if(!data.centers.contains(ac)){
			System.out.println(ac.getAddress());
			data.centers.add(ac);
			
			if(data.MainServer){
				//post node ostalim serverima
				//post agents classes ako ih ima
				//post node novom cvoru 
				//post agents classes novom cvoru
				//post agents running
			}
		}
	}
	@POST
	@Path("agents/classes")
	@Consumes(MediaType.APPLICATION_JSON)
	public void postClasses(ArrayList<AgentType> types){
		for(AgentType tip:types){
			if(!data.classes.contains(tip))
				data.classes.add(tip);
		}
	}
	@POST
	@Path("agents/running")
	@Consumes(MediaType.APPLICATION_JSON)
	public void postRunning(ArrayList<AID> running){
		for(AID tip:running){
			if(!data.running.containsKey(tip))
				data.running.put(tip,null);
		}
		
	}
	@DELETE
	@Path("node/{alias}")
	public void deleteNode(@PathParam("alias") String alias){
		for(AgentCenter ac:data.centers){
			if(ac.getAlias().equals(alias)){
				data.centers.remove(ac);
				break;
			}
		}
	}
	@GET
	@Path("node/")
	@Produces(MediaType.APPLICATION_JSON)
	public AgentCenter getNode(){
		return data.agentCenter;
	}
}
