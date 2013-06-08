/**
 * 
 */
package br.ufrj.del.geform.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import br.ufrj.del.geform.util.Type;


/**
*
*/
@XmlRootElement
@XmlType(propOrder={"question","options"})
public class Item {

	private String question;
	private List<String> options;
	private Type type;

	public Item() {}

	public Item( final String question ) {
		setQuestion( question );
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the options
	 */
	@XmlElementWrapper(name="options")
	@XmlElement(name="option")
	public List<String> getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(List<String> options) {
		this.options = options;
	}

	/**
	 * @return the type
	 */
	@XmlAttribute
	public Type getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}
}