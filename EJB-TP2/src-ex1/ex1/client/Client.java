package ex1.client;

import java.io.Console;

import javax.naming.InitialContext;

import ex1.serveur.GestionEmprunt;

public class Client {
	private static void pause() {
		Console c = System.console();
		c.printf("\nPress ENTER to continue\n", new Object[]{});
		c.readLine();
	}
	
	public static void main(String[] args) {
		try {
			InitialContext ic = new InitialContext();
			
			System.out.println("Test Excercice A : GestionEmprunt");
			GestionEmprunt gestion = (GestionEmprunt) ic.lookup("ex1.serveur.GestionEmprunt");
			
			int numEmprunteur = 1;
			String isbn = "RMRL";

			
			System.out.println("Test nouvelEmprunt");
			boolean nouvel = gestion.nouvelEmprunt(numEmprunteur);
			System.out.println("Resultat nouvelEmprunt : " + nouvel);
			pause();
			
			
			System.out.println("Test emprunter");
			boolean emprunte = gestion.emprunter(isbn);
			System.out.println("Resultat emprunt : " + emprunte);
			pause();
			
			
			System.out.println("Test rendre");
			boolean rendu = gestion.rendre(isbn);
			System.out.println("Resultat rendu : " + rendu);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
