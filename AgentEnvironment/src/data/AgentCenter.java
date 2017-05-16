package data;

import java.io.Serializable;

public class AgentCenter  implements Serializable{
	private String Alias;
	private String Address;
	public String getAlias() {
		return Alias;
	}
	public void setAlias(String alias) {
		Alias = alias;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public AgentCenter() {
		super();
	}
	@Override
	public boolean equals(Object object){
		 if (object == this) return true;
	        if (!(object instanceof AgentCenter)) {
	            return false;
	        }
	        AgentCenter obj=(AgentCenter) object;
			boolean AliasValue=false;
			boolean AddressValue=false;
			if(Alias!=null && Alias.equals(obj.getAlias()))AliasValue=true;
			if(Address!=null && Address.equals(obj.getAddress()))AddressValue=true;
			if(Alias==null &&obj.getAlias()==null)AliasValue=true;
			if(Address==null && obj.getAddress()==null)AddressValue=true;
			return AliasValue && AddressValue;
		
	}@Override
	public int hashCode(){
		  int result = 17;
		  if(Address!=null)
	        result = 31 * result + Address.hashCode();
		  if(Alias!=null)
	        result = 31 * result + Alias.hashCode();
	        return result;
	}
}
