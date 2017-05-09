package data;

import java.util.HashMap;

public class ACLMessage {
	private Enum Performative;
	private AID Sender;
	private AID[] Recievers;
	private AID ReplyTo;
	private String Content;
	private Object ContentObj;
	private HashMap<String,Object> UserArgs;
	private String Language;
	private String Encoding;
	private String Ontology;
	private String Protocol;
	private String ConversationId;
	private String ReplyWith;
	private String inReplyTo;
	private Long ReplyBy;
	public ACLMessage() {
		super();
	}
	public Enum getPerformative() {
		return Performative;
	}
	public void setPerformative(Enum performative) {
		Performative = performative;
	}
	public AID getSender() {
		return Sender;
	}
	public void setSender(AID sender) {
		Sender = sender;
	}
	public AID[] getRecievers() {
		return Recievers;
	}
	public void setRecievers(AID[] recievers) {
		Recievers = recievers;
	}
	public AID getReplyTo() {
		return ReplyTo;
	}
	public void setReplyTo(AID replyTo) {
		ReplyTo = replyTo;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public Object getContentObj() {
		return ContentObj;
	}
	public void setContentObj(Object contentObj) {
		ContentObj = contentObj;
	}
	public HashMap<String, Object> getUserArgs() {
		return UserArgs;
	}
	public void setUserArgs(HashMap<String, Object> userArgs) {
		UserArgs = userArgs;
	}
	public String getLanguage() {
		return Language;
	}
	public void setLanguage(String language) {
		Language = language;
	}
	public String getEncoding() {
		return Encoding;
	}
	public void setEncoding(String encoding) {
		Encoding = encoding;
	}
	public String getOntology() {
		return Ontology;
	}
	public void setOntology(String ontology) {
		Ontology = ontology;
	}
	public String getProtocol() {
		return Protocol;
	}
	public void setProtocol(String protocol) {
		Protocol = protocol;
	}
	public String getConversationId() {
		return ConversationId;
	}
	public void setConversationId(String conversationId) {
		ConversationId = conversationId;
	}
	public String getReplyWith() {
		return ReplyWith;
	}
	public void setReplyWith(String replyWith) {
		ReplyWith = replyWith;
	}
	public String getInReplyTo() {
		return inReplyTo;
	}
	public void setInReplyTo(String inReplyTo) {
		this.inReplyTo = inReplyTo;
	}
	public Long getReplyBy() {
		return ReplyBy;
	}
	public void setReplyBy(Long replyBy) {
		ReplyBy = replyBy;
	}
	
}
