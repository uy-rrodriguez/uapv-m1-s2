package ex1;

import java.util.ArrayList;
import java.util.List;

public class Carnet {
	
	private List<Contact> contacts;
	
	public Carnet() {
		contacts = new ArrayList<>();
		contacts.add(new Contact("Pepito", "1001"));
		contacts.add(new Contact("Lala", "2002"));
	}
	
	public List<Contact> getContacts() {
		return contacts;
	}
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer("<table border=1><th>Nom</th><th>Num&eacute;ro</th>");
		for (Contact contact : contacts) {
			buff.append(contact);
		}
		buff.append("</table>");
		return buff.toString();
	}
}
