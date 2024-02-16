/*
 * RateCardVO.java Created on Jan 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;



import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1556
 *
 */
public class RateCardVO extends AbstractVO {

    private String companyCode;
    private String rateCardID;
    private String rateCardDescription;
    private String rateCardStatus;
    private String description;
    private LocalDate validityStartDate;
    private LocalDate validityEndDate;
    private LocalDate creationDate;
    private String creationSource;
    private double mailDistanceFactor;
    private double categoryTonKMRefOne;
    private double categoryTonKMRefTwo;
    private double categoryTonKMRefThree;
    private double categoryTonKMRefFour;
    private double categoryTonKMRefFive;
    private double subclassTonKMRefOne;
    private double subclassTonKMRefTwo;
    private double subclassTonKMRefThree;
    private double subclassTonKMRefFour;
    private double subclassTonKMRefFive;
    private double exchangeRate;
    
    private String newRateCardID;
    private double newMailDistFactor;
    private double newCategoryTonKMRefOne;
    private double newCategoryTonKMRefTwo;
    private double newCategoryTonKMRefThree;
    private LocalDate newValidStartDate;
    private LocalDate newValidEndDate;
    
    //added by Sandeep 
    private LocalDate lastUpdateTime;
    private String lastUpdateUser;

    private int numberOfRateLines;

    //TODO Page<RateLineVO>
    private Page<RateLineVO> rateLineVOss;

    private String operationFlag;

    private Collection<RateLineVO> rateLineVOs;

    /**
     *
     */
    public RateCardVO() {

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
     * @return Returns the categoryTonKMRefFive.
     */
    public double getCategoryTonKMRefFive() {
        return categoryTonKMRefFive;
    }
    /**
     * @param categoryTonKMRefFive The categoryTonKMRefFive to set.
     */
    public void setCategoryTonKMRefFive(double categoryTonKMRefFive) {
        this.categoryTonKMRefFive = categoryTonKMRefFive;
    }
    /**
     * @return Returns the categoryTonKMRefFour.
     */
    public double getCategoryTonKMRefFour() {
        return categoryTonKMRefFour;
    }
    /**
     * @param categoryTonKMRefFour The categoryTonKMRefFour to set.
     */
    public void setCategoryTonKMRefFour(double categoryTonKMRefFour) {
        this.categoryTonKMRefFour = categoryTonKMRefFour;
    }
    /**
     * @return Returns the categoryTonKMRefOne.
     */
    public double getCategoryTonKMRefOne() {
        return categoryTonKMRefOne;
    }
    /**
     * @param categoryTonKMRefOne The categoryTonKMRefOne to set.
     */
    public void setCategoryTonKMRefOne(double categoryTonKMRefOne) {
        this.categoryTonKMRefOne = categoryTonKMRefOne;
    }
    /**
     * @return Returns the categoryTonKMRefThree.
     */
    public double getCategoryTonKMRefThree() {
        return categoryTonKMRefThree;
    }
    /**
     * @param categoryTonKMRefThree The categoryTonKMRefThree to set.
     */
    public void setCategoryTonKMRefThree(double categoryTonKMRefThree) {
        this.categoryTonKMRefThree = categoryTonKMRefThree;
    }
    /**
     * @return Returns the categoryTonKMRefTwo.
     */
    public double getCategoryTonKMRefTwo() {
        return categoryTonKMRefTwo;
    }
    /**
     * @param categoryTonKMRefTwo The categoryTonKMRefTwo to set.
     */
    public void setCategoryTonKMRefTwo(double categoryTonKMRefTwo) {
        this.categoryTonKMRefTwo = categoryTonKMRefTwo;
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
     * @return Returns the exchangeRate.
     */
    public double getExchangeRate() {
        return exchangeRate;
    }
    /**
     * @param exchangeRate The exchangeRate to set.
     */
    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
    /**
     * @return Returns the mailDistanceFactor.
     */
    public double getMailDistanceFactor() {
        return mailDistanceFactor;
    }
    /**
     * @param mailDistanceFactor The mailDistanceFactor to set.
     */
    public void setMailDistanceFactor(double mailDistanceFactor) {
        this.mailDistanceFactor = mailDistanceFactor;
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
     * @return Returns the rateCardStatus.
     */
    public String getRateCardStatus() {
        return rateCardStatus;
    }
    /**
     * @param rateCardStatus The rateCardStatus to set.
     */
    public void setRateCardStatus(String rateCardStatus) {
        this.rateCardStatus = rateCardStatus;
    }
    /**
     * @return Returns the subclassTonKMRefFive.
     */
    public double getSubclassTonKMRefFive() {
        return subclassTonKMRefFive;
    }
    /**
     * @param subclassTonKMRefFive The subclassTonKMRefFive to set.
     */
    public void setSubclassTonKMRefFive(double subclassTonKMRefFive) {
        this.subclassTonKMRefFive = subclassTonKMRefFive;
    }
    /**
     * @return Returns the subclassTonKMRefFour.
     */
    public double getSubclassTonKMRefFour() {
        return subclassTonKMRefFour;
    }
    /**
     * @param subclassTonKMRefFour The subclassTonKMRefFour to set.
     */
    public void setSubclassTonKMRefFour(double subclassTonKMRefFour) {
        this.subclassTonKMRefFour = subclassTonKMRefFour;
    }
    /**
     * @return Returns the subclassTonKMRefOne.
     */
    public double getSubclassTonKMRefOne() {
        return subclassTonKMRefOne;
    }
    /**
     * @param subclassTonKMRefOne The subclassTonKMRefOne to set.
     */
    public void setSubclassTonKMRefOne(double subclassTonKMRefOne) {
        this.subclassTonKMRefOne = subclassTonKMRefOne;
    }
    /**
     * @return Returns the subclassTonKMRefThree.
     */
    public double getSubclassTonKMRefThree() {
        return subclassTonKMRefThree;
    }
    /**
     * @param subclassTonKMRefThree The subclassTonKMRefThree to set.
     */
    public void setSubclassTonKMRefThree(double subclassTonKMRefThree) {
        this.subclassTonKMRefThree = subclassTonKMRefThree;
    }
    /**
     * @return Returns the subclassTonKMRefTwo.
     */
    public double getSubclassTonKMRefTwo() {
        return subclassTonKMRefTwo;
    }
    /**
     * @param subclassTonKMRefTwo The subclassTonKMRefTwo to set.
     */
    public void setSubclassTonKMRefTwo(double subclassTonKMRefTwo) {
        this.subclassTonKMRefTwo = subclassTonKMRefTwo;
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
     * @return Returns the creationDate.
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }
    /**
     * @param creationDate The creationDate to set.
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return Returns the creationSource.
     */
    public String getCreationSource() {
        return creationSource;
    }
    /**
     * @param creationSource The creationSource to set.
     */
    public void setCreationSource(String creationSource) {
        this.creationSource = creationSource;
    }


    /**
     * @return Returns the rateLineVOss.
     */
    public Page<RateLineVO> getRateLineVOss() {
        return rateLineVOss;
    }
    /**
     * @param rateLineVOss The rateLineVOss to set.
     */
    public void setRateLineVOss(Page<RateLineVO> rateLineVOss) {
        this.rateLineVOss = rateLineVOss;
    }

	/**
	 * @return Returns the rateCardDescription.
	 */
	public String getRateCardDescription() {
		return rateCardDescription;
	}

	/**
	 * @param rateCardDescription The rateCardDescription to set.
	 */
	public void setRateCardDescription(String rateCardDescription) {
		this.rateCardDescription = rateCardDescription;
	}

	/**
	 * @return Returns the numberOfRateLines.
	 */
	public int getNumberOfRateLines() {
		return numberOfRateLines;
	}

	/**
	 * @param numberOfRateLines The numberOfRateLines to set.
	 */
	public void setNumberOfRateLines(int numberOfRateLines) {
		this.numberOfRateLines = numberOfRateLines;
	}
	/*********Added by A-5166 for ICRD-17262 on 07-Feb-2013 Starts*****/
	/**
	 * @return the newRateCardID
	 */
	public String getNewRateCardID() {
		return newRateCardID;
	}


	/**
	 * @param newRateCardID the newRateCardID to set
	 */
	public void setNewRateCardID(String newRateCardID) {
		this.newRateCardID = newRateCardID;
	}
	/**
	 * @return the newMailDistFactor
	 */
	public double getNewMailDistFactor() {
		return newMailDistFactor;
	}


	/**
	 * @param newMailDistFactor the newMailDistFactor to set
	 */
	public void setNewMailDistFactor(double newMailDistFactor) {
		this.newMailDistFactor = newMailDistFactor;
	}
	/**
	 * @return the newCategoryTonKMRefOne
	 */
	public double getNewCategoryTonKMRefOne() {
		return newCategoryTonKMRefOne;
	}


	/**
	 * @param newCategoryTonKMRefOne the newCategoryTonKMRefOne to set
	 */
	public void setNewCategoryTonKMRefOne(double newCategoryTonKMRefOne) {
		this.newCategoryTonKMRefOne = newCategoryTonKMRefOne;
	}


	/**
	 * @return the newCategoryTonKMRefTwo
	 */
	public double getNewCategoryTonKMRefTwo() {
		return newCategoryTonKMRefTwo;
	}


	/**
	 * @param newCategoryTonKMRefTwo the newCategoryTonKMRefTwo to set
	 */
	public void setNewCategoryTonKMRefTwo(double newCategoryTonKMRefTwo) {
		this.newCategoryTonKMRefTwo = newCategoryTonKMRefTwo;
	}


	/**
	 * @return the newCategoryTonKMRefThree
	 */
	public double getNewCategoryTonKMRefThree() {
		return newCategoryTonKMRefThree;
	}


	/**
	 * @param newCategoryTonKMRefThree the newCategoryTonKMRefThree to set
	 */
	public void setNewCategoryTonKMRefThree(double newCategoryTonKMRefThree) {
		this.newCategoryTonKMRefThree = newCategoryTonKMRefThree;
	}
	 /**
	 * @return the newValidStartDate
	 */
	public LocalDate getNewValidStartDate() {
		return newValidStartDate;
	}


	/**
	 * @param newValidStartDate the newValidStartDate to set
	 */
	public void setNewValidStartDate(LocalDate newValidStartDate) {
		this.newValidStartDate = newValidStartDate;
	}


	/**
	 * @return the newValidEndDate
	 */
	public LocalDate getNewValidEndDate() {
		return newValidEndDate;
	}


	/**
	 * @param newValidEndDate the newValidEndDate to set
	 */
	public void setNewValidEndDate(LocalDate newValidEndDate) {
		this.newValidEndDate = newValidEndDate;
	}
	/**
	 * @return the rateLineVOs
	 */
	public Collection<RateLineVO> getRateLineVOs() {
		return rateLineVOs;
	}


	/**
	 * @param rateLineVOs the rateLineVOs to set
	 */
	public void setRateLineVOs(Collection<RateLineVO> rateLineVOs) {
		this.rateLineVOs = rateLineVOs;
	}
	/*********Added by A-5166 for ICRD-17262 on 07-Feb-2013 Ends*****/
}
