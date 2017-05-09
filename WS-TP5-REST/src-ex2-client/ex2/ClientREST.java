package ex2;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

public class ClientREST {
	
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget target = client.target("http://localhost:8080/WSREST-tp5-ex2");
		
		MediaType mediaType = null;
		WebTarget testTarget = null;
		Invocation.Builder invocationBuilder = null;
		Response response = null;
		
		
		// Test text/plain
		mediaType = MediaType.TEXT_PLAIN_TYPE;
		System.out.println("Test " + mediaType);
		
		testTarget = target.path("salut");
		invocationBuilder = testTarget.request(mediaType);
		response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
		
		
		// Test text/xml
		mediaType = MediaType.TEXT_XML_TYPE;
		System.out.println("Test " + mediaType);
		
		testTarget = target.path("hello");
		invocationBuilder = testTarget.request(mediaType);
		response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
				
		// Test text/html
		mediaType = MediaType.TEXT_HTML_TYPE;
		System.out.println("Test " + mediaType);
		
		testTarget = target.path("ola");
		invocationBuilder = testTarget.request(mediaType);
		response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
		
	}

}
