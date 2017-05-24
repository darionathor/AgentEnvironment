package communication;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import data.AID;
import data.AgentCenter;
import data.AgentType;
import data.DataHolder;

@Path("/")
public class AgentCenterAgentCenterRest {
	DataHolder data=DataHolder.getInstance();
	@Inject ClientAgentCenterRest rest;
	
	@POST
	@Path("/nodes")
	@Consumes(MediaType.APPLICATION_JSON)
	public void postNodes(ArrayList<AgentCenter> ac){
		for(AgentCenter cent:ac)
			if(!data.centers.contains(cent))data.centers.add(cent);
	}
	@POST
	@Path("/node")
	@Consumes(MediaType.APPLICATION_JSON)
	public void postNode(AgentCenter ac){
		System.out.println("post Node");
		if(!data.centers.contains(ac)){
			System.out.println(ac.getAddress());
			data.centers.add(ac);
			
			if(data.MainServer){
				//get agents/classes
				ArrayList<AgentType> newClustersTypes;
				try {
					newClustersTypes = getAgentClasses(ac);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						newClustersTypes = getAgentClasses(ac);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						removeNode(ac);
						return;
					}
				}
				data.supports.put(ac, newClustersTypes);
				ArrayList<AgentType> actuallyNew=new ArrayList<AgentType>();
				for(AgentType newType:newClustersTypes){
					if(!data.classes.contains(newType))actuallyNew.add(newType);
						
				}
				postClasses(newClustersTypes);
				//post node ostalim serverima
				try {
					postNodeOthers(ac);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						postNodeOthers(ac);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						removeNode(ac);
						return;
					}
				}
				//post agents classes ako ih ima
				if(actuallyNew.size()>0)
					try {
						postClassesOthers(actuallyNew,ac);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						try {
							postClassesOthers(actuallyNew,ac);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							removeNode(ac);
							return;
						}
					}
				//post node novom cvoru
				if(data.centers.size()>1)
					try {
						postNodesToNew(ac);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						try {
							postNodesToNew(ac);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							removeNode(ac);
							return;
						}
					}
				//post agents classes novom cvoru
				try {
					postClassesToNew(ac);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						postClassesToNew(ac);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						removeNode(ac);
						return;
					}
				}
				//post agents running
				try {
					postRunningtoNew(ac);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						postRunningtoNew(ac);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						removeNode(ac);
						return;
					}
				}
			}
		}
	}
	public void removeNode(AgentCenter ac) {
		// TODO Auto-generated method stub
		deleteNode(ac.getAlias());
		for(AgentCenter center:data.centers)
			if(!center.equals(ac))
		try {

			DataHolder dh=DataHolder.getInstance();
			ClientRequest request = new ClientRequest(
				"http://"+center.getAddress()+"/AgentEnvironment/rest/node/"+ac.getAlias());
			ObjectMapper om=new ObjectMapper();

			ClientResponse<String> response = request.delete();

		  } catch (Exception e) {

			e.printStackTrace();
		  }
		
	}
	private void postRunningtoNew(AgentCenter ac) throws Exception{
		// TODO Auto-generated method stub
		try {

			DataHolder dh=DataHolder.getInstance();
			ClientRequest request = new ClientRequest(
				"http://"+ac.getAddress()+"/AgentEnvironment/rest/agents/running");
			request.accept("application/json");
			ObjectMapper om=new ObjectMapper();
			String input = om.writeValueAsString(data.running.keySet());
			request.body("application/json", input);

			ClientResponse<String> response = request.post(String.class);

			/*if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}*/

		  } catch (Exception e) {

			e.printStackTrace();
			throw e;
		  }
	}
	private void postClassesToNew(AgentCenter ac)  throws Exception{
		// TODO Auto-generated method stub
		try {

			DataHolder dh=DataHolder.getInstance();
			ClientRequest request = new ClientRequest(
				"http://"+ac.getAddress()+"/AgentEnvironment/rest/agents/classes");
			request.accept("application/json");
			ObjectMapper om=new ObjectMapper();
			String input = om.writeValueAsString(data.classes);
			request.body("application/json", input);

			ClientResponse<String> response = request.post(String.class);

			/*if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}*/

		  }catch (Exception e) {

			e.printStackTrace();
			throw e;
		  }
	}
	private void postNodesToNew(AgentCenter ac)  throws Exception{
		// TODO Auto-generated method stub
		try {

			DataHolder dh=DataHolder.getInstance();
			ClientRequest request = new ClientRequest(
				"http://"+ac.getAddress()+"/AgentEnvironment/rest/nodes");
			request.accept("application/json");
			ObjectMapper om=new ObjectMapper();
			String input = om.writeValueAsString(data.centers);
			request.body("application/json", input);

			ClientResponse<String> response = request.post(String.class);

			/*if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}*/

		  } catch (Exception e) {

			e.printStackTrace();
			throw e;
		  }
	}
	private void postClassesOthers(ArrayList<AgentType> actuallyNew, AgentCenter ac)  throws Exception{
		// TODO Auto-generated method stub
		for(AgentCenter center:data.centers)
			if(!center.equals(ac))
		try {

			DataHolder dh=DataHolder.getInstance();
			ClientRequest request = new ClientRequest(
				"http://"+center.getAddress()+"/AgentEnvironment/rest/agents/classes");
			request.accept("application/json");
			ObjectMapper om=new ObjectMapper();
			String input = om.writeValueAsString(actuallyNew);
			request.body("application/json", input);

			ClientResponse<String> response = request.post(String.class);

			/*if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}*/

		  } catch (Exception e) {

			e.printStackTrace();
			throw e;
		  }
	}
	private ArrayList<AgentType> getAgentClasses(AgentCenter ac)  throws Exception{
		// TODO Auto-generated method stub
		try {

			ClientRequest request = new ClientRequest(
					"http://"+ac.getAddress()+"/AgentEnvironment/rest/agents/classes");
			request.accept("application/json");
			ClientResponse<String> response = request.get(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			}
			ObjectMapper om= new ObjectMapper();
			ArrayList<AgentType> al=om.readValue(response.getEntity().getBytes(), new TypeReference<ArrayList<AgentType>>() {
			});
			return al;
		  } catch (Exception e) {

			e.printStackTrace();
			throw e;
		  }

		
		
	}
	private void postNodeOthers(AgentCenter ac)  throws Exception{
		// TODO Auto-generated method stub
		for(AgentCenter center:data.centers)
			if(!center.equals(ac))
		try {

			DataHolder dh=DataHolder.getInstance();
			ClientRequest request = new ClientRequest(
				"http://"+center.getAddress()+"/AgentEnvironment/rest/post/node");
			request.accept("application/json");
			ObjectMapper om=new ObjectMapper();
			String input = om.writeValueAsString(ac);
			request.body("application/json", input);

			ClientResponse<String> response = request.post(String.class);

			/*if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(response.getEntity().getBytes())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}*/

		  } catch (Exception e) {

			e.printStackTrace();
			throw e;
		  }
	}
	@POST
	@Path("agents/classes")
	@Consumes(MediaType.APPLICATION_JSON)
	public void postClasses(ArrayList<AgentType> types){
		System.out.println("postClases");
		for(AgentType tip:types){
			if(!data.classes.contains(tip)){
				data.classes.add(tip);
				rest.broadcast();
			}
		}
	}
	@POST
	@Path("agents/running")
	@Consumes(MediaType.APPLICATION_JSON)
	public void postRunning(ArrayList<AID> running){
		System.out.println("postRunning");
		for(AID tip:running){
			if(!data.running.containsKey(tip)){
				data.running.put(tip,null);
				rest.broadcast();
		}
		}
		
	}
	
	@DELETE
	@Path("node/{alias}")
	public void deleteNode(@PathParam("alias") String alias){
		System.out.println("deleteNode");
		for(AgentCenter ac:data.centers){
			if(ac.getAlias().equals(alias)){
				data.centers.remove(ac);
				
				for(AgentType at:data.supports.get(ac)){
					boolean delete=true;
					for(AgentCenter center: data.centers){
						for(AgentType tajp: data.supports.get(center)){
							if(tajp.equals(at)){
								delete=false;
								break;
							}
						}
						if(delete==false)
							break;
					}
					AgentCenter main=new AgentCenter();
					main.setAddress("127.0.0.1:8080");
					main.setAlias("127.0.0.1:8080");
					for(AgentType tajp: data.supports.get(main)){
						if(tajp.equals(at)){
							delete=false;
							break;
						}
					}
					if(delete==false)
						break;
					if(delete)
						data.classes.remove(at);
				};
				data.supports.remove(ac);
				for(AID a: data.running.keySet()){
					if(a.getHost().equals(ac))data.running.remove(a);
				}

				rest.broadcast();
				break;
			}
		}
	}
	@GET
	@Path("node/")
	@Produces(MediaType.APPLICATION_JSON)
	public AgentCenter getNode(){
		System.out.println("getNode");
		return data.agentCenter;
	}
}
