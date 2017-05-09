package data;

import java.util.ArrayList;

import javax.ejb.Singleton;

@Singleton
public class DataHolder {

	public ArrayList<AgentType> classes=new ArrayList<AgentType>();
	public ArrayList<Agent> running=new ArrayList<Agent>();
}
