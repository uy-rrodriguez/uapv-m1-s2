package serveur;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GestionLivreBean implements GestionLivre {

	@PersistenceContext(unitName="GestionLivre")
	protected EntityManager em;
	
	@Override
	public void nouveauLivre(String isbn, String titre) {
		Livre l = new Livre(isbn, titre);
		//em.persist(l);
		System.out.println("em.persist " + l);
	}

	@Override
	public void supprimerLivre(String isbn) {
		Livre l = (Livre) em.find(Livre.class, isbn);
		//em.remove(l);
		System.out.println("em.remove " + l);
	}

}
