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
@XmlRootElement(name="collection")
@XmlType(propOrder={"id","collector","items"})
public class CollectionBean {

	private Long id;

	private List<AnswerBean> items;

	private String collector;

	public CollectionBean() {}

	/**
	 * @return the id
	 */
	@XmlElement(name="form")
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId( final Long id ) {
		this.id = id;
	}

	/**
	 * @return the answers
	 */
	@XmlElement(name="item")
	public List<AnswerBean> getItems() {
		return items;
	}

	/**
	 * @param answers the answers to set
	 */
	public void setItems( final List<AnswerBean> answers ) {
		this.items = answers;
	}

	/**
	 * @return the collector
	 */
	public String getCollector() {
		return collector;
	}

	/**
	 * @param collector the collector to set
	 */
	public void setCollector( final String collector ) {
		this.collector = collector;
	}

}
