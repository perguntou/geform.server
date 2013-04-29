package br.ufrj.softwaresmartphone.util;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 */
public class Item implements Parcelable {

	private String m_question;
	private Options m_options;

	/**
	 * Constructs a new Item instance with no question
	 * and no options.
	 */
	public Item() {
		this( new String() );
	}

	/**
	 * Constructs a new Item instance with the specified question
	 * and no options.
	 * @param question the item's question.
	 */
	public Item( String question ) {
		this( question, new Options() );
	}

	/**
	 * Constructs a new Item instance with the specified title
	 * and options.
	 * @param title the form title.
	 * @param options the form's options.
	 */
	public Item( String question, Options options ) {
		setQuestion( question );
		setOptions( options );
	}

	/**
	 * Returns the Item's question.
	 * @return the question.
	 */
	public String getQuestion( ) { return m_question; }

	/**
	 * Sets the item's question.
	 * @param question the item's question.
	 */
	public void setQuestion( String question )	{ m_question = question; }

	/**
	 * Returns the item's options.
	 * @return the item's options.
	 * @see Options
	 */
	public Options getOptions( ) { return m_options; }

	/**
	 * Sets the item's options.
	 * @param options the item's options.
	 * @see Options
	 */
	public void setOptions( Options options ) { m_options = options; }

	/**
	 * Returns if this Item has options.
	 * @return true if this Item has no options, false otherwise.
	 */
	public boolean hasOptions( ) { return !m_options.isEmpty(); }

	/*
	 * (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel( Parcel out, int flags ) {
		out.writeString( this.getQuestion() );
		out.writeParcelable( this.getOptions(), flags );
	}

	public static final Parcelable.Creator<Item> CREATOR
	= new Parcelable.Creator<Item>() {
		/*
		 * (non-Javadoc)
		 * @see android.os.Parcelable.Creator#createFromParcel(android.os.Parcel)
		 */
		public Item createFromParcel( Parcel in ) {
			return new Item( in );
		}

		/*
		 * (non-Javadoc)
		 * @see android.os.Parcelable.Creator#newArray(int)
		 */
		@Override
		public Item[] newArray(int size) {
			return new Item[size];
		}
	};

	/**
	 * Constructs a new Item instance from a {@link Parcel}.
	 * @param in the Parcel
	 */
	private Item( Parcel in ) {
		this.m_question = in.readString();
		this.m_options = in.readParcelable( Options.class.getClassLoader() );
	}

}
