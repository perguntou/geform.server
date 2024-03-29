package br.ufrj.del.geform.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import br.ufrj.del.geform.bean.AnswerBean;
import br.ufrj.del.geform.bean.CollectionBean;
import br.ufrj.del.geform.bean.FormBean;
import br.ufrj.del.geform.bean.ItemBean;
import br.ufrj.del.geform.bean.OptionBean;
import br.ufrj.del.geform.bean.TypeBean;
import br.ufrj.del.geform.db.model.Choice;
import br.ufrj.del.geform.db.model.Collection;
import br.ufrj.del.geform.db.model.Form;
import br.ufrj.del.geform.db.model.FormCollection;
import br.ufrj.del.geform.db.model.FormItem;
import br.ufrj.del.geform.db.model.Item;
import br.ufrj.del.geform.db.model.ItemOption;
import br.ufrj.del.geform.db.model.Options;
import br.ufrj.del.geform.db.model.Text;

public class DatabaseManager {

	private static final Logger logger = Logger.getLogger( DatabaseManager.class );

	private static final String DB_NAME = "geformdb";

	private	EntityManager entityManager;

	public DatabaseManager() {
		final EntityManagerFactory factory = Persistence.createEntityManagerFactory( DB_NAME );
		this.entityManager = factory.createEntityManager();
	}

	// Insert functions
	public void insertChoice( Long collectionID, Long itemID, Long optionID ) {
		Choice choiceAnswer = new Choice();
		choiceAnswer.setCollectionId( collectionID );
		choiceAnswer.setItemId( itemID );
		choiceAnswer.setOptionId( optionID );

		try{
			final EntityTransaction transaction = this.entityManager.getTransaction();
			transaction.begin();

			this.entityManager.persist( choiceAnswer );

			transaction.commit();
		} catch( Exception e ) {
			logger.error( "Error while inserting a choice.", e );
		}
	}

	public void insertCollection( CollectionBean collection ) {
		Collection collectionDB = new Collection();
		collectionDB.setCollector( collection.getCollector() );

		try{
			final EntityTransaction transaction = this.entityManager.getTransaction();
			transaction.begin();

			this.entityManager.persist( collectionDB );

			transaction.commit();
		} catch( Exception e ) {
			logger.error( "Error while inserting a collection.", e );
		}

		collection.setId( collectionDB.getId() );
	}

	public void insertForm( FormBean form ) {
		Form formDB = new Form();
		formDB.setCreator( form.getCreator() );
		formDB.setDescription( form.getDescription() );
		formDB.setTitle( form.getTitle() );
		final Calendar calendar = Calendar.getInstance();
		final Date timestamp = calendar.getTime();
		formDB.setTimestamp( timestamp );
		try{
			final EntityTransaction transaction = this.entityManager.getTransaction();
			transaction.begin();

			this.entityManager.persist( formDB );

			transaction.commit();
		} catch( Exception e ) {
			logger.error( "Error while inserting a form", e );
		}
		form.setId( formDB.getId() );
	}

	public void insertFormCollection( Long formId, Long collectionID ) {
		FormCollection form_collection = new FormCollection();
		form_collection.setFormId( formId );
		form_collection.setCollectionId( collectionID );

		try{
			final EntityTransaction transaction = this.entityManager.getTransaction();
			transaction.begin();

			this.entityManager.persist( form_collection );

			transaction.commit();
		} catch( Exception e ) {
			logger.error( "Error while inserting a form-collection", e );
		}
	}

	public void insertFormItem( Long formId, Long itemId, int index ) {
		FormItem form_item = new FormItem();
		form_item.setFormId( formId );
		form_item.setItemId( itemId );
		form_item.setIndex( index );

		try{
			final EntityTransaction transaction = this.entityManager.getTransaction();
			transaction.begin();

			this.entityManager.persist( form_item );

			transaction.commit();
		} catch( Exception e ) {
			logger.error( "Error while inserting a form-item", e );
		}
	}

	public void insertItem( ItemBean item ) {
		Item itemDB = new Item();
		itemDB.setQuestion( item.getQuestion() );
		final TypeBean type = item.getType();
		itemDB.setTypeId( type.ordinal() );

		try{
			final EntityTransaction transaction = this.entityManager.getTransaction();
			transaction.begin();

			this.entityManager.persist( itemDB );

			transaction.commit();
		} catch( Exception e ) {
			logger.error( "Error while inserting an item", e );
		}

		item.setId( itemDB.getId() );
	}

	public void insertItemOption( Long itemId, Long optionId, int index ) {
		ItemOption item_option = new ItemOption();
		item_option.setItemId( itemId );
		item_option.setOptionId( optionId );
		item_option.setIndex( index );

		try{
			final EntityTransaction transaction = this.entityManager.getTransaction();
			transaction.begin();

			this.entityManager.persist( item_option );

			transaction.commit();
		} catch( Exception e ) {
			logger.error( "Error while inserting an item-option", e );
		}
	}

	public void insertOption( OptionBean option ) {
		Options optionBD = new Options();
		optionBD.setValue( option.getValue() );

		try{
			final EntityTransaction transaction = this.entityManager.getTransaction();
			transaction.begin();

			this.entityManager.persist( optionBD );

			transaction.commit();
		} catch( Exception e ) {
			logger.error( "Error while inserting an option", e );
		}

		option.setId( optionBD.getId() );
	}

	public void insertText( Long collectionID, Long itemID, String answer ) {
		Text textAnswer = new Text();
		textAnswer.setCollectionId( collectionID );
		textAnswer.setItemId( itemID );
		textAnswer.setValue( answer );

		try{
			final EntityTransaction transaction = this.entityManager.getTransaction();
			transaction.begin();

			this.entityManager.persist( textAnswer );

			transaction.commit();
		} catch( Exception e ) {
			logger.error( "Error while inserting a text", e );
		}
	}

	public void insertNewForm( FormBean form ) {
		insertForm( form );

		List<ItemBean> items = form.getItems();

		int itemIndex = 1;
		for(final ItemBean item : items) {
			insertItem( item );
			insertFormItem( form.getId(), item.getId(), itemIndex++ );

			if( item.getType() != TypeBean.TEXT ){
				List<OptionBean> options = item.getOptions();

				int optionIndex = 1;
				for( final OptionBean option : options ) {
					insertOption( option );
					insertItemOption( item.getId(), option.getId(), optionIndex++ );
				}
			}
		}
	}

	public void insertCollections( FormBean form ) {
		List<CollectionBean> collections = form.getCollections();
		final Long formId = form.getId();
		for( final CollectionBean collection : collections ) {
			insertCollection( collection );
			final Long collectionId = collection.getId();
			insertFormCollection( formId, collectionId );

			final List<AnswerBean> answersBean = collection.getItems();
			final List<ItemBean> items = form.getItems();
			for( int index = 0; index < answersBean.size(); index++ ) {
				final AnswerBean answerBean = answersBean.get( index );
				final ItemBean item = items.get( index );

				final List<String> answers = answerBean.getAnswers();
				final Long itemId = item.getId();
				switch( item.getType() ) {
				case TEXT: {
					insertText( collectionId, itemId, answers.get( 0 ) );
					break;
				}
				case SINGLE_CHOICE:	{
					final List<OptionBean> options = item.getOptions();
					final int idx = Integer.parseInt( answers.get(0) );
					final OptionBean option = options.get( idx );
					final Long optionId = option.getId();
					insertChoice( collectionId, itemId, optionId );
					break;
				}
				case MULTIPLE_CHOICE: {
					for( final String answer : answers ){
						final List<OptionBean> options = item.getOptions();
						final int idx = Integer.parseInt( answer );
						final OptionBean option = options.get( idx );
						final Long optionId = option.getId();
						insertChoice( collectionId, itemId, optionId );
					}
					break;
				}
				default:
					break;
				}
			}
		}
	}

	// Select functions
	public List<Choice> selectChoiceAnswerList( Long collectionID, Long itemID ) {
		final String queryString = String.format( "SELECT c FROM choice c WHERE (collection_id = %s) AND (item_id = %s)", collectionID,itemID );
		Query query = this.entityManager.createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Choice> choiceAnswerList  = query.getResultList();

		return choiceAnswerList;
	}

	public Collection selectCollectionById( Long collectionID ) {
		Collection collection = new Collection();
		collection = this.entityManager.find( Collection.class, collectionID );
		return collection;
	}

	public List<Collection> selectFormCollections( Long formID ) {
		final String queryString = String.format( "SELECT fc FROM form_collection fc WHERE form_id = %s", formID );
		Query query = this.entityManager.createQuery( queryString );
		@SuppressWarnings("unchecked")
		List<FormCollection> formCollections  = query.getResultList();

		List<Collection> collections = new ArrayList<>();
		Collection c = new Collection();
		for( FormCollection formCollection : formCollections ){
			c = this.selectCollectionById( formCollection.getCollectionId() );
			collections.add( c );
		}
		return collections;
	}

	public Form selectFormById( Long formID ) {
		Form form = new Form();
		form = this.entityManager.find( Form.class, formID );
		return form;
	}

	public List<Item> selectFormItems( Long formID ) {
		final String queryString = String.format( "SELECT fi FROM form_item fi WHERE form_id = %s", formID );
		Query query = this.entityManager.createQuery( queryString );
		@SuppressWarnings("unchecked")
		List<FormItem> formItems  = query.getResultList();

		List<Item> items = new ArrayList<>();
		Item i = new Item();
		for( FormItem formItem : formItems ){
			i = this.selectItemById( formItem.getItemId() );
			items.add( i );
		}

		return items;
	}

	public Item selectItemById( Long itemID ) {
		Item item = new Item();
		item = this.entityManager.find( Item.class, itemID );
		return item;
	}

	public List<Options> selectItemOptions( Long itemID ) {
		final String queryString = String.format( "SELECT io FROM item_option io WHERE item_id = %s", itemID );
		Query query = this.entityManager.createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<ItemOption> itemOptions  = query.getResultList();

		List<Options> options = new ArrayList<>();
		Options o = new Options();
		for( ItemOption itemOption : itemOptions ) {
			o = this.selectOptionsById( itemOption.getOptionId() );
			options.add( o );
		}

		return options;
	}

	public Options selectOptionsById( Long optionID ) {
		Options option = new Options();
		option = this.entityManager.find( Options.class, optionID );
		return option;
	}

	public Text selectTextAnswer( Long collectionID, Long itemID ) {
		final String queryString = String.format( "SELECT t FROM text t WHERE (collection_id = %s) AND (item_id = %s)", collectionID,itemID );
		Query query = this.entityManager.createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Text> texts  = query.getResultList();
		Text textAnswer = texts.get(0);
		
		return textAnswer;
	}

	public FormBean selectFormBean( Long formID ) {
		FormBean formBean = new FormBean();
		Form form = this.selectFormById( formID );

		if( form == null ) {
			return null;
		}

		formBean.setId( form.getId() );
		formBean.setTitle( form.getTitle() );
		formBean.setDescription( form.getDescription() );
		formBean.setCreator( form.getCreator() );
		formBean.setTimestamp( form.getTimestamp() );

		List<Item> items;
		List<ItemBean> itemsBean = new ArrayList<>();

		items = this.selectFormItems( formID );

		for( Item item : items ) {
			ItemBean itemBean = new ItemBean();

			itemBean.setId( item.getId() );
			itemBean.setQuestion( item.getQuestion() );
			TypeBean typeBean = TypeBean.values()[item.getTypeId()];
			itemBean.setType( typeBean );
			
			if ( !TypeBean.TEXT.equals(typeBean) ){
				List<Options> options = this.selectItemOptions( item.getId() );
				List<OptionBean> optionsBean = new ArrayList<>();

				for( Options option : options ) {
					OptionBean optionBean = new OptionBean();

					optionBean.setId( option.getId() );
					optionBean.setValue( option.getValue() );

					optionsBean.add( optionBean );
				}
				itemBean.setOptions( optionsBean );
			}
			itemsBean.add( itemBean );
		}
		formBean.setItems( itemsBean );

		return formBean;
	}

	public List<CollectionBean> selectCollectionBeanList( Long formID ) {
		final FormBean formBean = this.selectFormBean( formID );

		final List<CollectionBean> collectionBeanList = new ArrayList<>();

		final List<Collection> collections = selectFormCollections( formID );

		//Map< ItemId, Map< OptionId, OptionValue > >
		final Map<Long,Map<Long,String>> map = new HashMap<Long,Map<Long,String>>();
		for( ItemBean item : formBean.getItems() ) {
			final List<OptionBean> options = item.getOptions();
			if( options == null ) {
				continue;
			}
			final Long itemId = item.getId();
			final Map<Long,String> innerMap = new HashMap<Long,String>();
			for( final OptionBean option : options ) {
				final Long key = option.getId();
				final String value = option.getValue();
				innerMap.put( key, value );
			}
			map.put( itemId, innerMap );
		}

		for( Collection collection: collections ){
			final CollectionBean collectionBean = new CollectionBean();

			final Long collectionId = collection.getId();
			collectionBean.setId( collectionId );
			collectionBean.setCollector( collection.getCollector() );

			final List<AnswerBean> answerBeanList = new ArrayList<>();

			final List<ItemBean> itemBeanList = formBean.getItems();
			for( ItemBean itemBean : itemBeanList ) {
				final Long itemId = itemBean.getId();
				AnswerBean answerBean = new AnswerBean();
				List<String> stringAnswer = new ArrayList<String>();

				final TypeBean type = itemBean.getType();
				switch( type ) {
				case TEXT: {
					Text textAnswer = this.selectTextAnswer( collectionId, itemId );
					final String value = textAnswer.getValue();
					stringAnswer.add( value );
					break;
				}
				case SINGLE_CHOICE:	{
					List<Choice> singleChoiceAnswerList = this.selectChoiceAnswerList( collectionId, itemId);
					//Colocar uma validação - Caso tenha mais de um item no array
					Choice singlechoiceAnswer = singleChoiceAnswerList.get( 0 );
					final Long optionId = singlechoiceAnswer.getOptionId();
					final Map<Long, String> optionsMap = map.get( itemId );
					final String optionValue = optionsMap.get( optionId );
					stringAnswer.add( optionValue );
					break;
				}
				case MULTIPLE_CHOICE: {
					List<Choice> MultipleChoiceAnswerList = this.selectChoiceAnswerList( collectionId, itemId );
					for( Choice multipleChoiceAnswer : MultipleChoiceAnswerList ) {
						final Long optionId = multipleChoiceAnswer.getOptionId();
						final Map<Long, String> optionsMap = map.get( itemId );
						final String optionValue = optionsMap.get( optionId );
						stringAnswer.add( optionValue );
					}
					break;
				}
				default:
					break;
				}

				answerBean.setAnswers( stringAnswer );
				answerBeanList.add( answerBean );
			}
			collectionBean.setItems( answerBeanList );
			collectionBeanList.add( collectionBean );
		}

		return collectionBeanList;
	}

}
