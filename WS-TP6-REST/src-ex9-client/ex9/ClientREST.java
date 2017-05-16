package ex9;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

public class ClientREST {
	
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget target = client.target("http://localhost:8080/WSREST-tp6-ex9/rest");
		WebTarget contactTarget = target.path("contact");
		
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget targetCible = null;
		
		
		// Test Contact by Nom EXISTANT
		System.out.println("Test Contact by Nom EXISTANT");
		targetCible = contactTarget.path("Pepito");
		System.out.println(targetCible);
		
		invocationBuilder = targetCible.request();
		response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
				
		// -----------------------------

		// Params
		String paramNom = args[0];
				
		// Test Contact by Nom INEXISTANT
		System.out.println("Test Contact by Nom INEXISTANT");
		targetCible = contactTarget.path(paramNom);
		System.out.println(targetCible);
		
		invocationBuilder = targetCible.request();
		response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
	}

}
