package q6;

import java.util.Date;

import javax.jms.Destination;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
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
		ConnectionJMS jmsACK = new ConnectionJMS();
		try {
			// On fait createQueueSession(false, Session.CLIENT_ACKNOWLEDGE)
			jms.connecterRecepteur();
			
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
			
			// Cr�ation d'un sender pour les ACK vers l'�metteur d'un message recu
			jmsACK.connecterEmetteur();
			QueueSender senderACK = jmsACK.getSession().createSender(null);
			
			
			// Boucle de r�ception de messages
			do {
				// R�ception de message
				System.out.println(nom + " : Attente de message");
				
				TextMessage msg = (TextMessage) receiver.receive();
				System.out.println(nom + " : " + new Date() + " : " + msg.getText());
				
				
				// Envoi de r�ponse � l'�metteur
				TextMessage msgACK = jmsACK.getSession().createTextMessage();
				msgACK.setText(nom + " : Message " + msg.getJMSCorrelationID() + " recu");
				senderACK.send(msg.getJMSReplyTo(), msgACK);
				
				System.out.println(nom + " : ACK envoy� � l'�metteur");
				
				
				// On envoie l'ACK au MOM
				msg.acknowledge();
				System.out.println(nom + " : ACK envoy� au MOM");
				System.out.println();
				
				
				// Petite attente pour test
				Thread.sleep(200);
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
