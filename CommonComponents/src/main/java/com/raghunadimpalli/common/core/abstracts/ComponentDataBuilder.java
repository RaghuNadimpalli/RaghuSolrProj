package com.raghunadimpalli.common.core.abstracts;

import java.util.List;

import com.raghunadimpalli.cc.core.exceptions.ApplicationException;

public interface ComponentDataBuilder<E> {
	
	List<E> buildComponentData(ComponentParams params) throws ApplicationException;
	Class<?> getComponentDataTransferObject();
	List<? extends Object> handleComponentRequests(String requestIdentifier, ComponentParams param) throws ApplicationException;
}
