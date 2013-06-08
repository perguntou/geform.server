/**
 * 
 */
package br.ufrj.del.geform.util;


/**
 *
 */
public enum Type {
	TEXT( "text" ),
	SINGLE_CHOICE( "single" ),
	MULTIPLE_CHOICE( "multiple" );

	private final String type;

	Type( final String type ) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}

}

