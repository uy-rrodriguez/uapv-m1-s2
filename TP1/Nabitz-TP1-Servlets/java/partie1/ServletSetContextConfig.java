package partie1;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletSetContextConfig extends HttpServlet {

	private void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String utilisateur = req.getParameter("utilisateur");
		this.getServletContext().setAttribute("DefaultUserName", utilisateur);
		
		resp.getOutputStream().println("<h3>Configuration modifiée. <a href='index.html'>Retour à l'acceuil</a></h3>");
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
