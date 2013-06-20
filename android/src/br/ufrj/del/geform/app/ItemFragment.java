/**
 * 
 */
package br.ufrj.del.geform.app;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import br.ufrj.del.geform.bean.Item;
import br.ufrj.del.geform.bean.Type;

/**
 *
 */
public class ItemFragment extends Fragment {
		public static final String ARG_POSITION = "position";
		public static final String ARG_ITEM = "item";
		public static final String ARG_ANSWER = "answer";

		protected View m_input;
		protected int m_position;
		protected Item m_item;
		protected ArrayList<String> m_answer;

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
}
