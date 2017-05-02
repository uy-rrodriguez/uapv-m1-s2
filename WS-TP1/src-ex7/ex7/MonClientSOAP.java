package ex6;

import javax.xml.ws.WebServiceRef;
import com.dataaccess.webservicesserver.*;

public class MonClientSOAP {

	@WebServiceRef(wsdlLocation="http://www.dataaccess.com/webservicesserver/textcasing.wso?WSDL")
	public static TextCasing service;
	
	public static void main(String[] args) {
		try {
			TextCasingSoapType port = service.getTextCasingSoap();
			System.out.println(port.invertStringCase(args[0]));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
