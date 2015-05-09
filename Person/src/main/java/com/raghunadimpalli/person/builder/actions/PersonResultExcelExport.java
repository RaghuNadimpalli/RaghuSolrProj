package com.raghunadimpalli.person.builder.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.raghunadimpalli.person.vo.PersonBasicVO;

@Component("action.personResultExcelExport")
public class PersonResultExcelExport extends AbstractExcelExportBuilder<ComponentResponse<String>> implements solrConstants{
	
	static final Logger logger = Logger.getLogger(PersonResultExcelExport.class);
	
	@Autowired
	private ExcelUtils excelUtils;
	
	@Autowired
	private SolrUtils solrUtils;
	
	@Override
	public ComponentResponse<String> performAction(List<?> data,Workbook workbook,ComponentParams params, ComponentResponse<String> response) throws ApplicationException{
		
		String searchString = params.getParameter("searchParam");
		Sheet sheet = workbook.createSheet("RAGZ_PERSON");
		
		@SuppressWarnings("unchecked")
		List<PersonBasicVO> detailsList = (ArrayList<PersonBasicVO>) data;
		
		excelUtils = new ExcelUtils();
		excelUtils.wb = workbook;
		excelUtils.updateStyles();
		
		//Create Header Cells
		int rowCount = 0;
		Row row = sheet.createRow(rowCount);
		
		String[] excelHeader = EXCEL_HEADER_FIELDS;
		
		excelHeader = EXCEL_HEADER_FIELDS;
		for(int i = 0; i<=excelHeader.length-1;i++)
		{
			excelUtils.createHeaderCell(workbook, row, i, excelHeader[i], excelUtils.headerCellStyle);
		}
		
		//Create data cell
		Iterator<PersonBasicVO> iterator = detailsList.iterator();
		PersonBasicVO basicVO = null;
		while(iterator.hasNext())
		{
			rowCount++;
			int ctr = 0;
			row = sheet.createRow(rowCount);
			
			//numberFormat, text, dateFormat ---> Excel Formats
			excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(basicVO.getId())), searchString, "center", "text");
			excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(basicVO.getType())), searchString, "center", "text"); 
			excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(basicVO.getTitle())), searchString, "left", "text");
			excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(basicVO.getFirstName())), searchString, "center", "text");
			excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(basicVO.getMiddleName())), searchString, "center", "text");
			excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(basicVO.getLastName())), searchString, "center", "text");
			excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(basicVO.getSuffix())), searchString, "center", "text");
			excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(basicVO.getEmailPromotion())), searchString, "center", "text");
			excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(basicVO.getAdditionalInfo())), searchString, "center", "text");
			excelUtils.highlightSearchString(workbook, row, (short) ctr++, solrUtils.formatData(excelUtils.nullValueCheck(basicVO.getModifiedDateText())), searchString, "center", "dateFormat");
		}
		
		//Auto Size the column widths
		for(int columnIndex = 0; columnIndex <= excelHeader.length - 1;columnIndex++)
		{
			sheet.autoSizeColumn(columnIndex);
		}
		
		return new DefaultComponentResponse<String>(true, "Excel Export Successful");
		
	}

}
