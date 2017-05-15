package data;

public class SocketMessage {
	private String type;
	private ACLMessage obj;
	private String name;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ACLMessage getObj() {
		return obj;
	}
	public void setObj(ACLMessage obj) {
		this.obj = obj;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSelectedClass() {
		return selectedClass;
	}
	public void setSelectedClass(String selectedClass) {
		this.selectedClass = selectedClass;
	}
	private String selectedClass;
	public SocketMessage() {
		super();
	}
}
