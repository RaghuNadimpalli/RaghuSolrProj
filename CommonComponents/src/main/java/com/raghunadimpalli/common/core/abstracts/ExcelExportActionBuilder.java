package com.raghunadimpalli.common.core.abstracts;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.raghunadimpalli.cc.core.exceptions.ApplicationException;

public interface ExcelExportActionBuilder<T> extends ComponentActionBuilder<T> {
	
	public T doAction(List data, Workbook workbook, ComponentParams params, T t) throws ApplicationException;

}
