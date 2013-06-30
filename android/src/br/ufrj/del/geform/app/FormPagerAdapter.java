/**
 * 
 */
package br.ufrj.del.geform.app;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseBooleanArray;
import android.widget.ListView;
import br.ufrj.del.geform.R;
import br.ufrj.del.geform.bean.Answers;
import br.ufrj.del.geform.bean.Form;

/**
 *
 */
public class FormPagerAdapter extends FragmentStatePagerAdapter {

	private Answers m_answers;

	FormPagerAdapter( FragmentManager manager, Answers answers ) {
		super( manager );
		m_answers = answers;
	}

	@Override
	public Fragment getItem( int index ) {
		final Form form = m_answers.getReference();
		@SuppressWarnings("unchecked")
		final ArrayList<String> answer = (ArrayList<String>) m_answers.get( index, new ArrayList<String>() );

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
