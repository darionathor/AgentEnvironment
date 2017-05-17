package data;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Singleton;

import agents.Ping;
public class DataHolder {
	public boolean MainServer=true;
	public ArrayList<AgentType> classes=new ArrayList<AgentType>();
	public HashMap<AID,Agent> running=new HashMap<AID,Agent>();
	public AgentCenter agentCenter=null;
	public ArrayList<AgentCenter> centers=new ArrayList<AgentCenter>();
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
	public void setSecondaryServer() {
		// TODO Auto-generated method stub
		MainServer=false;
		AgentCenter main=new AgentCenter();
		main.setAddress("localhost:8080");
		main.setAlias("localhost:8080");
		centers.add(main);
	}
}
