package br.ufrj.del.geform.db;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import br.ufrj.del.geform.bean.FormBean;
import br.ufrj.del.geform.bean.ItemBean;
import br.ufrj.del.geform.bean.OptionBean;
import br.ufrj.del.geform.bean.TypeBean;

import br.ufrj.del.geform.db.DatabaseManager;
import br.ufrj.del.geform.db.model.Form;
import br.ufrj.del.geform.db.model.FormItem;
import br.ufrj.del.geform.db.model.Item;
import br.ufrj.del.geform.db.model.Options;

//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//
//import br.ufrj.del.geform.db.manager.Manager;
//import br.ufrj.del.geform.db.model.Choice;
//import br.ufrj.del.geform.db.model.Collection;
//import br.ufrj.del.geform.db.model.Form;
//import br.ufrj.del.geform.db.model.FormCollection;
//import br.ufrj.del.geform.db.model.FormItem;
//import br.ufrj.del.geform.db.model.Item;
//import br.ufrj.del.geform.db.model.ItemOption;
//import br.ufrj.del.geform.db.model.Options;
//import br.ufrj.del.geform.db.model.Text;
//import br.ufrj.del.geform.db.model.Type;



public class TestHibernate {
	public static void main(String[] args) {


//		ItemBean item1 = new ItemBean();
//		item1.setQuestion("Qual seu nome?");
//		item1.setType(TypeBean.TEXT);
//
//		OptionBean opt1 = new OptionBean();
//		opt1.setValue("Option 1");
//		OptionBean opt2 = new OptionBean();
//		opt2.setValue("Option 2");
//		OptionBean opt3 = new OptionBean();
//		opt3.setValue("Option 3");
//
//		ItemBean item2 = new ItemBean();
//		item2.setQuestion("Quais suas preferências?");
//		List<OptionBean> options = new ArrayList<>();
//		options.add(opt1);
//		options.add(opt2);
//		options.add(opt3);
//		item2.setOptions(options);
//		item2.setType(TypeBean.MULTIPLE_CHOICE);
//
//		FormBean form = new FormBean();
//		form.setCreator("Diego");
//		form.setDescription("Teste de inserção.");
//		form.setTimestamp(Date.valueOf("2013-09-10"));
//		form.setTitle("Teste");
//		List<ItemBean> items = new ArrayList<>();
//		items.add(item1);
//		items.add(item2);
//		form.setItems(items);

		DatabaseManager m = new DatabaseManager();
		
//		m.insertNewForm( form );
		
		FormBean f = m.selectFormBean(12L);
		
		System.out.printf("ID %s\n", f.getId().toString());
		System.out.printf("Tit %s\n", f.getTitle());
		System.out.printf("Cria %s\n", f.getCreator());
		System.out.printf("Desc %s\n", f.getDescription());
		System.out.printf("Data %s\n", f.getTimestamp().toString());
		
		for(ItemBean item : f.getItems()){
			System.out.printf("ID %s\n", item.getId());
			System.out.printf("Perg %s\n", item.getQuestion());
			System.out.printf("Type %s\n", item.getType().toString());
			
			for(OptionBean option : item.getOptions()){
				System.out.printf("ID %s\n", option.getId());
				System.out.printf("Valor %s\n", option.getValue());
				
			}
		}

	}

}

