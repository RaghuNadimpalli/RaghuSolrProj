package com.raghunadimpalli.cc.core.http.client;

public class SolrXMLResponse {
	
	private String xmlString;

	public String getXmlString() {
		return xmlString;
	}

	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
	}
	
	@Override
	public String toString(){
		return "SolrXMLResponse [xmlString="+xmlString+"]";
	}

}
