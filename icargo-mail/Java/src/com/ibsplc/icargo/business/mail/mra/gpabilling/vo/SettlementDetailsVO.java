/**
 *SettlementDetailsVO.java Created on April 02, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-4823
 *
 */
public class SettlementDetailsVO extends AbstractVO{

	private String settlementId;
	private String gpaCode;
	private String companyCode;
	private String chequeNumber;
	private LocalDate chequeDate;
	private String chequeBank;
	private String chequeBranch;
	private String chequeCurrency;
	private Money chequeAmount;
	private String isDeleted;
	private String remarks;
	private int index;
	private String  settlementChequeNumbers;
	private String lastUpdatedUser;
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	private int settlementSequenceNumber;
	private int serialNumber;
	private String operationFlag;
	private String isAccounted;
	private Money outStandingChqAmt;
	private String settlementExists;
	private Money chequeAmountInSettlementCurr;
	private int invSerialNumber;//Added for ICRD-211662
	private LocalDate settlementDate;//added by A-7371 for ICRD-260740
    private String stlFlag;//added for icrd-235799 
	private String fromScreen;
	/**
	 * @return the isDeleted
	 */
	public String getIsDeleted() {
		return isDeleted;
	}
	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	/**
	 * @return the isAccounted
	 */
	public String getIsAccounted() {
		return isAccounted;
	}
	/**
	 * @param isAccounted the isAccounted to set
	 */
	public void setIsAccounted(String isAccounted) {
		this.isAccounted = isAccounted;
	}
	/**
	 * @return the settlementId
	 */
	public String getSettlementId() {
		return settlementId;
	}
	/**
	 * @param settlementId the settlementId to set
	 */
	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}
	/**
	 * @return the chequeNumber
	 */
	public String getChequeNumber() {
		return chequeNumber;
	}
	/**
	 * @param chequeNumber the chequeNumber to set
	 */
	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}
	/**
	 * @return the chequeDate
	 */
	public LocalDate getChequeDate() {
		return chequeDate;
	}
	/**
	 * @param chequeDate the chequeDate to set
	 */
	public void setChequeDate(LocalDate chequeDate) {
		this.chequeDate = chequeDate;
	}
	/**
	 * @return the chequeBank
	 */
	public String getChequeBank() {
		return chequeBank;
	}
	/**
	 * @param chequeBank the chequeBank to set
	 */
	public void setChequeBank(String chequeBank) {
		this.chequeBank = chequeBank;
	}
	/**
	 * @return the chequeBranch
	 */
	public String getChequeBranch() {
		return chequeBranch;
	}
	/**
	 * @param chequeBranch the chequeBranch to set
	 */
	public void setChequeBranch(String chequeBranch) {
		this.chequeBranch = chequeBranch;
	}
	/**
	 * @return the chequeCurrency
	 */
	public String getChequeCurrency() {
		return chequeCurrency;
	}
	/**
	 * @param chequeCurrency the chequeCurrency to set
	 */
	public void setChequeCurrency(String chequeCurrency) {
		this.chequeCurrency = chequeCurrency;
	}
	/**
	 * @return the chequeAmount
	 */
	public Money getChequeAmount() {
		return chequeAmount;
	}
	/**
	 * @param chequeAmount the chequeAmount to set
	 */
	public void setChequeAmount(Money chequeAmount) {
		this.chequeAmount = chequeAmount;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	/**
	 * @return the gpaCode
	 */
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 * @param gpaCode the gpaCode to set
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 * @return the settlementSequenceNumber
	 */
	public int getSettlementSequenceNumber() {
		return settlementSequenceNumber;
	}
	/**
	 * @param settlementSequenceNumber the settlementSequenceNumber to set
	 */
	public void setSettlementSequenceNumber(int settlementSequenceNumber) {
		this.settlementSequenceNumber = settlementSequenceNumber;
	}
	/**
	 * @return the serialNumber
	 */
	public int getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return the operationFlag
	 */
	public String getOperationFlag() {
		return operationFlag;
	}
	/**
	 * @param operationFlag the operationFlag to set
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	/**
	 * @return the outStandingChqAmt
	 */
	public Money getOutStandingChqAmt() {
		return outStandingChqAmt;
	}
	/**
	 * @param outStandingChqAmt the outStandingChqAmt to set
	 */
	public void setOutStandingChqAmt(Money outStandingChqAmt) {
		this.outStandingChqAmt = outStandingChqAmt;
	}
	/**
	 * @return the isSettlementExists
	 */
	public String getSettlementExists() {
		return settlementExists;
	}
	/**
	 * @param isSettlementExists the isSettlementExists to set
	 */
	public void setSettlementExists(String settlementExists) {
		this.settlementExists = settlementExists;
	}
	/**
	 * @return the chequeAmountInSettlementCurr
	 */
	public Money getChequeAmountInSettlementCurr() {
		return chequeAmountInSettlementCurr;
	}
	/**
	 * @param chequeAmountInSettlementCurr the chequeAmountInSettlementCurr to set
	 */
	public void setChequeAmountInSettlementCurr(Money chequeAmountInSettlementCurr) {
		this.chequeAmountInSettlementCurr = chequeAmountInSettlementCurr;
	}
	/**
	 * 	Getter for invSerialNumber 
	 *	Added by : A-6991 on 25-Sep-2017
	 * 	Used for :
	 */
	public int getInvSerialNumber() {
		return invSerialNumber;
	}
	/**
	 *  @param invSerialNumber the invSerialNumber to set
	 * 	Setter for invSerialNumber 
	 *	Added by : A-6991 on 25-Sep-2017
	 * 	Used for :
	 */
	public void setInvSerialNumber(int invSerialNumber) {
		this.invSerialNumber = invSerialNumber;
	}
	/**
	 * @author A-7371
	 * @return settlementDate
	 */
	public LocalDate getSettlementDate() {
		return settlementDate;
	}
	/**
	 * @author A-7371
	 * @param settlementDate the settlementDate to set
	 */
	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}
	public String getSettlementChequeNumbers() {
		return settlementChequeNumbers;
	}
	public void setSettlementChequeNumbers(String settlementChequeNumbers) {
		this.settlementChequeNumbers = settlementChequeNumbers;
	}
	public String getStlFlag() {
		return stlFlag;
	}
	public void setStlFlag(String stlFlag) {
		this.stlFlag = stlFlag;
	}
	public String getFromScreen() {
		return fromScreen;
	}
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}
/**
	 * 	Getter for lastUpdatedUser 
	 *	Added by : A-10647 on 27-Jan-2022
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}  
/**
	 *  @param lastUpdatedUser the lastUpdatedUser to set
	 * 	Setter for lastUpdatedUser 
	 *	Added by : A-10647 on 27-Jan-2022
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	
}
