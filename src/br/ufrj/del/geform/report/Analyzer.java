/**
 * 
 */
package br.ufrj.del.geform.report;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import br.ufrj.del.geform.bean.AnswerBean;
import br.ufrj.del.geform.bean.CollectionBean;
import br.ufrj.del.geform.bean.FormBean;
import br.ufrj.del.geform.bean.ItemBean;
import br.ufrj.del.geform.bean.OptionBean;
import br.ufrj.del.geform.bean.TypeBean;

/**
 *
 */
public class Analyzer {

	private FormBean form;
	private List<CollectionBean> collections;

	public Analyzer( FormBean form ) {
		setForm( form );
		//TODO: verificar alteracao para que se separe Collections de Forms, passando cada elemento separadamente no construtor
		final List<CollectionBean> collections = form.getCollections();
		setCollections( collections );
	}

	/**
	 * @return the form
	 */
	public FormBean getForm() {
		return form;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm( FormBean form ) {
		this.form = form;
	}

	/**
	 * @return the collections
	 */
	public List<CollectionBean> getCollections() {
		return collections;
	}

	/**
	 * @param collections the collections to set
	 */
	public void setCollections( List<CollectionBean> collections ) {
		this.collections = collections;
	}

	/**
	 * 
	 * @return
	 */
	public Report process() {
		final List<ItemBean> items = this.form.getItems();
		final Map<String, Map<String,Integer>> itemsReport = new HashMap<String, Map<String,Integer>>( items.size() );
		final Map<String,Integer> collectorsReport = new HashMap<String,Integer>();
		final Report report = new Report();
		report.setItems( itemsReport );
		report.setCollectors( collectorsReport );
		report.setTotal( this.collections.size() );
		final ListIterator<ItemBean> iterator = items.listIterator();
		while( iterator.hasNext() ) {
			final ItemBean item = iterator.next();
			final int index = iterator.previousIndex();
			final TypeBean type = item.getType();
			final String key = item.getQuestion();
			switch( type ) {
			case TEXT:
				//itens de texto nao sao quantificados
				itemsReport.put( key, null );
				continue;
			case MULTIPLE_CHOICE:
			case SINGLE_CHOICE:
				//inicializando contador de cada opcao
				final List<OptionBean> options = item.getOptions();
				final HashMap<String,Integer> itemCounter = new HashMap<String,Integer>( options.size() );
				for( final OptionBean option : options ) {
					final String value = option.getValue();
					itemCounter.put( value, 0 );
				}
				itemsReport.put( key, itemCounter );
				break;
			}

			final Map<String,Integer> counter = itemsReport.get( key );
			for( final CollectionBean collection : this.collections ) {
				final String collector = collection.getCollector();
				Integer collectorCounter = collectorsReport.containsKey( collector ) ? collectorsReport.get( collector ) : 0;
				collectorCounter++;
				collectorsReport.put( collector, collectorCounter );
				final List<AnswerBean> itemsAnswers = collection.getItems();
				final AnswerBean answerBean = itemsAnswers.get( index );
				for( final String answer : answerBean.getAnswers() ) {
					Integer count = counter.get( answer );
					++count;
					counter.put( answer, count );
				}
			}
		}

		return report;
	}

}
