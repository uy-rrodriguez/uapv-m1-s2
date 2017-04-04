package ex2.serveur;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GestionLivreBean implements GestionLivre {

	@PersistenceContext(unitName="GestionEmprunt")
	protected EntityManager em;
	
	public String getAuteur(String isbn) throws Exception {
		Livre l = em.find(Livre.class, isbn);
		if (l == null)
			throw new Exception("Le livre avec ISBN " + isbn + " n'existe pas");
		
		Auteur a = l.getAuteur();
		if (a == null)
			throw new Exception("Il n'y a pas d'auteur enregistre pour le livre avec ISBN " + isbn);
		
		return a.getNom();
	}

	@Override
	public void setAuteur(String isbn, int numAuteur) throws Exception {
		Livre l = em.find(Livre.class, isbn);
		if (l == null)
			throw new Exception("Le livre avec ISBN " + isbn + " n'existe pas");
		
		Auteur a = em.find(Auteur.class, numAuteur);
		if (a == null)
			throw new Exception("L'auteur avec le numero " + numAuteur + "n'existe pas");
		
		l.setAuteur(a);
		em.merge(l);
	}

	@Override
	public void unsetAuteur(String isbn) throws Exception {
		Livre l = em.find(Livre.class, isbn);
		if (l == null)
			throw new Exception("Le livre avec ISBN " + isbn + " n'existe pas");
		
		l.setAuteur(null);
		em.merge(l);
	}
}
