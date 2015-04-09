package com.raghunadimpalli.common.enums;

public enum Environment {
	LOCAL("local"),
	NONLOCAL("nonlocal"),
	QA("qa"),
	SIT("sit"),
	STAGE("stage"),
	UAT("uat"),
	PROD("prod");
	
	public String type;
	
	private Environment (String type)
	{
		this.type = type;
	}
	
	public String getEnvType()
	{
		return this.type;
	}
}
