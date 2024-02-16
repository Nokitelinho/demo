/*
 * BillingSiteAuditVO.java Created on Dec 19, 2013
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;

/**
 * The Class BillingSiteDetailsAuditVO.
 *
 * @author A-5219
 */
public class MCAAuditVO extends AuditVO {
	
	
	/** The Constant AUDIT_MODULENAME. */
	public static final String AUDIT_MODULENAME = "mail";
	
	/** submodule name. */
    public static final String AUDIT_SUBMODULENAME = "mra";
    
    /** Entity name. */
    public static final String AUDIT_ENTITY = "mail.mra.savemcadetails";
    
    
    public static final String ACTUAL_MCA_CREATED="MTKMRACCACRT_ACTUAL";
    
    
    public static final String ACTUAL_MCA_APPROVED="MTKMRACCAAPR_ACTUAL";
  //Added  by A-8527 for ICRD-350435
    public static final String ACTUAL_MCA_ACCEPTED="MTKMRACCAACP_ACTUAL";
    
    
    public static final String ACTUAL_MCA_DELETED="MTKMRACCADEL_ACTUAL";
    
    public static final String ACTUAL_MCA_REJECTED="MTKMRACCAREJ_ACTUAL";
    
    public static final String INTERNAL_MCA_CREATED="MTKMRACCACRT_INTERNAL";
    
    
    public static final String INTERNAL_MCA_APPROVED="MTKMRACCAAPR_INTERNAL";
    public static final String INTERNAL_MCA_ACCEPTED="MTKMRACCAACP_INTERNAL";
    
    public static final String INTERNAL_MCA_DELETED="MTKMRACCADEL_INTERNAL";
    
    public static final String INTERNAL_MCA_REJECTED="MTKMRACCAREJ_INTERNAL";
    //Added by A-7794 as part of ICRD-286237
    public static final String AUTO_MCA_CREATED="MTKMRAMCACRT_AUTO";
    public static final String AUTO_MCA_DELETED="MTKMRAMCADEL_AUTO";
    public static final String AUTO_MCA_APPROVED="MTKMRAMCAAPR_AUTO";
    public static final String AUTO_MCA_REJECTED="MTKMRAMCAREJ_AUTO";
    
    /** The ccaRefNumber code. */
    private String ccaRefNumber;
	
	/** The key type. */
	private String keyType;
	
	/** The max seq number. */
	private int maxSeqNumber;
	
	private String billingBasis;
	
	private long mailSequenceNumber;
	
	private String autoMCAUpdatedUser;
	
	
	/** The generated site code. */
	//private String generatedSiteCode;
	
	
	/**
	 * Gets the key type.
	 *
	 * @return the keyType
	 */
	public String getKeyType() {
		return keyType;
	}

	/**
	 * Sets the key type.
	 *
	 * @param keyType the keyType to set
	 */
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	/**
	 * Gets the max seq number.
	 *
	 * @return the maxSeqNumber
	 */
	public int getMaxSeqNumber() {
		return maxSeqNumber;
	}

	/**
	 * Sets the max seq number.
	 *
	 * @param maxSeqNumber the maxSeqNumber to set
	 */
	public void setMaxSeqNumber(int maxSeqNumber) {
		this.maxSeqNumber = maxSeqNumber;
	}

	/**
	 * Gets the ccaRefNumber
	 *
	 * @return the ccaRefNumber
	 */
	public String getCcaRefNumber() {
		return ccaRefNumber;
	}

	/**
	 * Sets the ccaRefNumber
	 *
	 * @param ccaRefNumber the new ccaRefNumber
	 */
	public void setCcaRefNumber(String ccaRefNumber) {
		this.ccaRefNumber = ccaRefNumber;
	}


	/**
	 * Instantiates a new ccaRefNumber audit vo.
	 *
	 * @param moduleName the module name
	 * @param subModuleName the sub module name
	 * @param entityName the entity name
	 */
	public MCAAuditVO(String moduleName, String subModuleName,String entityName) {
		super(moduleName, subModuleName, entityName);
		
	}

	/**
	 * @param billingBasis the billingBasis to set
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}

	/**
	 * @return the billingBasis
	 */
	public String getBillingBasis() {
		return billingBasis;
	}

	/**
	 * 	Getter for mailSequenceNumber 
	 *	Added by : a-8061 on 10-Aug-2018
	 * 	Used for :
	 */
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}

	/**
	 *  @param mailSequenceNumber the mailSequenceNumber to set
	 * 	Setter for mailSequenceNumber 
	 *	Added by : a-8061 on 10-Aug-2018
	 * 	Used for :
	 */
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}

	/**
	 * 	Getter for autoMCAUpdatedUser 
	 *	Added by : a-8061 on 05-Nov-2018
	 * 	Used for :
	 */
	public String getAutoMCAUpdatedUser() {
		return autoMCAUpdatedUser;
	}

	/**
	 *  @param autoMCAUpdatedUser the autoMCAUpdatedUser to set
	 * 	Setter for autoMCAUpdatedUser 
	 *	Added by : a-8061 on 05-Nov-2018
	 * 	Used for :
	 */
	public void setAutoMCAUpdatedUser(String autoMCAUpdatedUser) {
		this.autoMCAUpdatedUser = autoMCAUpdatedUser;
	}

	}
