package serveur;

public class Livre {
	private String isbn;
	
	private String titre;
	private boolean dispo;
	
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public boolean isDispo() {
		return dispo;
	}
	public void setDispo(boolean dispo) {
		this.dispo = dispo;
	}
	
	public Livre(String isbn, String titre) {
		this.isbn = isbn;
		this.titre = titre;
		this.dispo = true;
	}
}
