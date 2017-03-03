package serveur;

import javax.ejb.Remote;

@Remote
public interface GestionLivre {
	public void nouveauLivre(String isbn, String titre);
	public void supprimerLivre(String isbn);
}