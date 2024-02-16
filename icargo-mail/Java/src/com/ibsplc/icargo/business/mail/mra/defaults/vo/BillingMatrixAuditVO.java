/*
 * BillingMatrixAuditVO.java Created on Aug 3, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;

/**
 * @author A-5255 
 * @version	0.1, Aug 3, 2015
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Aug 3, 2015	     A-5255		First draft
 */

public class BillingMatrixAuditVO extends AuditVO {

	private String billingmatrixID; 
	private String keyType; 
	private int maxSeqNumber; 
	private String sysFlag; 
	
	/** The Constant AUDIT_MODULENAME. */
	public static final String AUDIT_MODULENAME = "mail";
	
	/** submodule name. */
    public static final String AUDIT_SUBMODULENAME = "mra";
	public static final String AUDIT_ENTITY = "mail.mra.saveBillingMatrix"; 
	public static final String AUDIT_ENTITY_PAR = "mail.mra.saveBillingMatrixPar";
	public static final String BILLINGLINE_CREATED="MALMRABLGLINCRT"; 
	public static final String BILLINGLINE_UPDATED ="MALMRABLGLINUPD";
	 public String agentInfo;
	/**
	 * 	Getter for agentInfo 
	 *	Added by : a-7531 on 25-Jan-2018
	 * 	Used for :
	 */
	public String getAgentInfo() {
		return agentInfo;
	}
	/**
	 *  @param agentInfo the agentInfo to set
	 * 	Setter for agentInfo 
	 *	Added by : a-7531 on 25-Jan-2018
	 * 	Used for :
	 */
	public void setAgentInfo(String agentInfo) {
		this.agentInfo = agentInfo;
	}
	/**
	 * 
	 * @param moduleName
	 * @param subModuleName
	 * @param entityName
	 */
	
	public BillingMatrixAuditVO(String moduleName, String subModuleName,String entityName) {
		super(moduleName, subModuleName, entityName);
		
	}
	/**
	 * @return the billingmatrixID
	 */
	public String getBillingmatrixID() {
		return billingmatrixID;
	}
	/**
	 * @param billingmatrixID the billingmatrixID to set
	 */
	public void setBillingmatrixID(String billingmatrixID) {
		this.billingmatrixID = billingmatrixID;
	}
	/**
	 * @return the keyType
	 */
	public String getKeyType() {
		return keyType;
	}
	/**
	 * @param keyType the keyType to set
	 */
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
	/**
	 * @return the maxSeqNumber
	 */
	public int getMaxSeqNumber() {
		return maxSeqNumber;
	}
	/**
	 * @param maxSeqNumber the maxSeqNumber to set
	 */
	public void setMaxSeqNumber(int maxSeqNumber) {
		this.maxSeqNumber = maxSeqNumber;
	}
	public String getSysFlag() {
		return sysFlag;
	}
	/**
	 * @param sysFlag the sysFlag to set
	 */
	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}
	

}
