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



/**
*
*/
@XmlRootElement
@XmlType(propOrder={"question","options"})
public class ItemClass {

	private Long id;
	private String question;
	private List<OptionClass> options;
	private TypeClass type;

	public ItemClass() {}

	public ItemClass( final String question ) {
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
	public List<OptionClass> getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(List<OptionClass> options) {
		this.options = options;
	}

	/**
	 * @return the type
	 */
	@XmlAttribute
	public TypeClass getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TypeClass type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}