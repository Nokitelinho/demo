/*
 * ULDIntMvtHistoryFilterVO.java Created on Mar 26, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2412
 *
 */
public class ULDIntMvtHistoryFilterVO extends AbstractVO{
	private String companyCode;
    private String uldNumber;
    
	private LocalDate fromDate;
	private LocalDate toDate;
	
	private int pageNumber;
	
	private int noOfMovements;
	    
    private int noOfLoanTxns;
    
    private int noOfTimesDmged;
    
    private int noOfTimesRepaired;
    
    private String reasonForMvt;
    
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public String getUldNumber() {
		return uldNumber;
	}
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	/**
	 * @return the noOfLoanTxns
	 */
	public int getNoOfLoanTxns() {
		return noOfLoanTxns;
	}
	/**
	 * @param noOfLoanTxns the noOfLoanTxns to set
	 */
	public void setNoOfLoanTxns(int noOfLoanTxns) {
		this.noOfLoanTxns = noOfLoanTxns;
	}
	/**
	 * @return the noOfMovements
	 */
	public int getNoOfMovements() {
		return noOfMovements;
	}
	/**
	 * @param noOfMovements the noOfMovements to set
	 */
	public void setNoOfMovements(int noOfMovements) {
		this.noOfMovements = noOfMovements;
	}
	/**
	 * @return the noOfTimesDmged
	 */
	public int getNoOfTimesDmged() {
		return noOfTimesDmged;
	}
	/**
	 * @param noOfTimesDmged the noOfTimesDmged to set
	 */
	public void setNoOfTimesDmged(int noOfTimesDmged) {
		this.noOfTimesDmged = noOfTimesDmged;
	}
	/**
	 * @return the noOfTimesRepaired
	 */
	public int getNoOfTimesRepaired() {
		return noOfTimesRepaired;
	}
	/**
	 * @param noOfTimesRepaired the noOfTimesRepaired to set
	 */
	public void setNoOfTimesRepaired(int noOfTimesRepaired) {
		this.noOfTimesRepaired = noOfTimesRepaired;
	}
	/**
	 * @return the reasonForMvt
	 */
	public String getReasonForMvt() {
		return reasonForMvt;
	}
	/**
	 * @param reasonForMvt the reasonForMvt to set
	 */
	public void setReasonForMvt(String reasonForMvt) {
		this.reasonForMvt = reasonForMvt;
	}
	
}
