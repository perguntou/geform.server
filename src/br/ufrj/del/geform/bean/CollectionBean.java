/**
 * 
 */
package br.ufrj.del.geform.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 */
@XmlRootElement
@XmlType(propOrder={"id","answers"})
public class CollectionBean {

	private Long m_id;

	private List<AnswerBean> m_answers;

	public CollectionBean() {}

	/**
	 * @return the m_id
	 */
	@XmlElement(name="form")
	public Long getId() {
		return m_id;
	}

	/**
	 * @param m_id the m_id to set
	 */
	public void setId( Long id ) {
		this.m_id = id;
	}

	/**
	 * @return the m_answers
	 */
	@XmlElement(name="item")
	public List<AnswerBean> getAnswers() {
		return m_answers;
	}

	/**
	 * @param m_answers the m_answers to set
	 */
	public void setAnswers( List<AnswerBean> answers ) {
		this.m_answers = answers;
	}
}
