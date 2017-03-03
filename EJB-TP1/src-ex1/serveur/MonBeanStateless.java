package serveur;

import javax.ejb.Stateless;

@Stateless
public class MonBeanStateless implements MonBean {
	public String salut(String user) {
		return "Salut " + user + " !";
	}
}