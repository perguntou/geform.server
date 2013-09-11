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
@XmlRootElement
@XmlType(propOrder={"id","timestamp","author","title","description","items"})
public class FormClass {

	private Long id;
	private String title;
	private String description;
	private String author;
	private Date timestamp;
	private List<ItemClass> items;

	public FormClass() {}

	public FormClass( String title ) {
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
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor( String author ) {
		this.author = author;
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
	public List<ItemClass> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems( List<ItemClass> items ) {
		this.items = items;
	}
}
