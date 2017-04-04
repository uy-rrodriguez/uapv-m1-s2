package ex1.serveur;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="details_livre")
public class DetailsLivre {
	
	@Id
	String isbn;
	int nbpages;
	int annee_parution;
	
	public int getNbpages() {
		return nbpages;
	}
	
}
