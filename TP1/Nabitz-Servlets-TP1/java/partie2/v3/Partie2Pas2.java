package partie2.v3;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Partie2Pas2 extends HttpServlet {
	
	private void writeResponse(String utilisateur, HttpServletResponse resp) throws ServletException, IOException {
		String content = "<h3>Utilisateur " + utilisateur + ", soyez le bienvenu.</h3>"
						+ "<div><a href='Partie2Pas2'>Recharger</a></div>"
						+ "<div><a href='Partie2Pas1'>Retour en arri�re</a></div>";

		resp.setContentType("text/html; charset=UTF-8");
		resp.getOutputStream().println(content);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		// R�cup�ration du cookie (peut �tre null)
		String utilisateur = CookieUtil.getUserNameByCookie(req);
		
		// R�cup�ration de la valeur par d�faut et suppression de cookie
		if (utilisateur == null)
			utilisateur = this.getInitParameter("DefaultUserName");;
		
		// On �crit la r�ponse
		writeResponse(utilisateur, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		// R�cup�ration de la valeur envoy�e par l'utilisateur
		String utilisateur = req.getParameter("utilisateur");
		
		// R�cup�ration de la valeur par d�faut et suppression de cookie
		if (utilisateur == null || utilisateur.trim().isEmpty()) {
			utilisateur = this.getInitParameter("DefaultUserName");
			CookieUtil.delUserCookie(resp);
		}
		
		// Cr�ation ou modification de cookie
		else {
			CookieUtil.setUserCookie(utilisateur, resp);
		}
		
		// On �crit la r�ponse
		writeResponse(utilisateur, resp);
	}

}
