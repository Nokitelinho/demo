/*
 * AirlineBillingAuditVO.java Created on July 16, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
/**
 * 
 * @author A-2391
 *
 */
public class AirlineBillingAuditVO extends AuditVO{
	
	 /**
	    * Module name
	    */
	    public static final String AUDIT_MODULENAME = "mail";
	    /**
	     * submodule name
	     */
	    public static final String AUDIT_SUBMODULENAME = "mra";
	    /**
	     * Entity name  mailtracking.mra.airlinebilling.RejectionMemo
	     */
	    
	    public static final String AUDIT_ENTITY = "mail.mra.airlinebilling.RejectionMemo";
	    
	    public static final String AUDIT_ENTITYCN66 = "mail.mra.airlinebilling.AirlineCN66Details";
	    
	    public static final String AUDIT_ENTITYCN51SMY = "mail.mra.airlinebilling.AirlineCN51Summary";
	    /**
	     * Audit Actions
	     */
	    public static final String AUDIT_CREATE_ACTION="CREATED";
	    public static final String AUDIT_UPDATE_ACTION="UPDATED";
	    public static final String AUDIT_DELETE_ACTION="DELETED";
	    public static final String AUDIT_ACCEPT_ACTION="ACCEPTED";
	    // added by Indu for RejectionMemo Audit
	    public static final String AUDIT_REJECTIONMEMO_CAPTURED = "INVRMC";
	    public static final String AUDIT_REJECTIONMEMO_UPDATED = "INVRMU";
	    public static final String AUDIT_REJECTIONMEMO_DELETED = "INVRMD";
	    // added by Sandeep for AirlineCN66 Audit
	    public static final String AUDIT_CN66_CAPTURED = "INVCAPARLCN66";
	    public static final String AUDIT_CN66_UPDATED = "INVUPDARLCN66";
	    public static final String AUDIT_CN66_DELETED = "INVDELARLCN66";
	    // added by Sandeep for AirlineCN51 Audit
	    public static final String AUDIT_CN51_CAPTURED = "INVCAPARLCN51";
	    public static final String AUDIT_CN51_UPDATED = "INVUPDARLCN51";
	    public static final String AUDIT_CN51_DELETED = "INVDELARLCN51";
	    //added for invoice acception and processing
	    public static final String AUDIT_INVOICE_PROCESSED = "INVPROCES";
	    public static final String AUDIT_INVOICE_ACCEPTED = "INVACP";

	    


   /**
    * airlineIdentifier
    */
	private int airlineIdentifier;
	/**
	 * The airlineCode
	 */
    private  String airlineCode;
    /**
     * The serialNumber
     */
    private int serialNumber;
	/**
	 * clearancePeriod
	 */
    private String clearancePeriod;
    /**
     * memoCode
     */
    private String memoCode;
	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	/**
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}
	/**
	 * @param airlineIdentifier The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}
	/**
	 * @return Returns the clearancePeriod.
	 */
	public String getClearancePeriod() {
		return clearancePeriod;
	}
	/**
	 * @param clearancePeriod The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
	}
	/**
	 * @return Returns the serialNumber.
	 */
	public int getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber The serialNumber to set.
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public AirlineBillingAuditVO(String arg0, String arg1, String arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return Returns the memoCode.
	 */
	public String getMemoCode() {
		return memoCode;
	}
	/**
	 * @param memoCode The memoCode to set.
	 */
	public void setMemoCode(String memoCode) {
		this.memoCode = memoCode;
	}
    

}
