package communication;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import data.DataHolder;
import data.SocketMessage;
import data.SocketOutMessage;
@ServerEndpoint(value="/socket")
public class ClientAgentCenterSocket {
	@Inject ClientAgentCenterRest rest;
	
	
	
	@OnOpen
    public void onOpen(Session session) {
    	DataHolder data=DataHolder.getInstance();
    	data.sessions.add(session);
    	System.out.println("session open");
    	ObjectMapper om= new ObjectMapper();
    	String values;
		try {
			SocketOutMessage mess= new SocketOutMessage();
			mess.classes=rest.getClasses();
			mess.running=rest.getRunning();
			mess.performative=rest.getMessages();
			values = om.writeValueAsString(mess);
	    	session.getBasicRemote().sendText(values);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @OnMessage
    public void onMessage(String message,Session session) {
    	System.out.println(message);
    	ObjectMapper om= new ObjectMapper();
    	try {
			SocketMessage sm=om.readValue(message, SocketMessage.class);
			System.out.println(sm.getType());
			if(sm.getType().equals("put")){
				rest.putRunning(sm.getSelectedClass(), sm.getName());
			}if(sm.getType().equals("del")){
				rest.deleteRunning(sm.getName());
			}if(sm.getType().equals("send")){
				rest.postMessages(sm.getObj());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @OnClose
    public void onClose(Session session){
    	DataHolder data=DataHolder.getInstance();
    	data.sessions.remove(session);
    }
}
