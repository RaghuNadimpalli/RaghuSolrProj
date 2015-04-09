package com.raghunadimpalli.common.core.abstracts;

import com.raghunadimpalli.cc.core.exceptions.ApplicationException;

public interface ComponentBuilderResolver<E> {
	ComponentDataBuilder<E> resolveDataBuilder(String componentId, ComponentParams params) throws ApplicationException;
	ComponentActionBuilder<E> resolveActionBuilder(String componentId, ComponentParams params) throws ApplicationException;
}
