package br.ufrj.del.geform.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.ufrj.del.geform.bean.CollectionBean;
import br.ufrj.del.geform.bean.FormBean;
import br.ufrj.del.geform.db.DatabaseManager;

@Path("/forms")
public class FormResources {

	DatabaseManager dbManager = new DatabaseManager();

	@POST
	@Consumes( MediaType.APPLICATION_XML )
	public final String create( FormBean form ) {
		dbManager.insertNewForm( form );
		final Long id = form.getId();
		return id.toString();
	}

	@GET
	@Produces( {MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON} )
	@Path("{id}")
	public final FormBean get( @PathParam("id") long id ) {
		final FormBean form = dbManager.selectFormBean( id );
		return form;
	}

	@POST
	@Consumes( MediaType.APPLICATION_XML )
	@Path("{id}")
	public final String update( List<CollectionBean> collections, @PathParam("id") final long targetId ) {
		Integer received = 0;
		final FormBean form = dbManager.selectFormBean( targetId );
		final List<CollectionBean> collectionList = new ArrayList<CollectionBean>( collections.size() );
		for( final CollectionBean collection : collections ) {
			final long currentId = collection.getId();
			if( currentId != targetId ) {
				final String message = String.format( "Form reference (id=%s) for this collection is different from target Form (id=%s)", currentId, targetId );
				throw new IllegalStateException( message );
			}
			collectionList.add( collection );
			++received;
		}
		form.setCollections( collectionList );
		dbManager.insertCollections( form );
		return received.toString();
	}

}
