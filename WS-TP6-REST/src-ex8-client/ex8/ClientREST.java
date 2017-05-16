package ex8;

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
		WebTarget target = client.target("http://localhost:8080/WSREST-tp6-ex8/rest");
		WebTarget contactTarget = target.path("contact");
		
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget targetCible = null;
		
		// Test DELETE Contact EXISTANT
		System.out.println("Test DELETE Contact EXISTANT");
		Contact c = new Contact("Pepito", "");
		
		targetCible = contactTarget.path(c.getNom());
		System.out.println(contactTarget.path(c.getNom()));
		
		invocationBuilder = targetCible.request();
		response = invocationBuilder.delete();
		
		System.out.println(response.getStatus());
		System.out.println(response);
		
		// -----------------------------

		// Params
		//String paramNom = args[0];
		//String paramNumero = args[1];
			
		// Test DELETE Contact EXISTANT
		System.out.println("Test DELETE Contact INEXISTANT");
		response = invocationBuilder.delete();
		
		System.out.println(response.getStatus());
		System.out.println(response);
	}

}
