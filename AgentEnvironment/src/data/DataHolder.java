package data;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Singleton;

import agents.Ping;
public class DataHolder {

	public ArrayList<AgentType> classes=new ArrayList<AgentType>();
	public HashMap<AID,Agent> running=new HashMap<AID,Agent>();
	private static DataHolder instance;
	public static DataHolder getInstance(){

		if(instance==null)
		instance=new DataHolder();
		return instance;
	}
	protected DataHolder(){
		classes.add(new AgentType("ping", "ping"));
		classes.add(new AgentType("pong", "pong"));
		System.out.println("kreirao dataholdera");
		instance=this;
	}
}
