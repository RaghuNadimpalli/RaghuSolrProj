package com.raghunadimpalli.core.solr;

public interface PersonSolr {
	public int IndexAllData();
	public void deleteAllRecords();
	public int fetchRecordCount();
	public void genericSearch();
}
