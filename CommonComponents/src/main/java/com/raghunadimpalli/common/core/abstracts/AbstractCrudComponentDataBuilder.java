package com.raghunadimpalli.common.core.abstracts;

import com.raghunadimpalli.cc.core.components.DefaultComponentResponse;
import com.raghunadimpalli.cc.core.exceptions.ApplicationException;

public abstract class AbstractCrudComponentDataBuilder<E> extends
		AbstractComponentDataBuilder implements CrudComponentBuilder<E> {
	
	@Override
	public ComponentResponse<E> create(Object data, ComponentParams param) throws ApplicationException{
		return new DefaultComponentResponse<E>(false, "Failed");
	}
	
	@Override
	public ComponentResponse<E> update(Object data, ComponentParams param) throws ApplicationException{
		return new DefaultComponentResponse<E>(false, "Failed");
	}
	
	@Override
	public ComponentResponse<E> delete(Object data, ComponentParams param) throws ApplicationException{
		return new DefaultComponentResponse<E>(false, "Failed");
	}

}
