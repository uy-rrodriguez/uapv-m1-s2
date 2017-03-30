package ex2.serveur;

import java.util.List;

import javax.ejb.Remote;

import ex2.serveur.Livre;

@Remote
public interface InfosLivre {
	public String getTitre(String isbn) throws Exception;
	public List<Livre> getLivresDisponibles() throws Exception;
}