package ex1.serveur;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ex1.serveur.Emprunteur;
import ex1.serveur.GestionEmprunt;
import ex1.serveur.LivreEmp;

@Stateful
public class GestionEmpruntBean implements GestionEmprunt {

	private Emprunteur emprunteur = null;

	@PersistenceContext(unitName="GestionEmprunt")
	protected EntityManager em;
	
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
	public boolean emprunter(String isbn) throws Exception {
		System.out.println("emprunter " + isbn);
		
		LivreEmp l = em.find(LivreEmp.class, isbn);
		if (l == null) {
			System.out.println("emprunter : ISBN non trouvé");
			return false;
		}
		
		if (l.getEmpruntepar() != 0) {
			System.out.println("emprunter : Livre déjà emprunté");
			return false;
		}
		
				
		emprunteur.setNblivresemp(emprunteur.getNblivresemp() + 1);
		l.setEmpruntepar(emprunteur.getNumemp());
		
		em.merge(emprunteur);
		System.out.println("emprunter em.merge " + emprunteur);
		
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
		
		// On modifie l'emprunteur
		Emprunteur e = em.find(Emprunteur.class, l.getEmpruntepar());
		if (e != null) {
			e.setNblivresemp(e.getNblivresemp() - 1);
		}
		
		l.setEmpruntepar(0);
		
		em.merge(e);
		System.out.println("emprunter em.merge " + e);
		
		em.merge(l);
		System.out.println("emprunter em.merge " + l);
		return true;
	}

}
