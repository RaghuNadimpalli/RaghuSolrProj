package com.raghunadimpalli.common.enums;

public enum OutreachDocType {
	SUBPROJECT("subproject"), 
	CLIENTLIST("clientlist"),
	CLIENTDOC("clientdoc"),
	MASTERLIST("masterlist"),
	CONTACTS("contacts");
	
	public String outDocType;
	
	private OutreachDocType(String outDocType)
	{
		this.outDocType = outDocType;
	}
	
	public String getOutDocType()
	{
		return this.outDocType;
	}
}
