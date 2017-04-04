package ex2.serveur;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="livre_emp")
@NamedQueries({
	@NamedQuery(name="getByISBN", query="SELECT l FROM LivreEmp l WHERE l.isbn = :isbn"),
	@NamedQuery(name="getDisponibles", query="SELECT l FROM LivreEmp l WHERE l.empruntepar = 0")
})
public class LivreEmp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7390191950291185324L;
	
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
