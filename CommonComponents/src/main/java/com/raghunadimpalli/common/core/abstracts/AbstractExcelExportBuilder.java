package com.raghunadimpalli.common.core.abstracts;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.raghunadimpalli.cc.core.exceptions.ApplicationException;

public abstract class AbstractExcelExportBuilder<T> extends
		AbstractComponentActionBuilder<T> implements ExcelExportActionBuilder<T> {
	
	@Override
	public T performAction(ComponentParams params, T t){
		return t;
	}
	
	@Override
	public T doAction(List data,Workbook workbook,ComponentParams params, T t) throws ApplicationException{
		t = this.performActionPreprocessing(data, workbook,params, t);
		t = this.performAction(data, workbook,params, t);
		return this.performActionPostProcessing(data,workbook,params,t);
	}
	
	protected T performActionPreprocessing(List<?> data,Workbook workbook, ComponentParams params, T t){
		return t;
	}
	
	abstract public T performAction(List<?> data,Workbook workbook,ComponentParams params,T t) throws ApplicationException;
	
	protected T performActionPostProcessing(List<?> data,Workbook workbook,ComponentParams params,T t){
		return t;
	}
	
}
