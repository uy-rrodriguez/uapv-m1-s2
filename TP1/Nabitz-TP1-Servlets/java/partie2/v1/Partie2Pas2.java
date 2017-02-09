package partie2.v1;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Partie2Pas2 extends HttpServlet {
	
	private void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		// R�cup�ration de la valeur envoy�e par l'utilisateur
		String utilisateur = req.getParameter("utilisateur");
		
		// R�cup�ration de la valeur par d�faut
		if (utilisateur == null || utilisateur.trim().isEmpty())
			utilisateur = this.getInitParameter("DefaultUserName");
		
		
		String content = "<h3>Utilisateur " + utilisateur + ", soyez le bienvenu.</h3>"
						+ "<div><a href='Partie2Pas1'>Retour en arri�re</a></div>";
		
		resp.setContentType("text/html; charset=UTF-8");
		resp.getOutputStream().println(content);
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
