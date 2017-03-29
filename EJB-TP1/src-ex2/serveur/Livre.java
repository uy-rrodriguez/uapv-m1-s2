package serveur;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="livre")
public class Livre implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2135270636474053334L;

	@Id
	private String isbn;
	
	private String titre;
	private int dispo;
	
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
	public int isDispo() {
		return dispo;
	}
	public void setDispo(int dispo) {
		this.dispo = dispo;
	}
	
	public Livre() {}
	public Livre(String isbn, String titre) {
		this.isbn = isbn;
		this.titre = titre;
		this.dispo = 1;
	}
}
