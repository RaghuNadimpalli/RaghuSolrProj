package com.raghunadimpalli.person.builder.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.raghunadimpalli.cc.core.exceptions.ApplicationException;
import com.raghunadimpalli.common.core.abstracts.AbstractComponentDataBuilder;
import com.raghunadimpalli.common.core.abstracts.ComponentParams;
import com.raghunadimpalli.person.solr.impl.PersonSolrImpl;
import com.raghunadimpalli.person.solr.util.SolrUtils;
import com.raghunadimpalli.person.solr.util.solrConstants;
import com.raghunadimpalli.person.vo.PersonBasicVO;
import com.raghunadimpalli.person.vo.PersonInputVO;

@Component("mvc.persondetails")
public class PersonBuilder extends AbstractComponentDataBuilder<PersonBasicVO> implements solrConstants {

	private static Logger log = Logger.getLogger(PersonBuilder.class);
	
	@Autowired
	private SolrUtils solrUtils;
	
	@Autowired
	private PersonSolrImpl personSolr;
	
	@Override
	public List<PersonBasicVO> buildComponentData(ComponentParams params) throws ApplicationException
	{
		List<PersonBasicVO> serviceList = new ArrayList<PersonBasicVO>();
		Map<String, Object> solrDataMap = new HashMap<String, Object>();
		String dataType = "";
		if(params.getParameter("searchParam") != null
				&& params.getParameter("dateRange") != null){
			dataType = "Excel";
		}
		else if(params.getParameter("searchParam") == null
				&& params.getParameter("dateRange") == null){
			if(solrUtils.containsSearchString(params))
			dataType = "Grid";
		}
		
		if(dataType.equals("Grid"))
		{
			PersonInputVO inputData = solrUtils.mapParamsToInput(params, BASIC_UI_SEARCH);
			solrDataMap = personSolr.getBasicSearchResults(inputData,params);
			serviceList = (List<PersonBasicVO>) solrDataMap.get("DataList");
			long numRecords = (long) solrDataMap.get("numRecords");
			params.setTotalCount(numRecords);
		}
		else if(dataType.equals("Excel"))
		{
			PersonInputVO inputData = solrUtils.mapParamsToInput(params, EXCEL_BASIC_UI_SEARCH);
			solrDataMap = personSolr.getBasicExcelResults(inputData,params);
			serviceList = (List<PersonBasicVO>) solrDataMap.get("DataList");
			long numRecords = (long) solrDataMap.get("numRecords");
			params.setTotalCount(numRecords);
		}
		return serviceList;
	}
	
//	@Override
//	public Class<?> getComponentDataTransferObject() {
//		return Custmo
//	}
}