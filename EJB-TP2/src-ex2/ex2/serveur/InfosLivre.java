package ex2.serveur;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface InfosLivre {
	public String getTitre(String isbn) throws Exception;
	public List<LivreEmp> getLivresDisponibles() throws Exception;
}