package com.raghunadimpalli.cc.core.components;

import com.raghunadimpalli.cc.core.exceptions.ApplicationException;
import com.raghunadimpalli.common.core.abstracts.ComponentActionBuilder;
import com.raghunadimpalli.common.core.abstracts.ComponentBuilderResolver;
import com.raghunadimpalli.common.core.abstracts.ComponentDataBuilder;
import com.raghunadimpalli.common.core.abstracts.ComponentParams;

public class BuilderResolverDelegator implements ComponentBuilderResolver{
	
	private ComponentBuilderResolver internalResolver;

	public ComponentBuilderResolver getInternalResolver() {
		return internalResolver;
	}

	public void setInternalResolver(ComponentBuilderResolver internalResolver) {
		this.internalResolver = internalResolver;
	}
	
	@Override
	public ComponentDataBuilder resolveDataBuilder(String componentId,
			ComponentParams params) throws ApplicationException{
		ComponentDataBuilder result = internalResolver.resolveDataBuilder(componentId, params);
		return result;
	}
	
	@Override
	public ComponentActionBuilder resolveActionBuilder(String componentId,ComponentParams params) throws ApplicationException{
		ComponentActionBuilder result = internalResolver.resolveActionBuilder(componentId, params);
		return result;
	}
	
	

}
