package com.raghunadimpalli.core.main;

import java.util.Calendar;
import java.util.Date;

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
			Calendar calendar = Calendar.getInstance();
			long startTime = new Date().getTime();
			System.out.println("Start Time of Indexing " + startTime);
			int totalIndexCount = personSolr.IndexAllData();
			long endTime = new Date().getTime();
			System.out.println("End Time of Indexing " + endTime);
			System.out.println("Difference Time of Indexing " + (endTime - startTime)/1000 + " seconds");
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