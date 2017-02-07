package q1;

import javax.jms.JMSException;
import javax.jms.QueueReceiver;
import javax.jms.TextMessage;
import javax.naming.NamingException;

public class Recepteur {

	private void run() throws NamingException, JMSException {
		ConnectionJMS jms = new ConnectionJMS();
		jms.connecter();
		
		QueueReceiver receiver = jms.getSession().createReceiver(jms.getQueue());
		TextMessage msg = (TextMessage) receiver.receive();
		System.out.println(msg.getText());
	}
	
	public static void main(String[] args) {
		try {
			Recepteur r = new Recepteur();
			r.run();
			System.exit(0);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
