package ex3.serveur;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface GestionLivre {
	public String getAuteur(String isbn) throws Exception;
	public void setAuteur(String isbn, int numAuteur) throws Exception;
	public void unsetAuteur(String isbn) throws Exception;
	
	public List<String> getLivresAuteur(String isbn) throws Exception;
}