package br.ufrj.del.geform.app;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import br.ufrj.del.geform.R;
import br.ufrj.del.geform.bean.Form;
import br.ufrj.del.geform.bean.Item;

/**
 *
 */
public class EditFormActivity extends ListActivity {
	final int ADD_ITEM_REQUEST_CODE = 0;
	final int EDIT_ITEM_REQUEST_CODE = 1;

	private Form m_form;

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_edit_form );

		m_form = getIntent().getParcelableExtra( "form" );

		((EditText) findViewById( R.id.form_name )).setText( m_form.title() );

		((Button) findViewById( R.id.button_new_item )).setOnClickListener( new OnClickListener() {
			@Override
			public void onClick( View view ) {
				Intent intent = new Intent( EditFormActivity.this, EditItemActivity.class );
				intent.putExtra( "item", new Item() );
				intent.putExtra( "requestPosition", m_form.size() + 1 );
				startActivityForResult( intent, ADD_ITEM_REQUEST_CODE );
			}
		} );

		setListAdapter( new ItemAdapter( this, android.R.layout.simple_list_item_1, m_form ) );

		((Button) findViewById( R.id.button_save )).setOnClickListener( new OnClickListener() {
			@Override
			public void onClick( View view ) {
				m_form.setTitle( ((EditText) findViewById( R.id.form_name )).getText().toString().trim() );

				if( !m_form.title().equals("") && m_form.size() > 0 ) {
					Intent intent = getIntent();
					intent.putExtra( "form", (Parcelable) m_form );
					setResult( Activity.RESULT_OK,  intent );
					finish();
				} else {
					Toast.makeText( EditFormActivity.this, R.string.message_form_error, Toast.LENGTH_LONG ).show();
				}
			}
		} );
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick( ListView listView, View view, int position, long id ) {
		super.onListItemClick( listView, view, position, id );

		Intent intent = new Intent( EditFormActivity.this, EditItemActivity.class );
		intent.putExtra( "item", (Item) listView.getItemAtPosition( position ) );
		intent.putExtra( "requestPosition", position + 1 );
		startActivityForResult( intent, EDIT_ITEM_REQUEST_CODE );
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult( int requestCode, int resultCode, Intent result ) {
		super.onActivityResult( requestCode, resultCode, result );

		if( resultCode == RESULT_OK ) {
			int resultPosition = checkBoundaries( result.getIntExtra( "resultPosition", m_form.size() ) - 1 ); 
			Item resultItem = (Item) result.getParcelableExtra( "item" );

			switch( requestCode ) {
			case ADD_ITEM_REQUEST_CODE:
				m_form.add( resultPosition, resultItem );
				break;
			case EDIT_ITEM_REQUEST_CODE:
				int requestPosition = result.getIntExtra( "requestPosition", m_form.size() ) - 1;
				if( requestPosition != resultPosition ) {
					if( resultPosition > requestPosition ) {
						resultPosition = Math.min( ++resultPosition, m_form.size() );
					} else {
						requestPosition = Math.min( ++requestPosition, m_form.size() );
					}
					m_form.add( resultPosition, resultItem );
					m_form.remove( requestPosition );
				} else {
					m_form.set( resultPosition, resultItem );
				}
				break;
			default:
				return;
			}
			((ItemAdapter) getListAdapter()).notifyDataSetChanged();
		}
	}

	/**
	 * Validates an index in the form. Limits the position to
	 * the form's boundaries, if out returns the last valid position.
	 * @param position the index
	 * @return a valid position
	 */
	private int checkBoundaries( int position ) {
		if( position < 0 || position > m_form.size() ) {
			Toast.makeText( getApplicationContext(), R.string.message_index_invalid, Toast.LENGTH_LONG ).show();
			position = m_form.size();
		}
		return position;
	}

}
