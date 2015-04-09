package com.raghunadimpalli.cc.core.solr;

import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServer;
import org.springframework.context.ApplicationContextException;

public class SolrUrlFactory {
	
	private Map<String, SolrServer> serverMap = new HashMap<String, SolrServer>();
	
	public SolrServer getSolrServer(String solrServerIdentifier)
	{
		SolrServer solrServer = this.serverMap.get(solrServerIdentifier);
		if(solrServer == null){
			throw new ApplicationContextException("Could not locate solrServer for identifier "+solrServerIdentifier);
		}
		return solrServer;
	}
	
	public Map<String, SolrServer> getServerMap()
	{
		return serverMap;
	}
	
	public void setServerMap(Map<String, SolrServer> serverMap)
	{
		this.serverMap = serverMap;
	}
}