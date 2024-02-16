/*
 * PostalAdministrationAuditVO.java Created on Jul 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;

/**
 * @author A-2270
 *
 */
public class PostalAdministrationAuditVO extends AuditVO {
	
	 /**
	    * Module name
	    */
	    public static final String AUDIT_MODULENAME = "mailtracking";
	    /**
	     * submodule name
	     */
	    public static final String AUDIT_SUBMODULENAME = "mra";
	    /**
	     * Entity name  mailtracking.mra.gpareporting.GPAReportingDetails
	     */
	    public static final String AUDIT_ENTITY = "mailtracking.mra.gpareporting.GPAReportingDetails";
	    /**
	     * Audit Actions
	     */
	    public static final String AUDIT_CREATE_ACTION="CREATE";
	    public static final String AUDIT_UPDATE_ACTION="UPDATE";
	    public static final String AUDIT_DELETE_ACTION="DELETE";
	    public static final String AUDIT_GPAREPORT_CAPTURED = "POACAPRPT";
	    public static final String AUDIT_GPAREPORT_UPDATED = "POAUPDRPT";
	    public static final String AUDIT_GPAREPORT_DELETED = "POADELRPT";
	    
	 private String poaCode ;
	
	//private String companyCode;
	
	private int serialNumber;
	
	private LocalDate billingPeriodFrom;
	
	private LocalDate billingPeriodTo;
	
	/**
	 * 
	 * @param productName
	 * @param moduleName
	 * @param auditName
	 */

	public PostalAdministrationAuditVO(String productName, String moduleName,
			String auditName) {
		super(productName, moduleName, auditName);

	}

	/**
	 * @return Returns the billingPeriodFrom.
	 */
	public LocalDate getBillingPeriodFrom() {
		return billingPeriodFrom;
	}

	/**
	 * @param billingPeriodFrom The billingPeriodFrom to set.
	 */
	public void setBillingPeriodFrom(LocalDate billingPeriodFrom) {
		this.billingPeriodFrom = billingPeriodFrom;
	}

	/**
	 * @return Returns the billingPeriodTo.
	 */
	public LocalDate getBillingPeriodTo() {
		return billingPeriodTo;
	}

	/**
	 * @param billingPeriodTo The billingPeriodTo to set.
	 */
	public void setBillingPeriodTo(LocalDate billingPeriodTo) {
		this.billingPeriodTo = billingPeriodTo;
	}

	/**
	 * @return Returns the poaCode.
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
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
	 * overrrided toString method inside auditVO class
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return new StringBuffer(" PoaCode: ").append(
				this.poaCode == null ? "null" : this.poaCode)
				.append(",billingPeriodFrom: ").append(
						this.billingPeriodFrom == null ? "null"
								: this.billingPeriodFrom).append(
						", billingPeriodTo: ").append(
						this.billingPeriodTo).append(",CompanyCode: ")
				.append(
						super.getCompanyCode() == null ? "null" : super
								.getCompanyCode()).append(", AdditionalInfo: ")
				.append(
						super.getAdditionalInformation() == null ? "null"
								: super.getAdditionalInformation()).append(
						", AuditRemarks: ").append(
						super.getAuditRemarks() == null ? "null" : super
								.getAuditRemarks()).append(", ActionCode: ")
				.append(
						super.getActionCode() == null ? "null" : super
								.getActionCode()).append(", UserId: ").append(
						super.getUserId() == null ? "null" : super.getUserId())
				.toString();
	}
	
	

}
