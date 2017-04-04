package ex4.serveur.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class NbMaxEmpruntsAtteint extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6935150958423593034L;

	public NbMaxEmpruntsAtteint(int max) {
		super("Nombre maximal de livres empruntes (" + max + ") atteint");
	}
}
