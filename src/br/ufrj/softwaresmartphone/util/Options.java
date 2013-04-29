package br.ufrj.softwaresmartphone.util;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class extends ArrayList{@literal <String>}.
 * @see ArrayList
 */
public class Options extends ArrayList<String> implements Parcelable {

	/*
	 * default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * choice types allowed for Options.
	 */
	public enum ChoiceType {
		single,
		multiple
	}

	/*
	 * the choice type for Options.
	 */
	private ChoiceType m_choice;

	/**
	 * Constructs a new Options instance with zero initial capacity
	 * and the default choice type {@code ChoiceType.SINGLE}.
	 * @see ChoiceType
	 */
	public Options() {
		super();
		setChoiceType( ChoiceType.single );
	}

	/**
	 * Returns the choice type of this Options.
	 * @return the choice type.
	 * @see ChoiceType
	 */
	public ChoiceType getChoiceType() { return m_choice; }

	/**
	 * Sets the choice type of this Options.
	 * @param choice the choice type.
	 * @see ChoiceType
	 */
	public void setChoiceType( ChoiceType choice ) { m_choice = choice; }

	/**
	 * Returns a new Options with the same elements, the same size,
	 * the same capacity and the same choice type as this Options.
	 * @return a shallow copy of this Options.
	 */
	@Override
	public Object clone() {
		Options cp = (Options) super.clone();
		cp.setChoiceType( this.getChoiceType() );
		return cp;
	}

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
		out.writeString( this.getChoiceType().toString() );
		out.writeSerializable( this );
	}

	public static final Parcelable.Creator<Options> CREATOR
	= new Parcelable.Creator<Options>() {
		/*
		 * (non-Javadoc)
		 * @see android.os.Parcelable.Creator#createFromParcel(android.os.Parcel)
		 */
		public Options createFromParcel( Parcel in ) {
			return new Options( in );
		}

		/*
		 * (non-Javadoc)
		 * @see android.os.Parcelable.Creator#newArray(int)
		 */
		@Override
		public Options[] newArray(int size) {
			return new Options[size];
		}
	};

	/**
	 * Constructs a new Options instance from a {@link Parcel}.
	 * @param in the Parcel
	 */
	@SuppressWarnings("unchecked")
	private Options( Parcel in ) {
		this.m_choice = ChoiceType.valueOf( in.readString() );
		this.addAll( (ArrayList<String>) in.readSerializable() );
	}

}
