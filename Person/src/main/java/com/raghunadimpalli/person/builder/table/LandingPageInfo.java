package com.raghunadimpalli.person.builder.table;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.raghunadimpalli.cc.core.exceptions.ApplicationException;
import com.raghunadimpalli.common.core.abstracts.AbstractComponentDataBuilder;
import com.raghunadimpalli.common.core.abstracts.ComponentParams;
import com.raghunadimpalli.common.dao.LandingInfo;
import com.raghunadimpalli.person.solr.impl.PersonSolrImpl;

@Component("mvc.landingInfo")
public class LandingPageInfo extends AbstractComponentDataBuilder<LandingInfo> {
	
	@Autowired
	private PersonSolrImpl personSolr;
	
	@Override
	public List<LandingInfo> buildComponentData(ComponentParams params) throws ApplicationException {
		List<LandingInfo> landingInfo = new ArrayList<LandingInfo>();
		LandingInfo info = new LandingInfo();
		info.setUserName("Raghu");
		String lastUpdatedDateStr = personSolr.getLastUpdatedDate();
		info.setLastUpdated(lastUpdatedDateStr);//"28-Jan-2015"
		landingInfo.add(info);
		return landingInfo;
	}

}
