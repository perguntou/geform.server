package br.ufrj.del.geform.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.ForeignKey;


@Entity(name="choice")
public class Choice implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="collection_id")
	@ForeignKey(name="fk_choice_collection")
	protected Long collectionId;
	
	@Id
	@Column(name="item_id")
	@ForeignKey(name="fk_choice_item_option")
	protected Long itemId;
	
	@Id
	@Column(name="option_id")
	@ForeignKey(name="fk_choice_item_option")
	protected Long optionId;

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
	 * @return the optionId
	 */
	public Long getOptionId() {
		return optionId;
	}

	/**
	 * @param optionId the optionId to set
	 */
	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}

}
