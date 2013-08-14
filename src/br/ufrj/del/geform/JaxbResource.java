package br.ufrj.del.geform;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/jaxb")
public class JaxbResource {

	@GET
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML } )
	public Jaxb getXML() {
		Jaxb jaxb = new Jaxb();
		jaxb.setTitle( "Jaxb Test" );
		jaxb.setDescription( "Resource for XML and JSON APPLICATION" );
		jaxb.setCreator( "GeFormWS" );

		return jaxb;
	}

}
