package com.raghunadimpalli.cc.core.export;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import com.raghunadimpalli.cc.core.components.DefaultComponentParams;
import com.raghunadimpalli.cc.core.components.DefaultComponentResponse;
import com.raghunadimpalli.cc.core.constants.MVCContants;
import com.raghunadimpalli.common.core.abstracts.ComponentBuilderResolver;
import com.raghunadimpalli.common.core.abstracts.ComponentParams;
import com.raghunadimpalli.common.core.abstracts.ComponentResponse;
import com.raghunadimpalli.common.core.abstracts.ExcelExportActionBuilder;
import com.raghunadimpalli.common.core.helpers.MVCComponentHelper;
import com.raghunadimpalli.common.core.override.spring.AbstractExcelView;

public class DefaultExcelReportView extends AbstractExcelView {

	private ComponentBuilderResolver<ComponentResponse<String>> componentBuilderResolver;

	public ComponentBuilderResolver<ComponentResponse<String>> getComponentBuilderResolver() {
		return componentBuilderResolver;
	}

	public void setComponentBuilderResolver(
			ComponentBuilderResolver<ComponentResponse<String>> componentBuilderResolver) {
		this.componentBuilderResolver = componentBuilderResolver;
	}
	
	/*@Override
	protected void buildExcelDocument(Map<String, Object> data,
			HSSFWorkbook hsfworkbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String actionServiceIdentifier = MVCComponentHelper.getActionServiceIdentifier(request);
		ComponentParams params = new DefaultComponentParams(request, response, null);
		List<Object> excelData = (List<Object>)data.get(MVCContants.EXPORT_DATA);
		ExcelExportActionBuilder<ComponentResponse<String>> excelBuilder = (ExcelExportActionBuilder<ComponentResponse<String>>) componentBuilderResolver.resolveActionBuilder(actionServiceIdentifier, params);
		excelBuilder.doAction(excelData, hsfworkbook,params, new DefaultComponentResponse<String>(false, "Excel Export Initialized"));
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String,Object> data,
			Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String actionServiceIdentifier = MVCComponentHelper.getActionServiceIdentifier(request);
		ComponentParams params = new DefaultComponentParams(request, response, null);
		List<Object> excelData = (List<Object>)data.get(MVCContants.EXPORT_DATA.getValue());
		ExcelExportActionBuilder<ComponentResponse<String>> excelBuilder = (ExcelExportActionBuilder<ComponentResponse<String>>) componentBuilderResolver.resolveActionBuilder(actionServiceIdentifier, params);
		excelBuilder.doAction(excelData, workbook,params, new DefaultComponentResponse<String>(false, "Excel Export Initialized"));
	}
}