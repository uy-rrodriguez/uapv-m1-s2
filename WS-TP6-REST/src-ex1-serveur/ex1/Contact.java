package ex1;

public class Contact {

	private String nom;
	private String numero;
	
	public Contact(String nom, String numero) {
		super();
		this.nom = nom;
		this.numero = numero;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getNom() {
		return nom;
	}
	
	@Override
	public String toString() {
		return "<tr><td>" + nom + "</td><td>" + numero + "</td></tr>";
	}
}
