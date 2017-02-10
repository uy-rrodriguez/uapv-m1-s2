package q15;

import javax.jms.JMSException;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConnectionJMS {
	private Topic topic;
	private TopicConnection connection;
	private TopicSession session;
	
	public void creerConnexion(String nom) throws NamingException, JMSException {
		InitialContext ic = new InitialContext();
		TopicConnectionFactory factory = (TopicConnectionFactory) ic.lookup("jms/tpJMSTopicConnFactory");
		
		topic = (Topic) ic.lookup("jms/tpJMSTopicDestination");
		connection = factory.createTopicConnection();
		
		
		if (nom != null && ! nom.isEmpty())
			connection.setClientID(nom);
		
		
		session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
	}
	
	/*
	public void creerSession() throws JMSException {
		session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
	}
	*/
	
	public void activerConnexion() throws JMSException {
		connection.start();
	}
	
	public void deconnecter() throws JMSException {
		if (connection != null) {
			connection.close();
		}
	}

	public Topic getTopic() {
		return topic;
	}

	public TopicConnection getConnection() {
		return connection;
	}

	public TopicSession getSession() {
		return session;
	}
}
