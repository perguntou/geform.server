package br.ufrj.del.geform.db.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
	
public
	void insertChoice(){
		
	}
	void insertCollection(){
		
	}
	void insertForm(){
		
	}
	void insertFormCollection(){
		
	}
	void insertFormItem(){
		
	}
	void insertItem(){
		
	}
	void insertItemOption(){
		
	}
	void insertOption(){
		
	}
	void insertText(){
		
	}
	void insertType(){
		
	}
	void updateChoice(){
		
	}
	void updateCollection(){
		
	}
	void updateForm(){
		
	}
	void updateFormCollection(){
		
	}
	void updateFormItem(){
		
	}
	void updateItem(){
		
	}
	void updateItemOption(){
		
	}
	void updateOption(){
		
	}
	void updateText(){
		
	}
	void updateType(){
		
	}
	void removeChoice(){
		
	}
	void removeCollection(){
		
	}
	void removeForm(){
		
	}
	void removeFormCollection(){
		
	}
	void removeFormItem(){
		
	}
	void removeItem(){
		
	}
	void removeItemOption(){
		
	}
	void removeOption(){
		
	}
	void removeText(){
		
	}
	void removeType(){
		
	}
	List<Choice> selectChoice(){
		return null;
		
	}
	List<Collection> selectCollection(){
		return null;
		
	}
	List<Form> selectForm(){
		return null;
		
	}
	List<FormCollection> selectFormCollection(){
		return null;
		
	}
	List<FormItem> selectFormItem(){
		return null;
		
	}
	List<Item> selectItem(){
		return null;
		
	}
	List<ItemOption> selectItemOption(){
		return null;
		
	}
	List<Options> selectOptions(){
		return null;
		
	}
	List<Text> selectText(){
		return null;
		
	}
	List<Type> selectType(){
		return null;
		
	}

}
