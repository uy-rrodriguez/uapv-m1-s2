package ex3;

import javax.jws.WebService;

@WebService
public class MonSOAP implements IMonSOAP {
	public String bonjour(String user) {
		return "Bonjour " + user + " !";
	}
}
