/*
 * BillingMatrixVO.java created on Feb 27, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2280
 *
 */
public class BillingMatrixVO extends AbstractVO{
	/**
	 * ACTIVE - A
	 */
	public static final String BILLING_STATUS_ACTIVE = "A";
	/**
	 * INACTIVE - I
	 */
	public static final String BILLING_STATUS_INACTIVE = "I";
	/**
	 * CANCELLED - C
	 */
	public static final String BILLING_STATUS_CANCELLED = "C";
	/**
	 * NEW - N
	 */
	public static final String BILLING_STATUS_NEW = "N";
	
    private String companyCode;
    private String billingMatrixId;
	private String billingMatrixStatus;    
    private String description;    
    private LocalDate validityStartDate;
    private LocalDate validityEndDate;     
    private int totalBillinglines;
    private String operationFlag;
    private boolean statusChanged;
    private String lastUpdatedUser;
    private LocalDate lastUpdatedTime;
    private Page<BillingLineVO> billingLineVOs;
    
    	/**
    	 * added by muralee for bugFix-15185
	 */
    
     private String airlineCode;
     private String poaCode;
    
    	/**
    	 * @return the airlineCode
    	 */
    	public String getAirlineCode() {
    		return airlineCode;
    	}
    	/**
    	 * @param airlineCode the airlineCode to set
    	 */
    	public void setAirlineCode(String airlineCode) {
    		this.airlineCode = airlineCode;
    	}
    	/**
    	 * @return the poaCode
    	 */
    	public String getPoaCode() {
    		return poaCode;
    	}
    	/**
    	 * @param poaCode the poaCode to set
    	 */
    	public void setPoaCode(String poaCode) {
    		this.poaCode = poaCode;
	}
	/**
	 * @return Returns the billingMatrixId.
	 */
	public String getBillingMatrixId() {
		return billingMatrixId;
	}
	/**
	 * @param billingMatrixId The billingMatrixId to set.
	 */
	public void setBillingMatrixId(String billingMatrixId) {
		this.billingMatrixId = billingMatrixId;
	}
	
	/**
	 * @return the lastUpdatedTime
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * @return the lastUpdatedUser
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	/**
	 * @return Returns the billingMatrixStatus.
	 */
	public String getBillingMatrixStatus() {
		return billingMatrixStatus;
	}
	/**
	 * @param billingMatrixStatus The billingMatrixStatus to set.
	 */
	public void setBillingMatrixStatus(String billingMatrixStatus) {
		this.billingMatrixStatus = billingMatrixStatus;
	}
	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}
	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}	
	/**
	 * @return Returns the totalBillinglines.
	 */
	public int getTotalBillinglines() {
		return totalBillinglines;
	}
	/**
	 * @param totalBillinglines The totalBillinglines to set.
	 */
	public void setTotalBillinglines(int totalBillinglines) {
		this.totalBillinglines = totalBillinglines;
	}
	/**
	 * @return Returns the validityEndDate.
	 */
	public LocalDate getValidityEndDate() {
		return validityEndDate;
	}
	/**
	 * @param validityEndDate The validityEndDate to set.
	 */
	public void setValidityEndDate(LocalDate validityEndDate) {
		this.validityEndDate = validityEndDate;
	}
	/**
	 * @return Returns the validityStartDate.
	 */
	public LocalDate getValidityStartDate() {
		return validityStartDate;
	}
	/**
	 * @param validityStartDate The validityStartDate to set.
	 */
	public void setValidityStartDate(LocalDate validityStartDate) {
		this.validityStartDate = validityStartDate;
	}
	/**
	 * @return Returns the billingLineVOs.
	 */
	public Page<BillingLineVO> getBillingLineVOs() {
		return billingLineVOs;
	}
	/**
	 * @param billingLineVOs The billingLineVOs to set.
	 */
	public void setBillingLineVOs(Page<BillingLineVO> billingLineVOs) {
		this.billingLineVOs = billingLineVOs;
	}
	/**
	 * @return Returns the statusChanged.
	 */
	public boolean getStatusChanged() {
		return statusChanged;
	}
	/**
	 * @param statusChanged The statusChanged to set.
	 */
	public void setStatusChanged(boolean statusChanged) {
		this.statusChanged = statusChanged;
	}
}
