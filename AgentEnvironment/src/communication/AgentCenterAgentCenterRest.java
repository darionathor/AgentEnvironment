package communication;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("/")
public class AgentCenterAgentCenterRest {
	@POST
	@Path("/node")
	public void postNode(){
		
	}
	@POST
	@Path("agents/classes")
	@Consumes(MediaType.APPLICATION_JSON)
	public void postClasses(){
		
	}@POST
	@Path("agents/running")
	@Consumes(MediaType.APPLICATION_JSON)
	public void postRunning(){
		
	}@DELETE
	@Path("node/{alias}")
	public void deleteNode(@PathParam("alias") String alias){
		
	}
	@GET
	@Path("node/")
	public void getNode(){
		
	}
}
