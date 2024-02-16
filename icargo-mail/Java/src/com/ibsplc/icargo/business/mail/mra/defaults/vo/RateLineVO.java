/*
 * RateLineVO.java Created on Jan 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1556
 *
 */
public class RateLineVO extends AbstractVO {

    private String companyCode;
    private String rateCardID;
    private int ratelineSequenceNumber;

    private String ratelineStatus;
    private String origin;
    private String destination;
    private double iataKilometre;
    private double mailKilometre;
    private double rateInSDRForCategoryRefOne;
    private double rateInSDRForCategoryRefTwo;
    private double rateInSDRForCategoryRefThree;
    private double rateInSDRForCategoryRefFour;
    private double rateInSDRForCategoryRefFive;
    private double rateInBaseCurrForCategoryRefOne;
    private double rateInBaseCurrForCategoryRefTwo;
    private double rateInBaseCurrForCategoryRefThree;
    private double rateInBaseCurrForCategoryRefFour;
    private double rateInBaseCurrForCategoryRefFive;
    private double rateInSDRForSubclassRefOne;
    private double rateInSDRForSubclassRefTwo;
    private double rateInSDRForSubclassRefThree;
    private double rateInSDRForSubclassRefFour;
    private double rateInSDRForSubclassRefFive;
    private double rateInBaseCurrForSubclassRefOne;
    private double rateInBaseCurrForSubclassRefTwo;
    private double rateInBaseCurrForSubclassRefThree;
    private double rateInBaseCurrForSubclassRefFour;
    private double rateInBaseCurrForSubclassRefFive;
    private LocalDate validityStartDate;
    private LocalDate validityEndDate;
    private String operationFlag;
    private String lastUpdateUser;
    private LocalDate lastUpdateTime;
    private String overlapflag;
    private int txnLogSerialNum;//added by a-7871 for ICRD-223130
    private String transactionCode;//added by a-7871 for ICRD-223130
    private RateLineErrorVO errorVO;//added by a-7871 for ICRD-223130
    private String orgDstLevel;
    
    /**
	 * @return the transactionCode
	 */
    public String getTransactionCode() {
		return transactionCode;
	}
    /**
	 * @param transactionCode the transactionCode to set
	 */
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	/**
	 * @return the txnLogSerialNum
	 */
	public int getTxnLogSerialNum() {
		return txnLogSerialNum;
	}
	/**
	 * @param txnLogSerialNum the txnLogSerialNum to set
	 */
	public void setTxnLogSerialNum(int txnLogSerialNum) {
		this.txnLogSerialNum = txnLogSerialNum;
	}
	/**
     * for denoting Active Status of a rateline
     */
    public static final String ACTIVE = "A";
    /**
     *
     */
    public RateLineVO() {

    }

    /**Added by A-5166 for ICRD-17262 on 07-Feb-2013
	 * @return the Overlapflag
	 */
    public String getOverlapflag()
    {
        return overlapflag;
    }
    
    /**Added by A-5166 for ICRD-17262 on 07-Feb-2013
	 * @param Overlapflag the Overlapflag to set
	 */
    public void setOverlapflag(String s)
    {
        overlapflag = s;
    }
    
    /**
	 * @return the lastUpdateTime
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}


	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}


	/**
	 * @return the lastUpdateUser
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}


	/**
	 * @param lastUpdateUser the lastUpdateUser to set
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
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
     * @return Returns the destination.
     */
    public String getDestination() {
        return destination;
    }
    /**
     * @param destination The destination to set.
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
	 * @return Returns the iataKilometre.
	 */
	public double getIataKilometre() {
		return iataKilometre;
	}


	/**
	 * @param iataKilometre The iataKilometre to set.
	 */
	public void setIataKilometre(double iataKilometre) {
		this.iataKilometre = iataKilometre;
	}


	/**
	 * @return Returns the mailKilometre.
	 */
	public double getMailKilometre() {
		return mailKilometre;
	}


	/**
	 * @param mailKilometre The mailKilometre to set.
	 */
	public void setMailKilometre(double mailKilometre) {
		this.mailKilometre = mailKilometre;
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
     * @return Returns the origin.
     */
    public String getOrigin() {
        return origin;
    }
    /**
     * @param origin The origin to set.
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    /**
     * @return Returns the rateCardID.
     */
    public String getRateCardID() {
        return rateCardID;
    }
    /**
     * @param rateCardID The rateCardID to set.
     */
    public void setRateCardID(String rateCardID) {
        this.rateCardID = rateCardID;
    }
    /**
     * @return Returns the rateInBaseCurrForCategoryRefFive.
     */
    public double getRateInBaseCurrForCategoryRefFive() {
        return rateInBaseCurrForCategoryRefFive;
    }
    /**
     * @param rateInBaseCurrForCategoryRefFive The rateInBaseCurrForCategoryRefFive to set.
     */
    public void setRateInBaseCurrForCategoryRefFive(
            double rateInBaseCurrForCategoryRefFive) {
        this.rateInBaseCurrForCategoryRefFive = rateInBaseCurrForCategoryRefFive;
    }
    /**
     * @return Returns the rateInBaseCurrForCategoryRefFour.
     */
    public double getRateInBaseCurrForCategoryRefFour() {
        return rateInBaseCurrForCategoryRefFour;
    }
    /**
     * @param rateInBaseCurrForCategoryRefFour The rateInBaseCurrForCategoryRefFour to set.
     */
    public void setRateInBaseCurrForCategoryRefFour(
            double rateInBaseCurrForCategoryRefFour) {
        this.rateInBaseCurrForCategoryRefFour = rateInBaseCurrForCategoryRefFour;
    }
    /**
     * @return Returns the rateInBaseCurrForCategoryRefOne.
     */
    public double getRateInBaseCurrForCategoryRefOne() {
        return rateInBaseCurrForCategoryRefOne;
    }
    /**
     * @param rateInBaseCurrForCategoryRefOne The rateInBaseCurrForCategoryRefOne to set.
     */
    public void setRateInBaseCurrForCategoryRefOne(
            double rateInBaseCurrForCategoryRefOne) {
        this.rateInBaseCurrForCategoryRefOne = rateInBaseCurrForCategoryRefOne;
    }
    /**
     * @return Returns the rateInBaseCurrForCategoryRefThree.
     */
    public double getRateInBaseCurrForCategoryRefThree() {
        return rateInBaseCurrForCategoryRefThree;
    }
    /**
     * @param rateInBaseCurrForCategoryRefThree The rateInBaseCurrForCategoryRefThree to set.
     */
    public void setRateInBaseCurrForCategoryRefThree(
            double rateInBaseCurrForCategoryRefThree) {
        this.rateInBaseCurrForCategoryRefThree = rateInBaseCurrForCategoryRefThree;
    }
    /**
     * @return Returns the rateInBaseCurrForCategoryRefTwo.
     */
    public double getRateInBaseCurrForCategoryRefTwo() {
        return rateInBaseCurrForCategoryRefTwo;
    }
    /**
     * @param rateInBaseCurrForCategoryRefTwo The rateInBaseCurrForCategoryRefTwo to set.
     */
    public void setRateInBaseCurrForCategoryRefTwo(
            double rateInBaseCurrForCategoryRefTwo) {
        this.rateInBaseCurrForCategoryRefTwo = rateInBaseCurrForCategoryRefTwo;
    }
    /**
     * @return Returns the rateInBaseCurrForSubclassRefFive.
     */
    public double getRateInBaseCurrForSubclassRefFive() {
        return rateInBaseCurrForSubclassRefFive;
    }
    /**
     * @param rateInBaseCurrForSubclassRefFive The rateInBaseCurrForSubclassRefFive to set.
     */
    public void setRateInBaseCurrForSubclassRefFive(
            double rateInBaseCurrForSubclassRefFive) {
        this.rateInBaseCurrForSubclassRefFive = rateInBaseCurrForSubclassRefFive;
    }
    /**
     * @return Returns the rateInBaseCurrForSubclassRefFour.
     */
    public double getRateInBaseCurrForSubclassRefFour() {
        return rateInBaseCurrForSubclassRefFour;
    }
    /**
     * @param rateInBaseCurrForSubclassRefFour The rateInBaseCurrForSubclassRefFour to set.
     */
    public void setRateInBaseCurrForSubclassRefFour(
            double rateInBaseCurrForSubclassRefFour) {
        this.rateInBaseCurrForSubclassRefFour = rateInBaseCurrForSubclassRefFour;
    }
    /**
     * @return Returns the rateInBaseCurrForSubclassRefOne.
     */
    public double getRateInBaseCurrForSubclassRefOne() {
        return rateInBaseCurrForSubclassRefOne;
    }
    /**
     * @param rateInBaseCurrForSubclassRefOne The rateInBaseCurrForSubclassRefOne to set.
     */
    public void setRateInBaseCurrForSubclassRefOne(
            double rateInBaseCurrForSubclassRefOne) {
        this.rateInBaseCurrForSubclassRefOne = rateInBaseCurrForSubclassRefOne;
    }
    /**
     * @return Returns the rateInBaseCurrForSubclassRefThree.
     */
    public double getRateInBaseCurrForSubclassRefThree() {
        return rateInBaseCurrForSubclassRefThree;
    }
    /**
     * @param rateInBaseCurrForSubclassRefThree The rateInBaseCurrForSubclassRefThree to set.
     */
    public void setRateInBaseCurrForSubclassRefThree(
            double rateInBaseCurrForSubclassRefThree) {
        this.rateInBaseCurrForSubclassRefThree = rateInBaseCurrForSubclassRefThree;
    }
    /**
     * @return Returns the rateInBaseCurrForSubclassRefTwo.
     */
    public double getRateInBaseCurrForSubclassRefTwo() {
        return rateInBaseCurrForSubclassRefTwo;
    }
    /**
     * @param rateInBaseCurrForSubclassRefTwo The rateInBaseCurrForSubclassRefTwo to set.
     */
    public void setRateInBaseCurrForSubclassRefTwo(
            double rateInBaseCurrForSubclassRefTwo) {
        this.rateInBaseCurrForSubclassRefTwo = rateInBaseCurrForSubclassRefTwo;
    }
    /**
     * @return Returns the rateInSDRForCategoryRefFive.
     */
    public double getRateInSDRForCategoryRefFive() {
        return rateInSDRForCategoryRefFive;
    }
    /**
     * @param rateInSDRForCategoryRefFive The rateInSDRForCategoryRefFive to set.
     */
    public void setRateInSDRForCategoryRefFive(
            double rateInSDRForCategoryRefFive) {
        this.rateInSDRForCategoryRefFive = rateInSDRForCategoryRefFive;
    }
    /**
     * @return Returns the rateInSDRForCategoryRefFour.
     */
    public double getRateInSDRForCategoryRefFour() {
        return rateInSDRForCategoryRefFour;
    }
    /**
     * @param rateInSDRForCategoryRefFour The rateInSDRForCategoryRefFour to set.
     */
    public void setRateInSDRForCategoryRefFour(
            double rateInSDRForCategoryRefFour) {
        this.rateInSDRForCategoryRefFour = rateInSDRForCategoryRefFour;
    }
    /**
     * @return Returns the rateInSDRForCategoryRefOne.
     */
    public double getRateInSDRForCategoryRefOne() {
        return rateInSDRForCategoryRefOne;
    }
    /**
     * @param rateInSDRForCategoryRefOne The rateInSDRForCategoryRefOne to set.
     */
    public void setRateInSDRForCategoryRefOne(double rateInSDRForCategoryRefOne) {
        this.rateInSDRForCategoryRefOne = rateInSDRForCategoryRefOne;
    }
    /**
     * @return Returns the rateInSDRForCategoryRefThree.
     */
    public double getRateInSDRForCategoryRefThree() {
        return rateInSDRForCategoryRefThree;
    }
    /**
     * @param rateInSDRForCategoryRefThree The rateInSDRForCategoryRefThree to set.
     */
    public void setRateInSDRForCategoryRefThree(
            double rateInSDRForCategoryRefThree) {
        this.rateInSDRForCategoryRefThree = rateInSDRForCategoryRefThree;
    }
    /**
     * @return Returns the rateInSDRForCategoryRefTwo.
     */
    public double getRateInSDRForCategoryRefTwo() {
        return rateInSDRForCategoryRefTwo;
    }
    /**
     * @param rateInSDRForCategoryRefTwo The rateInSDRForCategoryRefTwo to set.
     */
    public void setRateInSDRForCategoryRefTwo(double rateInSDRForCategoryRefTwo) {
        this.rateInSDRForCategoryRefTwo = rateInSDRForCategoryRefTwo;
    }
    /**
     * @return Returns the rateInSDRForSubclassRefFive.
     */
    public double getRateInSDRForSubclassRefFive() {
        return rateInSDRForSubclassRefFive;
    }
    /**
     * @param rateInSDRForSubclassRefFive The rateInSDRForSubclassRefFive to set.
     */
    public void setRateInSDRForSubclassRefFive(
            double rateInSDRForSubclassRefFive) {
        this.rateInSDRForSubclassRefFive = rateInSDRForSubclassRefFive;
    }
    /**
     * @return Returns the rateInSDRForSubclassRefFour.
     */
    public double getRateInSDRForSubclassRefFour() {
        return rateInSDRForSubclassRefFour;
    }
    /**
     * @param rateInSDRForSubclassRefFour The rateInSDRForSubclassRefFour to set.
     */
    public void setRateInSDRForSubclassRefFour(
            double rateInSDRForSubclassRefFour) {
        this.rateInSDRForSubclassRefFour = rateInSDRForSubclassRefFour;
    }
    /**
     * @return Returns the rateInSDRForSubclassRefOne.
     */
    public double getRateInSDRForSubclassRefOne() {
        return rateInSDRForSubclassRefOne;
    }
    /**
     * @param rateInSDRForSubclassRefOne The rateInSDRForSubclassRefOne to set.
     */
    public void setRateInSDRForSubclassRefOne(double rateInSDRForSubclassRefOne) {
        this.rateInSDRForSubclassRefOne = rateInSDRForSubclassRefOne;
    }
    /**
     * @return Returns the rateInSDRForSubclassRefThree.
     */
    public double getRateInSDRForSubclassRefThree() {
        return rateInSDRForSubclassRefThree;
    }
    /**
     * @param rateInSDRForSubclassRefThree The rateInSDRForSubclassRefThree to set.
     */
    public void setRateInSDRForSubclassRefThree(
            double rateInSDRForSubclassRefThree) {
        this.rateInSDRForSubclassRefThree = rateInSDRForSubclassRefThree;
    }
    /**
     * @return Returns the rateInSDRForSubclassRefTwo.
     */
    public double getRateInSDRForSubclassRefTwo() {
        return rateInSDRForSubclassRefTwo;
    }
    /**
     * @param rateInSDRForSubclassRefTwo The rateInSDRForSubclassRefTwo to set.
     */
    public void setRateInSDRForSubclassRefTwo(double rateInSDRForSubclassRefTwo) {
        this.rateInSDRForSubclassRefTwo = rateInSDRForSubclassRefTwo;
    }
    /**
     * @return Returns the ratelineSequenceNumber.
     */
    public int getRatelineSequenceNumber() {
        return ratelineSequenceNumber;
    }
    /**
     * @param ratelineSequenceNumber The ratelineSequenceNumber to set.
     */
    public void setRatelineSequenceNumber(int ratelineSequenceNumber) {
        this.ratelineSequenceNumber = ratelineSequenceNumber;
    }
    /**
     * @return Returns the ratelineStatus.
     */
    public String getRatelineStatus() {
        return ratelineStatus;
    }
    /**
     * @param ratelineStatus The ratelineStatus to set.
     */
    public void setRatelineStatus(String ratelineStatus) {
        this.ratelineStatus = ratelineStatus;
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
	public RateLineErrorVO getErrorVO() {
		return errorVO;
	}
	public void setErrorVO(RateLineErrorVO errorVO) {
		this.errorVO = errorVO;
	}
	/**
	 * 	Getter for orgDstLevel 
	 *	Added by : A-5219 on 15-Oct-2020
	 * 	Used for :
	 */
	public String getOrgDstLevel() {
		return orgDstLevel;
	}
	/**
	 *  @param orgDstLevel the orgDstLevel to set
	 * 	Setter for orgDstLevel 
	 *	Added by : A-5219 on 15-Oct-2020
	 * 	Used for :
	 */
	public void setOrgDstLevel(String orgDstLevel) {
		this.orgDstLevel = orgDstLevel;
	}
}
