/**
 * 
 */
package br.ufrj.del.geform.bean;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@XmlRootElement(name="item")
public class AnswerBean {

	List<String> answers;

	public AnswerBean() {}

	/**
	 * @return the answers
	 */
	@XmlElement(name="answer")
	public List<String> getAnswers() {
		return answers;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswers( List<String> answers ) {
		this.answers = answers;
	}

	public void setAnswers( String... answers ) {
		this.answers = Arrays.asList( answers );
	}

}
