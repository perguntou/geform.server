package br.ufrj.del.geform.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "geform.db";
	public static final int DATABASE_VERSION = 1;

	private static DatabaseHelper m_instance;

	/**
	 * 
	 * @param context to use to open or create the database.
	 */
	private DatabaseHelper( Context context ) {
		super( context, DATABASE_NAME, null, DATABASE_VERSION );
	}

	/**
	 * Return the instance of a DatabaseHelper
	 * that can be used to access the application's database.
	 * @param context to use to open or create the database.
	 * @return the DatabaseHelper instance.
	 */
	public static DatabaseHelper getInstance( Context context) {
		if( m_instance == null ) {
			m_instance = new DatabaseHelper( context.getApplicationContext() );
		}
		return m_instance;
	}

	/*
	 * (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate( SQLiteDatabase database ) {
		database.execSQL( FormsTable.CREATE );
	}

	/*
	 * (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
		Log.w( "database", "Upgrading database from version " + oldVersion	+ " to " + newVersion + ", which will destroy all old data" );
		db.execSQL( FormsTable.DROP );
		onCreate( db );
	}

	/**
	 * Inserts a form into the application's database.
	 * @param title the form title.
	 * @return the ID of the newly inserted form, or -1 if an error occurred.
	 * @throws SQLException
	 */
	public long insertForm ( String title ) throws SQLException {
		ContentValues content = new ContentValues();
		content.putNull( FormsTable._ID );
		content.put( FormsTable.COLUMN_TITLE, title );

		SQLiteDatabase db = m_instance.getWritableDatabase();
		db.beginTransaction();
		long id = -1;
		try {
			id = db.insertOrThrow( FormsTable.TABLE_FORMS, null, content );
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		return id; 
	}

	/**
	 * Gets the form title associated to specified ID.
	 * @param id the form ID.
	 * @return the form title.
	 */
	public String formTitle( Long id ) {
		SQLiteDatabase db = m_instance.getReadableDatabase();
		Cursor cursor = db.query( FormsTable.TABLE_FORMS, new String[] { FormsTable.COLUMN_TITLE }, FormsTable._ID + " = ?", new String[] { String.valueOf( id ) }, null, null, null );
		cursor.moveToFirst();

		return cursor.getString( cursor.getColumnIndexOrThrow( FormsTable.COLUMN_TITLE ) );
	}

	/**
	 * 
	 * @return
	 */
	public Cursor fetchAllForms() {
		SQLiteDatabase db = m_instance.getReadableDatabase();
		Cursor cursor = db.query( FormsTable.TABLE_FORMS, new String[] { FormsTable._ID, FormsTable.COLUMN_TITLE }, null, null, null, null, null, null );
		cursor.moveToFirst();

		return cursor;
	}

}
