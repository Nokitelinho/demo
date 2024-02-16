
 /*
 * MailAuditHistoryFilterVO.java Created on JUN 30, 2016 by A-5991 
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.io.Serializable;

public class MailAuditHistoryFilterVO  implements Serializable{
	
	
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
	

}
