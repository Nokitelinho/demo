/*
 * MemoFilterVO.java Created on Feb 14,2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1946
 *
 */
public class MemoFilterVO extends AbstractVO {

	 private String companyCode;
	 
	 private String airlineCodeFilter;
	 
	 private int airlineIdentifier;
	 
	 private String  invoiceNoFilter;
	 
	 private String clearancePeriod;
	 
	 private String interlineBillingType;	 
	 
	 private String dsnFilter;
	 
	 private String rejectionMemoNoFilter;
	 
	 
	 // Added For Outward Rejection Memo Report
	 
	 private String airlineNumber;
	 
	 
	 private String airlineName;
	 
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
	 * @return Returns the interlineBillingType.
	 */
	public String getInterlineBillingType() {
		return interlineBillingType;
	}
	/**
	 * @param interlineBillingType The interlineBillingType to set.
	 */
	public void setInterlineBillingType(String interlineBillingType) {
		this.interlineBillingType = interlineBillingType;
	}
	/**
	 * @return Returns the airlineCodeFilter.
	 */
	public String getAirlineCodeFilter() {
		return airlineCodeFilter;
	}
	/**
	 * @param airlineCodeFilter The airlineCodeFilter to set.
	 */
	public void setAirlineCodeFilter(String airlineCodeFilter) {
		this.airlineCodeFilter = airlineCodeFilter;
	}
	/**
	 * @return Returns the dsnFilter.
	 */
	public String getDsnFilter() {
		return dsnFilter;
	}
	/**
	 * @param dsnFilter The dsnFilter to set.
	 */
	public void setDsnFilter(String dsnFilter) {
		this.dsnFilter = dsnFilter;
	}
	/**
	 * @return Returns the invoiceNoFilter.
	 */
	public String getInvoiceNoFilter() {
		return invoiceNoFilter;
	}
	/**
	 * @param invoiceNoFilter The invoiceNoFilter to set.
	 */
	public void setInvoiceNoFilter(String invoiceNoFilter) {
		this.invoiceNoFilter = invoiceNoFilter;
	}
	/**
	 * @return Returns the rejectionMemoNoFilter.
	 */
	public String getRejectionMemoNoFilter() {
		return rejectionMemoNoFilter;
	}
	/**
	 * @param rejectionMemoNoFilter The rejectionMemoNoFilter to set.
	 */
	public void setRejectionMemoNoFilter(String rejectionMemoNoFilter) {
		this.rejectionMemoNoFilter = rejectionMemoNoFilter;
	}
	/**
	 * @return Returns the airlineName.
	 */
	public String getAirlineName() {
		return airlineName;
	}
	/**
	 * @param airlineName The airlineName to set.
	 */
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	/**
	 * @return Returns the airlineNumber.
	 */
	public String getAirlineNumber() {
		return airlineNumber;
	}
	/**
	 * @param airlineNumber The airlineNumber to set.
	 */
	public void setAirlineNumber(String airlineNumber) {
		this.airlineNumber = airlineNumber;
	}

}