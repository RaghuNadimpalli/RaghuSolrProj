package com.raghunadimpalli.person.solr;

import java.util.Map;

import com.raghunadimpalli.common.core.abstracts.ComponentParams;
import com.raghunadimpalli.person.vo.PersonExtendedVO;
import com.raghunadimpalli.person.vo.PersonInputVO;

public interface PersonSolr {
	public Map<String, Object> getBasicSearchResults(PersonInputVO inputVO, ComponentParams params);
	public Map<String, Object> getBasicExcelResults(PersonInputVO inputVO, ComponentParams params);
	public PersonExtendedVO getExtendedDetailsOfRecords(String uniqueId, String searchString, ComponentParams params);
	public PersonExtendedVO getExtendedDetailsOfExportRecords(String uniqueId, ComponentParams params);
}
