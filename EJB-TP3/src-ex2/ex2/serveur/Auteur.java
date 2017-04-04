package ex2.serveur;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="auteur")
public class Auteur {
	
	@Id
	int num;
	String nom;
	
	public String getNom() {
		return nom;
	}
	
}
