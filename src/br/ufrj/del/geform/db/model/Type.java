package br.ufrj.del.geform.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Type {
	@Id
	@GeneratedValue
	@Column(name="id")
	int id_type;
	@Column(name="value")
	String value;


	public int getId() {
		return id_type;
	}
	public void setId(int id) {
		this.id_type = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String nome) {
		this.value = nome;
	}

}
