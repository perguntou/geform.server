package br.ufrj.softwaresmartphone.geform;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;

/**
 *
 */
public class EditDialog extends DialogFragment {

	/**
	 * 
	 */
	public interface EditDialogListener {
		/**
		 * 
		 * @param dialog
		 */
		public void onDialogPositiveClick( DialogFragment dialog );

		/**
		 * 
		 * @param dialog
		 */
		public void onDialogNegativeClick( DialogFragment dialog );
	}

	EditDialogListener m_listener;
	private String m_inputValue = new String("");

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
		AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );

		builder.setTitle( R.string.dialog_edit );
		
		final EditText input = new EditText( getActivity().getApplicationContext() );
		input.setText( new StringBuilder( getArguments().getString( "value" ) ) );
		builder.setView( input );

		builder.setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick( DialogInterface dialog, int which ) {
				m_inputValue = input.getText().toString().trim();
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
		return m_inputValue;
	}
}
