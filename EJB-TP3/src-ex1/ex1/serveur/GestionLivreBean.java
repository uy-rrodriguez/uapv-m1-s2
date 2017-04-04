package ex1.serveur;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GestionLivreBean implements GestionLivre {

	@PersistenceContext(unitName="GestionEmprunt")
	protected EntityManager em;
	
	public int getNbPages(String isbn) throws Exception {
		Livre l = em.find(Livre.class, isbn);
		if (l == null)
			throw new Exception("Le livre avec ISBN " + isbn + " n'existe pas");
		
		DetailsLivre details = l.getDetails();
		if (details == null)
			throw new Exception("Il n'y a pas de details pour le livre avec ISBN " + isbn);
		
		return details.getNbpages();
	}
}
