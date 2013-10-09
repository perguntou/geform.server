package br.ufrj.del.geform.db;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

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
import br.ufrj.del.geform.db.model.Type;

public class DatabaseManager {

	private static final String DB_NAME = "geformdb";

	private EntityManagerFactory factory;

	private	EntityManager em;
	
	public DatabaseManager() {
		factory = Persistence.createEntityManagerFactory(DB_NAME);
		em = factory.createEntityManager();
	}

	// Insert functions

	public void insertChoice() {
		
	}

	public void insertCollection() {
		
	}

	public void insertForm(FormBean form) {
		Form formDB = new Form();
		formDB.setCreator(form.getCreator());
		formDB.setDescription(form.getDescription());
		formDB.setTitle(form.getTitle());
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime( form.getTimestamp() );
		final Date sqlDate = new Date( calendar.getTimeInMillis() );
		formDB.setTimestamp( sqlDate );
		try{
			em.getTransaction().begin();

			em.persist(formDB);

			em.getTransaction().commit();
		} catch( Exception e ) {
			System.out.printf( "Erro: %s", e.getMessage() );
		}
		form.setId(formDB.getId());
	}

	public void insertFormCollection() {
		
	}

	public void insertFormItem( Long formId, List<ItemBean> items ) {
		for( int i = 0; i < items.size(); i++ ){
			FormItem form_item = new FormItem();
			form_item.setFormId( formId );
			final ItemBean itemBean = items.get( i );
			final Long itemId = itemBean.getId();
			form_item.setItemId( itemId );
			form_item.setIndex( i+1 );

			try{
				em.getTransaction().begin();

				em.persist(form_item);

				em.getTransaction().commit();
			} catch( Exception e ) {
				System.out.printf( "Erro: %s", e.getMessage() );
			}
		}
	}

	public void insertItem( ItemBean item ) {
		Item itemDB = new Item();
		itemDB.setQuestion( item.getQuestion() );
		final TypeBean type = item.getType();
		itemDB.setTypeId( type.ordinal() );

		try{
			em.getTransaction().begin();

			em.persist(itemDB);

			em.getTransaction().commit();
		} catch( Exception e ) {
			System.out.printf( "Erro: %s", e.getMessage() );
		}
		
		item.setId(itemDB.getId());
	}

	public void insertItemOption( Long itemId, List<OptionBean> options ) {
		for( int i = 0; i < options.size(); i++ ){
			ItemOption item_option = new ItemOption();
			item_option.setItemId( itemId );
			final OptionBean optionBean = options.get(i);
			final Long optionId = optionBean.getId();
			item_option.setOptionId( optionId );
			item_option.setIndex( i+1 );

			try{
				em.getTransaction().begin();

				em.persist(item_option);

				em.getTransaction().commit();
			} catch( Exception e ) {
				System.out.printf( "Erro: %s", e.getMessage() );
			}
		}
	}

	public void insertOption( OptionBean option ) {
		Options optionBD = new Options();
		optionBD.setValue( option.getValue() );

		try{
			em.getTransaction().begin();

			em.persist(optionBD);

			em.getTransaction().commit();
		} catch( Exception e ) {
			System.out.printf( "Erro: %s", e.getMessage() );
		}

		option.setId(optionBD.getId());
	}

	public void insertText() {
		
	}

	public void insertType() {
		
	}

	public void insertNewForm( FormBean form ) {
		insertForm(form);

		List<ItemBean> items = form.getItems();
		for(final ItemBean item : items) {
			insertItem( item );
			
			if (item.getType() != TypeBean.TEXT){
				List<OptionBean> options = item.getOptions();
				for( final OptionBean option : options ) {
					insertOption( option );
				}
				insertItemOption( item.getId(), options );
			}
		}
		insertFormItem( form.getId(), items );
	}

// Update functions
	
	public void updateChoice(){
		
	}
	public void updateCollection(){
		
	}
	public void updateForm(){
		
	}
	public void updateFormCollection(){
		
	}
	public void updateFormItem(){
		
	}
	public void updateItem(){
		
	}
	public void updateItemOption(){
		
	}
	public void updateOption(){
		
	}
	public void updateText(){
		
	}
	public void updateType(){
		
	}
	public void removeChoice(){
		
	}
	
// Remove functions
	
	public void removeCollection(){
		
	}
	public void removeForm(){
		
	}
	public void removeFormCollection(){
		
	}
	public void removeFormItem(){
		
	}
	public void removeItem(){
		
	}
	public void removeItemOption(){
		
	}
	public void removeOption(){
		
	}
	public void removeText(){
		
	}
	public void removeType(){
		
	}
	
// Select functions
	
	public List<Choice> selectChoice(){
		return null;
		
	}
	public List<Collection> selectCollection(){
		return null;
		
	}
	public List<Form> selectForm(){
		return null;
		
	}
	public Form selectFormById(Long formID){
		Form form = new Form();
		form = em.find(Form.class, formID);
		return form;
	}
	public List<Item> selectFormItems(Long formID){
		Query query = em.createQuery(" SELECT fi FROM form_item fi WHERE form_id = " + formID.toString());
		List<FormItem> formItems  = query.getResultList();
		
		List<Item> items = new ArrayList<>();
		Item i = new Item();
		for(FormItem formItem : formItems){
			i = this.selectItemById(formItem.getItemId());
			items.add(i);
		}
		
		return items;
	}
	public List<FormCollection> selectFormCollection(){
		return null;
		
	}
	public List<FormItem> selectFormItem(){
		return null;
		
	}
	public List<Item> selectItem(){
		return null;
		
	}
	public Item selectItemById(Long itemID){
		Item item = new Item();
		item = em.find(Item.class, itemID);
		return item;
	}
	public List<Options> selectItemOptions(Long itemID){
		Query query = em.createQuery(" SELECT io FROM item_option io WHERE item_id = " + itemID.toString());
		List<ItemOption> itemOptions  = query.getResultList();
		
		List<Options> options = new ArrayList<>();
		Options o = new Options();
		for(ItemOption itemOption : itemOptions){
			o = this.selectOptionsById(itemOption.getOptionId());
			options.add(o);
		}
		
		return options;
	}
	public List<ItemOption> selectItemOption(){
		return null;
		
	}
	public List<Options> selectOptions(){
		return null;
		
	}
	public Options selectOptionsById(Long optionID){
		Options option = new Options();
		option = em.find(Options.class, optionID);
		return option;
	}
	public List<Text> selectText(){
		return null;
		
	}
	public List<Type> selectType(){
		return null;
		
	}
	public FormBean selectFormBean(Long formID){
		FormBean formBean = new FormBean();
		Form form = this.selectFormById(formID);
		
		formBean.setId(form.getId());
		formBean.setTitle(form.getTitle());
		formBean.setDescription(form.getDescription());
		formBean.setCreator(form.getCreator());
		formBean.setTimestamp(form.getTimestamp());
		
		List<Item> items;
		List<ItemBean> itemsBean = new ArrayList<>();
		
		items = this.selectFormItems(formID);
		
		for(Item item : items){
			ItemBean itemBean = new ItemBean();
			
			itemBean.setId(item.getId());
			itemBean.setQuestion(item.getQuestion());
			TypeBean typeBean = TypeBean.values()[item.getTypeId()];
			itemBean.setType(typeBean);
			
			List<Options> options = this.selectItemOptions(item.getId());
			List<OptionBean> optionsBean = new ArrayList<>();
			
			for(Options option : options){
				OptionBean optionBean = new OptionBean();
				
				optionBean.setId(option.getId());
				optionBean.setValue(option.getValue());
				
				optionsBean.add(optionBean);
			}
			itemBean.setOptions(optionsBean);
			itemsBean.add(itemBean);
		}
		formBean.setItems(itemsBean);
		
		return formBean;
	}
		
	}
