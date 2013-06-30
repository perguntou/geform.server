package br.ufrj.del.geform.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import br.ufrj.del.geform.R;
import br.ufrj.del.geform.bean.Answers;

public class FormPagerActivity extends FragmentActivity {

	private FormPagerAdapter m_pagerAdapter;
	private ViewPager m_viewPager;
	private Answers m_answers;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_form_pager );

		final Intent intent = getIntent();
		m_answers = intent.getParcelableExtra( "answers" );

		m_pagerAdapter = new FormPagerAdapter( getSupportFragmentManager(), m_answers );

		m_viewPager = (ViewPager) findViewById( R.id.pager );
		m_viewPager.setAdapter( m_pagerAdapter );

		m_viewPager.setCurrentItem( intent.getIntExtra( "position", 0 ) );
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		getMenuInflater().inflate( R.menu.menu_form_pager, menu );
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		switch( item.getItemId() ) {
		case R.id.menu_items_list:
			final Intent intent = getIntent();
			intent.putExtra( "answers", m_answers );
			setResult( Activity.RESULT_OK,  intent );
			finish();
			break;
		default:
			return false;
		}
		return true;
	}

}
