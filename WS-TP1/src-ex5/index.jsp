<%@page import="ex5.*"%>
<%
	String user = request.getParameter("user");
	if (user != null && ! user.isEmpty()) {
		IMonSOAP service = new MonSOAP();
		out.println("<h1>" + service.bonjour(user) + "</h1>");
	}
%>

<form action="index.jsp" method="POST">
	<label for="user">Utilisateur :</label>
	<input type="text" id="user" name="user" />
	<input type="submit" value="Envoyer" />
</form>