package com.raghunadimpalli.common.core.abstracts;

import com.raghunadimpalli.cc.core.exceptions.ApplicationException;

public abstract class AbstractComponentActionBuilder<T> implements ComponentActionBuilder<T> {

	public T doAction(ComponentParams params, T t) throws ApplicationException{
		t = this.performActionPreprocessing(params, t);
		t = this.performActionPostProcessing(params, t);
		return this.performActionPostProcessing(params, t);
	}
	
	protected T performActionPreprocessing(ComponentParams params, T t){
		return t;
	}
	
	abstract T performAction(ComponentParams params, T t);
	
	protected T performActionPostProcessing(ComponentParams params, T t){
		return t;
	}
}
