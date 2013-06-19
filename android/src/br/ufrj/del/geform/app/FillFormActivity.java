package br.ufrj.del.geform.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import br.ufrj.del.geform.R;
import br.ufrj.del.geform.bean.Form;

/**
 *
 */
public class FillFormActivity extends ListActivity {
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
		setContentView( R.layout.activity_fill_form );

		m_form = getIntent().getParcelableExtra( "form" );
		
		((TextView) findViewById( R.id.text_form_name )).setText( m_form.title() );


		setListAdapter( new ItemAdapter( this, android.R.layout.simple_list_item_1, m_form ) );

		((Button) findViewById( R.id.button_save )).setOnClickListener( new OnClickListener() {
			@Override
			public void onClick( View view ) {

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

		Intent intent = new Intent( FillFormActivity.this, FormPagerActivity.class );
		intent.putExtra( "form", (Parcelable) m_form );
		intent.putExtra( "position", position );
		startActivity( intent );
	}

}
