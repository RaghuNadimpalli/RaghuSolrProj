package com.raghunadimpalli.person.solr;

import java.util.Map;

import com.raghunadimpalli.person.vo.PersonExtendedVO;
import com.raghunadimpalli.person.vo.PersonInputVO;

public interface PersonSolr {
	public Map<String, Object> getBasicSearchResults(PersonInputVO inputVO);
	public Map<String, Object> getBasicExcelResults(PersonInputVO inputVO);
	public PersonExtendedVO getExtendedDetailsOfRecords(String uniqueId, String searchString);
	public PersonExtendedVO getExtendedDetailsOfExportRecords(String uniqueId);
}
