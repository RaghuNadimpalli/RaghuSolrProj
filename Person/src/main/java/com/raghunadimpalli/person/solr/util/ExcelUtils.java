package com.raghunadimpalli.person.solr.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;



public class ExcelUtils implements solrConstants {

	static final Logger logger = Logger.getLogger(ExcelUtils.class);
	
	public CellStyle headerCellStyle;
	public CellStyle textCellStyle;
	public CellStyle currencyTextCellStyle;
	public CellStyle dateTextCellStyle;
	public CellStyle numberTextCellStyle;
	public CellStyle yellowTextCellStyle;
	public CellStyle currencyYellowTextCellStyle;
	public CellStyle dateYellowTextCellStyle;
	public CellStyle numberYellowTextCellStyle;
	public Workbook wb;
	
	public SimpleDateFormat sdfSource = new SimpleDateFormat("dd-MMM-yyyy");
	public DataFormat cf;
	public short currencyFormat;
	public short dateFormat;
	public short numberFormat;
	
	public String nullValueCheck(String str)
	{
		if(str == null)
			return "";
		else
			return str;
	}
	
	public void createHeaderCell(Workbook wb, Row row, int column,String cellValue, CellStyle cellStyle)
	{
		Cell cell = row.createCell(column);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(cellValue);
	}
	
	public void createTextCell(Workbook wb, Row row, int column, String cellValue, String alignStr, CellStyle cellStyle, String formatType)
	{
		Cell cell = row.createCell(column);
		Date dtSourceDate = new Date();
		
		if(alignStr.equals("left"))
			cellStyle.setAlignment(cellStyle.ALIGN_LEFT);
		else if(alignStr.equals("right"))
			cellStyle.setAlignment(cellStyle.ALIGN_RIGHT);
		else
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
		
		if(formatType.equals("currency")){
			Double cellNum = Double.parseDouble(cellValue.replaceAll(",", ""));
			//cellStyle.setDataFormat(currencyFormat);
			cell.setCellValue(cellNum);
			cell.setCellStyle(cellStyle);
		}
		else if(formatType.equals("dateFormat"))
		{
			try
			{
				dtSourceDate = sdfSource.parse(cellValue);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			//cellStyle.setDataFormat(dateFormat);
			cell.setCellValue(dtSourceDate);
			cell.setCellStyle(cellStyle);
		}
		else
		{
			cell.setCellValue(cellValue);
			cell.setCellStyle(cellStyle);
		}
	}
	
	public void createHighlightTextCell(Workbook wb, Row row, int column, String cellValue, String alignStr, CellStyle cellStyle, String formatType)
	{
		Cell cell = row.createCell(column);
		Date dtSourceDate = new Date();
		
		if(alignStr.equals("left"))
			cellStyle.setAlignment(cellStyle.ALIGN_LEFT);
		else if(alignStr.equals("right"))
			cellStyle.setAlignment(cellStyle.ALIGN_RIGHT);
		else
			cellStyle.setAlignment(cellStyle.ALIGN_CENTER);
		
		if(formatType.equals("currency") && !cellValue.equals("")){
			Double cellNum = Double.parseDouble(cellValue.replaceAll(",", ""));
			//cellStyle.setDataFormat(currencyFormat);
			cell.setCellValue(cellNum);
			cell.setCellStyle(cellStyle);
		}
		else if(formatType.equals("dateFormat") && !cellValue.equals(""))
		{
			try
			{
				dtSourceDate = sdfSource.parse(cellValue);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			//cellStyle.setDataFormat(dateFormat);
			cell.setCellValue(dtSourceDate);
			cell.setCellStyle(cellStyle);
		}
		else
		{
			cell.setCellValue(cellValue);
			cell.setCellStyle(cellStyle);
		}
	}
	
	public void highlightSearchString(Workbook wb, Row row, short column, String cellValue, String strSearchString, String alignStr, String formatType)
	{
		Boolean matchBool = false;
		
		while (strSearchString.contains(SPACE + SPACE)) {
			strSearchString = strSearchString.replaceAll(SPACE+SPACE, SPACE); 
		}
		String[] strTokens = strSearchString.toLowerCase().split(SPACE);
		Set<String> setTokens = new HashSet<String>(Arrays.asList(strTokens));
		Iterator<String> iterator = setTokens.iterator();
		while(iterator.hasNext() && cellValue != null)
		{
			String strToken = iterator.next();
			strToken = strToken.trim();
			if(cellValue.toLowerCase().contains(strToken))
				matchBool = true;
			else
				break;
		}
		
		if(matchBool)
		{
			if(formatType.equals("currency"))
				createHighlightTextCell(wb, row, column, cellValue, alignStr, currencyYellowTextCellStyle, formatType);
			else if(formatType.equals("dateFormat"))
				createHighlightTextCell(wb, row, column, cellValue, alignStr, dateYellowTextCellStyle, formatType);
			else if(formatType.equals("numberFormat"))
				createHighlightTextCell(wb, row, column, cellValue, alignStr, numberYellowTextCellStyle, formatType);
			else 
				createHighlightTextCell(wb, row, column, cellValue, alignStr, yellowTextCellStyle, formatType);
		}
		else{
			if(formatType.equals("currency"))
				createHighlightTextCell(wb, row, column, cellValue, alignStr, currencyTextCellStyle, formatType);
			else if(formatType.equals("dateFormat"))
				createHighlightTextCell(wb, row, column, cellValue, alignStr, dateTextCellStyle, formatType);
			else if(formatType.equals("numberFormat"))
				createHighlightTextCell(wb, row, column, cellValue, alignStr, numberTextCellStyle, formatType);
			else 
				createHighlightTextCell(wb, row, column, cellValue, alignStr, textCellStyle, formatType);
		}
		
	}
	
	public void updateStyles()
	{
		Font headerFont = wb.createFont();
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setFontName("Arial");
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		cf = wb.createDataFormat();
		dateFormat = cf.getFormat("dd-MMM-yyyy");
		currencyFormat = cf.getFormat("_(* $#,##0.00_)");
		numberFormat = cf.getFormat("0");
		
		Font textFont = wb.createFont();
		textFont.setFontHeightInPoints((short) 8);
		textFont.setFontName("MS Sans Serif");
		
		headerCellStyle = wb.createCellStyle();
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setBorderTop(CellStyle.BORDER_MEDIUM_DASHED);
		headerCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		headerCellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
		headerCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerCellStyle.setFont(headerFont);
		
		textCellStyle = wb.createCellStyle();
		textCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		textCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		textCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		textCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		textCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		textCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		textCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		textCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		textCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		textCellStyle.setFont(textFont);
		
		currencyTextCellStyle = wb.createCellStyle();
		currencyTextCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		currencyTextCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		currencyTextCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		currencyTextCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		currencyTextCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		currencyTextCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		currencyTextCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		currencyTextCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		currencyTextCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		currencyTextCellStyle.setFont(textFont);
		currencyTextCellStyle.setDataFormat(currencyFormat);
		
		dateTextCellStyle = wb.createCellStyle();
		dateTextCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		dateTextCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		dateTextCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		dateTextCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		dateTextCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		dateTextCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		dateTextCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		dateTextCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		dateTextCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		dateTextCellStyle.setFont(textFont);
		dateTextCellStyle.setDataFormat(dateFormat);
		
		numberTextCellStyle = wb.createCellStyle();
		numberTextCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		numberTextCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		numberTextCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		numberTextCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		numberTextCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		numberTextCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		numberTextCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		numberTextCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		numberTextCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		numberTextCellStyle.setFont(textFont);
		numberTextCellStyle.setDataFormat(numberFormat);
		
		yellowTextCellStyle = wb.createCellStyle();
		yellowTextCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		yellowTextCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		yellowTextCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		yellowTextCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		yellowTextCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		yellowTextCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		yellowTextCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		yellowTextCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		yellowTextCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		yellowTextCellStyle.setFont(textFont);
		yellowTextCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		yellowTextCellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
		yellowTextCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		currencyYellowTextCellStyle = wb.createCellStyle();
		currencyYellowTextCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		currencyYellowTextCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		currencyYellowTextCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		currencyYellowTextCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		currencyYellowTextCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		currencyYellowTextCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		currencyYellowTextCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		currencyYellowTextCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		currencyYellowTextCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		currencyYellowTextCellStyle.setFont(textFont);
		currencyYellowTextCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		currencyYellowTextCellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
		currencyYellowTextCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		currencyYellowTextCellStyle.setDataFormat(currencyFormat);
		
		dateYellowTextCellStyle = wb.createCellStyle();
		dateYellowTextCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		dateYellowTextCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		dateYellowTextCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		dateYellowTextCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		dateYellowTextCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		dateYellowTextCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		dateYellowTextCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		dateYellowTextCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		dateYellowTextCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		dateYellowTextCellStyle.setFont(textFont);
		dateYellowTextCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		dateYellowTextCellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
		dateYellowTextCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		dateYellowTextCellStyle.setDataFormat(dateFormat);
		
		numberYellowTextCellStyle = wb.createCellStyle();
		numberYellowTextCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		numberYellowTextCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		numberYellowTextCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		numberYellowTextCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		numberYellowTextCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		numberYellowTextCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		numberYellowTextCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		numberYellowTextCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		numberYellowTextCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		numberYellowTextCellStyle.setFont(textFont);
		numberYellowTextCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		numberYellowTextCellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
		numberYellowTextCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		numberYellowTextCellStyle.setDataFormat(numberFormat);
	}
}