package com.raghunadimpalli.person.solr.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;

import com.raghunadimpalli.cc.core.solr.SolrUrlFactory;
import com.raghunadimpalli.person.solr.PersonSolr;
import com.raghunadimpalli.person.solr.util.SolrUtils;
import com.raghunadimpalli.person.solr.util.solrConstants;
import com.raghunadimpalli.person.vo.PersonBasicVO;
import com.raghunadimpalli.person.vo.PersonExtendedVO;
import com.raghunadimpalli.person.vo.PersonInputVO;


public class PersonSolrImpl implements PersonSolr, solrConstants {

//	@Autowired
//	public SolrServerInitializer solrServerInitializer;
//	
//	private SolrServer solrServer;
	
	@Autowired
	public SolrUrlFactory solrFactory;
	
	@Autowired
	private SolrUtils solrUtils;
	
//	public void setSolrServerInitializer(SolrServerInitializer solrServerInitializer) {
//		try{
//			this.solrServerInitializer = solrServerInitializer;
//			this.solrServer = solrServerInitializer.getPersonSolrInstance();
//		}
//		catch(Exception e){
//			throw new RuntimeException();
//		}
//	}
	
	public String getLastUpdatedDate()
	{
		List<PersonExtendedVO> personDetailsList;
		SolrQuery query;
		try
		{
			query = new SolrQuery();
			query.setQuery("*:*").
			setRows(1);
			query.setSort(solrUtils.getJavaFieldOnSolrField("modifiedDateText"), ORDER.desc);
			QueryResponse rsp = this.solrFactory.getSolrServer("person").query(query);
			personDetailsList = rsp.getBeans(PersonExtendedVO.class);
			PersonExtendedVO personVO = personDetailsList.get(0);
			return personVO.getModifiedDateText();
		}
		catch(Exception e)
		{
			throw new RuntimeException();
		}
	}
	
	@Override
	public Map<String, Object> getBasicSearchResults(PersonInputVO inputVO)
	{
		Map<String, Object> solrDataMap = new HashMap(); 
		List<PersonBasicVO> personBasicList;
		SolrQuery query;
		try
		{
			query = new SolrQuery();
			query.setQuery("person_allSearchFields:"+inputVO.getSearchString()).
			setFilterQueries("personText_modifiedDate:["+inputVO.getModifiedDateFilterForSolr()+"]").
			setStart(inputVO.getStartIndex()).
			setRows(inputVO.getLimit()).
			setHighlight(true).
			setHighlightRequireFieldMatch(false).
			setHighlightSnippets(5).
			setHighlightFragsize(5000).
			setHighlightSimplePre("<span class=\"yellowbg\">").
			setHighlightSimplePost("</span>");
			query.setParam("h1.highlightMultiTerm", true);
			query.setParam("h1.useFastVectorHighligher", true);
			query.setParam("h1.fragmenter", "regex");
			query.setParam("h1.fragmenterBuilder", "colored");
			query.setParam("h1.fragListBuilder", "simple");
			query.setParam("h1.phaseLimit", "1000");
			query.setParam("h1.f1", "*");
			query.setParam("h1.usePhraseHighlighter", true);
			
			if(inputVO.getSortField() != null && !inputVO.getSortField().equals("threadid"))
			{
				if(inputVO.getSortType()!=null && inputVO.getSortType().equals(ASC))
					query.setSort(solrUtils.getSolrFieldOnJavaField(inputVO.getSortField()), ORDER.asc);
				else if(inputVO.getSortType()!=null && inputVO.getSortType().equals(DESC))
					query.setSort(solrUtils.getSolrFieldOnJavaField(inputVO.getSortField()), ORDER.desc);
			}
			else {
				query.setSort(solrUtils.getSolrFieldOnJavaField("modifiedDateText"), ORDER.asc);
			}
			
			//QueryResponse rsp = solrServer.query(query);
			QueryResponse rsp = this.solrFactory.getSolrServer("person").query(query);
			personBasicList = rsp.getBeans(PersonBasicVO.class);
			solrDataMap.put("QTime", rsp.getQTime());
			solrDataMap.put("numRecords", rsp.getResults().getNumFound());
			for(int i=0;i<personBasicList.size();i++)
			{
				PersonBasicVO personVO = personBasicList.get(i);
				String id = personVO.getId();
				Map highlightSnippets = rsp.getHighlighting().get(id);
				Iterator it = highlightSnippets.entrySet().iterator();
				while(it.hasNext())
				{
					Map.Entry pairs = (Map.Entry) it.next();
					String key = (String) pairs.getKey();
					List pairList = (List) pairs.getValue();
					String value = (String) pairList.get(0);
					key = solrUtils.getJavaFieldOnSolrField(key);
					solrUtils.checkFieldForPersonBasicVO(personVO, key, value);
				}
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		solrDataMap.put("DataList", personBasicList);
		return solrDataMap;
	}
	
	
	@Override
	public Map<String, Object> getBasicExcelResults(PersonInputVO inputVO)
	{
		Map<String, Object> solrDataMap = new HashMap(); 
		List<PersonBasicVO> personExportList;
		SolrQuery query;
		try
		{
			query = new SolrQuery();
			query.setQuery("person_allSearchFields:"+inputVO.getSearchString()).
			setFilterQueries("personText_modifiedDate:["+inputVO.getModifiedDateFilterForSolr()+"]").
			setRows(5000);
			
			if(inputVO.getSortField() != null && !inputVO.getSortField().equals("threadid"))
			{
				if(inputVO.getSortType()!=null && inputVO.getSortType().equals(ASC))
					query.setSort(solrUtils.getSolrFieldOnJavaField(inputVO.getSortField()), ORDER.asc);
				else if(inputVO.getSortType()!=null && inputVO.getSortType().equals(DESC))
					query.setSort(solrUtils.getSolrFieldOnJavaField(inputVO.getSortField()), ORDER.desc);
			}
			else {
				query.setSort(solrUtils.getSolrFieldOnJavaField("modifiedDateText"), ORDER.asc);
			}
			
			//QueryResponse rsp = solrServer.query(query);
			QueryResponse rsp = this.solrFactory.getSolrServer("person").query(query);
			personExportList = rsp.getBeans(PersonBasicVO.class);
			solrDataMap.put("QTime", rsp.getQTime());
			solrDataMap.put("numRecords", rsp.getResults().getNumFound());
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		solrDataMap.put("DataList", personExportList);
		return solrDataMap;
	}
	
	@Override
	public PersonExtendedVO getExtendedDetailsOfRecords(String uniqueId, String searchString)
	{
		PersonExtendedVO details = new PersonExtendedVO();
		List<PersonExtendedVO> personDetailsList;
		Map<String, Object> solrDataMap = new HashMap(); 
		SolrQuery query;
		try
		{
			query = new SolrQuery();
			query.setQuery("person_allSearchFields:"+searchString).
			setFilterQueries("personText_id:"+uniqueId);
			
			//QueryResponse rsp = solrServer.query(query);
			QueryResponse rsp = this.solrFactory.getSolrServer("person").query(query);
			personDetailsList = rsp.getBeans(PersonExtendedVO.class);
			solrDataMap.put("QTime", rsp.getQTime());
			solrDataMap.put("numRecords", rsp.getResults().getNumFound());
			details = (PersonExtendedVO) personDetailsList.get(0);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		return details;
	}
	
	public PersonExtendedVO getExtendedDetailsOfExportRecords(String uniqueId)
	{
		PersonExtendedVO details = new PersonExtendedVO();
		List<PersonExtendedVO> personDetailsList;
		SolrQuery query;
		try
		{
			query = new SolrQuery();
			query.setQuery("personText_id:"+uniqueId);
			//QueryResponse rsp = solrServer.query(query);
			QueryResponse rsp = this.solrFactory.getSolrServer("person").query(query);
			personDetailsList = rsp.getBeans(PersonExtendedVO.class);
			details = (PersonExtendedVO) personDetailsList.get(0);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		return details;
	}
}