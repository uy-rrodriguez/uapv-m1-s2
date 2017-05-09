package ex1;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

public class ClientREST {
	
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient(/*new ClientConfig()*/);
		WebTarget target = client.target("http://localhost:8080/WSREST-tp5-ex1");
		
		WebTarget salutTarget = target.path("salut");
		Invocation.Builder salutInvocationBuilder = salutTarget.request(MediaType.TEXT_PLAIN_TYPE);
		
		Response response = salutInvocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
	}

}
