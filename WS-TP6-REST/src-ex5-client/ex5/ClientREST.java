package ex5;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

public class ClientREST {
	
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget target = client.target("http://localhost:8080/WSREST-tp6-ex5/rest");
		WebTarget contactTarget = target.path("contact");
		
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		
		// Params
		String paramNom = args[0];
		String paramNumero = args[1];
		
		// Test POST Contact
		System.out.println("Test POST Contact");
		
		Form form = new Form();
		form.param("nom", paramNom);
		form.param("numero", paramNumero);
		
		invocationBuilder = contactTarget.request();
		response = invocationBuilder.post(
				Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		String contactURI = response.readEntity(String.class);
		
		System.out.println(response.getStatus());
		System.out.println(contactURI);
		
		
		// Test GET Contact
		System.out.println("Test GET Contact");
		
		invocationBuilder = target.path(contactURI).request();
		response = invocationBuilder.get();
		
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
		
	}

}
