package q10;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.TextMessage;

@LocalBean
@Stateless
public class TraiteMessage {
	public void traitement(TextMessage msg) {
		try {
			System.out.println("Le traitement du message '" + msg.getText() + "' est en cours...");
		}
		catch (Exception e) {
			System.out.println("Erreur dans le traitement du message");
		}
	}
}
