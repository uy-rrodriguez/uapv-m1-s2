package ex1.serveur;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ex1.serveur.LivreEmp;

@Entity
@Table(name="emprunteur")
public class Emprunteur {
	@Id
	private int numemp;
	private String nom;
	private int nblivresemp;
	
	@OneToMany(mappedBy="emprunteur")
	private List<LivreEmp> livres;

	
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

	public List<LivreEmp> getLivres() {
		return livres;
	}

	public void setLivres(List<LivreEmp> livres) {
		this.livres = livres;
	}
}
