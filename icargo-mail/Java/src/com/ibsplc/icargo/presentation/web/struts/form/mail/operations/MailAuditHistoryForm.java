 /*
 * MailAuditHistoryForm.java Created on Nov 5 2015 by A-5945 for ICRD-119569
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

public class MailAuditHistoryForm extends ScreenModel{
	
	
	private static final String SCREEN_ID = "mailtracking.defaults.mailaudithistory";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "MailAuditHistoryResources";


	
	
	private String mailbagId;
	   private String dsn;
	   /**
	    * The originExchangeOffice
	    */

	   private String originExchangeOffice;
	   /**
	    * The destinationExchangeOffice
	    */

	   private String destinationExchangeOffice;
	   /**
	    * The mailSubClass
	    */

	   private String mailSubclass;
	  
	   /**
	    * The mailCategory
	    */

	   private String mailCategoryCode;
	   
	   private String companyCode;
	   
	   private String transaction;
	   private String auditableField;
	   
	   private String screenMode = "";
	   
	   private String entity;
	   private String childEntity;

	/**
	 * @return the entity
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(String entity) {
		this.entity = entity;
	}

	/**
	 * @return the childEntity
	 */
	public String getChildEntity() {
		return childEntity;
	}

	/**
	 * @param childEntity the childEntity to set
	 */
	public void setChildEntity(String childEntity) {
		this.childEntity = childEntity;
	}

	/**
	 * @return the mailbagId
	 */
	public String getMailbagId() {
		return mailbagId;
	}

	/**
	 * @param mailbagId the mailbagId to set
	 */
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}

	/**
	 * @return the dsn
	 */
	public String getDsn() {
		return dsn;
	}

	/**
	 * @param dsn the dsn to set
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	/**
	 * @return the originExchangeOffice
	 */
	public String getOriginExchangeOffice() {
		return originExchangeOffice;
	}

	/**
	 * @param originExchangeOffice the originExchangeOffice to set
	 */
	public void setOriginExchangeOffice(String originExchangeOffice) {
		this.originExchangeOffice = originExchangeOffice;
	}

	/**
	 * @return the destinationExchangeOffice
	 */
	public String getDestinationExchangeOffice() {
		return destinationExchangeOffice;
	}

	/**
	 * @param destinationExchangeOffice the destinationExchangeOffice to set
	 */
	public void setDestinationExchangeOffice(String destinationExchangeOffice) {
		this.destinationExchangeOffice = destinationExchangeOffice;
	}

	/**
	 * @return the mailSubclass
	 */
	public String getMailSubclass() {
		return mailSubclass;
	}

	/**
	 * @param mailSubclass the mailSubclass to set
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}

	/**
	 * @return the mailCategoryCode
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode the mailCategoryCode to set
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the productName
	 */
	public static String getProductName() {
		return PRODUCT_NAME;
	}

	/**
	 * @return the subproductName
	 */
	public static String getSubproductName() {
		return SUBPRODUCT_NAME;
	}

	/**
	 * @return screenId
	 */
	public String getScreenId() {
		return SCREEN_ID;
	}

	/**
	 * @return product
	 */
	public String getProduct() {
		return PRODUCT_NAME;
	}

	/**
	 * @return subProduct
	 */
	public String getSubProduct() {
		return SUBPRODUCT_NAME;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return the transaction
	 */
	public String getTransaction() {
		return transaction;
	}

	/**
	 * @param transaction the transaction to set
	 */
	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	/**
	 * @return the auditableField
	 */
	public String getAuditableField() {
		return auditableField;
	}

	/**
	 * @param auditableField the auditableField to set
	 */
	public void setAuditableField(String auditableField) {
		this.auditableField = auditableField;
	}

	/**
	 * @return the screenMode
	 */
	public String getScreenMode() {
		return screenMode;
	}

	/**
	 * @param screenMode the screenMode to set
	 */
	public void setScreenMode(String screenMode) {
		this.screenMode = screenMode;
	}


}
