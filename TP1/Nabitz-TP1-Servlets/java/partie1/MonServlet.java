package partie1;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MonServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nom = req.getParameter("nom");
		if (nom == null || nom.trim().equals(""))
			nom = "World";
		
		resp.getOutputStream().println("<h3>Hello " + nom + " !</h3> <h1>:D</h1>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String utilisateur = req.getParameter("utilisateur");
		if (utilisateur == null || utilisateur.trim().equals(""))
			utilisateur = "non renseigné";
		
		resp.getOutputStream().println("<h3>Bienvenu " + utilisateur + "</h3>");
	}

	@Override
	public void init() throws ServletException {
		//super.init();
		System.out.println("init");
	}

	@Override
	public void destroy() {
		//super.destroy();
		System.out.println("destroy");
	}

}
