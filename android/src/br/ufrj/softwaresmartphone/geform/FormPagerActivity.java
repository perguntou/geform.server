package br.ufrj.softwaresmartphone.geform;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import br.ufrj.softwaresmartphone.util.Form;
import br.ufrj.softwaresmartphone.util.Item;
import br.ufrj.softwaresmartphone.util.Type;

public class FormPagerActivity extends FragmentActivity {

	private FormPagerAdapter m_pagerAdapter;
	private ViewPager m_viewPager;
	private static Form m_form;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_form_pager );

		m_pagerAdapter = new FormPagerAdapter( getSupportFragmentManager() );

		m_form = getIntent().getParcelableExtra( "form" );

		m_viewPager = (ViewPager) findViewById( R.id.pager );
		m_viewPager.setAdapter( m_pagerAdapter );

		m_viewPager.setCurrentItem( getIntent().getIntExtra( "position", 0 ) );
	}

	public static class FormPagerAdapter extends FragmentStatePagerAdapter {

		FormPagerAdapter( FragmentManager manager ) {
			super( manager );
		}

		@Override
		public Fragment getItem( int index ) {
			Fragment fragment = new ItemFragment();
			Bundle args = new Bundle();
			args.putInt( ItemFragment.ARG_POSITION, index );
			args.putStringArrayList( ItemFragment.ARG_ANSWER, Answers.getInstance().get( index ) );
			args.putParcelable( ItemFragment.ARG_ITEM, m_form.get(index) );
			fragment.setArguments( args );
			return fragment;
		}

		@Override
		public int getCount() {
			return m_form.size();
		}

		@Override
		public CharSequence getPageTitle( int position ) {
			return R.string.label_question + String.valueOf( position + 1 );
		}
	}

	public static class ItemFragment extends Fragment {
		public static final String ARG_POSITION = "position";
		public static final String ARG_ITEM = "item";
		public static final String ARG_ANSWER = "answer";

		private View m_input;
		private int m_position;
		private Item m_item;
		private ArrayList<String> m_answer;

		@Override
		public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
			Bundle args = getArguments();
			Assert.assertTrue( !args.isEmpty() );

			Assert.assertTrue( args.containsKey( ItemFragment.ARG_POSITION ) );
			m_position = getArguments().getInt( ItemFragment.ARG_POSITION );

			Assert.assertTrue( args.containsKey( ItemFragment.ARG_ANSWER ) );
			m_answer = args.getStringArrayList( ItemFragment.ARG_ANSWER );
			if( m_answer == null ) {
				m_answer = new ArrayList<String>();
			}

			Assert.assertTrue( args.containsKey( ItemFragment.ARG_ITEM ) );
			m_item = (Item) args.getParcelable( ItemFragment.ARG_ITEM );

			Context context = inflater.getContext();
			LinearLayout rootView = new LinearLayout( context );
			rootView.setLayoutParams( new LayoutParams(
												ViewGroup.LayoutParams.MATCH_PARENT,
												ViewGroup.LayoutParams.WRAP_CONTENT) );
			rootView.setOrientation( LinearLayout.VERTICAL );

			TextView question = new TextView( context );
			rootView.addView( question );
			question.setText( m_item.getQuestion() );

			final Type type = m_item.getType();
			switch( type ) {
			case TEXT:
				final EditText tview = new EditText( context );
				tview.setLayoutParams( new LayoutParams(
												ViewGroup.LayoutParams.MATCH_PARENT,
												ViewGroup.LayoutParams.WRAP_CONTENT) );
				if( !m_answer.isEmpty() ) {
					Assert.assertTrue( m_answer.size() == 1 );
					tview.setText( m_answer.get(0) );
				} else {
					m_answer.add( new String() );
				}
				rootView.addView( tview );

				Button ok = new Button( context );
				ok.setText( android.R.string.ok );
				ok.setOnClickListener( new OnClickListener() {
					@Override
					public void onClick( View v ) {
						Assert.assertTrue( m_answer.size() == 1 );
						String value = tview.getText().toString().trim();
						m_answer.set( 0, value );
					}
				} );
				rootView.addView( ok );
				break;
			case SINGLE_CHOICE:
				m_input = new ListView( context );
				List<String> optionsSingle = m_item.getOptions();
				((ListView) m_input).setAdapter( new ArrayAdapter<String>( context, android.R.layout.simple_list_item_single_choice, optionsSingle ) );
				((ListView) m_input).setChoiceMode( ListView.CHOICE_MODE_SINGLE );
				for( int i = 0; i < m_answer.size(); i++ ) {
					((ListView) m_input).setItemChecked( Integer.parseInt(m_answer.get(i)), true );
				}
				rootView.addView( m_input );
				break;
			case MULTIPLE_CHOICE:
				m_input = new ListView( context );
				List<String> optionsMultiple = m_item.getOptions();
				((ListView) m_input).setAdapter( new ArrayAdapter<String>( context, android.R.layout.simple_list_item_multiple_choice, optionsMultiple ) );
				((ListView) m_input).setChoiceMode( ListView.CHOICE_MODE_MULTIPLE );
				for( int i = 0; i < m_answer.size(); i++ ) {
					((ListView) m_input).setItemChecked( Integer.parseInt(m_answer.get(i)), true );
				}
				rootView.addView( m_input );
				break;
				default:
			}

			return rootView;
		}

		@Override
		public void onPause() {
			if( m_item.hasOptions() ) {
				m_answer.clear();
				SparseBooleanArray checkedItems = ((ListView) m_input).getCheckedItemPositions();
				for( int i = 0; i < checkedItems.size(); i++ ) {
					if( checkedItems.valueAt(i) ) {
						m_answer.add( String.valueOf( checkedItems.keyAt( i ) ) );
					}
				}
			} else {
				if( m_answer.get(0).equals("") ) {
					m_answer.clear();
				}
			}
			if( !m_answer.isEmpty() ) {
				Answers.getInstance().put( m_position, m_answer );
			} else {
				Answers.getInstance().delete( m_position );
			}
			super.onPause();
		}
	}

}
