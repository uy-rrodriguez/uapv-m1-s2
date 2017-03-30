package ex2.serveur;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ex2.serveur.InfosLivre;
import ex2.serveur.Livre;

@Stateful
public class InfosLivreBean implements InfosLivre {

	@PersistenceContext(unitName="GestionEmprunt")
	protected EntityManager em;

	@Override
	public String getTitre(String isbn) throws Exception {
		System.out.println("getTitre " + isbn);
		
		Query query = em.createNamedQuery("getByISBN");
		query.setParameter("isbn", isbn);
		
		try {
			Livre l = (Livre) query.getSingleResult();
			return l.getTitre();
		}
		catch (NoResultException nrex) {
			return "<Le livre avec ISBN " + isbn + " n'existe pas !>";
		}
	}

	@Override
	public List<Livre> getLivresDisponibles() throws Exception {
		System.out.println("getLivresDisponibles");
		
		Query query = em.createNamedQuery("getDisponibles");
		List<Livre> resultat = query.getResultList();
		
		return resultat;
	}

}
