package data;

import java.io.IOException;

import javax.ejb.Stateful;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.websocket.Session;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Stateful
public abstract class Agent {
	private AID id;
	public abstract void handleMessage(ACLMessage message);
	public AID getId() {
		return id;
	}
	public void setId(AID id) {
		this.id = id;
	}
	protected void sendMessage(String message){
		System.out.println("sending message");
    	DataHolder data=DataHolder.getInstance();
    	for(Session session:data.sessions)
		try {
	    	session.getBasicRemote().sendText(message);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	for(AgentCenter ac:data.centers)
			try {

				ClientRequest request = new ClientRequest(
					"http://"+ac.getAddress()+"/AgentEnvironment/rest/agentMessage");
				request.accept("text/plain");
				request.body("text/plain", message);

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
	protected void post(ACLMessage message){
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
	}
}
