/*
 * TransactionVO.java Created on Jan 5, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.transaction.vo;

import java.io.Serializable;
import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class TransactionVO  extends AbstractVO implements Serializable{
	
	/**
     * module
     */
	public static final String MODULE ="uld"; 
	/**
	 * submodule
	 */
	public static final String SUBMODULE ="defaults";
	/**
	 * entity
	 */
	public static final String ENTITY ="uld.defaults.transaction.ULDTransaction";
	
	public static final String TO_BE_RETURNED ="T";
	
	public static final String TO_BE_INVOICED ="R";
	/**
	 * Borrow
	 */
	public static final String BORROW ="B";
	/**
	 * Loan
	 */
	public static final String LOAN ="L";
	
	
	public static final String INVOICED  ="I";
	/**
	 * companyCode
	 */
    private String companyCode;
    
    /**
	 * transactionType
	 */
    private String transactionType;
    
    /**
	 * transactionNature
	 */
    private String transactionNature;
   
    /**
	 * transactionStation
	 */
    private String transactionStation;
   
    /**
	 * transactionDate
	 */
    private LocalDate transactionDate;
    
    /**
	 * strTransactionDate to display date in Screen
	 */
    private String strTransactionDate;
    
    /**
	 * transactionTime to display time in Screen
	 */
    private String transactionTime;
    
    /**
	 * transactionRemark
	 */
    private String transactionRemark;
    
    /**
	 * partyType
	 */
    private String partyType;   
    
   
    
    /**
	 * partyCode
	 */
    private String fromPartyCode;
    
    /**
	 * partyName
	 */
    private String fromPartyName;
    
    /**
	 * partyCode
	 */
    private String toPartyCode;
    
    /**
	 * partyName
	 */
    private String toPartyName;
    
    /**
	 * currOwnerCode
	 */
    private int  currOwnerCode;
    
    private String  operationalFlag;
    
    /**
	 * operationalAirlineIdentifier
	 */
    private int operationalAirlineIdentifier;
    
    /**
	 * transactionStatus
	 */
    private String transactionStatus;
    
    /**
     * To triggrt the USR report to be printed
     */
    private boolean isToBePrinted;
    private String awbNumber;
    private String originatorName; //Added by A-4072 for CR ICRD-192300 
    private String emptyStatus;
    
    private String strLeaseEndDate;
    
    private String transactionId;
    
    
      /**
	 * uldTransactionDetailsVOs
	 */
    private Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs;
    
    /**
	 * accessoryTransactionVOs
	 */
    private Collection<AccessoryTransactionVO> accessoryTransactionVOs;
    
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
	 * @return Returns the operationalAirlineIdentifier.
	 */
	public int getOperationalAirlineIdentifier() {
		return operationalAirlineIdentifier;
	}
	/**
	 * @param operationalAirlineIdentifier The operationalAirlineIdentifier to set.
	 */
	public void setOperationalAirlineIdentifier(int operationalAirlineIdentifier) {
		this.operationalAirlineIdentifier = operationalAirlineIdentifier;
	}
	/**
	 * @return Returns the accessoryTransactions.
	 */
	public Collection<AccessoryTransactionVO> getAccessoryTransactionVOs() {
		return accessoryTransactionVOs;
	}
	/**
	 * @param accessoryTransactions The accessoryTransactions to set.
	 */
	public void setAccessoryTransactionVOs(Collection<AccessoryTransactionVO> accessoryTransactionVOs) {
		this.accessoryTransactionVOs = accessoryTransactionVOs;
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
	 * @return Returns the transactionType.
	 */
	public String getTransactionType() {
		return transactionType;
	}
	/**
	 * @param transactionType The transactionType to set.
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	/**
	 * @return Returns the uldTransactionDetailsVOs.
	 */
	public Collection<ULDTransactionDetailsVO> getUldTransactionDetailsVOs() {
		return uldTransactionDetailsVOs;
	}
	/**
	 * @param uldTransactionDetailsVOs The uldTransactionDetailsVOs to set.
	 */
	public void setUldTransactionDetailsVOs(Collection <ULDTransactionDetailsVO> uldTransactionDetailsVOs) {
		this.uldTransactionDetailsVOs = uldTransactionDetailsVOs;
	}
	
	/**
	 * @return Returns the fromPartyCode.
	 */
	public String getFromPartyCode() {
		return fromPartyCode;
	}
	/**
	 * @param fromPartyCode The fromPartyCode to set.
	 */
	public void setFromPartyCode(String fromPartyCode) {
		this.fromPartyCode = fromPartyCode;
	}
	/**
	 * @return Returns the fromPartyName.
	 */
	public String getFromPartyName() {
		return fromPartyName;
	}
	/**
	 * @param fromPartyName The fromPartyName to set.
	 */
	public void setFromPartyName(String fromPartyName) {
		this.fromPartyName = fromPartyName;
	}
	/**
	 * @return Returns the toPartyCode.
	 */
	public String getToPartyCode() {
		return toPartyCode;
	}
	/**
	 * @param toPartyCode The toPartyCode to set.
	 */
	public void setToPartyCode(String toPartyCode) {
		this.toPartyCode = toPartyCode;
	}
	/**
	 * @return Returns the toPartyName.
	 */
	public String getToPartyName() {
		return toPartyName;
	}
	/**
	 * @param toPartyName The toPartyName to set.
	 */
	public void setToPartyName(String toPartyName) {
		this.toPartyName = toPartyName;
	}
	/**
	 * @return Returns the partyType.
	 */
	public String getPartyType() {
		return this.partyType;
	}
	/**
	 * @param partyType The partyType to set.
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}
	/**
	 * @return Returns the strTransactionDate.
	 */
	public String getStrTransactionDate() {
		return this.strTransactionDate;
	}
	/**
	 * @param strTransactionDate The strTransactionDate to set.
	 */
	public void setStrTransactionDate(String strTransactionDate) {
		this.strTransactionDate = strTransactionDate;
	}
	/**
	 * @return Returns the transactionDate.
	 */
	public LocalDate getTransactionDate() {
		return this.transactionDate;
	}
	/**
	 * @param transactionDate The transactionDate to set.
	 */
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
	/**
	 * @return Returns the transactionNature.
	 */
	public String getTransactionNature() {
		return this.transactionNature;
	}
	/**
	 * @param transactionNature The transactionNature to set.
	 */
	public void setTransactionNature(String transactionNature) {
		this.transactionNature = transactionNature;
	}
	/**
	 * @return Returns the transactionRemark.
	 */
	public String getTransactionRemark() {
		return this.transactionRemark;
	}
	/**
	 * @param transactionRemark The transactionRemark to set.
	 */
	public void setTransactionRemark(String transactionRemark) {
		this.transactionRemark = transactionRemark;
	}
	/**
	 * @return Returns the transactionStation.
	 */
	public String getTransactionStation() {
		return this.transactionStation;
	}
	/**
	 * @param transactionStation The transactionStation to set.
	 */
	public void setTransactionStation(String transactionStation) {
		this.transactionStation = transactionStation;
	}
	/**
	 * @return Returns the currOwnerCode.
	 */
	public int getCurrOwnerCode() {
		return this.currOwnerCode;
	}
	/**
	 * @param currOwnerCode The currOwnerCode to set.
	 */
	public void setCurrOwnerCode(int currOwnerCode) {
		this.currOwnerCode = currOwnerCode;
	}
	/**
	 * @return Returns the transactionStatus.
	 */
	public String getTransactionStatus() {
		return this.transactionStatus;
	}
	/**
	 * @param transactionStatus The transactionStatus to set.
	 */
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	/**
	 * @return Returns the transactionTime.
	 */
	public String getTransactionTime() {
		return transactionTime;
	}
	/**
	 * @param transactionTime The transactionTime to set.
	 */
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	/**
	 * @return Returns the isToBePrinted.
	 */
	public boolean isToBePrinted() {
		return this.isToBePrinted;
	}
	/**
	 * @param isToBePrinted The isToBePrinted to set.
	 */
	public void setToBePrinted(boolean isToBePrinted) {
		this.isToBePrinted = isToBePrinted;
	}
	/**
	 * @return the awbNumber
	 */
	public String getAwbNumber() {
		return awbNumber;
	}
	/**
	 * @param awbNumber the awbNumber to set
	 */
	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}
	/**
	 * @return the emptyStatus
	 */
	public String getEmptyStatus() {
		return emptyStatus;
	}
	/**
	 * @param emptyStatus the emptyStatus to set
	 */
	public void setEmptyStatus(String emptyStatus) {
		this.emptyStatus = emptyStatus;
	}
	/**
	 * @return the originatorName
	 */
	public String getOriginatorName() {
		return originatorName;
	}
	/**
	 * @param originatorName the originatorName to set
	 */
	public void setOriginatorName(String originatorName) {
		this.originatorName = originatorName;
	}
	/**
	 * 
	 * 	Method		:	TransactionVO.getStrLeaseEndDate
	 *	Added on 	:	23-Dec-2021
	 * 	Used for 	:	getting Lease End date to display
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getStrLeaseEndDate() {
		return strLeaseEndDate;
	}
	/**
	 * 
	 * 	Method		:	TransactionVO.setStrLeaseEndDate
	 *	Added on 	:	23-Dec-2021
	 * 	Used for 	:	getting Lease End date to display
	 *	Parameters	:	@param strLeaseEndDate 
	 *	Return type	: 	void
	 */
	public void setStrLeaseEndDate(String strLeaseEndDate) {
		this.strLeaseEndDate = strLeaseEndDate;
	}
	/**
	 * 
	 * 	Method		:	TransactionVO.getTransactionId
	 *	Added on 	:	05-Dec-2022
	 * 	Used for 	: 	Getting the transaction ID
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getTransactionId() {
		return transactionId;
	}
	/**
	 * 
	 * 	Method		:	TransactionVO.setTransactionId
	 *	Added on 	:	05-Dec-2022
	 * 	Used for 	:	Setting the transaction ID
	 *	Parameters	:	@param transactionId 
	 *	Return type	: 	void
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}	
	
	
	
   
    

}
