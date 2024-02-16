/*
 * ULDNumberVO.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.vo;


import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * This class is used to represent the details for 
 * creating multiple ULDs
 * 
 * @author A-1347
 *
 */
public class ULDNumberVO extends AbstractVO{
    
    private int uldNumber;
    
    private String uldSerialNumber;
    
    private int noOfMovements;
    
    private int noOfLoanTxns;
    
    private int noOfTimesDmged;
    
    private int noOfTimesRepaired;
    
    
    
    
    /**
     * @return Returns the uldNumber.
     */
    public int getUldNumber() {
        return uldNumber;
    }
    /**
     * @param uldNumber The uldNumber to set.
     */
    public void setUldNumber(int uldNumber) {
        this.uldNumber = uldNumber;
    }
    /**
     * @return Returns the uldSerialNumber.
     */
    public String getUldSerialNumber() {
        return uldSerialNumber;
    }
    /**
     * @param uldSerialNumber The uldSerialNumber to set.
     */
    public void setUldSerialNumber(String uldSerialNumber) {
        this.uldSerialNumber = uldSerialNumber;
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
}
