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
		System.out.println("nouveauLivre em.persist " + l);
		return l;
	}

	@Override
	public boolean supprimerLivre(String isbn) throws Exception {
		Livre l = em.find(Livre.class, isbn);
		if (l == null)
			return false;
		
		em.remove(l);
		System.out.println("supprimerLivre em.remove " + l);
		return true;
	}

	@Override
	public boolean emprunterLivre(String isbn) throws Exception {
		Livre l = em.find(Livre.class, isbn);
		if (l == null || l.getDispo() == 0)
			return false;
		
		l.setDispo(0);
		
		em.merge(l);
		System.out.println("emprunterLivre em.merge " + l);
		return true;
	}

	@Override
	public boolean rendreLivre(String isbn) throws Exception {
		Livre l = em.find(Livre.class, isbn);
		if (l == null)
			return false;
		
		l.setDispo(1);
		
		em.merge(l);
		System.out.println("rendreLivre em.merge " + l);
		return true;
	}

}
