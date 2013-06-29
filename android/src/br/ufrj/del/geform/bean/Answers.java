package br.ufrj.del.geform.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

public class Answers extends SparseArray< Object > implements Parcelable {

	private Form m_reference;

	public Answers() {
		super();
	}

	public Answers( Form reference ) {
		super();
		setReference( reference );
	}

	/**
	 * @return the reference
	 */
	public Form getReference() {
		return m_reference;
	}

	/**
	 * @param reference the reference to set
	 */
	public void setReference( Form reference ) {
		this.m_reference = reference;
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
		out.writeSparseArray( this );
		out.writeParcelable( this.m_reference, flags );
	}

	public static final Parcelable.Creator<Answers> CREATOR
	= new Parcelable.Creator<Answers>() {
		/*
		 * (non-Javadoc)
		 * @see android.os.Parcelable.Creator#createFromParcel(android.os.Parcel)
		 */
		public Answers createFromParcel( Parcel in ) {
			return new Answers( in );
		}

		/*
		 * (non-Javadoc)
		 * @see android.os.Parcelable.Creator#newArray(int)
		 */
		@Override
		public Answers[] newArray( int size ) {
			return new Answers[ size ];
		}
	};

	/**
 	 * Constructs a new Answers instance from a {@link Parcel}.
	 * @param in the Parcel
	 */
	private Answers( Parcel in ) {
		in.readSparseArray( this.getClass().getClassLoader() );
		this.m_reference = in.readParcelable( Form.class.getClassLoader() );
	}

}
