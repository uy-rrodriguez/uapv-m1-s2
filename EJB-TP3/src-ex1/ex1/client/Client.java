package ex1.client;

import java.io.Console;

import javax.naming.InitialContext;

import ex1.serveur.GestionLivre;

public class Client {
	private static void pause() {
		Console c = System.console();
		c.printf("\nPress ENTER to continue\n", new Object[]{});
		c.readLine();
	}
	
	public static void main(String[] args) {
		try {
			InitialContext ic = new InitialContext();
			
			System.out.println("TP3 Ex 1 : OneToOne");
			GestionLivre gestion = (GestionLivre) ic.lookup("ex1.serveur.GestionLivre");
			
			String isbn = "111";
			
			System.out.println("Test getNbPages");
			int pages = gestion.getNbPages(isbn);
			System.out.println("Resultat : " + pages);
			pause();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
