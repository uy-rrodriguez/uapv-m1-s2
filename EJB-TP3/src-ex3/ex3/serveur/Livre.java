package ex3.serveur;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="livre_tp32")
public class Livre {
	
	@Id
	private String isbn;
	private String titre;
	
	@OneToOne
	@JoinColumn(name="auteur_num", referencedColumnName="num")
	private Auteur auteur;
	
	public Auteur getAuteur() {
		return auteur;
	}

	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}

	public String getTitre() {
		return titre;
	}
}
