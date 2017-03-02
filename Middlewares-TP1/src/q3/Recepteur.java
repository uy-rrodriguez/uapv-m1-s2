package q3;

import javax.jms.JMSException;
import javax.jms.QueueReceiver;
import javax.jms.TextMessage;
import javax.naming.NamingException;

public class Recepteur {

	private long millis = 0;
	private String nom = "";
	
	public Recepteur(long millis, String nom) {
		super();
		this.millis = millis;
		this.nom = nom;
	}

	private void run() throws NamingException, JMSException, InterruptedException {
		ConnectionJMS jms = new ConnectionJMS();
		jms.connecter();
		
		System.out.println("Connection créée");
		
		QueueReceiver receiver = jms.getSession().createReceiver(jms.getQueue());
		
		do {
			TextMessage msg = (TextMessage) receiver.receive();
			System.out.println(nom + " : " + msg.getText());
			Thread.sleep(millis);
		}
		while(true);
	}
	
	public static void main(String[] args) {
		try {
			Recepteur r = new Recepteur(Integer.parseInt(args[0]), args[1]);
			r.run();
			System.exit(0);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
