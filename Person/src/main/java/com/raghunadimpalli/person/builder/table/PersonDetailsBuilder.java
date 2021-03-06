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
import com.raghunadimpalli.person.vo.PersonExtendedVO;

@Component("mvc.infoGrid")
public class PersonDetailsBuilder extends AbstractComponentDataBuilder<Map<String, String>> {

	private static Logger log = Logger.getLogger(PersonDetailsBuilder.class);
	
	@Autowired
	private SolrUtils solrUtils;
	
	@Autowired
	private PersonSolrImpl personSolr;
	
	@Override
	public List<Map<String, String>> buildComponentData(ComponentParams params) throws ApplicationException
	{
		List<Map<String, String>> serviceList = new ArrayList<Map<String, String>>();
		HashMap paramMap = (params.getHttpPayloadData() instanceof HashMap) ? (HashMap) params.getHttpPayloadData(): new HashMap();
		String dataType = params.getParameter("type");
		String searchString = params.getParameter("searchParam");
		String dateRange = params.getParameter("dateRange");
		
//		if(((String) params.getParameter("type")).equals("export"))
//			dataType = "ExcelPdf";
//		else
//			dataType = "Grid";
		
		PersonExtendedVO details = new PersonExtendedVO();
		if(dataType == null)
		{
			String uniqueId = (String) paramMap.get("name");
			details = personSolr.getExtendedDetailsOfRecords(uniqueId, searchString, params);
		}
		else if(dataType.equals("export"))
		{
			String uniqueId = (String) params.getParameter("id");
			details = personSolr.getExtendedDetailsOfExportRecords(uniqueId, params);
		}	
		
		Map<String, String> personDetailInfo = new HashMap<String, String>();
		personDetailInfo.put("Id", details.getId());
		personDetailInfo.put("Type", details.getType());
		personDetailInfo.put("Title", details.getTitle());
		personDetailInfo.put("FirstName", details.getFirstName());
		personDetailInfo.put("MiddleName", details.getMiddleName());
		personDetailInfo.put("LastName", details.getLastName());
		personDetailInfo.put("Suffix", details.getSuffix());
		personDetailInfo.put("EmailPromotion", details.getEmailPromotion());
		personDetailInfo.put("AdditionalInfo", details.getAdditionalInfo());
		personDetailInfo.put("ModifiedDate", details.getModifiedDateText());
		
		//details Stuff
		//personDetailInfo.put("ModifiedDate", details.getModifiedDateText());
		
		//extended VO Stuff
		if(dataType != null && dataType.equals("export"))
		{
			//personDetailInfo.put("ModifiedDate", details.getModifiedDateText());
			//...........
		}
		serviceList.add(personDetailInfo);
		return serviceList;
	}
}