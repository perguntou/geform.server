package br.ufrj.del.geform.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.xmlpull.v1.XmlPullParserException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.ufrj.del.geform.Constants;
import br.ufrj.del.geform.R;
import br.ufrj.del.geform.bean.Answers;
import br.ufrj.del.geform.bean.Form;
import br.ufrj.del.geform.database.DatabaseHelper;
import br.ufrj.del.geform.database.FormsTable;
import br.ufrj.del.geform.net.DownloadTask;
import br.ufrj.del.geform.xml.FormXmlPull;


public class FormsActivity extends ListActivity {

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );

		setContentView( R.layout.activity_forms );

		final View header = getLayoutInflater().inflate( R.layout.header_forms, null );
		getListView().addHeaderView( header );

		final ListAdapter adapter = new SimpleCursorAdapter(
				getBaseContext(),
				android.R.layout.simple_list_item_1,
				DatabaseHelper.getInstance( this.getBaseContext() ).fetchAllForms(),
				new String[] { FormsTable.COLUMN_TITLE },
				new int[] { android.R.id.text1 },
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER );

		setListAdapter( adapter );

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
			final File directory = getDir( "forms", FragmentActivity.MODE_PRIVATE );
			final String path = directory + File.separator + Long.toString( identifier ) + Constants.extension;
			form = FormXmlPull.parse( new FileInputStream( path ) );
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

		final Answers answers = new Answers( form );
		Intent intent = new Intent( getApplicationContext(), FillFormActivity.class );
		intent.putExtra( "answers", answers );

		startActivity( intent );
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		getMenuInflater().inflate( R.menu.menu_forms, menu );
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		switch( item.getItemId() ) {
		case R.id.menu_form_add:
			Intent intent = new Intent( getApplicationContext(), EditFormActivity.class );
			intent.putExtra( "form", (Parcelable) new Form() );
			startActivityForResult( intent, 0 );
			break;
		case R.id.menu_form_download:
			try {
				//TODO get the URL to download the form from user input.
				final Form form = new DownloadTask().execute( "http://dl.dropbox.com/u/50275577/sample.gef" ).get();
				if( form == null ) {
					Toast.makeText( getBaseContext(), getString( R.string.message_download_error ), Toast.LENGTH_LONG ).show();
					return false;
				}
				insertAndUpdate( form );
			} catch( InterruptedException e ) {
				Log.e( "Download", e.getMessage() );
			} catch( ExecutionException e ) {
				Log.e( "Download", e.getMessage() );
			}
			break;
		default:
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent result ) {
		super.onActivityResult( requestCode, resultCode, result );

		if( resultCode == RESULT_OK ) {
			Form form = (Form) result.getParcelableExtra( "form" );
			insertAndUpdate( form );
		}
	}

	private void insertAndUpdate( Form form ) {
		Long id = DatabaseHelper.getInstance( this.getApplicationContext() ).insertForm( form.title() );
		try {
			FormXmlPull.serialize( form , new FileOutputStream( getDir( "forms", FragmentActivity.MODE_PRIVATE ) + File.separator + String.valueOf( id ) + Constants.extension ) );
		} catch (IllegalArgumentException e) {
			Log.e( "FillActivity", e.getMessage() );
		} catch (IllegalStateException e) {
			Log.e( "FillActivity", e.getMessage() );
		} catch (FileNotFoundException e) {
			Log.e( "FillActivity", e.getMessage() );
		} catch (XmlPullParserException e) {
			Log.e( "FillActivity", e.getMessage() );
		} catch (IOException e) {
			Log.e( "FillActivity", e.getMessage() );
		}

		final SimpleCursorAdapter adapter = (SimpleCursorAdapter) getListAdapter();
		adapter.changeCursor( DatabaseHelper.getInstance( this ).fetchAllForms() );
	}

}
