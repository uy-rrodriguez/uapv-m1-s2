package ex4;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
public class HelloWorldService {

	@GET
	@Path("salut")
	@Produces(MediaType.TEXT_PLAIN)
	public String getSalut(
			@QueryParam("user") String user) {
		
		return "Salut " + user + " !";
	}
	
	@GET
	@Path("salutDefault")
	@Produces(MediaType.TEXT_PLAIN)
	public String getSalutDefault(
			@DefaultValue("DefaultUser") @QueryParam("user") String user) {
		
		return "Salut " + user + " !";
	}
}
