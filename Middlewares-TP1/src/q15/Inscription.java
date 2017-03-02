package q15;

import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;

public class Inscription {
	public static void main(String[] args) {
		String nom = args[0];
		
		try {
			InitialContext ic = new InitialContext();
			TopicConnectionFactory factory = (TopicConnectionFactory) ic.lookup("jms/tpJMSTopicConnFactory");
			TopicConnection connection = factory.createTopicConnection();
			connection.setClientID(nom);
			
			System.out.println("Recepteur inscrit " + nom);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
