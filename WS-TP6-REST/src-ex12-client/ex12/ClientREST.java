package ex12;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

public class ClientREST {
	
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget target = client.target("http://localhost:8080/WSREST-tp6-ex12/rest");
		
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget targetCible = null;
		
		
		// Params
		String paramMethode = args[0];
				
		// Test Save contacts
		System.out.println("Test " + paramMethode + " contacts");
		targetCible = target.path(paramMethode);
		System.out.println(targetCible);
		
		invocationBuilder = targetCible.request();
		response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response);
		System.out.println(response.readEntity(String.class));
				
		// -----------------------------

	}

}
