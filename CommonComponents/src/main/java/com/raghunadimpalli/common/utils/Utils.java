package com.raghunadimpalli.common.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class Utils {
	private static final SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
	public static Date getJavaDate(String date){
		try
		{
			return simpleDate.parse(date);
		}
		catch(Exception e){
			return null;
		}
	}
	
	public static String getStringDate(Date date)
	{
		if(date==null){
			return null;
		}
		return simpleDate.format(date);
	}
	
	public static String checkPrimaryKeyId(BigDecimal pkValue)
	{
		if(pkValue==null){
			return "";
		}
		return pkValue.toString();
	}
	
	public static String[] splitString(String str)
	{
		String strArr[]=null;
		if(StringUtils.isNotBlank(str)){
			strArr = str.split(",");
		}
		return strArr;
	}
	
	public static Long[] createStringToLongArr (String str)
	{
		String strArr[] = null;
		Long[] longArr = null;
		if(StringUtils.isNotBlank(str)){
			strArr=str.split(",");
			longArr = new Long[strArr.length];
			for(int i=0;i<strArr.length;i++)
			{
				longArr[i] = Long.parseLong(strArr[i]);
			}
		}
		return longArr;
	}
	
	public static String convertToCamelCase(String inputStr)
	{
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isNotBlank(inputStr))
		{
			String[] str = inputStr.split("_");
			boolean firstTime = true;
			for(String temp:str)
			{
				if(firstTime)
				{
					sb.append(temp.toLowerCase());
					firstTime = false;
				}
				else
				{
					temp = temp.toLowerCase();
					sb.append(StringUtils.capitalise(temp));
				}
			}
		}
		return sb.toString();
	}
}
