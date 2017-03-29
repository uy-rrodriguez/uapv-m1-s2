package serveur;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GestionLivreBean implements GestionLivre {

	@PersistenceContext(unitName="GestionLivre")
	protected EntityManager em;
	
	@Override
	public Livre nouveauLivre(String isbn, String titre) throws Exception {
		Livre l = new Livre(isbn, titre);
		em.persist(l);
		System.out.println("em.persist " + l);
		return l;
	}

	@Override
	public boolean supprimerLivre(String isbn) throws Exception {
		try {
			Livre l = em.find(Livre.class, isbn);
			if (l == null)
				return false;
			
			em.remove(l);
			System.out.println("em.remove " + l);
			return true;
		}
		catch (Throwable e) {
			e.printStackTrace();
			throw new Exception("Erreur dans supprimerLivre : " + e.getMessage());
		}
	}

}
