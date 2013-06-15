package br.ufrj.del.geform.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.ufrj.del.geform.R;

public class MainActivity extends Activity {

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		((Button) findViewById( R.id.button_edit )).setOnClickListener( new OnClickListener() {
			@Override
			public void onClick( View view ) {
				Intent intent = new Intent( getApplicationContext(), EditActivity.class );
				startActivity( intent );
			}
        });

		((Button) findViewById( R.id.button_fill )).setOnClickListener( new OnClickListener() {
			@Override
			public void onClick( View view ) {
				Intent intent = new Intent( getApplicationContext(), FillActivity.class );
				startActivity( intent );
			}
		});

		findViewById( R.id.button_help ).setEnabled( false );
	}

}
