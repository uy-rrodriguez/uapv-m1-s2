package q12;

import java.util.Date;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.TopicSubscriber;

public class Recepteur implements MessageListener {

	private String nom = "";
	private String filtre = null;
	
	public Recepteur(String[] destinataires) {
		super();
		
		// Gestion de nom de destinataire et filtres
		this.nom = String.join(",", destinataires);
		
		String[] filtres = new String[destinataires.length];
		for (int i = 0; i < destinataires.length; i++) {
			filtres[i] = "destinataire='" + destinataires[i] + "'";
		}
		this.filtre = String.join(" OR ", filtres);
	}

	
	/**
	 * M�thode d�clench�e � la r�ception d'un message
	 * @param msg
	 */
	@Override
	public void onMessage(Message msg) {
		try {
			// R�ception de message
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
			jms.creerConnexion();
			System.out.println(nom + " : Connection cr��e (inactive)");
			
			// Cr�ation de r�cepteur avec ou sans filtre
			TopicSubscriber receiver = null;
			if (nom.isEmpty()) {
				receiver = jms.getSession().createSubscriber(jms.getTopic());
				System.out.println(nom + " : Receiver cr�� sans filtre");
			}
			else {
				receiver = jms.getSession().createSubscriber(jms.getTopic(), filtre, false);
				System.out.println(nom + " : Receiver cr�� avec filtre : " + filtre);
			}
			
			// Ajout d'un listener
			receiver.setMessageListener(this);
			
			// Activation de la connexion
			jms.activerConnexion();
			System.out.println(nom + " : Connection activ�e");
			
			
			// Boucle de r�ception de messages
			do {
				// Un travail looong
				System.out.println(nom + " : T�che longue en cours...");
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
