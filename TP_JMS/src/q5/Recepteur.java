package q5;

import java.util.Date;

import javax.jms.QueueReceiver;
import javax.jms.TextMessage;

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
			jms.connecterRecepteur(); // On fait createQueueSession(false, Session.CLIENT_ACKNOWLEDGE)
			
			System.out.println(nom + " : Connection cr��e");
			
			// Cr�ation de r�cepteur  avec ou sans filtre
			QueueReceiver receiver = null;
			if (nom.isEmpty()) {
				receiver = jms.getSession().createReceiver(jms.getQueue());
				System.out.println(nom + " : Receiver cr�� sans filtre");
			}
			else {
				receiver = jms.getSession().createReceiver(jms.getQueue(), filtre);
				System.out.println(nom + " : Receiver cr�� avec filtre : " + filtre);
			}
			
			// Boucle de r�ception de messages
			do {
				System.out.println(nom + " : Attente de message");
				TextMessage msg = (TextMessage) receiver.receive();
				System.out.println(nom + " : " + new Date() + " : " + msg.getText());
				
				// On envoie l'ACK quelques secondes apr�s
				System.out.println(nom + " : Attente 10 sec pour envoyer ACK");
				Thread.sleep(10000);
				msg.acknowledge();
				System.out.println(nom + " : ACK envoy�");
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
