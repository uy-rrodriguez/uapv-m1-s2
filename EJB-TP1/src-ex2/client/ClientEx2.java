package client;

import java.io.BufferedReader;
import java.io.Console;

import javax.naming.InitialContext;

import serveur.GestionLivre;
import serveur.Livre;


public class ClientEx2 {
	private static void pause() {
		Console c = System.console();
		c.printf("\nPress ENTER to continue\n", new Object[]{});
		c.readLine();
	}
	
	public static void main(String[] args) {
		try {
			InitialContext ic = new InitialContext();
			
			System.out.println("Test GestionLivre");
			GestionLivre gestion = (GestionLivre) ic.lookup("serveur.GestionLivre");
			
			String isbn = "RMRL";
			String[] isbns = {"Ric1", "Ric2", "Ric3", "Ric4", "Ric5"};
			
			
			System.out.println("Test nouveauLivre");
			Livre l = gestion.nouveauLivre(isbn, "Le Petit Ricci");
			System.out.println("Creation " + isbn + " : " + l);
			pause();
			
			
			System.out.println("Test plusieurs nouveaux livres");
			for (String isbnFor : isbns) {
				Livre lFor = gestion.nouveauLivre(isbnFor, isbnFor);
				System.out.println("    Creation " + isbnFor + " : " + lFor);
			}
			pause();
			
			
			System.out.println("Test emprunterLivre");
			boolean emprunte = gestion.emprunterLivre(isbn);
			System.out.println("Resultat emprunt : " + emprunte);
			pause();
			
			
			System.out.println("Test rendreLivre");
			boolean rendu = gestion.rendreLivre(isbn);
			System.out.println("Resultat rendu : " + rendu);
			pause();
			
			
			System.out.println("Test supprimerLivre");
			boolean supprime = gestion.supprimerLivre(isbn);
			System.out.println("Suppression " + isbn + " : " + supprime);
			pause();
			
			
			System.out.println("Test supprimer plusieurs livres");
			for (String isbnFor : isbns) {
				boolean supprimeFor = gestion.supprimerLivre(isbnFor);
				System.out.println("    Suppression " + isbnFor + " : " + supprimeFor);
			}
			pause();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
