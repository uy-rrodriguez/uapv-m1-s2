package ex4.client;

import java.io.Console;

import javax.naming.InitialContext;

import ex4.serveur.GestionEmprunt;
import ex4.serveur.exception.LivreDejaEmprunte;
import ex4.serveur.exception.NbMaxEmpruntsAtteint;

public class Client {
	private static void pause() {
		Console c = System.console();
		c.printf("\nPress ENTER to continue\n", new Object[]{});
		c.readLine();
	}
	
	public static void main(String[] args) {
		try {
			InitialContext ic = new InitialContext();
			
			System.out.println("\nTest Excercice D : Configuration");
			System.out.println("=======================================\n");
			GestionEmprunt gestion = (GestionEmprunt) ic.lookup("ex4.serveur.GestionEmprunt");
			
			int numEmprunteur = 10;
			String isbn = "RMRL";
			String[] isbnEmprunts = {"Ric1", "Ric2", "Ric3", "Ric4", "Ric5"};

			
			System.out.println("Test nouvelEmprunt");
			boolean nouvel = gestion.nouvelEmprunt(numEmprunteur);
			System.out.println("Resultat nouvelEmprunt : " + nouvel);
			pause();
			
			
			System.out.println("Test max emprunter");
			try {
				int i = 0;
				for (String isbnEmp : isbnEmprunts) {
					i++;
					System.out.println("    Emprunt " + i);
					boolean emprunte = gestion.emprunter(isbnEmp);
					System.out.println("    Resultat emprunt " + i + " : " + emprunte);
				}
			}
			catch (NbMaxEmpruntsAtteint ex) {
				System.out.println("Erreur : " + ex.getMessage());
			}
			catch (LivreDejaEmprunte ex) {
				System.out.println("Erreur : " + ex.getMessage());
			}
			pause();
			
			
			System.out.println("Test rendre tout");
			for (String isbnEmp : isbnEmprunts) {
				System.out.println("    Rendu " + isbnEmp);
				boolean rendu = gestion.rendre(isbnEmp);
				System.out.println("    Resultat rendu : " + rendu);
			}
			pause();
			
			
			System.out.println("Test deja emprunte");
			try {
				System.out.println("Emprunt 1");
				boolean emprunte = gestion.emprunter(isbn);
				System.out.println("Resultat emprunt 1 : " + emprunte);
				
				System.out.println("Emprunt 2");
				emprunte = gestion.emprunter(isbn);
				System.out.println("Resultat emprunt 2 : " + emprunte);
			}
			catch (NbMaxEmpruntsAtteint ex) {
				System.out.println("Erreur : " + ex.getMessage());
			}
			catch (LivreDejaEmprunte ex) {
				System.out.println("Erreur : " + ex.getMessage());
			}
			pause();
			
			
			System.out.println("Test rendre " + isbn);
			boolean rendu = gestion.rendre(isbn);
			System.out.println("Resultat rendu : " + rendu);
			pause();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
