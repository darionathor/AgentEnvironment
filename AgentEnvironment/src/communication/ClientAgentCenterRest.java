package communication;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.websocket.Session;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import agents.Initiator;
import agents.Participant;
import agents.Ping;
import agents.Pong;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import data.ACLMessage;
import data.AID;
import data.Agent;
import data.AgentCenter;
import data.AgentType;
import data.DataHolder;
import data.SocketOutMessage;
@Path("/")
@Stateless
public class ClientAgentCenterRest {
	   DataHolder data= DataHolder.getInstance();
	   public void broadcast(){
			System.out.println("session open");
	    	DataHolder data=DataHolder.getInstance();
	    	ObjectMapper om= new ObjectMapper();
	    	String values;
	    	for(Session session:data.sessions)
			try {
				SocketOutMessage mess= new SocketOutMessage();
				mess.classes=getClasses();
				mess.running=getRunning();
				mess.performative=getMessages();
				values = om.writeValueAsString(mess);
		    	session.getBasicRemote().sendText(values);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		for(AID a:data.running.keySet()){
			aids.add(a);
			System.out.println(a.getName());
		}
		return aids;
	}
	@PUT
	@Path("/agents/running/{type}/{name}")
	public void putRunning(@PathParam("type") String type, @PathParam("name") String name){
		System.out.println(type+" agent postavljen "+name);
		if(type.equals("ping")){
			Ping ping=new Ping();
			ping.setId(new AID(name, data.agentCenter, new AgentType("ping", "ping")));
			data.running.put(ping.getId(),ping);
			System.out.println("PING DODAT");
		}else if(type.equals("pong")){

			Pong pong=new Pong();
			pong.setId(new AID(name, data.agentCenter, new AgentType("pong", "pong")));
			data.running.put(pong.getId(),pong);
		}else if(type.equals("initiator")){

			Initiator pong=new Initiator();
			pong.setId(new AID(name, data.agentCenter, new AgentType("initiator", "initiator")));
			data.running.put(pong.getId(),pong);
		}else if(type.equals("participant")){

			Participant pong=new Participant();
			pong.setId(new AID(name, data.agentCenter, new AgentType("participant", "participant")));
			data.running.put(pong.getId(),pong);
		}
		

		broadcast();
		for(AgentCenter ac: data.centers)
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
		  }
		
	}
	@DELETE
	@Path("/agents/running/{aid}")
	public void deleteRunning(@PathParam("aid") String aid){
		System.out.println("agent zaustavljan");
		for(AID a:data.running.keySet()){
			if(a.getName().equals(aid)){
				data.running.remove(a);
				broadcast();
				for(AgentCenter ac: data.centers)
					try {

						DataHolder dh=DataHolder.getInstance();
						ClientRequest request = new ClientRequest(
							"http://"+ac.getAddress()+"/AgentEnvironment/rest/agents/running/"+aid);
						request.accept("application/json");

						ClientResponse<String> response = request.delete();

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
					  }
				break;
			}
		}
	}	
	
	@POST
	@Path("/messages")
	@Consumes(MediaType.APPLICATION_JSON)
	public void postMessages(ACLMessage message){
		System.out.println("message postavljen "+message.content);
		
		Context context;
		try {
			context = new InitialContext();
		
		ConnectionFactory cf = (ConnectionFactory)
				context.lookup("RemoteConnectionFactory");
		final Queue queue = (Queue) context
				.lookup("queue/mojQueue");

		context.close();
		Connection connection =
		cf.createConnection("guest", "");
		final javax.jms.Session session1 =
		connection.createSession(false,
		javax.jms.Session.AUTO_ACKNOWLEDGE);
		connection.start();
		/*MessageConsumer consumer =
				session1.createConsumer(queue);
				consumer.setMessageListener(this);*/
				ObjectMessage msg = session1.createObjectMessage(message);

				long sent = System.currentTimeMillis();

				msg.setLongProperty("sent", sent);

				MessageProducer producer =
				session1.createProducer(queue);
				producer.send(msg);
				producer.close();
				connection.stop();
				connection.close();
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		/*
		for(Agent a:data.running){
					a.handleMessage(message);
				}*/
			
		
		
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
