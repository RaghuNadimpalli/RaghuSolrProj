package com.raghunadimpalli.person.vo;

import java.util.Date;

public class PersonInputVO {
	
	private int limit;
	private String dateRange;
	private Date fromDate;
	private Date toDate;
	private String sortField;
	private String sortType;
	private int startIndex;
	private int page;
	private String searchString;
	private String searchType;
	private String modifiedDateFilterForSolr;
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getDateRange() {
		return dateRange;
	}
	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getModifiedDateFilterForSolr() {
		return modifiedDateFilterForSolr;
	}
	public void setModifiedDateFilterForSolr(String modifiedDateFilterForSolr) {
		this.modifiedDateFilterForSolr = modifiedDateFilterForSolr;
	}
	
	

}
