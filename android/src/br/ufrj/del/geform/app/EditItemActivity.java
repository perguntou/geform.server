package br.ufrj.del.geform.app;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import br.ufrj.del.geform.R;
import br.ufrj.del.geform.bean.Item;
import br.ufrj.del.geform.bean.Type;

/**
 * 
 */
public class EditItemActivity extends FragmentActivity {

	private Item m_item;

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );

		m_item = getIntent().getParcelableExtra( "item" );

		setContentView( R.layout.activity_edit_item );

		final EditText questionEditText = ((EditText) findViewById( R.id.item_question ));
		final EditText positionEditText = ((EditText) findViewById( R.id.item_position ));
		final EditOptionsFragment editOptionsFragment = (EditOptionsFragment) getSupportFragmentManager().findFragmentById( R.id.fragment_edit_options );
		final Spinner itemTypeSpinner = ((Spinner) findViewById( R.id.item_type )); 

		positionEditText.setText( String.valueOf( getIntent().getIntExtra( "requestPosition", 1 ) ) );
		questionEditText.setText( m_item.getQuestion() );
		final Type type = m_item.getType();
		itemTypeSpinner.setSelection( type.ordinal() );

		itemTypeSpinner.setOnItemSelectedListener( new OnItemSelectedListener() {
			@Override
			public void onItemSelected( AdapterView<?> adapter, View view, int position, long id ) {
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction = Type.values()[position] != Type.TEXT ? transaction.show( editOptionsFragment ) : transaction.hide( editOptionsFragment );
				transaction.commit();
			}

			@Override
			public void onNothingSelected( AdapterView<?> adapter ) {}
		} );

		((Button) findViewById( R.id.button_item_ok )).setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				m_item.setQuestion( questionEditText.getText().toString().trim() );

				if( !(m_item.getQuestion().equals("")) ) {
					List<String> options = editOptionsFragment.getOptions();
					final int typeOrdinal = itemTypeSpinner.getSelectedItemPosition();
					final Type type = Type.values()[typeOrdinal];
					m_item.setType( type );
					if( editOptionsFragment.isVisible() ) {
						m_item.setOptions( options );
					} else {
						m_item.getOptions().clear();
					}
					Intent intent = getIntent();
					intent.putExtra( "resultPosition", Integer.parseInt( positionEditText.getText().toString() ) );
					intent.putExtra( "item", m_item );
					setResult( Activity.RESULT_OK,  intent );
					finish();
				}
			}
		} );

	}

	/**
	 * Returns the edited item
	 * @return the item
	 */
	public Item getItem() {
		return m_item;
	}

}
