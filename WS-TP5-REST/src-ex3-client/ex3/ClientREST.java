package ex3;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

public class ClientREST {
	
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget target = client.target("http://localhost:8080/WSREST-tp5-ex3");
		WebTarget salutTarget = target.path("salut");

		Invocation.Builder invocationBuilder = null;
		Response response = null;
		
		
		// Test SANS parametres
		System.out.println("Test SANS parametres");
		invocationBuilder = salutTarget.request();
		response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
		
		
		// Test AVEC parametres
		System.out.println("Test AVEC parametres");
		invocationBuilder = salutTarget.path("Pepito").request();
		response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
	}

}
