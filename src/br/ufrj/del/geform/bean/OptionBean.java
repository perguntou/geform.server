package br.ufrj.del.geform.bean;

public class OptionBean {

	private Long id;
	private String value;

	public OptionBean() {
		//Do nothing
	}

	public OptionBean( final String option ) {
		setValue( option );
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
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the option
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param option the option to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return value;
	}

}
