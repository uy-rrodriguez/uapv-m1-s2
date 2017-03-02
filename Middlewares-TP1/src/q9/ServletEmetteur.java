package q9;

import java.io.IOException;
import java.io.PrintStream;

import javax.jms.QueueSender;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletEmetteur extends HttpServlet {
	
	private static final long serialVersionUID = 4441776791094964623L;

	protected void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String message = req.getParameter("msg");
		String destinataire = req.getParameter("destinataire");
		
		ConnectionJMS jms = new ConnectionJMS();
		
		try {
			jms.connecter();
			
			resp.getOutputStream().println("Connection créée");
			
			QueueSender sender = jms.getSession().createSender(jms.getQueue());
			TextMessage msg = jms.getSession().createTextMessage();
			msg.setText(message + " (pour " + destinataire + ")");
			
			// Ajout d'une propriété particulière
			if (destinataire != null)
				msg.setStringProperty("destinataire", destinataire);
			
			sender.send(msg);
			resp.getOutputStream().println("Message envoyé à " + destinataire + "!");
		}
		catch(Exception e) {
			resp.getOutputStream().println("<pre>");
			e.printStackTrace(new PrintStream(resp.getOutputStream()));
			resp.getOutputStream().println("</pre>");
		}
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
