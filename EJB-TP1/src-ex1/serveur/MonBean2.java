package serveur;

import javax.ejb.Remote;

@Remote
public interface MonBean2 {
	public String salut(String user);
	public String re_salut();
}