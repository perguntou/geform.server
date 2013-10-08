package br.ufrj.del.geform.db;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.ufrj.del.geform.bean.FormBean;
import br.ufrj.del.geform.bean.ItemBean;
import br.ufrj.del.geform.bean.OptionBean;
import br.ufrj.del.geform.bean.TypeBean;

import br.ufrj.del.geform.db.DatabaseManager;

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
//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("geformdb");
//		EntityManager em = factory.createEntityManager();
//		
//		Type t1 = new Type();
//		t1.setValue("Texto");
//		Type t2 = new Type();
//		t2.setValue("Escolha Única");
//		
//
//		Form f = new Form();
//		f.setCreator("Eu");
//		f.setDescription("Desc.");
//		f.setTitle("Meu Form");
//		f.setTimestamp(Date.valueOf("2013-07-26"));
//		
//		
//		Item i1 = new Item();
//		i1.setQuestion("Qual seu nome?");
//		i1.setTypeId(20L);
//		
//		Item i2 = new Item();
//		i2.setQuestion("Qual a cor da casa?");
//		i2.setTypeId(21L);
//		
//		Collection c = new Collection();
//		c.setCollector("Recenciador");
//		
//		Options o = new Options();
//		o.setValue("Op");
//		
//		Choice choice = new Choice();
//		choice.setCollectionId(3L);
//		choice.setItemId(10L);
//		choice.setOptionId(1L);
//		
//		FormCollection fc = new FormCollection();
//		fc.setFormId(11L);
//		fc.setCollectionId(3L);
//		
//		FormItem fi1 = new FormItem();
//		fi1.setFormId(f.getId());
//		fi1.setItemId(i1.getId());
//		fi1.setIndex(1);
//		
//		FormItem fi2 = new FormItem();
//		fi2.setFormId(f.getId());
//		fi2.setItemId(i2.getId());
//		fi2.setIndex(2);
//		
//		ItemOption io = new ItemOption();
//		io.setItemId(10L);
//		io.setOptionId(1L);
//		io.setIndex(1);
//		
//		Text text = new Text();
//		text.setCollectionId(3L);
//		text.setItemId(9L);
//		text.setValue("Resposta");

		ItemBean item1 = new ItemBean();
		item1.setQuestion("Qual seu nome?");
		item1.setType(TypeBean.TEXT);

		OptionBean opt1 = new OptionBean();
		opt1.setValue("Option 1");
		OptionBean opt2 = new OptionBean();
		opt2.setValue("Option 2");
		OptionBean opt3 = new OptionBean();
		opt3.setValue("Option 3");

		ItemBean item2 = new ItemBean();
		item2.setQuestion("Quais suas preferências?");
		List<OptionBean> options = new ArrayList<>();
		options.add(opt1);
		options.add(opt2);
		options.add(opt3);
		item2.setOptions(options);
		item2.setType(TypeBean.MULTIPLE_CHOICE);

		FormBean form = new FormBean();
		form.setCreator("Diego");
		form.setDescription("Teste de inserção.");
		form.setTimestamp(Date.valueOf("2013-09-10"));
		form.setTitle("Teste");
		List<ItemBean> items = new ArrayList<>();
		items.add(item1);
		items.add(item2);
		form.setItems(items);

		DatabaseManager m = new DatabaseManager();
		m.insertNewForm( form );
	}

}

