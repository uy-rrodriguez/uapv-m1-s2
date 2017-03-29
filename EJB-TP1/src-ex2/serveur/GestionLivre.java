package serveur;

import javax.ejb.Remote;

@Remote
public interface GestionLivre {
	public Livre nouveauLivre(String isbn, String titre) throws Exception;
	public boolean supprimerLivre(String isbn) throws Exception;
}