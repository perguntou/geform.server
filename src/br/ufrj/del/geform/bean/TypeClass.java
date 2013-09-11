/**
 * 
 */
package br.ufrj.del.geform.bean;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 *
 */
@XmlEnum
public enum TypeClass {
	@XmlEnumValue("text")
	TEXT( "text" ),
	@XmlEnumValue("single")
	SINGLE_CHOICE( "single" ),
	@XmlEnumValue("multiple")
	MULTIPLE_CHOICE( "multiple" );

	private final String type;

	TypeClass( final String type ) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}

}

