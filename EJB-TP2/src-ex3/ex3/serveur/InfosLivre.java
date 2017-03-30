package ex3.serveur;

import java.util.List;

import javax.ejb.Remote;

import ex3.serveur.Livre;

@Remote
public interface InfosLivre {
	public String getTitre(String isbn) throws Exception;
	public List<Livre> getLivresDisponibles() throws Exception;
}