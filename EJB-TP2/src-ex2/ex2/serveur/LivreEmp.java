package ex2.serveur;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ex2.serveur.Emprunteur;

@Entity
@Table(name="livre_emp")
public class LivreEmp {
	@Id
	private String isbn;
	private String titre;
	
	@OneToOne
	@JoinColumn(name="empruntepar", referencedColumnName="numemp")
	private Emprunteur emprunteur;

	
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

	public Emprunteur getEmprunteur() {
		return emprunteur;
	}

	public void setEmprunteur(Emprunteur emprunteur) {
		this.emprunteur = emprunteur;
	}

	
}
