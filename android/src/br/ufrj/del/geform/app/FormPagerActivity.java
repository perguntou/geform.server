package br.ufrj.del.geform.app;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseBooleanArray;
import android.widget.ListView;
import br.ufrj.del.geform.R;
import br.ufrj.del.geform.bean.Answers;
import br.ufrj.del.geform.bean.Form;

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

		m_pagerAdapter = new FormPagerAdapter( getSupportFragmentManager() );

		m_viewPager = (ViewPager) findViewById( R.id.pager );
		m_viewPager.setAdapter( m_pagerAdapter );

		m_viewPager.setCurrentItem( intent.getIntExtra( "position", 0 ) );
	}

	public class FormPagerAdapter extends FragmentStatePagerAdapter {

		FormPagerAdapter( FragmentManager manager ) {
			super( manager );
		}

		@Override
		public Fragment getItem( int index ) {
			final Form form = m_answers.getReference();
			@SuppressWarnings("unchecked")
			ArrayList<String> answer = (ArrayList<String>) m_answers.get( index, new ArrayList<String>() );

			Fragment fragment = new ItemFragment() {
				@Override
				public void onPause() {
					switch( m_item.getType() ) {
					case TEXT:
						if( m_answer.get(0).equals("") ) {
							m_answer.clear();
						}
						break;
					case SINGLE_CHOICE:
					case MULTIPLE_CHOICE:
						m_answer.clear();
						SparseBooleanArray checkedItems = ((ListView) m_input).getCheckedItemPositions();
						for( int i = 0; i < checkedItems.size(); i++ ) {
							if( checkedItems.valueAt(i) ) {
								m_answer.add( String.valueOf( checkedItems.keyAt( i ) ) );
							}
						}
						break;
					default:
					}
					if( !m_answer.isEmpty() ) {
						m_answers.put( m_position, m_answer );
					} else {
						m_answers.delete( m_position );
					}
					super.onPause();
				}

			};

			Bundle args = new Bundle();
			args.putInt( ItemFragment.ARG_POSITION, index );
			args.putStringArrayList( ItemFragment.ARG_ANSWER, answer );
			args.putParcelable( ItemFragment.ARG_ITEM, form.get(index) );
			fragment.setArguments( args );
			return fragment;
		}

		@Override
		public int getCount() {
			final Form form = m_answers.getReference();
			return form.size();
		}

		@Override
		public CharSequence getPageTitle( int position ) {
			return R.string.label_question + String.valueOf( position + 1 );
		}
	}

}
