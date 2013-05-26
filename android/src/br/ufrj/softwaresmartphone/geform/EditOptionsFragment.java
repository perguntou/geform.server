package br.ufrj.softwaresmartphone.geform;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import br.ufrj.softwaresmartphone.util.Options;

/**
 *
 */
public class EditOptionsFragment extends ListFragment implements EditDialog.EditDialogListener {

	private Options m_options;

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		Options options = ((EditItemActivity) getActivity()).getItem().getOptions();
		m_options = (options != null) ? options : new Options();

		View view = inflater.inflate( R.layout.edit_options, container, false );

		setListAdapter( new ArrayAdapter<String>( view.getContext(), android.R.layout.simple_list_item_1, m_options ) );

		view.findViewById( R.id.button_new_option ).setOnClickListener( new OnClickListener() {
			@Override
			public void onClick( View view ) {
				editOptionDialog( new String(), m_options.size() );
			}
		} );

		return view;
	}

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	public void onListItemClick( ListView listView, View view, int position, long id ) {
		editOptionDialog( getListView().getItemAtPosition( position ).toString(), position );
	}

	/*
	 * (non-Javadoc)
	 * @see br.ufrj.softwaresmartphone.geform.EditDialog.EditDialogListener#onDialogPositiveClick(android.support.v4.app.DialogFragment)
	 */
	@Override
	public void onDialogPositiveClick( DialogFragment dialog ) {
		String inputValue = ((EditDialog) dialog).getInputValue();
		int position = dialog.getArguments().getInt( "index" );
		if( !inputValue.equals("") ) {
			if( position < m_options.size() ) {
				m_options.set( position, inputValue );
			} else {
				m_options.add( inputValue );
			}
		}
		((BaseAdapter) getListAdapter()).notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * @see br.ufrj.softwaresmartphone.geform.EditDialog.EditDialogListener#onDialogNegativeClick(android.support.v4.app.DialogFragment)
	 */
	@Override
	public void onDialogNegativeClick( DialogFragment dialog ) {}

	/**
	 * Creates an edit dialog to change an option's value
	 * @param option the initial value
	 * @param position the index of the option
	 * @see EditDialog
	 */
	public void editOptionDialog( String option, int position ) {
		DialogFragment newFragment = new EditDialog();
		Bundle args = new Bundle();
		args.putString( "value", option );
		args.putInt( "index", position );
		newFragment.setArguments( args );  
		newFragment.show( getFragmentManager(), "option" );
	}

	/**
	 * Returns the edited options
	 * @return the options
	 */
	public Options getOptions() {
		return m_options;
	}

}
