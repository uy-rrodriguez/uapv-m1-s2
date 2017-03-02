package q6;

import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;

public class Emetteur {
	private String message = "";
	private String destinataire = "";
	
	public Emetteur(String message, String destinataire) {
		super();
		this.message = message;
		this.destinataire = destinataire;
	}

	private void run() throws Exception {
		ConnectionJMS jms = new ConnectionJMS();
		try {
			jms.connecterEmetteur();
			
			System.out.println("Connection cr��e");
			
			// Cr�ation d'un �metteur
			QueueSender sender = jms.getSession().createSender(jms.getQueue());
			
			// Cr�ation d'un r�cepteur pour les ACK
			TemporaryQueue queueACK = jms.getSession().createTemporaryQueue();
			QueueReceiver receiverACK = jms.getSession().createReceiver(queueACK);
			
			for (int i = 0; i < 20; i++) {
				TextMessage msg = jms.getSession().createTextMessage();
				msg.setText(i + " : " + message + " (pour " + destinataire + ")");
				
				// Ajout d'une propri�t� particuli�re
				if (destinataire != null)
					msg.setStringProperty("destinataire", destinataire);
				
				// Ajout d'une queue pour la r�ponse
				msg.setJMSReplyTo(queueACK);
				
				// Ajout d'un ID de message
				msg.setJMSCorrelationID(Integer.toString(i));
				
				// Envoi du message
				sender.send(msg);
				
				System.out.println("Message " + i + " envoy� � " + destinataire + "!");
				
				// Attente d'ACK
				System.out.println("Attente d'ACK de message " + i);
				TextMessage msgACK = (TextMessage) receiverACK.receive();
				System.out.println("ACK recu : " + msgACK.getText());
				System.out.println();
			}
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
			String destinataire = null;
			if (args.length > 1)
				destinataire = args[1];
			
			Emetteur e = new Emetteur(args[0], destinataire);
			e.run();
			System.exit(0);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
