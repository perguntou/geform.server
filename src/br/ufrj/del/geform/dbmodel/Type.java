package br.ufrj.del.geform.dbmodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Type {
	@Id
	@GeneratedValue
	@Column(name="id")
	Long id_type;
	@Column(name="value")
	String value;


	public Long getId() {
		return id_type;
	}
	public void setId(Long id) {
		this.id_type = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String nome) {
		this.value = nome;
	}

}
