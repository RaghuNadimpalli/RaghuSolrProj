package com.raghunadimpalli.cc.core.components;

import java.util.HashMap;
import java.util.Map;

import com.raghunadimpalli.common.core.abstracts.ComponentResponse;

public class DefaultComponentResponse<E> implements ComponentResponse<E> {

		private boolean executionStatus;
		private String executionMessage;
		private Map<String,E> responseParameters = new HashMap<String, E>();
		
		public DefaultComponentResponse(boolean executionStatus,String executionMsg){
			this.executionStatus = executionStatus;
			this.executionMessage = executionMsg;
		}

		@Override
		public boolean getExecutionStatus() {
			return executionStatus;
		}
		
		@Override
		public String getExecutionMessage() {
			return executionMessage;
		}
		
		@Override
		public void setResponseParameter(String key, E value) {
			this.responseParameters.put(key,value);
		}

		@Override	
		public Map<String, E> getResponseParameters() {
			return this.responseParameters;
		}

		@Override
		public boolean isResponseAvailable(){
			return responseParameters.size()>0;
		}
		
		
}
