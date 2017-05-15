package ex6;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

public class ClientREST {
	
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget target = client.target("http://localhost:8080/WSREST-tp6-ex6/rest");
		WebTarget contactTarget = target.path("contact");
		
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		
		// Params
		String paramNom = args[0];
		String paramNumero = args[1];
		
		// Test POST Contact
		System.out.println("Test POST Contact");
		Contact c = new Contact(paramNom, paramNumero);
		
		invocationBuilder = contactTarget.request(MediaType.APPLICATION_XML);
		response = invocationBuilder.post(
				Entity.entity(c, MediaType.APPLICATION_XML));
		URI contactURI = response.getLocation();// readEntity(URI.class);
		
		System.out.println(response.getStatus());
		System.out.println(contactURI);
		
		
		// Test GET Contact
		System.out.println("Test GET Contact");
		
		invocationBuilder = client.target(contactURI).request();
		response = invocationBuilder.get();
		
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
		
	}

}
