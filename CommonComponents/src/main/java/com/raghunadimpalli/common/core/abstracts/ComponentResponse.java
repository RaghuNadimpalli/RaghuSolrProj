package com.raghunadimpalli.common.core.abstracts;

import java.util.Map;

public interface ComponentResponse<E> {
	boolean getExecutionStatus();
	String getExecutionMessage();
	void setResponseParameter(String key,E value);
	Map<String,E> getResponseParameters();
	boolean isResponseAvailable();
}
