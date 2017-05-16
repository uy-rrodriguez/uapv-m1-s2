package ex13;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public class ClientREST {
	
	private static final String URL_BASE = "http://localhost:8080/WSREST-tp6-ex13/rest";
	
	public static void main(String[] args) {
		// Users
		HttpAuthenticationFeature authAdmin = HttpAuthenticationFeature.basicBuilder()
			    //.nonPreemptive()
			    .credentials("admin", "admin")
			    .build();

		HttpAuthenticationFeature authUser1 = HttpAuthenticationFeature.basicBuilder()
			    //.nonPreemptive()
			    .credentials("user1", "user1")
			    .build();
	
		
		// Un client pour chaque type d'authentification
		// Sans user
		Client clientSansUser = ClientBuilder.newClient(new ClientConfig());
		WebTarget targetSansUser = clientSansUser.target(URL_BASE);
		
		// Admin
		ClientConfig configAdmin = new ClientConfig();
		configAdmin.register(authAdmin);
		Client clientAdmin = ClientBuilder.newClient(configAdmin);
		WebTarget targetAdmin = clientAdmin.target(URL_BASE);
		
		// User1
		ClientConfig configUser1 = new ClientConfig();
		configUser1.register(authUser1);
		Client clientUser1 = ClientBuilder.newClient(configUser1);
		WebTarget targetUser1 = clientUser1.target(URL_BASE);
		
		
		// Objets pour lancer les tests
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		WebTarget targetCible = null;
		
		
		// Params
		String paramNom = args[0];
		
		
		// --------------------------------------------------
		// Tests
		// --------------------------------------------------
		
		// Test DELETE Contact SANS USER
		System.out.println();
		System.out.println("Test DELETE Contact SANS USER");
		targetCible = targetSansUser.path("contact").path(paramNom);
		System.out.println(targetCible);
		
		invocationBuilder = targetCible.request();
		response = invocationBuilder.delete();
		System.out.println(response.getStatus());
		System.out.println(response);
		System.out.println(response.readEntity(String.class));
		
		// -----------------------------
		
		// Test DELETE Contact AVEC user1
		System.out.println();
		System.out.println("Test DELETE Contact AVEC user1");
		targetCible = targetUser1.path("contact").path(paramNom);
		System.out.println(targetCible);
		
		invocationBuilder = targetCible.request();
		response = invocationBuilder.delete();
		System.out.println(response.getStatus());
		System.out.println(response);
		System.out.println(response.readEntity(String.class));
				
		// -----------------------------

		// Test DELETE Contact AVEC admin
		System.out.println();
		System.out.println("Test DELETE Contact AVEC admin");
		targetCible = targetAdmin.path("contact").path(paramNom);
		System.out.println(targetCible);
		
		invocationBuilder = targetCible.request();
		response = invocationBuilder.delete();
		System.out.println(response.getStatus());
		System.out.println(response);
		System.out.println(response.readEntity(String.class));
				
		// -----------------------------
				
		// Test GET Carnet SANS USER
		System.out.println();
		System.out.println("Test GET Carnet SANS USER");
		targetCible = targetSansUser.path("carnet");
		System.out.println(targetCible);
		
		invocationBuilder = targetCible.request();
		response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response);
		System.out.println(response.readEntity(String.class));
		
		// -----------------------------
				
		// Test GET Carnet AVEC user1
		System.out.println();
		System.out.println("Test GET Carnet AVEC user1");
		targetCible = targetUser1.path("carnet");
		System.out.println(targetCible);
		
		invocationBuilder = targetCible.request();
		response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response);
		System.out.println(response.readEntity(String.class));
		
		//-----------------------------
				
		//Test GET Carnet AVEC admin
		System.out.println();
		System.out.println("Test GET Carnet AVEC admin");
		targetCible = targetAdmin.path("carnet");
		System.out.println(targetCible);
		
		invocationBuilder = targetCible.request();
		response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response);
		System.out.println(response.readEntity(String.class));

	}

}
