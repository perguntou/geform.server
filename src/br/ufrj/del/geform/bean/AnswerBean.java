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

	List<String> m_answer;

	public AnswerBean() {}

	/**
	 * @return the answer
	 */
	public List<String> getAnswer() {
		return m_answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer( List<String> answer ) {
		this.m_answer = answer;
	}

	public void setAnswer( String... strings ) {
		this.m_answer = Arrays.asList( strings );
	}

}
