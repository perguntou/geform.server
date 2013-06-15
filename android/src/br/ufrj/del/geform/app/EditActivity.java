package br.ufrj.del.geform.app;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.ufrj.del.geform.Constants;
import br.ufrj.del.geform.R;
import br.ufrj.del.geform.app.ListFormsFragment.ListFormsListener;
import br.ufrj.del.geform.bean.Form;
import br.ufrj.del.geform.database.DatabaseHelper;
import br.ufrj.del.geform.xml.FormXmlPull;

/**
 * 
 */
public class EditActivity extends FragmentActivity implements ListFormsListener {
	final int NEW_FORM_REQUEST_CODE = 0;
	final int EDIT_FORM_REQUEST_CODE = 1;

	ListFormsFragment m_fragment;

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_edit );

		((Button) findViewById( R.id.button_new_form )).setOnClickListener( new OnClickListener() {
			@Override
			public void onClick( View view ) {
				Intent intent = new Intent( getApplicationContext(), EditFormActivity.class );
				intent.putExtra( "form", (Parcelable) new Form() );
				startActivityForResult( intent, NEW_FORM_REQUEST_CODE );
			}
		} );

		m_fragment = (ListFormsFragment) getSupportFragmentManager().findFragmentById( R.id.fragment_list_forms );
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent result ) {
		super.onActivityResult( requestCode, resultCode, result );

		if( resultCode == RESULT_OK ) {
			switch( requestCode ) {
			//TODO: Edit a form should replace the existing one.
			//Changes on a form modifies the answers filled out??
			case NEW_FORM_REQUEST_CODE:
			case EDIT_FORM_REQUEST_CODE:
				Form form = (Form) result.getParcelableExtra( "form" );
				Long id = DatabaseHelper.getInstance( this.getApplicationContext() ).insertForm( form.title() );
				try {
					FormXmlPull.serialize( form , new FileOutputStream( getDir( "forms", FragmentActivity.MODE_PRIVATE ) + File.separator + String.valueOf( id ) + Constants.extension ) );
				} catch( IllegalArgumentException e ) {
					Log.e( "FormSerialize", e.getMessage() );
					e.printStackTrace();
				} catch( IllegalStateException e ) {
					Log.e( "FormSerialize", e.getMessage() );
					e.printStackTrace();
				} catch( FileNotFoundException e ) {
					Log.e( "FormSerialize", e.getMessage() );
					e.printStackTrace();
				} catch( XmlPullParserException e ) {
					Log.e( "FormSerialize", e.getMessage() );
					e.printStackTrace();
				} catch( IOException e ) {
					Log.e( "FormSerialize", e.getMessage() );
					e.printStackTrace();
				}

				m_fragment.m_adapter.changeCursor( DatabaseHelper.getInstance( this ).fetchAllForms() );
				break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see br.ufrj.del.geform.ListFormsFragment.ListFormsListener#onFormSelected(br.ufrj.softwaresmartphone.util.Form)
	 */
	@Override
	public void onFormSelected( Form form ) {
		Intent intent = new Intent( getApplicationContext(), EditFormActivity.class );
		intent.putExtra( "form", (Parcelable) form.clone() );

		startActivityForResult( intent, EDIT_FORM_REQUEST_CODE );
	}

	/*
	 * (non-Javadoc)
	 * @see br.ufrj.del.geform.ListFormsFragment.ListFormsListener#onFormDownloaded(br.ufrj.softwaresmartphone.util.Form)
	 */
	@Override
	public void onFormDownloaded(Form form) { this.onFormSelected( form ); }

}
