/*
 * RejectionMemoFilterVO.java Created on may 18,2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-2391
 *
 */

public class RejectionMemoFilterVO extends AbstractVO{
	private String companyCode;
	private String memoCode;
	private String invoiceNumber;
	private int airlineIdentifier;
	private String clearancePeriod;
	//Added by Deepthi for AirNZ903
	private LocalDate dsnDate;
	private String billingBasis;
	private String csgDocNum;
	private int csgSeqNum;
	private String poaCode;
	/**
	 * @return the billingBasis
	 */
	public String getBillingBasis() {
		return billingBasis;
	}
	/**
	 * @param billingBasis the billingBasis to set
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}
	/**
	 * @return the csgDocNum
	 */
	public String getCsgDocNum() {
		return csgDocNum;
	}
	/**
	 * @param csgDocNum the csgDocNum to set
	 */
	public void setCsgDocNum(String csgDocNum) {
		this.csgDocNum = csgDocNum;
	}
	/**
	 * @return the csgSeqNum
	 */
	public int getCsgSeqNum() {
		return csgSeqNum;
	}
	/**
	 * @param csgSeqNum the csgSeqNum to set
	 */
	public void setCsgSeqNum(int csgSeqNum) {
		this.csgSeqNum = csgSeqNum;
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
	 * @return the dsnDate
	 */
	public LocalDate getDsnDate() {
		return dsnDate;
	}
	/**
	 * @param dsnDate the dsnDate to set
	 */
	public void setDsnDate(LocalDate dsnDate) {
		this.dsnDate = dsnDate;
	}



}
