/**
 * 
 */
package br.ufrj.del.geform.util;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 *
 */
@XmlEnum
public enum Type {
	@XmlEnumValue("text")
	TEXT( "text" ),
	@XmlEnumValue("single")
	SINGLE_CHOICE( "single" ),
	@XmlEnumValue("multiple")
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

