package data;

import java.util.ArrayList;
import java.util.HashMap;

public class ACLMessage {
	public Performative performative;
	public AID sender;
	public ArrayList<AID> receivers;
	public AID replyTo;
	public String content;
	public Object contentObj;
	public HashMap<String,Object> userArgs;
	public String language;
	public String encoding;
	public String ontology;
	public String protocol;
	public String conversationId;
	public String replyWith;
	public String inReplyTo;
	public Long replyBy;
	public ACLMessage() {
		super();
		receivers=new ArrayList<AID>();
		userArgs=new HashMap<String, Object>();
	}public boolean canReplyTo() {
		return sender != null || replyTo != null;
	}

	public ACLMessage makeReply(Performative performative) {
		if (!canReplyTo())
			throw new IllegalArgumentException("There's no-one to receive the reply.");
		ACLMessage reply = new ACLMessage();
		reply.performative=performative;
		// receiver
		reply.receivers.add(replyTo != null ? replyTo : sender);
		// description of content
		reply.language = language;
		reply.ontology = ontology;
		reply.encoding = encoding;
		// control of conversation
		reply.protocol = protocol;
		reply.conversationId = conversationId;
		reply.inReplyTo = replyWith;
		return reply;
	}
	
}
