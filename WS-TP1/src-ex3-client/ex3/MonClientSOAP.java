package ex3;

public class MonClientSOAP {
	
	public static void main(String[] args) {
		try {
			MonSOAPService service = new MonSOAPService();
			MonSOAP port = service.getMonSOAPPort();
			System.out.println(port.bonjour("Pepito"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
