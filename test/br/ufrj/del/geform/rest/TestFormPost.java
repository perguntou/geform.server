package br.ufrj.del.geform.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.UriBuilder;

import br.ufrj.del.geform.bean.FormBean;
import br.ufrj.del.geform.bean.ItemBean;
import br.ufrj.del.geform.bean.OptionBean;
import br.ufrj.del.geform.bean.TypeBean;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class TestFormPost {

	private final static String uri = "http://localhost:8080/GeForm";
	private final static String pattern = "rest";
	private final static String resource = "forms";

	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create( config );
		WebResource service = client.resource( getBaseURI() );

		final FormBean form = new FormBean();
		form.setTitle( "Form sample" );
		form.setCreator("JAX-RS");
		form.setDescription( "Sample from server." );
		final List<ItemBean> items = new ArrayList<ItemBean>();
		final ItemBean itemText = new ItemBean( "Text Item" );
		itemText.setType( TypeBean.TEXT );
		items.add(itemText);
		final ItemBean itemSingle = new ItemBean( "Single Choice Item" );
		itemSingle.setType( TypeBean.SINGLE_CHOICE );
		final List<OptionBean> singleOptions;
		singleOptions = ( itemSingle.getOptions() != null ) ? itemSingle.getOptions() : new ArrayList<OptionBean>();
		singleOptions.add( new OptionBean("Yes") );
		singleOptions.add( new OptionBean("No") );
		itemSingle.setOptions(singleOptions);
		items.add(itemSingle);
		final ItemBean itemMultiple = new ItemBean( "Multiple Choice Item" );
		itemMultiple.setType( TypeBean.MULTIPLE_CHOICE );
		final List<OptionBean> multipleOptions;
		multipleOptions = ( itemMultiple.getOptions() != null ) ? itemMultiple.getOptions() : new ArrayList<OptionBean>();
		multipleOptions.add( new OptionBean("Option 1") );
		multipleOptions.add( new OptionBean("Option 2") );
		multipleOptions.add( new OptionBean("Option 3") );
		itemMultiple.setOptions(multipleOptions);
		items.add(itemMultiple);
		final Calendar date = Calendar.getInstance();
		form.setTimestamp( date.getTime() );
		form.setItems(items);

		final WebResource path = service.path( pattern ).path(resource);
		ClientResponse responseInt = path.post( ClientResponse.class, form );
		String entityInt = responseInt.getEntity( String.class );
		System.out.println (responseInt);
		System.out.println (entityInt);
	}

	public static URI getBaseURI() {
		return UriBuilder.fromUri( uri ).build();
	}

}
