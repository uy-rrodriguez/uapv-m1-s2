package ex2;

import javax.jws.WebService;

@WebService(name="MonSOAP")
public class MonSOAP implements IMonSOAP {
	public String bonjour(String user) {
		return "Bonjour " + user + " !";
	}
}
