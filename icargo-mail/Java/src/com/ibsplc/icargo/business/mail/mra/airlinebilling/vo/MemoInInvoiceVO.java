/*
 * MemoInInvoiceVO.java Created on Jun 15, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * TODO Add the purpose of this class
 * 
 * @author A-2407
 * 
 */

public class MemoInInvoiceVO  extends AbstractVO {
	/**
	 * Length of Memo Number Key
	 */
	public static final int MEMO_NUMBER_KEY_LENGTH = 5;
	/**
	 * Prefix for Rejection Memo Number
	 */
	public static final String PREFIX_REJECTION_MEMO = "RM";
	
	private String companyCode;
	
	private int airlineIdentifier;
	
	private String interlineBlgType;
	
	private String invoiceNumber;
	
	private String memoNumber;
	
	private String remarks;
	
	private double totAmtInContractCur;
	
	private String memoCode;
	
	private String clearancePeriod;
	
	private String airlineCode;
	
	private double provisionalAmount;
	
	private double reportedAmount;
	
	private double differenceAmount;
	
	private double previousDifferenceAmount;
	
	private String contractCurrCode;
	
	private String operationalFlag;
	
	private LocalDate memoDate;
	
	//Added for Outward Rejection Memo Report
	
	private String airlineName;
	
	
	private String outwardInvNumber;
	
	private String lastUpdatedUser;
	
	private LocalDate lastUpdatedTime;
	
	private String dsn;
	
	
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
	 * @return Returns the operationalFlag.
	 */
	public String getOperationalFlag() {
		return operationalFlag;
	}
	/**
	 * @param operationalFlag The operationalFlag to set.
	 */
	public void setOperationalFlag(String operationalFlag) {
		this.operationalFlag = operationalFlag;
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
	 * @return Returns the interlineBlgType.
	 */
	public String getInterlineBlgType() {
		return interlineBlgType;
	}

	/**
	 * @param interlineBlgType The interlineBlgType to set.
	 */
	public void setInterlineBlgType(String interlineBlgType) {
		this.interlineBlgType = interlineBlgType;
	}

	/**
	 * @return Returns the invoiceNumber.
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber The invoiceNumber to set.
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return Returns the memoNumber.
	 */
	public String getMemoNumber() {
		return memoNumber;
	}

	/**
	 * @param memoNumber The memoNumber to set.
	 */
	public void setMemoNumber(String memoNumber) {
		this.memoNumber = memoNumber;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the totAmtInContractCur.
	 */
	public double getTotAmtInContractCur() {
		return totAmtInContractCur;
	}

	/**
	 * @param totAmtInContractCur The totAmtInContractCur to set.
	 */
	public void setTotAmtInContractCur(double totAmtInContractCur) {
		this.totAmtInContractCur = totAmtInContractCur;
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
	 * @return Returns the contractCurrCode.
	 */
	public String getContractCurrCode() {
		return contractCurrCode;
	}
	/**
	 * @param contractCurrCode The contractCurrCode to set.
	 */
	public void setContractCurrCode(String contractCurrCode) {
		this.contractCurrCode = contractCurrCode;
	}
	/**
	 * @return Returns the differenceAmount.
	 */
	public double getDifferenceAmount() {
		return differenceAmount;
	}
	/**
	 * @param differenceAmount The differenceAmount to set.
	 */
	public void setDifferenceAmount(double differenceAmount) {
		this.differenceAmount = differenceAmount;
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
	/**
	 * @return Returns the provisionalAmount.
	 */
	public double getProvisionalAmount() {
		return provisionalAmount;
	}
	/**
	 * @param provisionalAmount The provisionalAmount to set.
	 */
	public void setProvisionalAmount(double provisionalAmount) {
		this.provisionalAmount = provisionalAmount;
	}
	/**
	 * @return Returns the reportedAmount.
	 */
	public double getReportedAmount() {
		return reportedAmount;
	}
	/**
	 * @param reportedAmount The reportedAmount to set.
	 */
	public void setReportedAmount(double reportedAmount) {
		this.reportedAmount = reportedAmount;
	}
	
	/**
	 * @return Returns the memoDate.
	 */
	public LocalDate getMemoDate() {
		return memoDate;
	}

	/**
	 * @param memoDate The memoDate to set.
	 */
	public void setMemoDate(LocalDate memoDate) {
		this.memoDate = memoDate;
	}
	/**
	 * @return Returns the previousDifferenceAmount.
	 */
	public double getPreviousDifferenceAmount() {
		return previousDifferenceAmount;
	}
	/**
	 * @param previousDifferenceAmount The previousDifferenceAmount to set.
	 */
	public void setPreviousDifferenceAmount(double previousDifferenceAmount) {
		this.previousDifferenceAmount = previousDifferenceAmount;
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
	 * @return Returns the outwardInvNumber.
	 */
	public String getOutwardInvNumber() {
		return outwardInvNumber;
	}
	/**
	 * @param outwardInvNumber The outwardInvNumber to set.
	 */
	public void setOutwardInvNumber(String outwardInvNumber) {
		this.outwardInvNumber = outwardInvNumber;
	}
	
	
	
	

}
