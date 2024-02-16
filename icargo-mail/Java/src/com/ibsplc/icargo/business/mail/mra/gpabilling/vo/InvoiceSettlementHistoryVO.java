/*
 * InvoiceSettlementHistoryVO.java Created on Jan 8, 2007
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
 * @author A-1556
 *
 */
public class InvoiceSettlementHistoryVO extends AbstractVO {

    private String companyCode;
    private String gpaCode;
    private String invoiceNumber; 
    private LocalDate settlementDate;
    private Money amountInSettlementCurrency;
    //added for ICRD -7316
    private String settlementReferenceNumber;
    private String chequeNumber;
	private LocalDate chequeDate;
	private String chequeBank;
	private String chequeBranch;
	private String chequeCurrency;
	private Money chequeAmount;
	private Boolean isDeleted;
	private String remarks;
	private String mcaRefNum;
    
	
	/**
	 * @return Returns the amountInSettlementCurrency.
	 */
	public Money getAmountInSettlementCurrency() {
		return amountInSettlementCurrency;
	}
	/**
	 * @param amountInSettlementCurrency The amountInSettlementCurrency to set.
	 */
	public void setAmountInSettlementCurrency(Money amountInSettlementCurrency) {
		this.amountInSettlementCurrency = amountInSettlementCurrency;
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
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 * @param gpaCode The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
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
	 * @return Returns the settlementDate.
	 */
	public LocalDate getSettlementDate() {
		return settlementDate;
	}
	/**
	 * @param settlementDate The settlementDate to set.
	 */
	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}
	/**
	 * @return the settlementReferenceNumber
	 */
	public String getSettlementReferenceNumber() {
		return settlementReferenceNumber;
	}
	/**
	 * @param settlementReferenceNumber the settlementReferenceNumber to set
	 */
	public void setSettlementReferenceNumber(String settlementReferenceNumber) {
		this.settlementReferenceNumber = settlementReferenceNumber;
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
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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
	public String getMcaRefNum() {
		return mcaRefNum;
	}
	public void setMcaRefNum(String mcaRefNum) {
		this.mcaRefNum = mcaRefNum;
	}
    
    
}
