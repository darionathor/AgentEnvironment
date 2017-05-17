package communication;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import data.AgentCenter;
import data.DataHolder;

@Stateless
public class StartupTimer {
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
