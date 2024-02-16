/*
 * ULDReturnTxnVO.java Created on Jan 5, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.transaction.vo;

import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class ULDReturnTxnVO  extends AbstractVO implements Serializable{
    
    private String companyCode;
    //Either Loan(L)/Borrow(B)
    private String transactionType;
    private String uldNumber;
    private String transactionAirportCode;
    private LocalDate transactionDate;
    //Agent - G ,  Airline - A , Others - O
    private String partyType;
    //Respective Codes
    private String partyCode;
    
    private String crn;
    
    private String returnRemark;
    
    private String uldCondition;
    
    
    private String fromPartyCode;
    
    private String toPartyCode;    
    /*
     * added for bug 51699
     */
    private String destinationStation;
    
    /**
	 * @return the uldCondition
	 */
	public String getUldCondition() {
		return uldCondition;
	}
	/**
	 * @param uldCondition the uldCondition to set
	 */
	public void setUldCondition(String uldCondition) {
		this.uldCondition = uldCondition;
	}
	/**
     * 
     * @return
     */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * 
	 * @return
	 */
	public String getPartyCode() {
		return partyCode;
	}
	/**
	 * 
	 * @param partyCode
	 */
	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}
	/**
	 * 
	 * @return
	 */
	public String getPartyType() {
		return partyType;
	}
	/**
	 * 
	 * @param partyType
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}
	/**
	 * 
	 * @return
	 */
	public String getTransactionAirportCode() {
		return transactionAirportCode;
	}
	/**
	 * 
	 * @param transactionAirportCode
	 */
	public void setTransactionAirportCode(String transactionAirportCode) {
		this.transactionAirportCode = transactionAirportCode;
	}
	/**
	 * 
	 * @return
	 */
	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	/**
	 * 
	 * @param transactionDate
	 */
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
	/**
	 * 
	 * @return
	 */
	public String getTransactionType() {
		return transactionType;
	}
	/**
	 * 
	 * @param transactionType
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	/**
	 * 
	 * @return
	 */
	public String getUldNumber() {
		return uldNumber;
	}
	/**
	 * 
	 * @param uldNumber
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	/**
	 * @return Returns the fromPartyIdentifier.
	 */
	/**
	 * @return Returns the returnRemark.
	 */
	public String getReturnRemark() {
		return returnRemark;
	}
	/**
	 * @param returnRemark The returnRemark to set.
	 */
	public void setReturnRemark(String returnRemark) {
		this.returnRemark = returnRemark;
	}
	/**
	 * @return the crn
	 */
	public String getCrn() {
		return crn;
	}
	/**
	 * @param crn the crn to set
	 */
	public void setCrn(String crn) {
		this.crn = crn;
	}
	/**
	 * @return the fromPartyCode
	 */
	public String getFromPartyCode() {
		return fromPartyCode;
	}
	/**
	 * @param fromPartyCode the fromPartyCode to set
	 */
	public void setFromPartyCode(String fromPartyCode) {
		this.fromPartyCode = fromPartyCode;
	}
	/**
	 * @return the toPartyCode
	 */
	public String getToPartyCode() {
		return toPartyCode;
	}
	/**
	 * @param toPartyCode the toPartyCode to set
	 */
	public void setToPartyCode(String toPartyCode) {
		this.toPartyCode = toPartyCode;
	}
	/**
	 * @return the destinationStation
	 */
	public final String getDestinationStation() {
		return destinationStation;
	}
	/**
	 * @param destinationStation the destinationStation to set
	 */
	public final void setDestinationStation(String destinationStation) {
		this.destinationStation = destinationStation;
	}
}
