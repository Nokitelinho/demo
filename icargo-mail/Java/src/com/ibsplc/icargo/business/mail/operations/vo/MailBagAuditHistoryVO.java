 /*
 * MailBagAuditHistoryVO.java Created on JUN 30, 2016 by A-5991
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Calendar;

import com.ibsplc.xibase.server.framework.audit.vo.AuditHistoryVO;

public class MailBagAuditHistoryVO extends AuditHistoryVO{


	   /**
	    * The Dsn
	    */
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

	   private int year;

	   private long serialNumber;
	   private long historySequenceNumber;

	private String lastUpdateUser;

	private String auditField;

	private String oldValue;

	private String newValue;
	 private String usercode;
	 private String station;

	/**
	 * Last update date and time
	 */
	private Calendar lastUpdateTime;

	private long mailSequenceNumber;

	/**
	 * @return the lastUpdateUser
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}



	/**
	 * @param lastUpdateUser the lastUpdateUser to set
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}



	/**
	 * @return the auditField
	 */
	public String getAuditField() {
		return auditField;
	}



	/**
	 * @param auditField the auditField to set
	 */
	public void setAuditField(String auditField) {
		this.auditField = auditField;
	}



	/**
	 * @return the oldValue
	 */
	public String getOldValue() {
		return oldValue;
	}



	/**
	 * @param oldValue the oldValue to set
	 */
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}



	/**
	 * @return the newValue
	 */
	public String getNewValue() {
		return newValue;
	}



	/**
	 * @param newValue the newValue to set
	 */
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}



	/**
	 * @return the lastUpdateTime
	 */
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}



	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
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
	 * @return the year
	 */
	public int getYear() {
		return year;
	}



	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}



	public MailBagAuditHistoryVO(String moduleName, String subModuleName,
			String entityName) {
		super(moduleName, subModuleName, entityName);
		// TODO Auto-generated constructor stub
	}



	/**
	 * @return the usercode
	 */
	public String getUsercode() {
		return usercode;
	}



	/**
	 * @param usercode the usercode to set
	 */
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}



	/**
	 * @return the station
	 */
	public String getStation() {
		return station;
	}



	/**
	 * @param station the station to set
	 */
	public void setStation(String station) {
		this.station = station;
	}



	/**
	 * @return the serialNumber
	 */
	public long getSerialNumber() {
		return serialNumber;
	}



	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @param mailSequenceNumber the mailSequenceNumber to set
	 */
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	/**
	 * @return the mailSequenceNumber
	 */
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}



}
