package q10;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(mappedName="jms/tpJMSDestination",
				activationConfig={
					@ActivationConfigProperty(
						propertyName = "messageSelector",
						propertyValue = "destinataire='MDB_1'"
					)
				})
public class Recepteur implements MessageListener {
	@EJB
	TraiteMessage traiteur;
	
	@Override
	public void onMessage(Message msg) {
		TextMessage txtMsg = null;
		try {
			if (msg instanceof TextMessage) {
				txtMsg = (TextMessage) msg;
				System.out.println("Message recu : " + txtMsg.getText());
				System.out.println("On appele le traitement du message");
				
				traiteur.traitement(txtMsg);
				
			}
			else {
				System.out.println("Le message n'est pas de type texte");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
