package ex4.serveur;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ex4.serveur.Emprunteur;
import ex4.serveur.GestionEmprunt;
import ex4.serveur.LivreEmp;
import ex4.serveur.exception.LivreDejaEmprunte;
import ex4.serveur.exception.NbMaxEmpruntsAtteint;

@Stateful
public class GestionEmpruntBean implements GestionEmprunt {

	private Emprunteur emprunteur = null;
	
	@Resource(name = "maxLivresEmpruntes")
	int maxLivresEmpruntes;

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
		if (emprunteur.getNblivresemp() >= maxLivresEmpruntes) {
			
			// Annulation des transactions
			ctx.setRollbackOnly();
			
			throw new NbMaxEmpruntsAtteint(maxLivresEmpruntes);
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
