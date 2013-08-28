package br.ufrj.del.geform.dbmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.ForeignKey;


@Entity(name="text")
public class Text implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="item_id")
	@ForeignKey(name="fk_item_collection_item")
	protected Long itemId;
	
	@Id
	@Column(name="collection_id")
	@ForeignKey(name="fk_item_collection_collection")
	protected Long collectionId;
	
	@Column(name="value")
	private String value;

	/**
	 * @return the itemId
	 */
	public Long getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the collectionId
	 */
	public Long getCollectionId() {
		return collectionId;
	}

	/**
	 * @param collectionId the collectionId to set
	 */
	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
