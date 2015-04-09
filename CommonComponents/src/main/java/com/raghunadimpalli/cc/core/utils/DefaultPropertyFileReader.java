package com.raghunadimpalli.cc.core.utils;

import com.raghunadimpalli.cc.core.exceptions.ApplicationException;
import com.raghunadimpalli.common.core.helpers.MVCComponentHelper;

public class DefaultPropertyFileReader {
	
	public void setRowData(String rowData){
		try{
			MVCComponentHelper.populateUserDetails(rowData);
		} catch(ApplicationException e){
			e.printStackTrace();
		}
	}

}
