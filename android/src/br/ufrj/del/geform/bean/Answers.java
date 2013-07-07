package br.ufrj.del.geform.bean;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

public class Answers implements Parcelable {

	private Form m_reference;
	private SparseArray<Answer> m_answers;

	/**
	 * 
	 * @param reference
	 */
	public Answers( Form reference ) {
		setReference( reference );
		setAnswers( new SparseArray<Answer>() );
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

	/**
	 * 
	 * @param answers the answers to set
	 */
	private void setAnswers( SparseArray<Answer> answers ) {
		m_answers = answers;
	}

	/**
	 * 
	 * @return the answers
	 */
	public SparseArray<Answer> getAnswers() {
		return m_answers;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Answer get( int key ) {
		return m_answers.get( key, new Answer() );
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void put( int key, Answer value ) {
		m_answers.put( key, value );
	}

	/**
	 * 
	 * @param key
	 */
	public void delete( int key ) {
		m_answers.delete( key );
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
		out.writeParcelable( this.m_reference, flags );
		final Bundle bundle = new Bundle( Answer.class.getClassLoader() );
		bundle.putSparseParcelableArray( "answers", this.m_answers );
		out.writeBundle( bundle );
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
 	 * Constructs a new Answer instance from a {@link Parcel}.
	 * @param in the Parcel
	 */
	private Answers( Parcel in ) {
		m_reference = in.readParcelable( Form.class.getClassLoader() );
		final Bundle bundle = in.readBundle();
		bundle.setClassLoader( Answer.class.getClassLoader() );
		m_answers = bundle.getSparseParcelableArray( "answers" );
	}

}
