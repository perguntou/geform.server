/**
 * 
 */
package br.ufrj.del.geform.report;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@XmlRootElement
public class Report {

	private Map<String, Map<String, Integer>> items;
	private Map<String, Integer> collectors;
	private Integer total;

	public Report() {
		//do nothing
	}

	/**
	 * @return the items
	 */
	public Map<String, Map<String, Integer>> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems( Map<String, Map<String, Integer>> items ) {
		this.items = items;
	}

	/**
	 * @return the collectors
	 */
	public Map<String, Integer> getCollectors() {
		return collectors;
	}

	/**
	 * @param collectors the collectors to set
	 */
	public void setCollectors( Map<String, Integer> collectors ) {
		this.collectors = collectors;
	}

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal( Integer total ) {
		this.total = total;
	}

}
