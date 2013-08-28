package br.ufrj.del.geform.dbmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.ForeignKey;






@Entity(name = "form_item")
public class FormItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="form_id")	
	@ForeignKey(name="fk_form_item_form")
	protected Long formId;

	@Id
	@Column(name="item_id")
	@ForeignKey(name="fk_form_item_item")
	protected Long itemId;

	@Column(name="item_index")
	private int item_index;

	/**
	 * @return the index
	 */
	public int getIndex() {
		return item_index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.item_index = index;
	}

	/**
	 * @return the formId
	 */
	public Long getFormId() {
		return formId;
	}

	/**
	 * @param formId the formId to set
	 */
	public void setFormId(Long formId) {
		this.formId = formId;
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

}

