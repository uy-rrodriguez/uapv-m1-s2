package ex1.serveur;

import javax.ejb.Remote;

@Remote
public interface GestionEmprunt {
	public boolean nouvelEmprunt(int numEmprunteur) throws Exception;
	public boolean emprunter(String isbn) throws Exception;
	public boolean rendre(String isbn) throws Exception;
}