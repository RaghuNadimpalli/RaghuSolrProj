package com.raghunadimpalli.person.vo;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

public class PersonBasicVO {

	@Field("person_id")
	private String id;
	
	@Field("person_entityType")
	private String entityType = "PersonEntity";
	
	@Field("personText_type")
	private String type;
	
	@Field("personText_title")
	private String title;
	
	@Field("personText_firstName")
	private String firstName;
	
	@Field("personText_middleName")
	private String middleName;
	
	@Field("personText_lastName")
	private String lastName;
	
	@Field("personText_suffix")
	private String suffix;
	
	@Field("personText_emailPromotion")
	private String emailPromotion;
	
	@Field("personText_additionalInfo")
	private String additionalInfo;
	
	@Field("personText_modifiedDateText")
	private String modifiedDateText;
	
	@Field("person_modifiedDate")
	private Date modifiedDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getEmailPromotion() {
		return emailPromotion;
	}

	public void setEmailPromotion(String emailPromotion) {
		this.emailPromotion = emailPromotion;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getModifiedDateText() {
		return modifiedDateText;
	}

	public void setModifiedDateText(String modifiedDateText) {
		this.modifiedDateText = modifiedDateText;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
		
}