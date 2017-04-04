package ex1.serveur;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="emprunteur")
public class Emprunteur {
	
	@Id
	private int numemp;
	private String nom;
	private int nblivresemp;

	
	public int getNumemp() {
		return numemp;
	}

	public void setNumemp(int numemp) {
		this.numemp = numemp;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getNblivresemp() {
		return nblivresemp;
	}

	public void setNblivresemp(int nblivresemp) {
		this.nblivresemp = nblivresemp;
	}

}
