package serveur;

import javax.ejb.Stateless;

@Stateless
public class MonBeanStateful implements MonBean2 {
	private String user;
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String salut(String user) {
		this.user = user;
		return "Salut " + user + " !";
	}

	@Override
	public String re_salut() {
		return "Salut " + this.user + " !";
	}
}