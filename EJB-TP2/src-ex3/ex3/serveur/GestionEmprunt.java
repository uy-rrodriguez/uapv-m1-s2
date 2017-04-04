package ex3.serveur;

import javax.ejb.Remote;

import ex3.serveur.exception.LivreDejaEmprunte;
import ex3.serveur.exception.NbMaxEmpruntsAtteint;

@Remote
public interface GestionEmprunt {
	public boolean nouvelEmprunt(int numEmprunteur) throws Exception;
	public boolean emprunter(String isbn) throws NbMaxEmpruntsAtteint, LivreDejaEmprunte;
	public boolean rendre(String isbn) throws Exception;
	
	// Pour tester l'erreur d'emprunteur non existant
	public boolean emprunterAvecEmprunteur(String isbn, int numemp) throws NbMaxEmpruntsAtteint, LivreDejaEmprunte;
}