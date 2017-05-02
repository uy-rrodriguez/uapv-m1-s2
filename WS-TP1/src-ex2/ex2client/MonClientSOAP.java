package ex2client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import ex2.IMonSOAP;

public class MonClientSOAP {

	public static void main(String[] args) {
		try {
			URL urlWS = new URL("http://localhost:8080/WSSOAP-tp1-ex2/MonSOAPService?wsdl");
			QName qnameWS = new QName("http://ex2/", "MonSOAPService");
			Service service = Service.create(urlWS, qnameWS);
			
			IMonSOAP port = service.getPort(IMonSOAP.class);
			System.out.println(port.bonjour("Pepito"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
