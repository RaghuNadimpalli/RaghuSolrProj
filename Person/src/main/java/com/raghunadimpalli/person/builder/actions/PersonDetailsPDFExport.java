package com.raghunadimpalli.person.builder.actions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.raghunadimpalli.cc.core.components.DefaultComponentResponse;
import com.raghunadimpalli.cc.core.exceptions.ApplicationException;
import com.raghunadimpalli.common.core.abstracts.AbstractPDFExportBuilder;
import com.raghunadimpalli.common.core.abstracts.ComponentParams;
import com.raghunadimpalli.common.core.abstracts.ComponentResponse;
import com.raghunadimpalli.person.solr.util.solrConstants;
import com.raghunadimpalli.person.vo.PersonExtendedVO;

@Component("action.personDetailsPdfExport")
public class PersonDetailsPDFExport extends AbstractPDFExportBuilder<ComponentResponse<String>> implements solrConstants{

	static final Logger logger = Logger.getLogger(PersonDetailsPDFExport.class);
	private static Font fontTableHeader = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	
	@SuppressWarnings("unchecked")
	@Override
	public ComponentResponse<String> performAction(List<?> data,
			Document document,PdfWriter writer,ComponentParams params, 
			ComponentResponse<String> t) throws ApplicationException
	{
		String searchString = params.getParameter("searchParam");
		
		List<PersonExtendedVO> detailsList = (List<PersonExtendedVO>) data;
		Map<String, String> personDetailInfo = new HashMap<String, String>();
		personDetailInfo = (Map<String, String>) detailsList.get(0);
		
		//Fonts
		Font fontTitle = new Font(FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);
		Font fontTag = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
		
		try
		{
			Paragraph paraHeading = new Paragraph("Person Search");
			paraHeading.setAlignment(Element.ALIGN_CENTER);
			Font fontHeading = new Font(FontFamily.HELVETICA, 10, Font.UNDERLINE,BaseColor.BLACK);
			paraHeading.setFont(fontHeading);
			document.add(paraHeading);
			
			paraHeading = new Paragraph(" ");
			document.add(paraHeading);
			
			PdfPTable table = new PdfPTable(2);
			PdfPCell c1 = new PdfPCell(new Phrase("Field", fontTableHeader));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(c1);
			
			c1 = new PdfPCell(new Phrase("Value", fontTableHeader));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(c1);
			
			if(params.getParameter("tab").equals("detailed"))
			{
				String[] excelHeader = EXCEL_EXPANDED_DETAILS_HEADER_FIELDS;
				for(int i = 0;i<=excelHeader.length - 1;i++)
				{
					table.addCell(createLabelCell(excelHeader[i]));
					table.addCell(createValueCell(personDetailInfo.get(excelHeader[i]),searchString));
				}
			}
			else
			{
				String[] excelHeader = EXCEL_DETAILS_HEADER_FIELDS;
				for(int i = 0;i<=excelHeader.length - 1;i++)
				{
					table.addCell(createLabelCell(excelHeader[i]));
					table.addCell(createValueCell(personDetailInfo.get(excelHeader[i]),searchString));
				}
			}
			document.add(table);
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
		return new DefaultComponentResponse<String>(true, "PDF Exported");
	}
		
	private static PdfPCell createLabelCell(String text)
	{
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
		//Create Cell
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		//Set Style
		//Alignment
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		//Background Color
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		//Height
		cell.setMinimumHeight(18f);
		return cell;
	}
	
	private static PdfPCell createValueCell(String text, String strSearchString)
	{
		Boolean matchBool = false;
		while (strSearchString.contains(SPACE + SPACE))
		{
			strSearchString = strSearchString.replaceAll(SPACE + SPACE, SPACE);
		}
		String[] strTokens = strSearchString.toLowerCase().split(SPACE);
		Set<String> setTokens = new HashSet<String>(Arrays.asList(strTokens));
		Iterator<String> iterator = setTokens.iterator();
		while(iterator.hasNext() && text != null)
		{
			String strToken = iterator.next();
			strToken = strToken.trim();
			if(text.toLowerCase().contains(strToken))
				matchBool = true;
			else
				break;
		}
		
		//Font
		Font font = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
		
		//Create Cell
		PdfPCell cell = new PdfPCell(new Phrase(text,font));
		
		//Set Style
		//Alignment
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		if(matchBool){
			//Background Color
			cell.setBackgroundColor(BaseColor.YELLOW);
		}
		
		//Height
		cell.setMinimumHeight(18f);
		return cell;
	}
}