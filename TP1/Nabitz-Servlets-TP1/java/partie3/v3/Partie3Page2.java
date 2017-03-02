package partie3.v3;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Partie3Page2 extends HttpServlet {

	private void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String style = "<link rel='stylesheet' type='text/css' href='../css/style.css'>";
		String header = "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Partie 3, Page bidon 2</title>" + style + "</head><body>";
		String footer = "</body></html>";
				
		String contenu = "<div id='container'>"
						+ "		<h3>Page bidon 2</h3>"
						+ "		<a href='Partie3Page1'>Page précédente</a>"
						+ "		<a href='Partie3Pas3'>Voir les informations dans la session</a>"
						+ "</div>";
		
		resp.setContentType("text/html; charset=UTF-8");
		resp.getOutputStream().println(header + contenu + footer);
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
