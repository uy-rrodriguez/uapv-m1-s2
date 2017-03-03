package serveur;

import javax.ejb.Remote;

@Remote
public interface MonBean {
	public String salut(String user);
}