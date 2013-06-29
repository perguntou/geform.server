package br.ufrj.del.geform.app;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import br.ufrj.del.geform.R;
import br.ufrj.del.geform.bean.Answers;
import br.ufrj.del.geform.bean.Form;

/**
 *
 */
public class FillFormActivity extends ListActivity {

	private Answers m_answers;

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@SuppressLint("NewApi")
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_fill_form );

		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
			getActionBar().setDisplayHomeAsUpEnabled( true );
		}

		m_answers = getIntent().getParcelableExtra( "answers" );

		final Form form = m_answers.getReference();
		((TextView) findViewById( R.id.text_form_name )).setText( form.title() );

		setListAdapter( new ItemAdapter( this, android.R.layout.simple_list_item_1, form ) );
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		getMenuInflater().inflate( R.menu.menu_fill_form, menu );
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		switch( item.getItemId() ) {
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.menu_commit:

			break;
		default:
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick( ListView listView, View view, int position, long id ) {
		super.onListItemClick( listView, view, position, id );

		Intent intent = new Intent( FillFormActivity.this, FormPagerActivity.class );
		intent.putExtra( "answers", m_answers );
		intent.putExtra( "position", position );
		startActivity( intent );
	}

}
