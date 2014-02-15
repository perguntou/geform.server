package br.ufrj.del.geform.rest;

import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import br.ufrj.del.geform.bean.CollectionBean;
import br.ufrj.del.geform.bean.FormBean;
import br.ufrj.del.geform.db.DatabaseManager;
import br.ufrj.del.geform.export.Exporter;
import br.ufrj.del.geform.report.Analyzer;
import br.ufrj.del.geform.report.Report;


@Path("/forms")
public class FormResources {

	private static final Logger logger = Logger.getLogger( FormResources.class );

	DatabaseManager dbManager = new DatabaseManager();

	@POST
	@Consumes( {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON} )
	public final String create( FormBean form ) {
		dbManager.insertNewForm( form );
		final Long id = form.getId();
		return id.toString();
	}

	@GET
	@Produces( {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON} )
	@Path("{id}")
	public final Response get( @PathParam("id") long id, @Context HttpServletRequest httpRequest ) {
		final FormBean form = dbManager.selectFormBean( id );
		final ResponseBuilder builder = form != null ? Response.ok(form) : Response.status( Status.NOT_FOUND );
		final Response response = builder.build();
		return response;
	}

	@POST
	@Consumes( {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON} )
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

	@GET
	@Produces( MediaType.APPLICATION_JSON )
	@Path("{id}/report")
	public final Response generateReport( @PathParam("id") long id, @Context HttpServletRequest httpRequest ) {
		final FormBean form = dbManager.selectFormBean( id );
		if( form == null ) {
			return Response.status( Status.NOT_FOUND ).build();
		}

		final List<CollectionBean> collections = dbManager.selectCollectionBeanList( id );
		if( collections.isEmpty() ) {
			return Response.noContent().build();
		}

		form.setCollections( collections );
		final Analyzer analyzer = new Analyzer( form );
		final Report report = analyzer.process();
		return Response.ok( report ).build();
	}

	@GET
//	@Produces( "text/tab-separated-values" )
	@Path("{id}/export")
	public final Response export(  @PathParam("id") long id, @Context HttpServletRequest httpRequest, @Context HttpServletResponse httpResponse ) {
		try {
			final FormBean form = dbManager.selectFormBean( id );
			if( form == null ) {
				return Response.status( Status.NOT_FOUND ).build();
			}

			final List<CollectionBean> collections = dbManager.selectCollectionBeanList( id );
			if( collections.isEmpty() ) {
				return Response.noContent().build();
			}

			form.setCollections( collections );
			final String date = String.format( "%tF_%<tH-%<tM-%<tS", Calendar.getInstance().getTime() );
			final String filename = String.format( "collections_form%s_%s", id, date );
			final String contentDisposition = String.format( "attachment; filename=\"%s.tsv\"", filename );
			httpResponse.addHeader( "Content-Disposition", contentDisposition );
			httpResponse.addHeader( "Cache-Control", "no-cache" );

			final ServletOutputStream out = httpResponse.getOutputStream();
			final OutputStreamWriter writer = new OutputStreamWriter( out, Charset.forName("UTF-16LE") );
			Exporter.writeTsv( writer, form );
			return Response.ok().build();
		} catch( final Throwable e ) {
			logger.error( "Error exporting collections", e );
			return Response.serverError().build();
		}
	}

}
