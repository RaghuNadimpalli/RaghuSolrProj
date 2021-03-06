package com.raghunadimpalli.common.core.override.spring;

import java.io.ByteArrayOutputStream;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.LocalizedResourceHelper;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

public abstract class AbstractPdfView extends AbstractView {
	
	public AbstractPdfView()
	{
		setContentType("application/pdf");
	}
	
	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}
	
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//IE workaround: write into byte array first
		ByteArrayOutputStream baos = createTemporaryOutputStream();
		
		//Apply preferences and build metadata.
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		prepareWriter(model,writer,request);
		buildPdfMetaData(model, document, request);
		
		//Build PDF Document
		writer.setInitialLeading(16);
		document.open();
		buildPdfDocument(model,document,writer,request,response);
		document.close();
		
		//Flush to HTTP response
		writeToResponse(response, baos);
	}
	
	protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
		throws DocumentException{
		writer.setViewerPreferences(getViewerPreferences());
	}
	
	protected int getViewerPreferences()
	{
		return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
	}
	
	protected void buildPdfMetaData(Map <String, Object> model, Document document, HttpServletRequest request){
	}
	
	protected abstract void buildPdfDocument(Map <String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
}
