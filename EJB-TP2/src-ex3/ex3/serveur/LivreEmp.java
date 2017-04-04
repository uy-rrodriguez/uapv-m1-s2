package ex3.serveur;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="livre_emp")
public class LivreEmp {
	@Id
	private String isbn;
	private String titre;
	private int empruntepar;

	
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

	public int getEmpruntepar() {
		return empruntepar;
	}

	public void setEmpruntepar(int empruntepar) {
		this.empruntepar = empruntepar;
	}
	
}
