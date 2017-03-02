package q4;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.QueueReceiver;
import javax.jms.TextMessage;
import javax.naming.NamingException;

public class Recepteur {

	private String nom = "";
	private String filtre = null;
	
	public Recepteur(String[] destinataires) {
		super();
		this.nom = String.join(",", destinataires);
		
		String[] filtres = new String[destinataires.length];
		for (int i = 0; i < destinataires.length; i++) {
			filtres[i] = "destinataire='" + destinataires[i] + "'";
		}
		this.filtre = String.join(" OR ", filtres);
	}

	private void run() throws Exception {
		ConnectionJMS jms = new ConnectionJMS();
		try {
			jms.connecter();
			
			System.out.println(nom + " : Connection créée");
			
			QueueReceiver receiver = null;
			if (nom.isEmpty()) {
				receiver = jms.getSession().createReceiver(jms.getQueue());
				System.out.println(nom + " : Receiver créé sans filtre");
			}
			else {
				receiver = jms.getSession().createReceiver(jms.getQueue(), filtre);
				System.out.println(nom + " : Receiver créé avec filtre : " + filtre);
			}
			
			do {
				System.out.println(nom + " : Attente de message");
				TextMessage msg = (TextMessage) receiver.receive();
				System.out.println(nom + " : " + new Date() + " : " + msg.getText());
			}
			while(true);
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			jms.deconnecter();
		}
	}
	
	public static void main(String[] args) {
		try {
			String[] noms = {""};
			if (args.length > 0) {
				noms = args[0].split(",");
			}
			
			Recepteur r = new Recepteur(noms);
			r.run();
			System.exit(0);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
