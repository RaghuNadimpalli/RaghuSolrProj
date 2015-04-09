package com.raghunadimpalli.core.main;

import com.raghunadimpalli.core.solr.PersonSolr;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PersonMain {

	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-solrIndex.xml");
		PersonSolr personSolr = (PersonSolr) context.getBean("personSolr");
		try{
			personSolr.deleteAllRecords();
			System.out.println("Delete All Records");
			int totalIndexCount = personSolr.IndexAllData();
			System.out.println("Index Completed for "+totalIndexCount+" records");
			//personSolr.genericSearch();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Error :"+e.getMessage());
		}
	}
}