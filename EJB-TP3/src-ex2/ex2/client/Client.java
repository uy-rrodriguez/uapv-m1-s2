package ex2.client;

import java.io.Console;

import javax.naming.InitialContext;

import ex2.serveur.GestionLivre;

public class Client {
	private static void pause() {
		Console c = System.console();
		c.printf("\nPress ENTER to continue\n", new Object[]{});
		c.readLine();
	}
	
	public static void main(String[] args) {
		try {
			InitialContext ic = new InitialContext();
			
			System.out.println("TP3 Ex 2 : OneToMany");
			GestionLivre gestion = (GestionLivre) ic.lookup("ex2.serveur.GestionLivre");
			
			String isbn = "111";
			System.out.println("Test getAuteur");
			String nom = gestion.getAuteur(isbn);
			System.out.println("Auteur du livre " + isbn + " : " + nom);
			pause();
			
			
			String isbnAuteur = "RMRL";
			int numAuteur = 10;
			System.out.println("Test setAuteur");
			gestion.setAuteur(isbnAuteur, numAuteur);
			nom = gestion.getAuteur(isbnAuteur);
			System.out.println("Auteur du livre " + isbnAuteur + " : " + nom);
			pause();
			
			
			System.out.println("Test unsetAuteur");
			gestion.unsetAuteur(isbnAuteur);
			try {
				nom = gestion.getAuteur(isbnAuteur);
				System.out.println("Auteur du livre " + isbnAuteur + " : " + nom);
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
