package data;

public class AID {
	private String Name;
	private AgentCenter Host;
	private AgentType Type;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public AgentCenter getHost() {
		return Host;
	}
	public void setHost(AgentCenter host) {
		Host = host;
	}
	public AID(String name, AgentCenter host, AgentType type) {
		super();
		Name = name;
		Host = host;
		Type = type;
	}
	public AgentType getType() {
		return Type;
	}
	public void setType(AgentType type) {
		Type = type;
	}
}
