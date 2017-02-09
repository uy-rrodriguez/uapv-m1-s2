package q2;

import javax.jms.JMSException;
import javax.jms.QueueSender;
import javax.jms.TextMessage;
import javax.naming.NamingException;

public class Emetteur {
	public static void main(String[] args) {
		try {
			if (args.length > 0) {
				ConnectionJMS jms = new ConnectionJMS();
				jms.connecter();
				
				System.out.println("Connection créée");
				
				QueueSender sender = jms.getSession().createSender(jms.getQueue());
				TextMessage msg = jms.getSession().createTextMessage();
				
				for (int i = 0; i < 5; i++) {
					msg.setText(i + " : " + args[0]);
					sender.send(msg);
					System.out.println("Message " + i + " envoyé !");
				}
				
				System.exit(0);
			}
			else {
				System.out.println("Pas d'argument fourni");
				System.exit(-2);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
