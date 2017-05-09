package ex4;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

public class ClientREST {
	
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget target = client.target("http://localhost:8080/WSREST-tp5-ex4");
		WebTarget salutTarget = target.path("salut");
		WebTarget salutDefaultTarget = target.path("salutDefault");
		
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		
		
		// Test SANS default
		System.out.println("Test query parameters SANS default, AVEC valeurs");
		invocationBuilder = salutTarget.queryParam("user", "Pepito").request();
		response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
		
		System.out.println("Test query parameters SANS default, SANS donner de valeur");
		invocationBuilder = salutTarget.request();
		response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
		
		
		// Test AVEC default
		System.out.println("Test query parametres AVEC default, AVEC valeurs");
		invocationBuilder = salutDefaultTarget.queryParam("user", "Pepito").request();
		response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
		
		System.out.println("Test query parametres AVEC default, SANS donner de valeur");
		invocationBuilder = salutDefaultTarget.request();
		response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
	}

}
