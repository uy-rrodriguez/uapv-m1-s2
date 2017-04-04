package ex4.serveur.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class LivreDejaEmprunte extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3299311653574501696L;

	public LivreDejaEmprunte(String isbn) {
		super("Le livre avec ISBN " + isbn + " a deja ete emprunte");
	}
}
