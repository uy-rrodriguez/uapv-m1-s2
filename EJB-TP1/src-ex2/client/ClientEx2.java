package client;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import serveur.GestionLivre;


public class ClientEx2 {
	public static void main(String[] args) {
		try {
			InitialContext ic = new InitialContext();
			GestionLivre gestion = (GestionLivre) ic.lookup("ex2.GestionLivre");
			
			String isbn = "123456789";
			gestion.nouveauLivre(isbn, "Le Petit Prince");
			gestion.supprimerLivre(isbn);
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
