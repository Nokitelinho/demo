/*
 * EmbargoAuditVO.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information
 * of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.vo;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;

/**
 * @author A-1358
 *
 */
/**
 * @author A-1894
 *
 */
public class EmbargoAuditVO extends AuditVO {
	
	
	/**
	 * Module name
	 */
	public static final String AUDIT_MODULENAME = "reco";
	/**
	 * submodule name
	 */
	public static final String AUDIT_SUBMODULENAME = "defaults";
	/**
	 * Entity name
	 */
	public static final String AUDIT_ENTITY = "reco.defaults.Embargo";

    /**
     * Audit Action code of inserting embargo
     */
    public static final String EMBARGO_INSERT_ACTIONCODE = "CRTEMB";

    /**
     * Audit Action code of updating embargo
     */
    public static final String EMBARGO_UPDATE_ACTIONCODE = "UPDEMB";

    /**
     * Audit Action code of cancelling embargo
     */
    public static final String EMBARGO_CANCEL_ACTIONCODE = "CNLEMB";

    /**
     * Audit Key for sequence number generation
     */
    public static final String EMBARGO_AUDIT_KEY = "EMBARGO_AUDIT_KEY";

    /**
     * This constructor invokes the constructor of the super class - AuditVO
     * @param productName
     * @param moduleName
     * @param entityName
     */
    public EmbargoAuditVO(String productName, String moduleName,
    		String auditName) {
    	super(productName, moduleName, auditName);
    }

    private String embargoReferenceNumber;

	private long sequenceNumber;



	/**
	 * @return Returns the embargoReferenceNumber.
	 */
	public String getEmbargoReferenceNumber() {
		return embargoReferenceNumber;
	}


	/**
	 * @param embargoReferenceNumber The embargoReferenceNumber to set.
	 */
	public void setEmbargoReferenceNumber(String embargoReferenceNumber) {
		this.embargoReferenceNumber = embargoReferenceNumber;
	}



	/**
	 * @return Returns the sequenceNumber.
	 */
	public long getSequenceNumber() {
		return sequenceNumber;
	}


	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}


}
