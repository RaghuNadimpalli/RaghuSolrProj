package com.raghunadimpalli.common.db.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final Date todayDate = new Date();
	public static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	public static SimpleDateFormat dtAndTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public static SimpleDateFormat sqlDtAndTimeFormatter = new SimpleDateFormat("yyyy-MM=dd hh:mm:ss");
	
	public static Date getFormatterTodayDate()
	{
		Date toDate = null;
		try
		{
			toDate = formatter.parse(formatter.format(new Date()));
		}
		catch(ParseException ignored){}
		
		return toDate;
	}
	
	public static Date getTodayDateAndTime()
	{
		return todayDate;
	}
	
	public static String getDateAndTimeStr(Date date)
	{
		return ((date != null)? dtAndTimeFormatter.format(date) : null);
	}
	
	public static String getSQLDateAndTimeStr(Date date)
	{
		return sqlDtAndTimeFormatter.format(date);
	}
	
}
