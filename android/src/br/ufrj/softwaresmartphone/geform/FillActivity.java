package br.ufrj.softwaresmartphone.geform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import br.ufrj.softwaresmartphone.geform.ListFormsFragment.ListFormsListener;
import br.ufrj.softwaresmartphone.util.Form;
import br.ufrj.softwaresmartphone.util.FormXmlPull;

public class FillActivity extends FragmentActivity implements ListFormsListener {

	ListFormsFragment m_fragment;

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_fill );

		m_fragment = (ListFormsFragment) getSupportFragmentManager().findFragmentById( R.id.fragment_list_forms );
	}

	/*
	 * (non-Javadoc)
	 * @see br.ufrj.softwaresmartphone.geform.ListFormsFragment.ListFormsListener#onFormSelected(br.ufrj.softwaresmartphone.util.Form)
	 */
	@Override
	public void onFormSelected( Form form ) {
		Intent intent = new Intent( getApplicationContext(), FillFormActivity.class );
		intent.putExtra( "form", (Parcelable) form );

		Answers.getInstance().clear();

		startActivity( intent );
	}

	@Override
	public void onFormDownloaded( Form form ) {
		Long id = GeFormDatabase.getInstance( this.getApplicationContext() ).insertForm( form.title() );
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

		m_fragment.m_adapter.changeCursor( GeFormDatabase.getInstance( this ).fetchAllForms() );

		this.onFormSelected( form );
	}

}
