package com.raghunadimpalli.common.enums;

public enum NodeTypes {
	PROJECT("project"), SUBPROJECT("subproject"), CLIENT("client");
	
	public String code;
	
	private NodeTypes(String c)
	{
		code = c;
	}
	
	public String getCode()
	{
		return code;
	}
}
