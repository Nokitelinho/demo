/**
 * GPASettlementVO.java Created on April 02, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-4823
 *
 */
public class GPASettlementVO extends AbstractVO{
	private String settlementId;
	private LocalDate settlementDate;
	private String settlementCurrency;
	private int settlementSequenceNumber;
	private String companyCode;
	private String gpaCode;
	private Collection<SettlementDetailsVO> settlementDetailsVOs;
	private Collection<InvoiceSettlementVO> invoiceSettlementVOs;
	private String operationFlag;
	private String lastUpdatedUser;
	private LocalDate lastUpdatedTime;
	private int displayPage;
	private LocalDate fromDate; //added by a-5133 as part of ICRD-23808
	private LocalDate toDate; //added by a-5133 as part of ICRD-23808
	//Added by A-7794 as part of ICRD-194277
	private String accountNumber;
	private String frmScreen;
	private Page<InvoiceSettlementVO> invoiceSettlementVOsPage;
	//a-7531
	private Page<InvoiceSettlementVO> invoiceSettlementVO;
	private String updateFlag;
	private String overrideRounding;//added by 
	private String settlementChequeNumber ;
	
	private boolean fromBatchSettlmentJob;
	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}
	public String getOverrideRounding() {
		return overrideRounding;
	}
	public void setOverrideRounding(String overrideRounding) {
		this.overrideRounding = overrideRounding;
	}
	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	/**
	 * @return the frmScreen
	 */
	public String getFrmScreen() {
		return frmScreen;
	}
	/**
	 * @param frmScreen the frmScreen to set
	 */
	public void setFrmScreen(String frmScreen) {
		this.frmScreen = frmScreen;
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
	 * @return the settlementCurrency
	 */
	public String getSettlementCurrency() {
		return settlementCurrency;
	}
	/**
	 * @param settlementCurrency the settlementCurrency to set
	 */
	public void setSettlementCurrency(String settlementCurrency) {
		this.settlementCurrency = settlementCurrency;
	}
	/**
	 * @return the settlementDetailsVOs
	 */
	public Collection<SettlementDetailsVO> getSettlementDetailsVOs() {
		return settlementDetailsVOs;
	}
	/**
	 * @param settlementDetailsVOs the settlementDetailsVOs to set
	 */
	public void setSettlementDetailsVOs(
			Collection<SettlementDetailsVO> settlementDetailsVOs) {
		this.settlementDetailsVOs = settlementDetailsVOs;
	}
	/**
	 * @return the invoiceSettlementVOs
	 */
	public Collection<InvoiceSettlementVO> getInvoiceSettlementVOs() {
		return invoiceSettlementVOs;
	}
	/**
	 * @param invoiceSettlementVOs the invoiceSettlementVOs to set
	 */
	public void setInvoiceSettlementVOs(
			Collection<InvoiceSettlementVO> invoiceSettlementVOs) {
		this.invoiceSettlementVOs = invoiceSettlementVOs;
	}
	/**
	 * @return the lastUpdatedUser
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	/**
	 * @return the lastUpdatedTime
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
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
	 * @return the settlementDate
	 */
	public LocalDate getSettlementDate() {
		return settlementDate;
	}
	/**
	 * @param settlementDate the settlementDate to set
	 */
	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
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
	 * @param displayPage the displayPage to set
	 */
	public void setDisplayPage(int displayPage) {
		this.displayPage = displayPage;
	}
	/**
	 * @return the displayPage
	 */
	public int getDisplayPage() {
		return displayPage;
	}
	/**
	 * 
	 * @return invoiceSettlementVOsPage
	 */
	public Page<InvoiceSettlementVO> getInvoiceSettlementVOsPage() {
		return invoiceSettlementVOsPage;
	}
	/**
	 * 
	 * @param invoiceSettlementVOsPage the invoiceSettlementVOsPage to set
	 */
	public void setInvoiceSettlementVOsPage(Page<InvoiceSettlementVO> invoiceSettlementVOsPage) {
		this.invoiceSettlementVOsPage = invoiceSettlementVOsPage;
	}
	public Page<InvoiceSettlementVO> getInvoiceSettlementVO() {
		return invoiceSettlementVO;
	}
	public void setInvoiceSettlementVO(Page<InvoiceSettlementVO> invoiceSettlementVO) {
		this.invoiceSettlementVO = invoiceSettlementVO;
	}
	/*public String getInvoiceSettlementLevel() {
		return invoiceSettlementLevel;
	}
	public void setInvoiceSettlementLevel(String invoiceSettlementLevel) {
		this.invoiceSettlementLevel = invoiceSettlementLevel;
	}*/
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}
	public String getSettlementChequeNumber() {
		return settlementChequeNumber;
	}
	public void setSettlementChequeNumber(String settlementChequeNumber) {
		this.settlementChequeNumber = settlementChequeNumber;
	}
	public boolean isFromBatchSettlmentJob() {
		return fromBatchSettlmentJob;
	}
	public void setFromBatchSettlmentJob(boolean fromBatchSettlmentJob) {
		this.fromBatchSettlmentJob = fromBatchSettlmentJob;
	}
	
	private String invoiceRefNumber;
	private String chequeNumber;
	public String getInvoiceRefNumber() {
		return invoiceRefNumber;
	}
	public void setInvoiceRefNumber(String invoiceRefNumber) {
		this.invoiceRefNumber = invoiceRefNumber;
	}
	public String getChequeNumber() {
		return chequeNumber;
	}
	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}
	
	
	
	
}
