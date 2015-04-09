package com.raghunadimpalli.common.core.abstracts;

import com.raghunadimpalli.cc.core.exceptions.ApplicationException;

public interface ComponentActionBuilder<T> {
	
	public T doAction(ComponentParams params, T t) throws ApplicationException;

}
