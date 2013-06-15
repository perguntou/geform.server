package br.ufrj.del.geform.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import br.ufrj.del.geform.Constants;
import br.ufrj.del.geform.R;
import br.ufrj.del.geform.bean.Form;
import br.ufrj.del.geform.database.DatabaseHelper;
import br.ufrj.del.geform.net.DownloadTask;
import br.ufrj.del.geform.xml.FormXmlPull;

/**
 *
 */
public class ListFormsFragment extends ListFragment {

	/**
	 * 
	 */
	public interface ListFormsListener {
		/**
		 * 
		 * @param form the selected form
		 */
		public void onFormSelected( Form form );

		/**
		 * 
		 * @param form
		 */
		public void onFormDownloaded( Form form );
	}

	ListFormsListener m_listener;
	SimpleCursorAdapter m_adapter;
	MenuItem m_menuItem;

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach( Activity activity ) {
		super.onAttach( activity );

		try {
			m_listener = (ListFormsListener) activity;
		} catch( ClassCastException e ) {
			throw new ClassCastException( activity.toString() + " must implement ListFormsListener" );
		}

		m_adapter = new SimpleCursorAdapter(
								getActivity().getBaseContext(),
								android.R.layout.simple_list_item_1,
								DatabaseHelper.getInstance( getActivity() ).fetchAllForms(),
								new String[] { DatabaseHelper.COLUMN_TITLE },
								new int[] { android.R.id.text1 },
								CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER );

		setListAdapter( m_adapter );

		setHasOptionsMenu( true );
	}

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		return inflater.inflate( R.layout.list_forms, container );
	}

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	public void onListItemClick( ListView listView, View view, int position, long id ) {
		super.onListItemClick( listView, view, position, id );

		Form form = null;
		try {
			long identifier = listView.getItemIdAtPosition( position );
			form = FormXmlPull.parse( new FileInputStream( getActivity().getDir( "forms", FragmentActivity.MODE_PRIVATE ) + File.separator + Long.toString( identifier ) + Constants.extension ) );
			form.setId( identifier );
		} catch( FileNotFoundException e ) {
			Log.e( "FormParse", e.getMessage() );
			e.printStackTrace();
		} catch( XmlPullParserException e ) {
			Log.e( "FormParse", e.getMessage() );
			e.printStackTrace();
		} catch( IOException e ) {
			Log.e( "FormParse", e.getMessage() );
			e.printStackTrace();
		}

		m_listener.onFormSelected( form );
	}

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateOptionsMenu(android.view.Menu, android.view.MenuInflater)
	 */
	@Override
	public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {
		m_menuItem = menu.add( R.string.menu_fromURL );
		m_menuItem.setIcon( android.R.drawable.stat_sys_download );
		super.onCreateOptionsMenu( menu, inflater );
	}

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		boolean result = false;

		Form form = null;
		if( item.getItemId() == m_menuItem.getItemId() ) {
			try {
				//TODO get the URL to download the form from user input.
				form = new DownloadTask().execute( "http://dl.dropbox.com/u/50275577/sample.gef" ).get();
				if( form != null ) {
					result = true;
					m_listener.onFormDownloaded( form );
				} else {
					Toast.makeText( getActivity().getBaseContext(), getString( R.string.message_download_error ), Toast.LENGTH_LONG ).show();
				}
			} catch( InterruptedException e ) {
				Log.e( "Download", e.getMessage() );
			} catch( ExecutionException e ) {
				Log.e( "Download", e.getMessage() );
			}
		}

		return result;
	}

}
