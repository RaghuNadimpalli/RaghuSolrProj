package com.raghunadimpalli.cc.core.constants;

public enum MVCContants {
	HTTP_PAYLOAD("http_payload"),
	COMPONENT_ID("compId"),
	ACTION_SERVICE_IDENTIFIER("action_service_identifier"),
	SUB_COMPONENT_ID("subCompId"),
	REQUESTOR_ID("requestorId"),
	EXPORT_DATA("export_data");
	
	public enum EXPORT{
		EXCEL("excel"),
		PDF("pdf"),
		EXPORT_FORMAT("export_format");
		
		private String value;
		
		private EXPORT(String value){
			this.value = value;
		}
		
		public String getValue(){
			return value;
		}
	}
	
	private String value;
	
	private MVCContants(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
