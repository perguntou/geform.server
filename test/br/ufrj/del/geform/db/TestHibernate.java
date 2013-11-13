package br.ufrj.del.geform.db;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.ufrj.del.geform.bean.AnswerBean;
import br.ufrj.del.geform.bean.CollectionBean;
import br.ufrj.del.geform.bean.FormBean;
import br.ufrj.del.geform.bean.ItemBean;
import br.ufrj.del.geform.bean.OptionBean;
import br.ufrj.del.geform.bean.TypeBean;

public class TestHibernate {
	public static void main(String[] args) {


//		ItemBean item1 = new ItemBean();
//		item1.setQuestion("Qual seu endereço?");
//		item1.setType(TypeBean.TEXT);
//
//		OptionBean opt1 = new OptionBean();
//		opt1.setValue("Opção 1");
//		OptionBean opt2 = new OptionBean();
//		opt2.setValue("Opção 2");
//		OptionBean opt3 = new OptionBean();
//		opt3.setValue("Opção 3");
//
//		ItemBean item2 = new ItemBean();
//		item2.setQuestion("Qual escolha?");
//		List<OptionBean> options = new ArrayList<>();
//		options.add(opt1);
//		options.add(opt2);
//		options.add(opt3);
//		item2.setOptions(options);
//		item2.setType(TypeBean.MULTIPLE_CHOICE);
//
//		FormBean form = new FormBean();
//		form.setCreator("Diego");
//		form.setDescription("Segundo teste de inserção.");
//		form.setTimestamp(Date.valueOf("2013-10-10"));
//		form.setTitle("Teste 2");
//		List<ItemBean> items = new ArrayList<>();
//		items.add(item1);
//		items.add(item2);
//		form.setItems(items);

//		AnswerBean answer1 = new AnswerBean();
//		answer1.setAnswers("Diego");
//
//		AnswerBean answer2 = new AnswerBean();
//		answer2.setAnswers("2","4");
//		
//		List<AnswerBean> answers1 = new ArrayList<>();
//		answers1.add(answer1);
//		answers1.add(answer2);
//		
//		CollectionBean collection1 = new CollectionBean();
//		collection1.setCollector("Diego");
//		collection1.setAnswers(answers1);
//		
//		AnswerBean answer3 = new AnswerBean();
//		answer3.setAnswers("Teste");
//
//		AnswerBean answer4 = new AnswerBean();
//		answer4.setAnswers("2","3","4");
//		
//		List<AnswerBean> answers2 = new ArrayList<>();
//		answers2.add(answer3);
//		answers2.add(answer4);
//		
//		CollectionBean collection2 = new CollectionBean();
//		collection2.setCollector("Teste");
//		collection2.setAnswers(answers2);
//		
//		List<CollectionBean> collections = new ArrayList<>();
//		collections.add(collection1);
//		collections.add(collection2);
//		
//		
		DatabaseManager m = new DatabaseManager();
		
		List<CollectionBean> collectionBeanList = m.selectCollectionBeanList(12L);
		for( CollectionBean collectionBean : collectionBeanList ){
			System.out.println(collectionBean.getCollector());
			List<AnswerBean> answerBeanList = collectionBean.getItems();
			for( AnswerBean answerBean : answerBeanList ){
				List<String> answers = answerBean.getAnswers();
				for( String answer : answers ){
					System.out.println(answer);
				}
			}
		}
		
//		
//		FormBean form = m.selectFormBean(12L);
//		
//		form.setCollections(collections);
//		
//		m.insertCollections(form);
		
//		m.insertNewForm( form );

//		FormBean f = m.selectFormBean(12L);
//		
//		System.out.printf("ID %s\n", f.getId().toString());
//		System.out.printf("Tit %s\n", f.getTitle());
//		System.out.printf("Cria %s\n", f.getCreator());
//		System.out.printf("Desc %s\n", f.getDescription());
//		System.out.printf("Data %s\n", f.getTimestamp().toString());
//		
//		for(ItemBean item : f.getItems()){
//			System.out.printf("ID %s\n", item.getId());
//			System.out.printf("Perg %s\n", item.getQuestion());
//			System.out.printf("Type %s\n", item.getType().toString());
//			
//			for(OptionBean option : item.getOptions()){
//				System.out.printf("ID %s\n", option.getId());
//				System.out.printf("Valor %s\n", option.getValue());
//				
//			}
//		}
//
	}

}

