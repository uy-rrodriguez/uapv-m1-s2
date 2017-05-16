package ex11;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

public class ClientREST {
	
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget target = client.target("http://localhost:8080/WSREST-tp6-ex11/rest");
		
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget targetCible = null;
		
		
		// Params
		String paramNom = args[0];
				
		// Test Supprimer Contact Cookie
		System.out.println("Test Supprimer contact cookie");
		targetCible = target.path("supp").path(paramNom);
		System.out.println(targetCible);
		
		invocationBuilder = targetCible.request();
		response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response);
				
		// -----------------------------
				
		// Test Dernier Contact Supprime
		System.out.println("Test Dernier contact supprime");
		targetCible = target.path("dernier");
		System.out.println(targetCible);
		
		invocationBuilder = targetCible.request();
		response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response);
		System.out.println(response.readEntity(String.class));
		
		// -----------------------------
				
		// Test Lister cookies
		System.out.println("Test Lister cookies");
		targetCible = target.path("listCookies");
		System.out.println(targetCible);
		
		invocationBuilder = targetCible.request();
		response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response);
		System.out.println(response.readEntity(String.class));
	}

}
