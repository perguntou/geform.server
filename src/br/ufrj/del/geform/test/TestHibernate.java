package br.ufrj.del.geform.test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.ufrj.del.geform.bean.FormClass;
import br.ufrj.del.geform.bean.ItemClass;
import br.ufrj.del.geform.bean.OptionClass;
import br.ufrj.del.geform.bean.TypeClass;

import br.ufrj.del.geform.db.manager.Manager;

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
		
		ItemClass item1 = new ItemClass();
		item1.setQuestion("Qual seu nome?");
		item1.setType(TypeClass.TEXT);
		
		OptionClass opt1 = new OptionClass();
		opt1.setOption("Option 1");
		OptionClass opt2 = new OptionClass();
		opt2.setOption("Option 2");
		OptionClass opt3 = new OptionClass();
		opt3.setOption("Option 3");
		
		ItemClass item2 = new ItemClass();
		item2.setQuestion("Quais suas preferências?");
		List<OptionClass> options = new ArrayList<>();
		options.add(opt1);
		options.add(opt2);
		options.add(opt3);
		item2.setOptions(options);
		item2.setType(TypeClass.MULTIPLE_CHOICE);
		
		FormClass form = new FormClass();
		form.setAuthor("Diego");
		form.setDescription("Teste de inserção.");
		form.setTimestamp(Date.valueOf("2013-09-10"));
		form.setTitle("Teste");
		List<ItemClass> items = new ArrayList<>();
		items.add(item1);
		items.add(item2);
		form.setItems(items);
		
		Manager m = new Manager();
		
		form = m.insertNewForm(form);
		
		
		
		

//		try{
//			em.getTransaction().begin();
//			em.persist(t1);
//			em.persist(t2);
//			em.persist(f);
//			em.persist(i1);
//			em.persist(i2);
//			em.persist(c);
//			em.persist(o);
//			em.persist(io);
//			em.persist(fc);
//			em.persist(fi1);
//			em.persist(fi2);
//			em.persist(choice);
//			em.persist(text);
//			
//			em.getTransaction().commit();
//		} catch( Exception e ) {
//			System.out.printf( "Erro: %s", e.getMessage() );
//		}
	}
}

