/**
 * 
 */
package br.ufrj.del.geform.bean;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.ufrj.del.geform.util.DateAdapter;

/**
*
*/
@XmlRootElement(name="form")
@XmlType(propOrder={"id","timestamp","creator","title","description","items","collections"})
public class FormBean {

	private Long id;
	private String title;
	private String description;
	private String creator;
	private Date timestamp;
	private List<ItemBean> items;
	private List<CollectionBean> collections;

	public FormBean() {}

	public FormBean( String title ) {
		setTitle( title );
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId( Long id ) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle( String title ) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription( String description ) {
		this.description = description;
	}

	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator( String creator ) {
		this.creator = creator;
	}

	/**
	 * @return the timestamp
	 */
	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp( Date timestamp ) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the items
	 */
	@XmlElement(name="item")
	public List<ItemBean> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems( List<ItemBean> items ) {
		this.items = items;
	}

	/**
	 * @return the collection
	 */
	@XmlElement(name="collection")
	public List<CollectionBean> getCollections() {
		return collections;
	}

	/**
	 * @param collection the collection to set
	 */
	public void setCollections(List<CollectionBean> collections) {
		this.collections = collections;
	}
}
