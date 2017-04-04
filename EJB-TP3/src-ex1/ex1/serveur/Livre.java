package ex1.serveur;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="livre_tp31")
public class Livre {
	
	@Id
	String isbn;
	String titre;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	DetailsLivre details;
	
	public DetailsLivre getDetails() {
		return details;
	}
}
