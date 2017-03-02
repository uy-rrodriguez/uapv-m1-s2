package partie3.v3;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Partie3Pas3 extends HttpServlet {
	
	private void writeResponse(String utilisateur, int age, String codePostal, HttpServletResponse resp) throws ServletException, IOException {
		String style = "<link rel='stylesheet' type='text/css' href='../css/style.css'>";
		
		String header = "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Form utilisateur</title>" + style + "</head><body>";
		
		String menu = "<div>&nbsp;</div>"
					+ "<div>"
					+ "		<ul>Naviguer entre les pages :"
					+ "			<li><a href='" + resp.encodeURL("Partie3Page1") + "'>Page 1</a></li>"
					+ "			<li><a href='" + resp.encodeURL("Partie3Page2") + "'>Page 2</a></li>"
					+ "		</ul>"
					+ "</div>"
					+ "<div>&nbsp;</div>"
					+ "<div><a href='" + resp.encodeURL("Partie3Pas2") +"'>Éditer les informations</a></div>"
					+ "<div>&nbsp;</div>"
					+ "<div><a href='" + resp.encodeURL("Partie3Pas4") +"'>Fermer la session</a></div>";
		
		String footer = "</body></html>";
		
		String form = "<div id='container'>"
						+ " 	<h3>Vous êtes " + utilisateur + ".</h3>"
						+ " 	<h2>" + age + " ans.</h2>"
						+ " 	<h2>CP " + codePostal + ".</h3>"
						+ "	</div>";
		
		resp.setContentType("text/html; charset=UTF-8");
		resp.getOutputStream().println(header + form + menu + footer);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		// Récupération des valeurs depuis la session
		String utilisateurActuel = (String) session.getAttribute("utilisateur");
		
		int age = 0;
		if (session.getAttribute("age") != null)
			age = (Integer) session.getAttribute("age");
		
		String codePostal = (String) session.getAttribute("codePostal");
		
		// On écrit la réponse
		writeResponse(utilisateurActuel, age, codePostal, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		// Récupération des valeurs depuis la session
		String utilisateurActuel = (String) session.getAttribute("utilisateur");
		
		// Récupération des valeurs envoyées par l'utilisateur
		int age = 0;
		String ageStr = req.getParameter("age");
		if (! ageStr.trim().isEmpty())
			age = Integer.parseInt(ageStr);
		
		String codePostal = req.getParameter("codePostal");
		
		// Stockage dans la session
		session.setAttribute("age", age);
		session.setAttribute("codePostal", codePostal);
		
		// On écrit la réponse
		writeResponse(utilisateurActuel, age, codePostal, resp);
	}

}
