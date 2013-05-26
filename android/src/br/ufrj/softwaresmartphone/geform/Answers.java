package br.ufrj.softwaresmartphone.geform;

import java.util.ArrayList;

import android.util.SparseArray;

public class Answers extends SparseArray< ArrayList<String> > {

	static Answers m_instance;

	private Answers() {
		super();
	}

	static Answers getInstance() {
		if( m_instance == null ) {
			m_instance = new Answers();
		}

		return m_instance;
	}

}
