package com.raghunadimpalli.common.core.abstracts;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;




import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.raghunadimpalli.cc.core.exceptions.ApplicationException;

public abstract class AbstractPDFExportBuilder<T> extends
		AbstractComponentActionBuilder<T> implements PDFExportActionBuilder<T> {

	@Override
	public T performAction(ComponentParams params, T t) {
		// TODO Auto-generated method stub
		return t;
	}
	
	@Override
	public T doAction(List data,Document document, PdfWriter writer, ComponentParams params, T t) throws ApplicationException{
		t = this.performActionPreprocessing(data, document,writer,t);
		t = this.performAction(data, document,writer,params,t);
		return this.performActionPostProcessing(data, document,writer,t);
	}
	
	protected T performActionPreprocessing(List<?> data,Document document, PdfWriter writer, T t) {
		return t;
	}
	
	abstract public T performAction(List<?> data,Document document,PdfWriter writer,ComponentParams params, T t) throws ApplicationException;
	
	protected T performActionPostProcessing(List<?> data, Document document,PdfWriter writer, T t){
		return t;
	}

}
