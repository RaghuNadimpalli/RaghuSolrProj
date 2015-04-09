package com.raghunadimpalli.common.core.abstracts;

import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.raghunadimpalli.cc.core.exceptions.ApplicationException;

public interface PDFExportActionBuilder<T> extends ComponentActionBuilder<T> {

	public T doAction(List data, Document document, PdfWriter writer, ComponentParams params, T t) throws ApplicationException;
}
