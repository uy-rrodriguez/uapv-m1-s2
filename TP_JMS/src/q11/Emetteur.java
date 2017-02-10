package q11;

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
			jms.connecter();
			
			System.out.println("Connection créée");
			
			// Création d'un émetteur
			TopicPublisher sender = jms.getSession().createPublisher(jms.getTopic());
			
			for (int i = 0; i < 5; i++) {
				TextMessage msg = jms.getSession().createTextMessage();
				msg.setText(i + " : " + message + " (pour " + destinataire + ")");
				
				// Ajout d'une propriété particulière
				if (destinataire != null)
					msg.setStringProperty("destinataire", destinataire);
				
				// Ajout d'un ID de message
				msg.setJMSCorrelationID(Integer.toString(i));
				
				// Envoi du message
				sender.send(msg);
				
				System.out.println("Message " + i + " envoyé à " + destinataire + "!");
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
