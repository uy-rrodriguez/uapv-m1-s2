package partie3.v2;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Partie3Pas2 extends HttpServlet {
	
	private void writeResponse(String utilisateur, int age, String codePostal, HttpServletResponse resp) throws ServletException, IOException {
		String style = "<link rel='stylesheet' type='text/css' href='../css/style.css'>";
		
		String header = "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Form utilisateur</title>" + style + "</head><body>";
		
		String footer = "<div><a href='" + resp.encodeURL("Partie3Pas1") +"'>Éditer les informations</a></div>"
						+ "</body></html>";
		
		String form = "<form action='" + resp.encodeURL("Partie3Pas3") +"' method='post'>"
						+ "	<div id='container'>"
						+ " 	<h3>Utilisateur " + utilisateur + ", soyez le bienvenu.</h3>"
						+ "		<div>"
						+ "			<input type='text' name='age' value='" + age + "' placeholder=\"Votre âge\" />"
						+ "		</div>"
						+ "		<div>"
						+ "			<input type='text' name='codePostal' value='" + codePostal + "' placeholder=\"Code postal\" />"
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
		HttpSession session = req.getSession();
		
		// Récupération des valeurs depuis la session
		String utilisateur = (String) session.getAttribute("utilisateur");
		
		int age = 0;
		if (session.getAttribute("age") != null)
			age = (Integer) session.getAttribute("age");
		
		String codePostal = "";
		if (session.getAttribute("codePostal") != null)
			codePostal = (String) session.getAttribute("codePostal");
		
		// On écrit la réponse
		writeResponse(utilisateur, age, codePostal, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession();
		
		// Récupération des valeurs depuis la session
		int age = 0;
		if (session.getAttribute("age") != null)
			age = (Integer) session.getAttribute("age");
		
		String codePostal = "";
		if (session.getAttribute("codePostal") != null)
			codePostal = (String) session.getAttribute("codePostal");

		// Récupération de la valeur envoyée par l'utilisateur
		String utilisateur = req.getParameter("utilisateur");
		
		// Stockage dans la session
		session.setAttribute("utilisateur", utilisateur);
		
		// On écrit la réponse
		writeResponse(utilisateur, age, codePostal, resp);
	}

}
