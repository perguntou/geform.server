package br.ufrj.del.geform.db.manager;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.ufrj.del.geform.bean.FormClass;
import br.ufrj.del.geform.bean.ItemClass;
import br.ufrj.del.geform.bean.OptionClass;

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

public class Manager {

private
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("geformdb");
	EntityManager em = factory.createEntityManager();
	
// Insert functions

	public void insertChoice(){
		
	}
	public void insertCollection(){
		
	}
	public Long insertForm(FormClass form){
		
		Form formDB = new Form();
		formDB.setCreator(form.getAuthor());
		formDB.setDescription(form.getDescription());
		formDB.setTitle(form.getTitle());
		formDB.setTimestamp((Date) form.getTimestamp());
		try{
			em.getTransaction().begin();

			em.persist(formDB);

			em.getTransaction().commit();
		} catch( Exception e ) {
			System.out.printf( "Erro: %s", e.getMessage() );
		}
		
		return formDB.getId();
	}
	public void insertFormCollection(){
		
		
	}
	public void insertFormItem(Long formId, List<ItemClass> items){
		
		for(int i = 0 ; i < items.size() ; i++){
			FormItem form_item = new FormItem();
			form_item.setFormId(formId);
			form_item.setItemId(items.get(i).getId());
			form_item.setIndex(i+1);
			
			try{
				em.getTransaction().begin();

				em.persist(form_item);

				em.getTransaction().commit();
			} catch( Exception e ) {
				System.out.printf( "Erro: %s", e.getMessage() );
			}
		}
	}
	public Long insertItem(ItemClass item){
		
		Item itemDB = new Item();
		itemDB.setQuestion(item.getQuestion());
		itemDB.setTypeId(item.getType().ordinal());
		
		try{
			em.getTransaction().begin();

			em.persist(itemDB);

			em.getTransaction().commit();
		} catch( Exception e ) {
			System.out.printf( "Erro: %s", e.getMessage() );
		}
		
		return itemDB.getId();
	}
	public void insertItemOption(Long itemId, List<OptionClass> options){
		
		for(int i = 0 ; i < options.size() ; i++){
			ItemOption item_option = new ItemOption();
			item_option.setItemId(itemId);
			item_option.setOptionId(options.get(i).getId());
			item_option.setIndex(i+1);
			
			try{
				em.getTransaction().begin();

				em.persist(item_option);

				em.getTransaction().commit();
			} catch( Exception e ) {
				System.out.printf( "Erro: %s", e.getMessage() );
			}
		}
	}
	public Long insertOption(OptionClass option){
		
		Options optionBD = new Options();
		optionBD.setValue(option.getOption());
		
		try{
			em.getTransaction().begin();

			em.persist(optionBD);

			em.getTransaction().commit();
		} catch( Exception e ) {
			System.out.printf( "Erro: %s", e.getMessage() );
		}
		
		return optionBD.getId();
	}
	public void insertText(){
		
	}
	public void insertType(){
		
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
	public List<FormCollection> selectFormCollection(){
		return null;
		
	}
	public List<FormItem> selectFormItem(){
		return null;
		
	}
	public List<Item> selectItem(){
		return null;
		
	}
	public List<ItemOption> selectItemOption(){
		return null;
		
	}
	public List<Options> selectOptions(){
		return null;
		
	}
	public List<Text> selectText(){
		return null;
		
	}
	public List<Type> selectType(){
		return null;
		
	}

}
