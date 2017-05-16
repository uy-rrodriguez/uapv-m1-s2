package ex10;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Carnet {
	
	private Map<String, Contact> contacts;
	
	public Carnet() {
		contacts = new HashMap<>();
		contacts.put("Pepito", new Contact("Pepito", "1001"));
		contacts.put("Lala", new Contact("Lala", "2002"));
	}
	
	public Map<String, Contact> getContacts() {
		return contacts;
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
