package partie1;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletGetContextConfig extends HttpServlet {

	private void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String utilisateur = req.getParameter("utilisateur");
		if (utilisateur == null || utilisateur.trim().isEmpty()) {
			utilisateur = (String) this.getServletContext().getAttribute("DefaultUserName");
			
			if (utilisateur == null || utilisateur.trim().isEmpty()) {
				resp.getOutputStream().println("<h1 style='color: #FFEEEE'>Le nom d'utilisateur par d�faut n'est pas d�fini !</h1>");
			}
		}
		
		resp.getOutputStream().println("<h3>Utilisateur " + utilisateur + ", soyez le bienvenu.</h3>");
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
