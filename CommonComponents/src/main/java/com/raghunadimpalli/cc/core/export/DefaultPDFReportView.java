package com.raghunadimpalli.cc.core.export;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.raghunadimpalli.cc.core.components.DefaultComponentParams;
import com.raghunadimpalli.cc.core.components.DefaultComponentResponse;
import com.raghunadimpalli.cc.core.constants.MVCContants;
import com.raghunadimpalli.cc.core.exceptions.ApplicationException;
import com.raghunadimpalli.common.core.abstracts.ComponentBuilderResolver;
import com.raghunadimpalli.common.core.abstracts.ComponentParams;
import com.raghunadimpalli.common.core.abstracts.ComponentResponse;
import com.raghunadimpalli.common.core.abstracts.PDFExportActionBuilder;
import com.raghunadimpalli.common.core.helpers.MVCComponentHelper;
import com.raghunadimpalli.common.core.override.spring.AbstractPdfView;

public class DefaultPDFReportView extends AbstractPdfView {

	private ComponentBuilderResolver<ComponentResponse<String>> componentBuilderResolver;
	
	public ComponentBuilderResolver<ComponentResponse<String>> getComponentBuilderResolver() {
		return componentBuilderResolver;
	}


	public void setComponentBuilderResolver(
			ComponentBuilderResolver<ComponentResponse<String>> componentBuilderResolver) {
		this.componentBuilderResolver = componentBuilderResolver;
	}


	/*@Override
	protected void buildPdfDocument(Map<String, Object> data,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		String actionServiceIdentifier = MVCComponentHelper.getActionServiceIdentifier(request);
		ComponentParams params = new DefaultComponentParams(request, response, null);
		List<Object> excelData = (List<Object>)data.get(MVCContants.EXPORT_DATA);
		PDFExportActionBuilder<ComponentResponse<String>> excelBuilder = (PDFExportActionBuilder<ComponentResponse<String>>) componentBuilderResolver.resolveActionBuilder(actionServiceIdentifier, params);
		excelBuilder.doAction(excelData, document,writer,params, new DefaultComponentResponse<String>(false, "PDF Export Initialized"));
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws ApplicationException {
		// TODO Auto-generated method stub
		
		String actionServiceIdentifier = MVCComponentHelper.getActionServiceIdentifier(request);
		ComponentParams params = new DefaultComponentParams(request, response, null);
		List<Object> excelData = (List<Object>)model.get(MVCContants.EXPORT_DATA.getValue());
		PDFExportActionBuilder<ComponentResponse<String>> pdfBuilder = (PDFExportActionBuilder<ComponentResponse<String>>) componentBuilderResolver.resolveActionBuilder(actionServiceIdentifier, params);
		pdfBuilder.doAction(excelData, document,writer,params, new DefaultComponentResponse<String>(false, "PDF Export Initialized"));
	}
}