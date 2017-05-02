package ex1;

import javax.jws.WebService;

@WebService
public class MonSOAP {

	public String bonjour(String user) {
		return "Bonjour " + user + " !";
	}
}
