package partie2;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Partie2Pas2_v2 extends HttpServlet {
	
	private void writeResponse(String utilisateur, HttpServletResponse resp) throws ServletException, IOException {
		String content = "<h3>Utilisateur " + utilisateur + ", soyez le bienvenu.</h3>"
						+ "<div><a href='Partie2Pas2_v2'>Recharger</a></div>"
						+ "<div><a href='Partie2Pas1_v2'>Retour en arrière</a></div>";

		resp.getOutputStream().println(content);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Récupération du cookie (peut être null)
		Cookie cookie = CookieUtil.getUserCookie(req);
		
		// Récupération de la valeur par défaut et suppression de cookie
		String utilisateur = this.getInitParameter("DefaultUserName");
		if (cookie != null)
			utilisateur = cookie.getValue();
		
		// On écrit la réponse
		writeResponse(utilisateur, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Récupération de la valeur envoyée par l'utilisateur
		String utilisateur = req.getParameter("utilisateur");
		
		// Récupération de la valeur par défaut et suppression de cookie
		if (utilisateur == null || utilisateur.trim().isEmpty()) {
			utilisateur = this.getInitParameter("DefaultUserName");
			CookieUtil.delUserCookie(resp);
		}
		
		// Création ou modification de cookie
		else {
			CookieUtil.setUserCookie(utilisateur, resp);
		}
		
		// On écrit la réponse
		writeResponse(utilisateur, resp);
	}

}
