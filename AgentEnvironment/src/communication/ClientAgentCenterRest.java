package communication;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import data.ACLMessage;
@Path("/")
public class ClientAgentCenterRest {

	@GET
	@Path("<{host address}>:<{port}>/agents/classes")
	public void getClasses(){
		
	}
	@GET
	@Path("<{host address}>:<{port}>/agents/running")
	public void getRunning(){
	}
	@PUT
	@Path("<{host address}>:<{port}>/agents/running/{type}/{name}")
	public void putRunning(@PathParam("type") String type, @PathParam("name") String name){
	}@DELETE
	@Path("<{host address}>:<{port}>/agents/running/{aid}")
	public void deleteRunning(@PathParam("aid") String aid){
	}	
	
	@POST
	@Path("<{host address}>:<{port}>//messages")
	@Consumes(MediaType.APPLICATION_JSON)
	public void postMessages(ACLMessage message){
		
	}
		
	@GET
	@Path("<{host address}>:<{port}>/agents/messages")
	public void getMessages(){
		
	}
}
