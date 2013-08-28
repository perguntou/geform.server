package br.ufrj.del.geform.dbmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.ForeignKey;


@Entity(name="item_option")
public class ItemOption implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="item_id")
	@ForeignKey(name="fk_item_option_item")
	protected Long itemId;
	
	@Id
	@Column(name="option_id")
	@ForeignKey(name="fk_item_option_option")
	protected Long optionId;
	
	@Column(name="option_index")
	private int option_index;

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

	/**
	 * @return the index
	 */
	public int getIndex() {
		return option_index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.option_index = index;
	}

}
