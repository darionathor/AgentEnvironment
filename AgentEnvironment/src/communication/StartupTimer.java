package communication;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.inject.Inject;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import data.AgentCenter;
import data.AgentType;
import data.DataHolder;

@Stateless
public class StartupTimer {
	@Inject AgentCenterAgentCenterRest rest;
	
	@Schedule(second="*/10",hour = "*", minute = "*", persistent = false)
    protected void init(Timer timer)
    {
		DataHolder dh=DataHolder.getInstance();
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		ObjectName socketBindingMBean;
		try {
			socketBindingMBean = new ObjectName("jboss.as:socket-binding-group=standard-sockets,socket-binding=http");

    		String  host = (String)  mBeanServer.getAttribute(socketBindingMBean, "boundAddress");
    		Integer port = (Integer) mBeanServer.getAttribute(socketBindingMBean, "boundPort");
    		System.out.println(host);
    		System.out.println(port);
			AgentCenter as=new AgentCenter();
			as.setAddress(host+":"+port);
			as.setAlias(host+":"+port);
			dh.agentCenter=as;

			ArrayList<AgentType> classes= new ArrayList<AgentType>();
			classes.add(new AgentType("ping", "ping"));
			classes.add(new AgentType("pong", "pong"));
			classes.add(new AgentType("initiator", "initiator"));
			classes.add(new AgentType("participant", "participant"));
			dh.supports.put(as, classes);
    		if(!port.equals(8080)){
    			System.out.println("secondary server");
    			dh.setSecondaryServer();
    			performCenterHandshake();
    		}
    	       timer.cancel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
	@Schedule(second="*/60",hour = "*", minute = "*", persistent = false)
    protected void heartbeat(Timer timer)
    {
		DataHolder dh=DataHolder.getInstance();
		for(AgentCenter ac:dh.centers)
			if(ac!=dh.agentCenter)
		try {

			ClientRequest request = new ClientRequest(
				"http://"+ac.getAddress()+"/AgentEnvironment/rest/node");
			request.accept("application/json");
			ObjectMapper om=new ObjectMapper();
			String input = om.writeValueAsString(dh.agentCenter);

			ClientResponse<String> response = request.get(String.class);

			om.readValue(response.getEntity().getBytes(),AgentCenter.class);

		  }  catch (Exception e) {

			e.printStackTrace();
			System.out.println("failed");
			//TODO onfail
			try {

				ClientRequest request = new ClientRequest(
					"http://"+ac.getAddress()+"/AgentEnvironment/rest/node");
				request.accept("application/json");
				ObjectMapper om=new ObjectMapper();
				String input = om.writeValueAsString(dh.agentCenter);

				ClientResponse<String> response = request.get(String.class);

				om.readValue(response.getEntity().getBytes(),AgentCenter.class);

			  }  catch (Exception e2) {

				e.printStackTrace();
				System.out.println("failed again");
				rest.removeNode(ac);
				//TODO onfail
			  }
		  }

    }
	@SuppressWarnings("deprecation")
	private void performCenterHandshake() {
		// TODO Auto-generated method stub
		//1-post/node na master
		 try {

				DataHolder dh=DataHolder.getInstance();
				ClientRequest request = new ClientRequest(
					"http://localhost:8080/AgentEnvironment/rest/node");
				request.accept("application/json");
				ObjectMapper om=new ObjectMapper();
				String input = om.writeValueAsString(dh.agentCenter);
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

			  } catch (MalformedURLException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			  } catch (Exception e) {

				e.printStackTrace();

			  }

			
	}
}
