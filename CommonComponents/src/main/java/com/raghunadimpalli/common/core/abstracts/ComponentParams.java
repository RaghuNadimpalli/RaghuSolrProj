package com.raghunadimpalli.common.core.abstracts;

public interface ComponentParams {
	String getParameter(String key);
	String getUser();
	Object getParamData(String key);
	void setParamData(String key, Object Value);
	Object getHttpPayloadData();
	Long getTotalCount();
	void setTotalCount(Long totalCount);
}
