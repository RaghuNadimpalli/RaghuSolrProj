package com.raghunadimpalli.core.solr;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

public class SolrServerInitializer {
	
	private String solrURL;
	
	private SolrServer personSolrServer = null;
	
	public SolrServerInitializer(String solrURL){
		this.solrURL = solrURL;
		init();
	}
	
	private synchronized void init(){
		if(personSolrServer == null){
			personSolrServer = null;
			try
			{
				personSolrServer = new HttpSolrServer(solrURL);
			}
			catch(Exception e){
				//e.getMessage()
			}
		}
	}
	
	public SolrServer getPersonSolrInstance() throws Exception{
		if(personSolrServer == null){
			throw new Exception("SolrServer was not initialized to return the instance");
		}
		return personSolrServer;
	}
	
	public String getSolrURL() {
		return solrURL;
	}

	public void setSolrURL(String solrURL) {
		this.solrURL = solrURL;
	}
}