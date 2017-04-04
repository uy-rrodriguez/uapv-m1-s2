package ex2.client;

import java.io.Console;
import java.util.List;

import javax.naming.InitialContext;

import ex2.serveur.InfosLivre;
import ex2.serveur.LivreEmp;

public class Client {
	private static void pause() {
		Console c = System.console();
		c.printf("\nPress ENTER to continue\n", new Object[]{});
		c.readLine();
	}
	
	public static void main(String[] args) {
		try {
			InitialContext ic = new InitialContext();
			
			System.out.println("\nTest Excercice B : InfosLivre");
			System.out.println("=======================================\n");
			InfosLivre infos = (InfosLivre) ic.lookup("ex2.serveur.InfosLivre");
			
			String isbn = "RMRL";

			
			System.out.println("Test recuperer titre");
			String titreLivre = infos.getTitre(isbn);
			System.out.println("Resultat getTitre : " + titreLivre);
			pause();
			
			
			System.out.println("Test lister disponibles");
			List<LivreEmp> dispos = infos.getLivresDisponibles();
			System.out.println("Livres dispos (" + dispos.size() + ")");
			for (LivreEmp l : dispos) {
				System.out.println("  - " + l.getTitre());
			}
			pause();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
