package ex2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class HelloWorldService {

	@GET
	@Path("salut")
	@Produces(MediaType.TEXT_PLAIN)
	public String getSalutText() {
		return "Salut Monde !";
	}
	
	@GET
	@Path("hello")
	@Produces(MediaType.TEXT_XML)
	public String getSalutXML() {
		return "<response>Salut Monde !</response>";
	}
	
	@GET
	@Path("ola")
	@Produces(MediaType.TEXT_HTML)
	public String getSalutHTML() {
		return "<!DOCTYPE html><html><body><h1>Salut Monde !</h1></body></html>";
	}
}
