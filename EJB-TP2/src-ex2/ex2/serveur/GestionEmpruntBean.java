package ex2.serveur;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ex2.serveur.Emprunteur;
import ex2.serveur.GestionEmprunt;
import ex2.serveur.LivreEmp;

@Stateful
public class GestionEmpruntBean implements GestionEmprunt {

	private int numEmprunteur = 0;

	@PersistenceContext(unitName="GestionEmprunt")
	protected EntityManager em;
	
	@Override
	public boolean nouvelEmprunt(int numEmprunteur) throws Exception {
		System.out.println("nouvelEmprunt " + numEmprunteur);
		
		Emprunteur e = em.find(Emprunteur.class, numEmprunteur);
		if (e == null) {
			System.out.println("nouvelEmprunt : Emprunteur " + numEmprunteur + " non trouvé");
			return false;
		}
		
		this.numEmprunteur = numEmprunteur;
		return true;
	}

	@Override
	public boolean emprunter(String isbn) throws Exception {
		System.out.println("emprunter " + isbn);
		
		LivreEmp l = em.find(LivreEmp.class, isbn);
		if (l == null) {
			System.out.println("emprunter : ISBN non trouvé");
			return false;
		}
		
		if (l.getEmprunteur() != null) {
			System.out.println("emprunter : Livre déjà emprunté");
			return false;
		}
		
		Emprunteur e = em.find(Emprunteur.class, numEmprunteur);
		if (e == null) {
			System.out.println("nouvelEmprunt : Emprunteur " + numEmprunteur + " non trouvé");
			return false;
		}
		
		e.setNblivresemp(e.getNblivresemp() + 1);
		l.setEmprunteur(e);
		
		em.merge(e);
		System.out.println("emprunter em.merge " + e);
		
		em.merge(l);
		System.out.println("emprunter em.merge " + l);
		return true;
	}

	@Override
	public boolean rendre(String isbn) throws Exception {
		System.out.println("rendre " + isbn);
		
		LivreEmp l = em.find(LivreEmp.class, isbn);
		if (l == null) {
			System.out.println("rendre : ISBN non trouvé");
			return false;
		}
		
		if (l.getEmprunteur() == null) {
			System.out.println("rendre : Livre non emprunté");
			return false;
		}
		
		Emprunteur e = l.getEmprunteur();
		e.setNblivresemp(e.getNblivresemp() - 1);
		l.setEmprunteur(null);
		
		em.merge(e);
		System.out.println("emprunter em.merge " + e);
		
		em.merge(l);
		System.out.println("emprunter em.merge " + l);
		return true;
	}
}
