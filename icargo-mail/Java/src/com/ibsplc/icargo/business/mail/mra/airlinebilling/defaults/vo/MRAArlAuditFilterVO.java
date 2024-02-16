/*
 * MRAArlAuditFilterVO.java Created on Aug 16, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-2391
 *
 */
public class MRAArlAuditFilterVO  extends AbstractVO{
	private String companyCode;  
    private LocalDate txnFromDate;
 	private LocalDate txnToDate;
 	private String airlineCode;
	private String clearancePeriod;
	private int airlineIdentifier;
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
	 * @return Returns the txnFromDate.
	 */
	public LocalDate getTxnFromDate() {
		return txnFromDate;
	}
	/**
	 * @param txnFromDate The txnFromDate to set.
	 */
	public void setTxnFromDate(LocalDate txnFromDate) {
		this.txnFromDate = txnFromDate;
	}
	/**
	 * @return Returns the txnToDate.
	 */
	public LocalDate getTxnToDate() {
		return txnToDate;
	}
	/**
	 * @param txnToDate The txnToDate to set.
	 */
	public void setTxnToDate(LocalDate txnToDate) {
		this.txnToDate = txnToDate;
	}

}
