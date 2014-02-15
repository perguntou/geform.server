/**
 * 
 */
package br.ufrj.del.geform.export;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.dozer.CsvDozerBeanWriter;
import org.supercsv.io.dozer.ICsvDozerBeanWriter;
import org.supercsv.prefs.CsvPreference;

import br.ufrj.del.geform.bean.CollectionBean;
import br.ufrj.del.geform.bean.FormBean;
import br.ufrj.del.geform.bean.ItemBean;

/**
 *
 */
public class Exporter {

	private static final Logger logger = Logger.getLogger( Exporter.class );

	/**
	 * 
	 * @param form
	 * @return
	 */
	private static String[] mapFields( final FormBean form ) {
		final List<ItemBean> items = form.getItems();
		final int size = items.size();
		final String[] array = new String[ size+1 ];
		array[0] = "collector";
		for( final ListIterator<ItemBean> iterator = items.listIterator(); iterator.hasNext(); ) {
			iterator.next();
			final int index = iterator.previousIndex();
			final String entry = String.format( "items[%s]", index );
			array[index+1] = entry;
		}
		return array;
	}

	/**
	 * 
	 * @param form
	 * @return
	 */
	private static CellProcessor[] getProcessors( final FormBean form ) {
		final List<ItemBean> items = form.getItems();
		final int size = items.size();
		final CellProcessor[] array = new CellProcessor[size+1];
		array[0] = new Trim();
		for( final ListIterator<ItemBean> iterator = items.listIterator(); iterator.hasNext(); ) {
			iterator.next();
			final int index = iterator.previousIndex();
			array[index+1] = new NotNull();
		}
		return array;
	}

	/**
	 * 
	 * @param writer
	 * @param form
	 * @throws Exception
	 */
	public static void writeTsv( final Writer writer, final FormBean form ) throws Exception {

		final List<CollectionBean> collections = form.getCollections();

		ICsvDozerBeanWriter beanWriter = null;
		try {
			beanWriter = new CsvDozerBeanWriter( writer, CsvPreference.TAB_PREFERENCE );

			// configure the mapping from the fields to the TSV columns
			final String[] fieldMapping = mapFields( form );
			beanWriter.configureBeanMapping( CollectionBean.class, fieldMapping );

			// write the header
			final List<String> header = new ArrayList<String>();
			header.add( "Collector" );
			header.addAll( form.getQuestions() );
			final String[] headerArray = header.toArray( new String[0] );
			beanWriter.writeHeader( headerArray );

			final CellProcessor[] processors = getProcessors( form );

			// write the beans
			for( final CollectionBean collection : collections ) {
				beanWriter.write( collection, processors );
			}
		} catch( IOException e ) {
			logger.error( "Error while writing tsv.", e );
		} finally {
			if( beanWriter != null ) {
				beanWriter.close();
			}
		}
	}

}
