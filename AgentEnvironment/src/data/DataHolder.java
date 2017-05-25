package data;

import java.util.ArrayList;
import java.util.HashMap;

import javax.websocket.Session;
public class DataHolder {
	public boolean MainServer=true;
	public ArrayList<AgentType> classes=new ArrayList<AgentType>();
	public HashMap<AID,Agent> running=new HashMap<AID,Agent>();
	public HashMap<AgentCenter,ArrayList<AgentType>> supports=new HashMap<AgentCenter, ArrayList<AgentType>>();
	public AgentCenter agentCenter=null;
	public ArrayList<AgentCenter> centers=new ArrayList<AgentCenter>();
	public ArrayList<Session> sessions=new ArrayList<Session>();
	private static DataHolder instance;
	public static DataHolder getInstance(){

		if(instance==null)
		instance=new DataHolder();
		return instance;
	}
	protected DataHolder(){
		classes.add(new AgentType("ping", "ping"));
		classes.add(new AgentType("pong", "pong"));
		classes.add(new AgentType("initiator", "initiator"));
		classes.add(new AgentType("participant", "participant"));
		System.out.println("kreirao dataholdera");
		instance=this;
	}
	public void setSecondaryServer(String address) {
		// TODO Auto-generated method stub
		MainServer=false;
		AgentCenter main=new AgentCenter();
		main.setAddress(address);
		main.setAlias(address);
		centers.add(main);
	}
}
