package q14;

import java.util.Date;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.TopicSubscriber;

public class Recepteur implements MessageListener {

	private String nom = "";
	private String filtre = null;
	
	public Recepteur(String nom, String[] destinataires) {
		super();
		
		// Gestion de nom du recepteur et filtres
		this.nom = nom;
		
		if (destinataires != null) {
			String[] filtres = new String[destinataires.length];
			for (int i = 0; i < destinataires.length; i++) {
				filtres[i] = "destinataire='" + destinataires[i] + "'";
			}
			this.filtre = String.join(" OR ", filtres);
		}
	}

	
	/**
	 * Méthode déclenchée à la réception d'un message
	 * @param msg
	 */
	@Override
	public void onMessage(Message msg) {
		try {
			// Réception de message
			System.out.println(nom + " : Message recu");
			
			TextMessage txtMsg = (TextMessage) msg;
			System.out.println(nom + " : " + new Date() + " : " + txtMsg.getText());
			System.out.println();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Demarrage du Recepteur et boucle de traitement long.
	 * 
	 * @throws Exception
	 */
	private void run() throws Exception {
		ConnectionJMS jms = new ConnectionJMS();
		
		try {
			// Creation de la connexion
			jms.creerConnexion(nom);
			System.out.println(nom + " : Connection créée (inactive)");
			
			// Création de récepteur avec ou sans filtre
			TopicSubscriber receiver = null;
			if (filtre == null) {
				if (nom.isEmpty())
					receiver = jms.getSession().createSubscriber(jms.getTopic());
				else
					receiver = jms.getSession().createDurableSubscriber(jms.getTopic(), nom);
				
				System.out.println(nom + " : Receiver créé sans filtre");
			}
			else {
				if (nom.isEmpty())
					receiver = jms.getSession().createSubscriber(jms.getTopic(), filtre, false);
				else
					receiver = jms.getSession().createDurableSubscriber(jms.getTopic(), nom, filtre, false);
				
				System.out.println(nom + " : Receiver créé avec filtre : " + filtre);
			}
			
			// Ajout d'un listener
			receiver.setMessageListener(this);
			
			// Activation de la connexion
			jms.activerConnexion();
			System.out.println(nom + " : Connection activée");
			
			
			// Boucle de réception de messages
			do {
				// Un travail looong
				System.out.println(nom + " : Tâche longue en cours...");
				Thread.sleep(5000);
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
	
	
	/**
	 * Main pour tester la classe.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String nom = "";
			if (args.length > 0) {
				nom = args[0];
			}
			
			String[] destinataires = null;
			if (args.length > 1) {
				destinataires = args[1].split(",");
			}
			
			Recepteur r = new Recepteur(nom, destinataires);
			r.run();
			System.exit(0);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
