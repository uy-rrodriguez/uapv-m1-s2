package ex3.client;

import java.io.Console;
import java.util.List;

import javax.naming.InitialContext;

import ex3.serveur.InfosLivre;
import ex3.serveur.Livre;

public class Client {
	private static void pause() {
		Console c = System.console();
		c.printf("\nPress ENTER to continue\n", new Object[]{});
		c.readLine();
	}
	
	public static void main(String[] args) {
		try {
			InitialContext ic = new InitialContext();
			
			System.out.println("Test Excercice B : InfosLivre");
			InfosLivre infos = (InfosLivre) ic.lookup("ex3.serveur.InfosLivre");
			
			String isbn = "RMRL";

			
			System.out.println("Test recuperer titre");
			String titreLivre = infos.getTitre(isbn);
			System.out.println("Resultat getTitre : " + titreLivre);
			pause();
			
			
			System.out.println("Test lister disponibles");
			List<Livre> dispos = infos.getLivresDisponibles();
			System.out.println("Livres dispos (" + dispos.size() + ")");
			for (Livre l : dispos) {
				System.out.println("  - " + l.getTitre());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
