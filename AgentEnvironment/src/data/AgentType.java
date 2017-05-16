package data;

import java.io.Serializable;

public class AgentType  implements Serializable{
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
	@Override
	public boolean equals(Object object){
		if (object == this) return true;
        if (!(object instanceof AgentType)) {
            return false;
        }
        AgentType obj=(AgentType) object;
		boolean NameValue=false;
		boolean ModuleValue=false;
		if(Name!=null && Name.equals(obj.getName()))NameValue=true;
		if(Module!=null && Module.equals(obj.getModule()))ModuleValue=true;
		if(Name==null &&obj.getName()==null)NameValue=true;
		if(Module==null && obj.getModule()==null)ModuleValue=true;
		
		return NameValue && ModuleValue;
	}@Override
	public int hashCode(){
		  int result = 17;
		  if(Name!=null)
	        result = 31 * result + Name.hashCode();
		  if(Module!=null)
	        result = 31 * result + Module.hashCode();
	        return result;
	}
}
