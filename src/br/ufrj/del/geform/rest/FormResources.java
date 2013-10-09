package br.ufrj.del.geform.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.ufrj.del.geform.bean.FormBean;
import br.ufrj.del.geform.db.DatabaseManager;

@Path("/forms")
public class FormResources {

	DatabaseManager dbManager = new DatabaseManager();

	@POST
	@Consumes( MediaType.APPLICATION_XML )
	public final String create( FormBean form ) {
		dbManager.insertNewForm( form );

		final String result = String.format( "id = %s", form.getId() );
		return result;
	}

	@GET
	@Produces( {MediaType.TEXT_XML, MediaType.APPLICATION_XML} )
	@Path("{id}")
	public final FormBean get( @PathParam("id") long id ) {
		final FormBean form = dbManager.selectFormBean( id );
		return form;
	}

}
