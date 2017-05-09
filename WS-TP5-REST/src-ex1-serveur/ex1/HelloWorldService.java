package ex1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/salut")
public class HelloWorldService {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getSalut() {
		return "Salut Monde !";
	}
}
