package com.raghunadimpalli.person.solr.util;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

public interface solrConstants {
	
	String BASIC_UI_SEARCH = "BASIC_UI_SEARCH";
	String DETAIL_UI_SEARCH = "DETAIL_UI_SEARCH";
	String EXCEL_BASIC_UI_SEARCH = "EXCEL_BASIC_UI_SEARCH";
	String EXCEL_DETAIL_UI_SEARCH = "EXCEL_DETAIL_UI_SEARCH";
	String PDF_BASIC_UI_SEARCH = "PDF_BASIC_UI_SEARCH";
	String PDF_DETAIL_UI_SEARCH = "PDF_DETAIL_UI_SEARCH";

	String COMMA = ",";
	String NEWLINE = "\n";
	String DOUBLE_QUOTE = "\"";
	String HTML_SPACE = "&nbsp;";
	String BLANK = "";
	String AMPERSAND = "&";
	String ASTERIX = "\\*";
	String SPACE = " ";
	String PIPE = "|";
	String NULL = "NULL";
	
	String ASC = "ASC";
	String DESC = "DESC";
	
	int ALLFROMYEAR = 2011;
	int ALLFROMMONTH = 11;
	int ALLFROMDAY = 11;
	String ALLFROMDATE = ALLFROMYEAR + "/" + ALLFROMMONTH + "/" + ALLFROMDAY;
	
	String[] EXCEL_HEADER_FIELDS = {
			"Type", "Title", "First Name", "Middle Name","Last Name","Suffix","Email Promotion","Additional Info","Modified Date"
	};
	
	String[] EXCEL_DETAILS_HEADER_FIELDS = {
			"Type", "Title", "First Name", "Middle Name","Last Name","Suffix","Email Promotion","Additional Info","Modified Date"
	};
	
	String[] EXCEL_EXPANDED_DETAILS_HEADER_FIELDS = {
			"Type", "Title", "First Name", "Middle Name","Last Name","Suffix","Email Promotion","Additional Info","Modified Date"
	};		
}
