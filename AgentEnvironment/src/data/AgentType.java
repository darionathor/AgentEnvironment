package data;

public class AgentType {
	private String Name;
	private String Module;
	public AgentType() {
		super();
	}
	public AgentType(String name, String module) {
		super();
		Name = name;
		Module = module;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getModule() {
		return Module;
	}
	public void setModule(String module) {
		Module = module;
	}
}
