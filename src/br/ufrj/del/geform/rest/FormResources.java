package br.ufrj.del.geform.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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

}
