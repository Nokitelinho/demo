package com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class PaymentBatchSettlementDetailsVO extends AbstractVO{
	
	 private String companyCode;
	  private String batchID;
	  private String paCode;
	  private String mailbagId;
	  private String consignmentDocNum;
	  private Money paidAmount;
	  private LocalDate mailbagDate;
	  private LocalDate payDate;
	  private String currencyCode;
	  private long mailSeqNum;
	  private long batchSequenceNum;
	  private Money batchAmount;
	  private Money appliedAmount;
	  private Money unappliedAmount;
	  private String invoiceNumber;
	  private String accountNumber;
	  private String reasonCode;	    
	  private String remarks;
	  private LocalDate settlementDate;
	  private String lstUpdatedUser;
	  private LocalDate lstUpdatedTime;
	  
	public long getBatchSequenceNum() {
		return batchSequenceNum;
	}
	public void setBatchSequenceNum(long batchSequenceNum) {
		this.batchSequenceNum = batchSequenceNum;
	}   
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getBatchID() {
		return batchID;
	}
	public void setBatchID(String batchID) {
		this.batchID = batchID;
	}
	public String getPaCode() {
		return paCode;
	}
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	public String getMailbagId() {
		return mailbagId;
	}
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}
	public String getConsignmentDocNum() {
		return consignmentDocNum;
	}
	public void setConsignmentDocNum(String consignmentDocNum) {
		this.consignmentDocNum = consignmentDocNum;
	}
	public Money getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(Money paidAmount) {
		this.paidAmount = paidAmount;
	}
	public LocalDate getMailbagDate() {
		return mailbagDate;
	}
	public void setMailbagDate(LocalDate mailbagDate) {
		this.mailbagDate = mailbagDate;
	}
	public LocalDate getPayDate() {
		return payDate;
	}
	public void setPayDate(LocalDate payDate) {
		this.payDate = payDate;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public long getMailSeqNum() {
		return mailSeqNum;
	}
	public void setMailSeqNum(long mailSeqNum) {
		this.mailSeqNum = mailSeqNum;
	}
	public void setBatchAmount(Money batchAmount) {
		this.batchAmount = batchAmount;
	}
	public Money getAppliedAmount() {
		return appliedAmount;
	}
	public void setAppliedAmount(Money appliedAmount) {
		this.appliedAmount = appliedAmount;
	}
	public Money getUnappliedAmount() {
		return unappliedAmount;
	}
	public void setUnappliedAmount(Money unappliedAmount) {
		this.unappliedAmount = unappliedAmount;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Money getBatchAmount() {
		return batchAmount;
	}    
	
	public LocalDate getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}
	public String getLstUpdatedUser() {
		return lstUpdatedUser;
	}
	public void setLstUpdatedUser(String lstUpdatedUser) {
		this.lstUpdatedUser = lstUpdatedUser;
	}
	public LocalDate getLstUpdatedTime() {
		return lstUpdatedTime;
	}
	public void setLstUpdatedTime(LocalDate lstUpdatedTime) {
		this.lstUpdatedTime = lstUpdatedTime;
	}
	
}
