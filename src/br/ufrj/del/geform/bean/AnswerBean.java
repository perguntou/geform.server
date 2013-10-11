/**
 * 
 */
package br.ufrj.del.geform.bean;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@XmlRootElement
public class AnswerBean {

	List<String> m_answers;
	
	public AnswerBean() {}

	/**
	 * @return the answers
	 */
	public List<String> getAnswers() {
		return m_answers;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswers( List<String> answers ) {
		this.m_answers = answers;
	}

	public void setAnswers( String... answers ) {
		this.m_answers = Arrays.asList( answers );
	}

}
