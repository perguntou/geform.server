package br.ufrj.del.geform.rest;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class TestFormGet {

	private final static String uri = "http://localhost:8080/GeFormWS";
	private final static String pattern = "rest";
	private final static String resource = "forms";

	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		ClientConfig config = new DefaultClientConfig();
//		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create( config );
		WebResource service = client.resource( getBaseURI() );

		final String id = "1";
		final WebResource path = service.path( pattern ).path( resource ).path(id);

		final Builder acceptTextPlain = path.accept( MediaType.TEXT_PLAIN );
		final ClientResponse textPlain = acceptTextPlain.get( ClientResponse.class );
		System.out.println( textPlain.toString() );

		final Builder acceptAppXml = path.accept( MediaType.APPLICATION_XML );
		final String appXml = acceptAppXml.get( String.class );
		System.out.println( appXml );

//		final Builder acceptAppJson = path.accept( MediaType.APPLICATION_JSON );
//		final String appJson = acceptAppJson.get( String.class );
//		System.out.println( appJson );

		final Builder acceptTextXml = path.accept( MediaType.TEXT_XML );
		final String textXml = acceptTextXml.get( String.class );
		System.out.println( textXml );
	}

	public static URI getBaseURI() {
		return UriBuilder.fromUri( uri ).build();
	}

}
