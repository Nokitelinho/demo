/*
 * InvoiceSettlementFilterVO.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;



import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1556
 *
 */
public class InvoiceSettlementFilterVO extends AbstractVO {

    private String companyCode;
    private String gpaCode;
    private String invoiceNumber; 
    private String settlementStatus;
    private LocalDate fromDate;
    private LocalDate toDate;
    //added for ICRD 7316
    private String chequeNumber;
    private String gpaName;
    private String settlementReferenceNumber;
    //a-7531  for icrd-235799
    private String mailbagID;
    private long mailsequenceNum;
    private int pageNumber;
    private String billingPeriod;
    private String billingPeriodfrom;
    private String billingPeriodto;
    private String settlementCurrency;
    private String totalBlgamt;
    private String totalSettledAmt;
    private String settlementDate;
    private int defaultPageSize;
    private String mcaRefnumber;
    private int totalRecords;
    private String actionFlag;
    
    
    
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
	 * @return Returns the settlementStatus.
	 */
	public String getSettlementStatus() {
		return settlementStatus;
	}
	/**
	 * @param settlementStatus The settlementStatus to set.
	 */
	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
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
	 * @return the gpaName
	 */
	public String getGpaName() {
		return gpaName;
	}
	/**
	 * @param gpaName the gpaName to set
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}
	public String getMailbagID() {
		return mailbagID;
	}
	public void setMailbagID(String mailbagID) {
		this.mailbagID = mailbagID;
	}

	public long getMailsequenceNum() {
		return mailsequenceNum;
	}
	public void setMailsequenceNum(long mailsequenceNum) {
		this.mailsequenceNum = mailsequenceNum;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getBillingPeriod() {
		return billingPeriod;
	}
	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}
	public String getSettlementCurrency() {
		return settlementCurrency;
	}
	public void setSettlementCurrency(String settlementCurrency) {
		this.settlementCurrency = settlementCurrency;
	}
	public String getTotalSettledAmt() {
		return totalSettledAmt;
	}
	public void setTotalSettledAmt(String totalSettledAmt) {
		this.totalSettledAmt = totalSettledAmt;
	}
	public String getTotalBlgamt() {
		return totalBlgamt;
	}
	public void setTotalBlgamt(String totalBlgamt) {
		this.totalBlgamt = totalBlgamt;
	}
	public String getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}
	public int getDefaultPageSize() {
		return defaultPageSize;
	}
	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}
	public String getMcaRefnumber() {
		return mcaRefnumber;
	}
	public void setMcaRefnumber(String mcaRefnumber) {
		this.mcaRefnumber = mcaRefnumber;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public String getBillingPeriodfrom() {
		return billingPeriodfrom;
	}
	public void setBillingPeriodfrom(String billingPeriodfrom) {
		this.billingPeriodfrom = billingPeriodfrom;
	}
	public String getBillingPeriodto() {
		return billingPeriodto;
	}
	public void setBillingPeriodto(String billingPeriodto) {
		this.billingPeriodto = billingPeriodto;
	}
	/**
	 * 	Getter for actionFlag 
	 *	Added by : A-7531 on 18-Feb-2019
	 * 	Used for :
	 */
	public String getActionFlag() {
		return actionFlag;
	}
	/**
	 *  @param actionFlag the actionFlag to set
	 * 	Setter for actionFlag 
	 *	Added by : A-7531 on 18-Feb-2019
	 * 	Used for :
	 */
	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}
    

}
