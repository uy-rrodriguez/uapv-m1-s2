package ex2;

import javax.jws.WebService;

@WebService(name="MonSOAP")
public interface IMonSOAP {
	public String bonjour(String user);
}
