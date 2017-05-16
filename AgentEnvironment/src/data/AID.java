package data;

import java.io.Serializable;

public class AID implements Serializable{
	private String Name;
	private AgentCenter Host;
	private AgentType Type;
	public String getName() {
		return Name;
	}
	public AID() {
		super();
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
	@Override
	public boolean equals(Object object){
		 if (object == this) return true;
	        if (!(object instanceof AID)) {
	            return false;
	        }
			AID obj=(AID) object;
			boolean nameValue=false;
			boolean hostValue=false;
			boolean typeValue=false;
			if(Name!=null && Name.equals(obj.getName()))nameValue=true;
			if(Host!=null && Host.equals(obj.getHost()))hostValue=true;
			if(Type!=null && Type.equals(obj.getType()))typeValue=true;
			if(Name==null &&obj.getName()==null)nameValue=true;
			if(Host==null && obj.getHost()==null)hostValue=true;
			if(Type==null && obj.getType()==null)typeValue=true;
			
			return nameValue && hostValue && typeValue;
		
	}
	@Override
	public int hashCode(){
		  int result = 17;
		  if(Name!=null)
	        result = 31 * result + Name.hashCode();
		  if(Host!=null)
	        result = 31 * result + Host.hashCode();
		  if(Type!=null)
	        result = 31 * result + Type.hashCode();
	        return result;
	}
}
