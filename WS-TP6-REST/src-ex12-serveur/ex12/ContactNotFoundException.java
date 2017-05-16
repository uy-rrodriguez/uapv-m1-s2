package ex12;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ContactNotFoundException extends WebApplicationException {

	private static final long serialVersionUID = -8927286196443560989L;
	
	@Override
	public Response getResponse() {
		return Response
				.status(Response.Status.NOT_FOUND)
				.entity("Ce contact est inconnu")
				.build();
	}
}
