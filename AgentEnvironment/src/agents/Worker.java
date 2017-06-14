package agents;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Stateful;

import data.ACLMessage;
import data.AID;
import data.Agent;
import data.Performative;

@Stateful
public class Worker extends Agent {

	@Override
	public void handleMessage(ACLMessage message) {
		// TODO Auto-generated method stub
			if(message.performative==Performative.REQUEST){
				File fa=(File) message.contentObj;
				HashMap<String,Integer> hm=new HashMap<String, Integer>();
				BufferedReader br=null;
				FileReader fr = null;

				try {

					fr = new FileReader(fa);
					br = new BufferedReader(fr);

					String sCurrentLine;


					while ((sCurrentLine = br.readLine()) != null) {
						for(int i=0;i<sCurrentLine.length();i++){
							String s=String.valueOf(sCurrentLine.charAt(i));
							if(hm.containsKey(s))
								hm.put(s, hm.get(s)+1);
							else
								hm.put(s,1);
						}
					}

				} catch (IOException e) {

					e.printStackTrace();

				} finally {

					try {

						if (br != null)
							br.close();

						if (fr != null)
							fr.close();

					} catch (IOException ex) {

						ex.printStackTrace();

					}

				}
				
				ACLMessage mess=new ACLMessage();
				mess.performative=Performative.INFORM;
				mess.sender=getId();
				ArrayList<AID> rec=new ArrayList<AID>();
				rec.add(message.sender);
				mess.receivers=rec;
				mess.contentObj=hm;
				post(mess);
			}
	}

}
