package com.raghunadimpalli.common.core.abstracts;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.raghunadimpalli.cc.core.exceptions.ApplicationException;


public abstract class AbstractComponentDataBuilder<E> implements ComponentDataBuilder<E> {
	
	@Override
	public List<? extends Object> handleComponentRequests(String requestIdentifier, ComponentParams params) throws ApplicationException{
		return Collections.emptyList();
	}
	
	@Override
	public Class<?> getComponentDataTransferObject(){
		return Map.class;
	}
	

}
