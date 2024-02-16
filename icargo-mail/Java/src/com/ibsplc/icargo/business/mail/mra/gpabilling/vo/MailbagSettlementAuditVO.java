/*
 * MailbagSettlementAuditVO.java Created on Jun 05, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import java.util.Calendar;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;



/**
 * @author A-7871 
 * @version	Jun 05, 2018
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jun 05, 2018     A-7871		First draft
 */
public class MailbagSettlementAuditVO extends AuditVO{

	

	public MailbagSettlementAuditVO(String moduleName, String subModuleName,
			String entityName) {
		super(moduleName, subModuleName, entityName);
		// TODO Auto-generated constructor stub
	}
	/** The Constant AUDIT_MODULENAME. */
	public static final String AUDIT_MODULENAME = "mail";
	
	/** submodule name. */
    public static final String AUDIT_SUBMODULENAME = "mra";
	public static final String AUDIT_ENTITY = "mail.mra.saveSettlementsAtMailbagLevel"; 
	
	
	
	private long malSeqnum;
	private String serialNumber;
	private String invoiceNumber;
	private Calendar lastUpdateTime;
	private String lastUpdateUser;
	private Calendar lastUpdateDate;
	private String mailbagID;
	
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
	 * @return the lastUpdateDate
	 */
	public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}
	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	/**
	 * @return the malSeqnum
	 */
	public long getMalSeqnum() {
		return malSeqnum;
	}
	/**
	 * @param malSeqnum the malSeqnum to set
	 */
	public void setMalSeqnum(long malSeqnum) {
		this.malSeqnum = malSeqnum;
	}
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getMailbagID() {
		return mailbagID;
	}
	public void setMailbagID(String mailbagID) {
		this.mailbagID = mailbagID;
	}
}
