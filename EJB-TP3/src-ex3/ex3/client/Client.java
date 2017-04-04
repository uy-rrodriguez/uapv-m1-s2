package ex3.client;

import java.io.Console;
import java.util.List;

import javax.naming.InitialContext;

import ex3.serveur.GestionLivre;

public class Client {
	private static void pause() {
		Console c = System.console();
		c.printf("\nPress ENTER to continue\n", new Object[]{});
		c.readLine();
	}
	
	public static void main(String[] args) {
		try {
			InitialContext ic = new InitialContext();
			
			System.out.println("TP3 Ex 2 : ManyToOne");
			GestionLivre gestion = (GestionLivre) ic.lookup("ex3.serveur.GestionLivre");
			
			String isbn = "111";
			System.out.println("Test getLivresAuteur");
			System.out.println("Livres du meme auteur que ISBN " + isbn);
			try {
				List<String> titres = gestion.getLivresAuteur(isbn);
				for (String titre : titres) {
					System.out.println(" - " + titre);
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			pause();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
