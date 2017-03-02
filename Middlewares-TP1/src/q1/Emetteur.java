package q1;

import javax.jms.JMSException;
import javax.jms.QueueSender;
import javax.jms.TextMessage;
import javax.naming.NamingException;

public class Emetteur {
	private void run() throws NamingException, JMSException {
		ConnectionJMS jms = new ConnectionJMS();
		jms.connecter();
		
		System.out.println("Connection créée");
		
		QueueSender sender = jms.getSession().createSender(jms.getQueue());
		TextMessage msg = jms.getSession().createTextMessage();
		msg.setText("Hola Mundo!");
		sender.send(msg);
		System.out.println("Message envoyé !");
	}
	
	public static void main(String[] args) {
		try {
			Emetteur e = new Emetteur();
			e.run();
			System.exit(0);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
