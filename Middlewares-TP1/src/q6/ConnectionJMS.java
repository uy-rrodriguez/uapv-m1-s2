package q6;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConnectionJMS {
	private Queue queue;
	private QueueConnection connection;
	private QueueSession session;
	
	public void connecterEmetteur() throws NamingException, JMSException {
		InitialContext ic = new InitialContext();
		QueueConnectionFactory factory = (QueueConnectionFactory) ic.lookup("jms/tpJMSConnFactory");
		
		queue = (Queue) ic.lookup("jms/tpJMSDestination");
		connection = factory.createQueueConnection();
		session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		connection.start();
	}
	
	public void connecterRecepteur() throws NamingException, JMSException {
		InitialContext ic = new InitialContext();
		QueueConnectionFactory factory = (QueueConnectionFactory) ic.lookup("jms/tpJMSConnFactory");
		
		queue = (Queue) ic.lookup("jms/tpJMSDestination");
		connection = factory.createQueueConnection();
		session = connection.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
		connection.start();
	}
	
	public void deconnecter() throws JMSException {
		if (connection != null) {
			connection.close();
		}
	}

	public Queue getQueue() {
		return queue;
	}

	public QueueConnection getConnection() {
		return connection;
	}

	public QueueSession getSession() {
		return session;
	}
}
