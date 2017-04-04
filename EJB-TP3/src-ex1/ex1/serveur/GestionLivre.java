package ex1.serveur;

import javax.ejb.Remote;

@Remote
public interface GestionLivre {
	public int getNbPages(String isbn) throws Exception;
}