package br.ufrj.del.geform.test;

import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.ufrj.del.geform.dbmodel.Choice;
import br.ufrj.del.geform.dbmodel.Collection;
import br.ufrj.del.geform.dbmodel.Form;
import br.ufrj.del.geform.dbmodel.FormCollection;
import br.ufrj.del.geform.dbmodel.FormItem;
import br.ufrj.del.geform.dbmodel.Item;
import br.ufrj.del.geform.dbmodel.ItemOption;
import br.ufrj.del.geform.dbmodel.Options;
import br.ufrj.del.geform.dbmodel.Text;
import br.ufrj.del.geform.dbmodel.Type;



public class TestHibernate {
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("geformdb");
		EntityManager em = factory.createEntityManager();
		
		Type t1 = new Type();
		t1.setValue("Texto");
		Type t2 = new Type();
		t2.setValue("Escolha Única");
		

		Form f = new Form();
		f.setCreator("Eu");
		f.setDescription("Desc.");
		f.setTitle("Meu Form");
		f.setTimestamp(Date.valueOf("2013-07-26"));
		
		
		Item i1 = new Item();
		i1.setQuestion("Qual seu nome?");
		i1.setTypeId(20L);
		
		Item i2 = new Item();
		i2.setQuestion("Qual a cor da casa?");
		i2.setTypeId(21L);
		
		Collection c = new Collection();
		c.setCollector("Recenciador");
		
		Options o = new Options();
		o.setValue("Op");
		
		Choice choice = new Choice();
		choice.setCollectionId(3L);
		choice.setItemId(10L);
		choice.setOptionId(1L);
		
		FormCollection fc = new FormCollection();
		fc.setFormId(11L);
		fc.setCollectionId(3L);
		
		FormItem fi1 = new FormItem();
		fi1.setFormId(11L);
		fi1.setItemId(9L);
		fi1.setIndex(1);
		
		FormItem fi2 = new FormItem();
		fi2.setFormId(11L);
		fi2.setItemId(10L);
		fi2.setIndex(2);
		
		ItemOption io = new ItemOption();
		io.setItemId(10L);
		io.setOptionId(1L);
		io.setIndex(1);
		
		Text text = new Text();
		text.setCollectionId(3L);
		text.setItemId(9L);
		text.setValue("Resposta");
		
		

		try{
			em.getTransaction().begin();
			em.persist(t1);
			em.persist(t2);
			em.persist(f);
			em.persist(i1);
			em.persist(i2);
			em.persist(c);
			em.persist(o);
			em.persist(io);
			em.persist(fc);
			em.persist(fi1);
			em.persist(fi2);
			em.persist(choice);
			em.persist(text);
			
			em.getTransaction().commit();
		} catch( Exception e ) {
			System.out.printf( "Erro: %s", e.getMessage() );
		}
	}
}

