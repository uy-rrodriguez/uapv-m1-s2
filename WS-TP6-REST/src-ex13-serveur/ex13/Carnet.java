package ex13;

import java.util.HashMap;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Carnet {
	
	private HashMap<String, Contact> contacts;
	
	public Carnet() {
		contacts = new HashMap<>();
		contacts.put("Pepito", new Contact("Pepito", "1001"));
		contacts.put("Lala", new Contact("Lala", "2002"));
	}

	@XmlElementWrapper(name="contacts")
	public HashMap<String, Contact> getContacts() {
		return this.contacts;
	}
	
	public void setContacts(HashMap<String, Contact> contacts) {
		this.contacts = contacts;
	}
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer("<table border=1><th>Nom</th><th>Num&eacute;ro</th>");
		
		Iterator<String> it = contacts.keySet().iterator();
		while (it.hasNext()) {
			String contactNom = it.next();
			buff.append(contacts.get(contactNom));
		}
		
		buff.append("</table>");
		return buff.toString();
	}
}
