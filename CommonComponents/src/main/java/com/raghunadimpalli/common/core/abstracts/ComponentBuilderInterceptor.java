package com.raghunadimpalli.common.core.abstracts;

public interface ComponentBuilderInterceptor {
	void preDataBuild(ComponentDataBuilder builder, ComponentParams params) throws Exception;
	void postDataBuild(Object rawData, ComponentParams params) throws Exception;
}
