package ex3.serveur;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="auteur")
public class Auteur {
	
	@Id
	int num;
	String nom;
	
	@OneToMany(mappedBy="auteur")
	List<Livre> livres;
	
	public String getNom() {
		return nom;
	}

	public List<Livre> getLivres() {
		return livres;
	}
	
}
