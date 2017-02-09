package partie3.v2;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Partie3Pas1 extends HttpServlet {

	private void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Récupération des valeurs depuis la session
		HttpSession session = req.getSession();
		String utilisateur = "";
		if (session.getAttribute("utilisateur") != null)
			utilisateur = (String) session.getAttribute("utilisateur");

		
		// Réponse
		String style = "<link rel='stylesheet' type='text/css' href='../css/style.css'>";
		String header = "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Form utilisateur</title>" + style + "</head><body>";
		String footer = "</body></html>";
				
		String form = "<form action='" + resp.encodeURL("Partie3Pas2") +"' method='post'>"
						+ "	<div id='container'>"
						+ "		<div>"
						+ "			<input type='text' name='utilisateur' value='" + utilisateur + "' placeholder=\"Nom de l'utilisateur\" />"
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
