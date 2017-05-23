package communication;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class Startup implements ServletContextListener{
	
	@Override
	   public void contextInitialized(ServletContextEvent contextEvent) {
	        /* Do Startup stuff. */
		System.out.println("server started");
		
	   }

	   @Override
	   public void contextDestroyed(ServletContextEvent contextEvent) {
	        /* Do Shutdown stuff. */
		   
	   }
}
