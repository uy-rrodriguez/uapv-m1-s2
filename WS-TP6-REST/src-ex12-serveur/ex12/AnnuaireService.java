package ex12;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.util.Map.Entry;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Path("/")
public class AnnuaireService {

	private static final String HTML_HEAD = "<!DOCTYPE html><html><body>";
	private static final String HTML_FOOT = "</body></html>";
	
	private static Carnet carnet = new Carnet();
	
	private static final String COOKIE_DERNIER_SUPP = "DERNIER_CONTACT_SUPP";
	
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
	public String getNumeroByNom(
			@PathParam("nom") String nom)
					throws ContactNotFoundException {
		
		if (carnet.getContacts().containsKey(nom))
			return carnet.getContacts().get(nom).getNumero();
		
		throw new ContactNotFoundException();
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
			r = Response.noContent().status(204).build();
		}
		
		return r;
	}
	
	@DELETE
	@Path("contact/{nom}")
	public Response deleteContact(
			@PathParam("nom") String nom) {
		
		Response r;
		
		if (carnet.getContacts().containsKey(nom)) {
			carnet.getContacts().remove(nom);
			r = Response.ok().build();
		}
		else {
			r = Response.status(404).build();
		}
		
		return r;
	}
	
	@GET
	@Path("supp/{nom}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteContactCookie(
			@PathParam("nom") String nom) {
		
		Response r;
		
		if (carnet.getContacts().containsKey(nom)) {
			carnet.getContacts().remove(nom);
			r = Response
					.ok("Contact supprime")
					.cookie(new NewCookie(COOKIE_DERNIER_SUPP, nom, "/WSREST-tp6-ex11", "", "", -1, false))
					.build();
		}
		else {
			r = Response.status(404).build();
		}
		
		return r;
	}
	
	@GET
	@Path("dernier")
	public String dernierContactCookie(
			@CookieParam(COOKIE_DERNIER_SUPP) String cookie) {
		
		if (cookie != null) {
			return cookie; 
		}
		
		return "Cookie inexistante";
	}
	
	@GET
	@Path("listCookies")
	public String listCookies(
			@Context HttpHeaders headers) {
		
		StringBuilder str = new StringBuilder();
		for (Entry<String, Cookie> entryCookie : headers.getCookies().entrySet()) {
			//str.append(entryCookie.getValue().toString());
			str.append(entryCookie.getValue().getName() + ":" + entryCookie.getValue().getValue());
			str.append(";");
		}
		return str.toString();
	}
	
	@GET
	@Path("save")
	public Response saveContacts() {
		try {
			File file = new File("C:\\Users\\Ricci\\Desktop\\carnet.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Carnet.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			//jaxbMarshaller.setAdapter(new MapContactAdapter());
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			
			// Test file
			jaxbMarshaller.marshal(carnet, file);
			
			// Test console
			jaxbMarshaller.marshal(carnet, System.out);
			
			// Test string
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			jaxbMarshaller.marshal(carnet, out);
			String strOut = new String(out.toByteArray());
			
			return Response
					.ok("Contacts sauvegardes. \n" + strOut)
					.build();
		}
		catch (Exception e) {
			e.printStackTrace();
			
			return Response
					.serverError()
					.entity("Erreur lors du stockage des contacts. " + e.getMessage())
					.build();
		}
	}
	
	@GET
	@Path("load")
	public Response loadContacts() {
		try {
			File file = new File("C:\\Users\\Ricci\\Desktop\\carnet.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Carnet.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
			// Test file
			carnet = (Carnet) jaxbUnmarshaller.unmarshal(file);
			
			return Response
					.ok("Contacts importes : " + carnet.getContacts().size())
					.build();
		}
		catch (Exception e) {
			e.printStackTrace();
			
			return Response
					.serverError()
					.entity("Erreur lors de l'importation des contacts. " + e.getMessage())
					.build();
		}
	}
	
}
