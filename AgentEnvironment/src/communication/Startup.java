package communication;

import javax.ejb.Singleton;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import data.AgentCenter;
import data.DataHolder;

public class Startup implements ServletContextListener{
	
	
	@Override
	   public void contextInitialized(ServletContextEvent contextEvent) {
	      
		System.out.println("server started");
		
	   }

	   @Override
	   public void contextDestroyed(ServletContextEvent contextEvent) {
		   DataHolder data= DataHolder.getInstance();
		   AgentCenter ac= data.agentCenter;
				// TODO Auto-generated method stub
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
}
