package com.raghunadimpalli.person.solr.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.raghunadimpalli.common.core.abstracts.ComponentParams;
import com.raghunadimpalli.person.vo.PersonBasicVO;
import com.raghunadimpalli.person.vo.PersonInputVO;

public class SolrUtils implements solrConstants {
	
	private static Logger log = Logger.getLogger(SolrUtils.class);
	
	public Boolean containsSearchString(ComponentParams params)
	{
		HashMap paramMap = (params.getHttpPayloadData() instanceof HashMap) ? (HashMap) params.getHttpPayloadData(): new HashMap();
		String dataRange = (String) paramMap.get("dateRange");
		String searchString = (String) paramMap.get("searchString");
		if(searchString == null)
			return false;
		else if(dataRange == null)
			return false;
		return true;
	}
	
	public String getNiceSearchString(String searchParam){
		String result = "";
		try 
		{
			result = URLDecoder.decode(searchParam, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		if(!searchParam.equalsIgnoreCase(result)){
			//result = searchParam.replaceAll("%20", " ");
			result = "\""+result+"\"";
		}
		return result;
	}
	
	public PersonInputVO mapParamsToInput(ComponentParams params, String searchType)
	{
		PersonInputVO inputData = new PersonInputVO();
		log.info("Input Param Data : "+params.getHttpPayloadData());
		HashMap paramMap = (params.getHttpPayloadData() instanceof HashMap) ? (HashMap) params.getHttpPayloadData():new HashMap();
		String dateRange = replaceSpaces((String) paramMap.get("dateRange"));
		inputData.setLimit(Integer.parseInt((String) paramMap.get("limit")));
		inputData.setDateRange(dateRange);
		inputData.setPage(Integer.parseInt((String) paramMap.get("page")));
		inputData.setSearchString(formatSearchString((String) paramMap.get("searchString")));
		inputData.setSortField((String) paramMap.get("sort"));
		inputData.setSortType((String) paramMap.get("dir"));
		inputData.setStartIndex(Integer.parseInt((String) paramMap.get("start")));
		inputData.setSearchType(searchType);
		if(dateRange != null){
			inputData.setFromDate(getFromDate(dateRange));
			inputData.setToDate(getToDate(dateRange));
			inputData.setBusinessDateFilterForSolr(dateFilter(inputData.getFromDate(), inputData.getToDate()));
		}
		return inputData;
	}
	
	private String formatSearchString(String searchString)
	{
		String formattedStr = "";
		if(searchString.indexOf("%20") > -1
				|| searchString.indexOf(" ") > -1){
			formattedStr = "\""+searchString+"\"";
			formattedStr = replaceSpaces(formattedStr);
		}
		else{
			formattedStr = searchString;
		}
		return formattedStr;
	}
	
	private static String replaceSpaces(String str)
	{
		StringBuffer strBuffer = new StringBuffer();
		for(int i = 0;i < str.length(); i++)
		{
			if(str.charAt(i) == '%'
					&& str.charAt(i+1) == '2'
					&& str.charAt(i+2) == '0'){
				strBuffer.append(" ");
				i++;
				i++;
			}
			else{
				strBuffer.append(str.charAt(i));
			}
		}
		return strBuffer.toString();
	}
	
	private static String removeSpaces(String str)
	{
		StringBuffer strBuffer = new StringBuffer();
		for(int i = 0;i < str.length(); i++)
		{
			if(str.charAt(i) == '%'
					&& str.charAt(i+1) == '2'
					&& str.charAt(i+2) == '0'){
				strBuffer.append("");
				i++;
				i++;
			}
			else{
				strBuffer.append(str.charAt(i));
			}
		}
		return strBuffer.toString();
	}
	
	private String dateFilter(Date fromDate, Date toDate)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
		String fromDateStr = dateFormat.format(fromDate)+"T00:00:00Z";
		String toDateStr = dateFormat.format(toDate)+"T00:00:00Z";
		return fromDateStr+" TO "+toDateStr;
	}
	
	public PersonInputVO mapParamsToInputExcel(ComponentParams params, String searchType)
	{
		PersonInputVO inputData = new PersonInputVO();
		String dateRange = replaceSpaces((String) params.getParameter("searchParam"));
		inputData.setDateRange(dateRange);
		inputData.setSearchString(formatSearchString((String) params.getParameter("searchParam")));
		inputData.setSearchType(searchType);
		if(dateRange != null){
			inputData.setFromDate(getFromDate(dateRange));
			inputData.setToDate(getToDate(dateRange));
			inputData.setBusinessDateFilterForSolr(dateFilter(inputData.getFromDate(), inputData.getToDate()));
		}
		return inputData;
	}
	
	private Date getFromDate(String dateRange)
	{
		int toIndex = dateRange.indexOf("to");
		Date fromDate = new Date();
		Date currentDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		try
		{
			if(toIndex > -1)
			{
				String fromDateString = dateRange.substring(0, toIndex-1);
				fromDate = dateFormat.parse(fromDateString);
			}
			else
			{
				fromDate = DateUtils.addMonths(currentDate, -(Integer.parseInt(dateRange)));
			}
		}
		catch(Exception e)
		{
			log.error("getFromDate Exception "+e.getMessage());
		}
		return fromDate;
	}
	
	private Date getToDate(String dateRange)
	{
		int toIndex = dateRange.indexOf("to");
		Date toDate = new Date();
		Date currentDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		try
		{
			if(toIndex > -1)
			{
				String toDateString = dateRange.substring(toIndex+3, dateRange.length());
				toDate = dateFormat.parse(toDateString);
			}
		}
		catch(Exception e)
		{
			log.error("getFromDate Exception "+e.getMessage());
		}
		return toDate;
	}
	
	public static boolean checkFieldForPersonBasicVO(PersonBasicVO object, String fieldName, Object fieldValue)
	{
		Class<?> clazz = object.getClass();
		while(clazz != null)
		{
			try
			{
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(object, fieldValue);
				return true;
			}
			catch (NoSuchFieldException e)
			{
				clazz = clazz.getSuperclass();
			}
			catch (Exception e)
			{
				throw new IllegalStateException(e);
			}
		}
		return false;
	}
	
	public String getJavaFieldOnSolrField(String solrField)
	{
		String javaField = solrField.replaceAll("personText_", "");
		return javaField;
	}
	
	public String getSolrFieldOnJavaField(String javaField)
	{
		String solrField = "";
		if(javaField.indexOf("Text") > 0)
		{
			javaField = javaField.replaceAll("Text", "");
			solrField = "person_"+javaField;
		}
		else
		{
			solrField = "personText_"+javaField;
		}
		return solrField;
	}
	
	public String formatData(String strCurrFieldOrigValue)
	{
		if(strCurrFieldOrigValue == null || strCurrFieldOrigValue.equalsIgnoreCase(NULL) || strCurrFieldOrigValue.equals(HTML_SPACE))
		{
			return BLANK;
		}
		return strCurrFieldOrigValue.replaceAll(DOUBLE_QUOTE, DOUBLE_QUOTE + DOUBLE_QUOTE);
	}
}
