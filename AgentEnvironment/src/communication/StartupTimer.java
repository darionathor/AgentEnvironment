package communication;

import java.lang.management.ManagementFactory;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.management.MBeanServer;
import javax.management.ObjectName;

@Stateless
public class StartupTimer {
	@Schedule(second="*/10",hour = "*", minute = "*", persistent = false)
    protected void init(Timer timer)
    {
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		ObjectName socketBindingMBean;
		try {
			socketBindingMBean = new ObjectName("jboss.as:socket-binding-group=standard-sockets,socket-binding=http");

    		String  host = (String)  mBeanServer.getAttribute(socketBindingMBean, "boundAddress");
    		Integer port = (Integer) mBeanServer.getAttribute(socketBindingMBean, "boundPort");
    		System.out.println(host);
    		System.out.println(port);
    		if(!port.equals(8080)){
    			System.out.println("secondary server");
    		}
    	       timer.cancel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}
