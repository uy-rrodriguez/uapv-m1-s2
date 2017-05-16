package ex7;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class AnnuaireService {

	private static final String HTML_HEAD = "<!DOCTYPE html><html><body>";
	private static final String HTML_FOOT = "</body></html>";
	
	private static Carnet carnet = new Carnet();
	
	@GET
	@Path("carnet")
	@Produces(MediaType.TEXT_HTML)
	public String getCarnet() {
		StringBuffer buff = new StringBuffer(HTML_HEAD);
		
		if (carnet.getContacts().size() <= 0)
			buff.append("Liste vide");
		else
			buff.append(carnet);
		
		buff.append(HTML_FOOT);
		return buff.toString();
	}
	
	@GET
	@Path("contact/{nom}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getNumeroByNom(
			@PathParam("nom") String nom) {
		
		if (carnet.getContacts().containsKey(nom))
			return carnet.getContacts().get(nom).getNumero();
		
		return "Inconnu";
	}
	
	@POST
	@Path("contact")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response postContact(
			@FormParam("nom") String nom,
			@FormParam("numero") String numero) {
		
		if (!carnet.getContacts().containsKey(nom)) {
			carnet.getContacts().put(nom, new Contact(nom, numero));
		}
		
		Response r = Response.created(URI.create("contact/" + nom)).build();
		return r;
	}
	
	@POST
	@Path("contact")
    @Consumes(MediaType.APPLICATION_XML)
	public Response postContact(Contact contact) {
		if (!carnet.getContacts().containsKey(contact.getNom())) {
			carnet.getContacts().put(contact.getNom(), contact);
		}
		
		Response r = Response.created(URI.create("contact/" + contact.getNom())).build();
		return r;
	}
	
	@PUT
	@Path("contact")
    @Consumes(MediaType.APPLICATION_XML)
	public Response putContact(Contact contact) {
		Response r;
		
		if (carnet.getContacts().containsKey(contact.getNom())) {
			carnet.getContacts().get(contact.getNom()).setNumero(contact.getNumero());
			r = Response.created(URI.create("contact/" + contact.getNom())).build();
		}
		else {
			r = Response.status(204).build();
		}
		
		return r;
	}
	
}
