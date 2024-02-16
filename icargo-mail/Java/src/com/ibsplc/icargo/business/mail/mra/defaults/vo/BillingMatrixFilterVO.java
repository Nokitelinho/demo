/*
 * BillingMatrixFilterVO.java created on Feb 27, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2280
 *
 */
public class BillingMatrixFilterVO extends AbstractVO{
	
	private String companyCode;
	private String billingMatrixId;
	private String poaCode;
	private String airlineCode;
	private String status;
	private LocalDate validFrom;
	private LocalDate validTo;
	private int pageNumber;
	private LocalDate txnFromDate;
	private LocalDate txnToDate;
	private int totalRecordCount;//Added by A-5218 to enable Last Link in Pagination to start
				
	/**
	 * @return the totalRecordCount
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	/**
	 * @param totalRecordCount the totalRecordCount to set
	 */
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	/**
	 * @return Returns the pageNumber.
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber The pageNumber to set.
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
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
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return Returns the validFrom.
	 */
	public LocalDate getValidFrom() {
		return validFrom;
	}
	/**
	 * @param validFrom The validFrom to set.
	 */
	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}
	/**
	 * @return Returns the validTo.
	 */
	public LocalDate getValidTo() {
		return validTo;
	}
	/**
	 * @param validTo The validTo to set.
	 */
	public void setValidTo(LocalDate validTo) {
		this.validTo = validTo;
	}
	/**
	 * @return the txnFromDate
	 */
	public LocalDate getTxnFromDate() {
		return txnFromDate;
	}
	/**
	 * @param txnFromDate the txnFromDate to set
	 */
	public void setTxnFromDate(LocalDate txnFromDate) {
		this.txnFromDate = txnFromDate;
	}
	/**
	 * @return the txnToDate
	 */
	public LocalDate getTxnToDate() {
		return txnToDate;
	}
	/**
	 * @param txnToDate the txnToDate to set
	 */
	public void setTxnToDate(LocalDate txnToDate) {
		this.txnToDate = txnToDate;
	}
	
	
	

}
