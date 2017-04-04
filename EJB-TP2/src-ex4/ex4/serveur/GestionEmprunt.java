package ex4.serveur;

import javax.ejb.Remote;

import ex4.serveur.exception.LivreDejaEmprunte;
import ex4.serveur.exception.NbMaxEmpruntsAtteint;

@Remote
public interface GestionEmprunt {
	public boolean nouvelEmprunt(int numEmprunteur) throws Exception;
	public boolean emprunter(String isbn) throws NbMaxEmpruntsAtteint, LivreDejaEmprunte;
	public boolean rendre(String isbn) throws Exception;
}