package q9;

import javax.ejb.ActivationConfigProperty;
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

	@Override
	public void onMessage(Message msg) {
		TextMessage txtMsg = null;
		try {
			if (msg instanceof TextMessage) {
				txtMsg = (TextMessage) msg;
				System.out.println(txtMsg.getText());
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
