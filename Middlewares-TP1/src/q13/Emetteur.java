package q13;

import javax.jms.TextMessage;
import javax.jms.TopicPublisher;

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
			// Creation de connexion
			jms.creerConnexion();
			System.out.println("Connection cr��e (inactive)");
			
			// Cr�ation d'un �metteur
			TopicPublisher sender = jms.getSession().createPublisher(jms.getTopic());
			
			// Activation de connexion
			jms.activerConnexion();
			System.out.println("Connection activ�e");
			
			
			// Envoi de messages
			for (int i = 0; i < 5; i++) {
				TextMessage msg = jms.getSession().createTextMessage();
				msg.setText(i + " : " + message + " (pour " + destinataire + ")");
				
				// Ajout d'une propri�t� particuli�re
				if (destinataire != null)
					msg.setStringProperty("destinataire", destinataire);
				
				// Ajout d'un ID de message
				msg.setJMSCorrelationID(Integer.toString(i));
				
				// Envoi du message
				sender.send(msg);
				
				System.out.println("Message " + i + " envoy� � " + destinataire + "!");
				System.out.println();
			}
			
			
			// Envoi de message prioritaire
			TextMessage msg = jms.getSession().createTextMessage();
			msg.setText("MESSAGE PRIORITAIRE (pour " + destinataire + ")");
			
			// Priorite
			sender.setPriority(9);
			
			// Ajout d'une propri�t� particuli�re
			if (destinataire != null)
				msg.setStringProperty("destinataire", destinataire);
			
			// Envoi du message
			sender.send(msg);
			
			System.out.println("Message PRIORITAIRE envoy� � " + destinataire + "!");
			System.out.println();
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
