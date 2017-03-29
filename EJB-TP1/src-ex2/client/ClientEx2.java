package client;

import javax.naming.InitialContext;

import serveur.GestionLivre;
import serveur.Livre;


public class ClientEx2 {
	public static void main(String[] args) {
		try {
			InitialContext ic = new InitialContext();
			
			System.out.println("Test GestionLivre");
			GestionLivre gestion = (GestionLivre) ic.lookup("serveur.GestionLivre");
			
			String isbn = "zzzz";
			
			System.out.println("Test nouveauLivre");
			Livre l = gestion.nouveauLivre(isbn, "Le Petit Prince");
			System.out.println("Livre : " + l);
			
			System.out.println("Test supprimerLivre");
			boolean res = gestion.supprimerLivre(isbn);
			System.out.println("Resultat suppression : " + res);
		}
		catch (Exception e) {
			//System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
