package ex10;

public class ContactNotFoundPlainException extends Exception {

	private static final long serialVersionUID = -5856946112515609383L;

	public ContactNotFoundPlainException() {
		super("Contact inconnu");
	}
}
