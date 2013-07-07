/**
 * 
 */
package br.ufrj.del.geform.app;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.ListView;
import br.ufrj.del.geform.R;
import br.ufrj.del.geform.bean.Answer;
import br.ufrj.del.geform.bean.Answers;
import br.ufrj.del.geform.bean.Form;

/**
 *
 */
public class FormPagerAdapter extends FragmentStatePagerAdapter {

	private Answers m_answers;

	/**
	 * 
	 * @param manager
	 * @param answers
	 */
	FormPagerAdapter( FragmentManager manager, Answers answers ) {
		super( manager );
		m_answers = answers;
	}

	/**
	 * @return the answers
	 */
	public Answers getAnswers() {
		return m_answers;
	}

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.FragmentStatePagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem( int index ) {
		final Form form = m_answers.getReference();
		final Answer answer = m_answers.get( index );

		final Fragment fragment = new ItemFragment() {
			/*
			 * (non-Javadoc)
			 * @see br.ufrj.del.geform.app.ItemFragment#onListItemClick(android.widget.ListView, android.view.View, int, long)
			 */
			@Override
			public void onListItemClick( ListView listView, View view, int position, long id ) {
				super.onListItemClick( listView, view, position, id );
				updateAnswersFromItemFragment( this );
			}
			/*
			 * (non-Javadoc)
			 * @see br.ufrj.del.geform.app.ItemFragment#onDialogPositiveClick(android.support.v4.app.DialogFragment)
			 */
			@Override
			public void onDialogPositiveClick( DialogFragment dialog ) {
				super.onDialogPositiveClick( dialog );
				updateAnswersFromItemFragment( this );
			}
		};

		Bundle args = new Bundle();
		args.putInt( ItemFragment.ARG_POSITION, index );
		args.putParcelable( ItemFragment.ARG_ITEM, form.get(index) );
		args.putParcelable( ItemFragment.ARG_ANSWER, answer );

		fragment.setArguments( args );
		return fragment;
	}

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		final Form form = m_answers.getReference();
		return form.size();
	}

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
	 */
	@Override
	public CharSequence getPageTitle( int position ) {
		return R.string.label_question + String.valueOf( position + 1 );
	}

	/**
	 * 
	 * @param fragment
	 */
	private void updateAnswersFromItemFragment( ItemFragment fragment ) {
		Bundle args = fragment.getArguments();
		final int position = args.getInt( ItemFragment.ARG_POSITION );
		final Answer answer = fragment.getAnswer();
		if( !answer.isEmpty() ) {
			m_answers.put( position, answer );
		} else {
			m_answers.delete( position );
		}
	}

}
