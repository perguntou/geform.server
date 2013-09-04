package br.ufrj.del.geform.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.ForeignKey;

@Entity(name="form_collection")
public class FormCollection implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="form_id")
	@ForeignKey(name="fk_form_collection_form")
	protected Long formId;
	
	@Id
	@Column(name="collection_id")
	@ForeignKey(name="fk_form_collection_collection")
	protected Long collectionId;

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

}
