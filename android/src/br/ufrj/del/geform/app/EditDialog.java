package br.ufrj.del.geform.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import br.ufrj.del.geform.R;

/**
 *
 */
public class EditDialog extends DialogFragment {

	public static final String ARGUMENT_TITLE = "title";
	public static final String ARGUMENT_VALUE = "value";

	private EditDialogListener m_listener;
	private EditText m_input;

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach( Activity activity ) {
		super.onAttach( activity );

		try {
			m_listener = (EditDialogListener) ((FragmentActivity) activity).getSupportFragmentManager().findFragmentById( R.id.fragment_edit_options );
		} catch( ClassCastException e ) {
			throw new ClassCastException( activity.toString() + " must implement EditDialogListener" );
		}
	}

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog( Bundle savedInstanceState ) {

		final AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );

		final CharSequence title;
		final CharSequence value;
		final Bundle args = getArguments();

		final CharSequence defaultTitle = getString( R.string.dialog_edit );
		final CharSequence defaultValue = "";

		if( args != null ) {
			final CharSequence candidateTitle = args.getString( ARGUMENT_TITLE );  
			title = candidateTitle == null ? defaultTitle :  candidateTitle;

			final CharSequence candidateValue = args.getString( ARGUMENT_VALUE );
			value = candidateTitle == null ? defaultValue :  candidateValue;
		} else {
			title = defaultTitle;
			value = defaultValue;
		}

		builder.setTitle( title );

		m_input = new EditText( getActivity().getApplicationContext() );
		m_input.setText( value );
		builder.setView( m_input );

		builder.setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick( DialogInterface dialog, int which ) {
				m_listener.onDialogPositiveClick( EditDialog.this );
			}
		} );

		builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick( DialogInterface dialog, int which ) {
				m_listener.onDialogNegativeClick( EditDialog.this );
				dialog.dismiss();
			}
		} );
		return builder.create();
	}

	/**
	 * Returns the value entered
	 * @return the input value
	 */
	public String getInputValue() {
		return m_input.getText().toString().trim();
	}

}
