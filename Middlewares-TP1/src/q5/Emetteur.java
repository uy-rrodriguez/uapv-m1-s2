package q5;

import javax.jms.QueueSender;
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
			
			System.out.println("Connection créée");
			
			QueueSender sender = jms.getSession().createSender(jms.getQueue());
			TextMessage msg = jms.getSession().createTextMessage();
			msg.setText(message + " (pour " + destinataire + ")");
			
			// Ajout d'une propriété particulière
			if (destinataire != null)
				msg.setStringProperty("destinataire", destinataire);
			
			sender.send(msg);
			System.out.println("Message envoyé à " + destinataire + "!");
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
