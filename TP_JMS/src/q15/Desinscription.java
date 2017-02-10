package q15;

import javax.jms.Session;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.InitialContext;

public class Desinscription {
	public static void main(String[] args) {
		String nom = args[0];
		
		try {
			InitialContext ic = new InitialContext();
			TopicConnectionFactory factory = (TopicConnectionFactory) ic.lookup("jms/tpJMSTopicConnFactory");
			TopicConnection connection = factory.createTopicConnection();
			connection.setClientID(nom);
			
			TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			session.unsubscribe(nom);
			
			System.out.println("Recepteur desinscrit " + nom);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
