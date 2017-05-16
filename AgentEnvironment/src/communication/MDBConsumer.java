package communication;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import data.ACLMessage;
import data.AID;
import data.Agent;
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
				for(AID a:message.receivers){
					Agent agent=data.running.get(a);
					if(agent!=null)agent.handleMessage(message);
				}
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
