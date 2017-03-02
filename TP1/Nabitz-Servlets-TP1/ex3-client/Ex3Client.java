import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Ex3Client {

	public static void main(String[] args) {
		try {
			String nom = "null";
			if (args.length > 0) {
				nom = args[0];
			}
			
			URL urlEx1 = new URL("http://localhost:8080/JEE-TP1/MonServlet?nom=" + nom);
			URLConnection conn = urlEx1.openConnection();
			
			InputStreamReader reader = new InputStreamReader(conn.getInputStream());
			char[] buff = new char[256];
			while (reader.read(buff) > 0) {
				System.out.println(buff);
				buff = new char[256];
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
