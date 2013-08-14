package br.ufrj.del.geform;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Jaxb {

	private String m_title;
	private String m_description;
	private String m_creator;

	public String getTitle() {
		return m_title;
	}

	public void setTitle( String title ) {
		m_title = title;
	}

	public String getDescription() {
		return m_description;
	}

	public void setDescription( String description ) {
		m_description = description;
	}

	public String getCreator() {
		return m_creator;
	}

	public void setCreator( String creator ) {
		m_creator = creator;
	}

}
