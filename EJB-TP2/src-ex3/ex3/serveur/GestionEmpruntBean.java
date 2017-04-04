package ex3.serveur;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ex3.serveur.Emprunteur;
import ex3.serveur.GestionEmprunt;
import ex3.serveur.LivreEmp;
import ex3.serveur.exception.LivreDejaEmprunte;
import ex3.serveur.exception.NbMaxEmpruntsAtteint;

@Stateful
public class GestionEmpruntBean implements GestionEmprunt {

	private Emprunteur emprunteur = null;
	private static final int MAX_LIVRES_EMP = 3;
	

	@PersistenceContext(unitName="GestionEmprunt")
	protected EntityManager em;
	
	@Resource
	SessionContext ctx;
	
	@Override
	public boolean nouvelEmprunt(int numEmprunteur) throws Exception {
		System.out.println("nouvelEmprunt " + numEmprunteur);
		
		emprunteur = em.find(Emprunteur.class, numEmprunteur);
		if (emprunteur == null) {
			System.out.println("nouvelEmprunt : Emprunteur " + numEmprunteur + " non trouvé");
			return false;
		}
		
		return true;
	}

	@Override
	public boolean emprunter(String isbn) throws NbMaxEmpruntsAtteint, LivreDejaEmprunte {
		System.out.println("emprunter " + isbn);
		
		// Recherche des beans
		LivreEmp l = em.find(LivreEmp.class, isbn);
		if (l == null) {
			System.out.println("emprunter : ISBN non trouvé");
			return false;
		}
		
		// Contrôle de nombre de livres empruntés
		if (emprunteur.getNblivresemp() >= MAX_LIVRES_EMP) {
			
			// Annulation des transactions
			ctx.setRollbackOnly();
			
			throw new NbMaxEmpruntsAtteint(MAX_LIVRES_EMP);
		}
		
		// Pas d'erreur, modification de l'emprunteur
		emprunteur.setNblivresemp(emprunteur.getNblivresemp() + 1);
		em.merge(emprunteur);
		System.out.println("emprunter em.merge " + emprunteur);
		
		
		// Contrôle de livre déjà emprunté
		if (l.getEmpruntepar() != 0) {
			
			// Annulation des transactions
			ctx.setRollbackOnly();
			
			throw new LivreDejaEmprunte(isbn);	
		}
		
		// Pas d'erreur, modification du livre 
		l.setEmpruntepar(emprunteur.getNumemp());
		em.merge(l);
		System.out.println("emprunter em.merge " + l);
		
		
		return true;
	}
	
	@Override
	public boolean emprunterAvecEmprunteur(String isbn, int numemp) throws NbMaxEmpruntsAtteint, LivreDejaEmprunte {
		System.out.println("emprunterAvecEmprunteur ISBN " + isbn + ", emprunteur " + numemp);
		
		// Recherche des beans
		LivreEmp l = em.find(LivreEmp.class, isbn);
		if (l == null) {
			System.out.println("emprunterAvecEmprunteur : ISBN non trouvé");
			return false;
		}
		
		// Pas d'erreur, modification de l'emprunteur
		Emprunteur e = new Emprunteur();
		e.setNumemp(numemp);
		e.setNblivresemp(1);
		em.merge(e);
		System.out.println("emprunter em.merge " + e);
		
		// Pas d'erreur, modification du livre 
		l.setEmpruntepar(numemp);
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
		
		if (l.getEmpruntepar() == 0) {
			System.out.println("rendre : Livre non emprunté");
			return false;
		}
		
		Emprunteur e = em.find(Emprunteur.class, l.getEmpruntepar());
		if (e != null) {
			e.setNblivresemp(e.getNblivresemp() - 1);
			
			if (e.getNumemp() == emprunteur.getNumemp())
				emprunteur = e;
		}
		
		l.setEmpruntepar(0);
		
		em.merge(e);
		System.out.println("emprunter em.merge " + e);
		
		em.merge(l);
		System.out.println("emprunter em.merge " + l);
		
		return true;
	}
}
