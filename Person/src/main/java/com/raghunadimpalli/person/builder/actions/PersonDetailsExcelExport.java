package com.raghunadimpalli.person.builder.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.raghunadimpalli.cc.core.components.DefaultComponentResponse;
import com.raghunadimpalli.cc.core.exceptions.ApplicationException;
import com.raghunadimpalli.common.core.abstracts.AbstractExcelExportBuilder;
import com.raghunadimpalli.common.core.abstracts.ComponentParams;
import com.raghunadimpalli.common.core.abstracts.ComponentResponse;
import com.raghunadimpalli.person.solr.util.ExcelUtils;
import com.raghunadimpalli.person.solr.util.SolrUtils;
import com.raghunadimpalli.person.solr.util.solrConstants;
import com.raghunadimpalli.person.vo.PersonExtendedVO;

@Component("action.personDetailsExcelExport")
public class PersonDetailsExcelExport extends AbstractExcelExportBuilder<ComponentResponse<String>> implements solrConstants{
	
	static final Logger logger = Logger.getLogger(PersonDetailsExcelExport.class);
	
	@Autowired
	private ExcelUtils excelUtils;
	
	@Autowired
	private SolrUtils solrUtils;
	
	@Override
	public ComponentResponse<String> performAction(List<?> data,Workbook workbook,ComponentParams params, ComponentResponse<String> response) throws ApplicationException{
		
		String searchString = params.getParameter("searchParam");
		Sheet sheet = workbook.createSheet("RAGZ_PERSON");
		
		@SuppressWarnings("unchecked")
		List<PersonExtendedVO> detailsList = (ArrayList<PersonExtendedVO>) data;
		Map<String, String> personDetailInfo = new HashMap<String, String>();
		personDetailInfo = (Map<String, String>) detailsList.get(0);
		
		excelUtils = new ExcelUtils();
		excelUtils.wb = workbook;
		excelUtils.updateStyles();
		
		//Create Header Cells
		int rowCount = 0;
		Row row = sheet.createRow(rowCount);
		
		String[] excelHeader = EXCEL_EXPANDED_DETAILS_HEADER_FIELDS;
		
		if(params.getParameter("tab").equals("detailed"))
		{
			excelHeader = EXCEL_EXPANDED_DETAILS_HEADER_FIELDS;
			for(int i = 0; i<=excelHeader.length-1;i++)
			{
				excelUtils.createHeaderCell(workbook, row, i, excelHeader[i], excelUtils.headerCellStyle);
			}
		}
		else
		{
			excelHeader = EXCEL_DETAILS_HEADER_FIELDS;
			for(int i = 0; i<=excelHeader.length-1;i++)
			{
				excelUtils.createHeaderCell(workbook, row, i, excelHeader[i], excelUtils.headerCellStyle);
			}
		}
		
		//Create data cell
		rowCount++;
		int ctr = 0;
		row = sheet.createRow(rowCount);
		
		//numberFormat, text, dateFormat ---> Excel Formats
		excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(personDetailInfo.get("type"))), searchString, "center", "text"); 
		excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(personDetailInfo.get("title"))), searchString, "center", "text");
		excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(personDetailInfo.get("firstName"))), searchString, "center", "text");
		excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(personDetailInfo.get("middleName"))), searchString, "center", "text");
		excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(personDetailInfo.get("lastName"))), searchString, "center", "text");
		excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(personDetailInfo.get("suffix"))), searchString, "center", "text");
		excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(personDetailInfo.get("emailPromotion"))), searchString, "center", "text");
		excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(personDetailInfo.get("additionalInfo"))), searchString, "center", "text");
		excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(personDetailInfo.get("modifiedDate"))), searchString, "center", "dateFormat");
		
		if(params.getParameter("tab").equals("detailed"))
		{
			//Additional Columns to be added for detilaed excel
			//excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(personDetailInfo.get("type"))), searchString, "center", "text"); 
		}
		
		//Auto Size the column widths
		for(int columnIndex = 0; columnIndex <= excelHeader.length - 1;columnIndex++)
		{
			sheet.autoSizeColumn(columnIndex);
		}
		
		return new DefaultComponentResponse<String>(true, "Excel Export Successful");
		
	}

}
