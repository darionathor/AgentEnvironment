package communication;

import java.util.ArrayList;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import data.ACLMessage;
import data.AID;
import data.Agent;
import data.AgentCenter;
import data.DataHolder;
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
			    @ActivationConfigProperty(
			    		propertyName  = "destination",
			    			propertyValue = "topic/mojQueue")
		})
public class MDBConsumer implements MessageListener {
	DataHolder data= DataHolder.getInstance();
	@Override
	public void onMessage(Message arg0) {
		// TODO Auto-generated method stub
			System.out.println(arg0);
			try {
				ACLMessage message=(ACLMessage) ((ObjectMessage)arg0).getObject();
				System.out.println(message.content);
				ArrayList<AgentCenter> centers= new ArrayList<AgentCenter>();
				for(AID a:message.receivers){
					if(a.getHost().equals(data.agentCenter)){
						Agent agent=data.running.get(a);
						if(agent!=null)agent.handleMessage(message);
					}else{
						if(!centers.contains(a.getHost()))centers.add(a.getHost());
					}
				}
				for(AgentCenter ac:centers)
				try {

					ClientRequest request = new ClientRequest(
						"http://"+ac.getAddress()+"/AgentEnvironment/rest/messages");
					request.accept("application/json");
					ObjectMapper om=new ObjectMapper();
					String input = om.writeValueAsString(message);
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
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
