package ex3;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/salut")
public class HelloWorldService {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getSalut() {
		return "Salut Monde !";
	}
	
	@GET
	@Path("{user}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getSalut(@PathParam("user") String user) {
		return "Salut " + user + " !";
	}
}
