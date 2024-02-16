/*
 * ULDMovementFilterVO.java Created on Dec 21, 2005
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
 * @author A-1347
 *
 */
public class ULDMovementFilterVO extends AbstractVO{
    
    private String companyCode;
    private String uldNumber;
    
	private LocalDate fromDate;
	private LocalDate toDate;

	private int pageNumber; 
	private int totalRecords;
	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}
	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	private int noOfLoanTxns; 
	private int noOfMovements; 
	private int noOfTimesDmged; 
	private int noOfTimesRepaired; 
  
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
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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
     * @return Returns the fromDate.
     */
    public LocalDate getFromDate() {
        return fromDate;
    }
    /**
     * @param fromDate The fromDate to set.
     */
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }
    /**
     * @return Returns the toDate.
     */
    public LocalDate getToDate() {
        return toDate;
    }
    /**
     * @param toDate The toDate to set.
     */
    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
    /**
     * @return Returns the uldNumber.
     */
    public String getUldNumber() {
        return uldNumber;
    }
    /**
     * @param uldNumber The uldNumber to set.
     */
    public void setUldNumber(String uldNumber) {
        this.uldNumber = uldNumber;
    }

    
}
