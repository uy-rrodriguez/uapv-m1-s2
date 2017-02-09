package partie2.v2;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Partie2Pas1 extends HttpServlet {

	private void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String style = "<link rel='stylesheet' type='text/css' href='../css/style.css'>";
		String header = "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Form utilisateur</title>" + style + "</head><body>";
		String footer = "</body></html>";
		
		String form = "<form action='Partie2Pas2' method='post'>"
						+ "	<div id='container'>"
						+ "		<div>"
						+ "			<input type='text' name='utilisateur' placeholder=\"Nom de l'utilisateur\" />"
						+ "		</div>"
						+ "		<div>"
						+ "			<input type='submit' value='Envoyer' />"
						+ "		</div>"
						+ "	</div>"
						+ "</form>";
		
		resp.setContentType("text/html; charset=UTF-8");
		resp.getOutputStream().println(header + form + footer);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doRequest(req, resp);
	}

}
