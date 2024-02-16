package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * 
 * @author A-7371
 *
 */
public class MailInvoicMessageVO extends AbstractVO{
	
	private String companyCode;
	
	private int serialNumber;
	
	private String poaCode;
	
	private LocalDate reportingPeriodFrom;

	private LocalDate reportingPeriodTo;
	
	private String invoiceReferenceNumber;
	
	private int numberOfMailbags;
	
	private String messageReferenceNumber;
	
	private LocalDate messageDate;
	
	private LocalDate invoiceDate;
	
	private String payeeCode;
	
	private String payerCode;
	
	private String assignedCarrier;
	
	private String invoiceAdvieType;
	
	private Money totalInvoiceAmount;
	
	private String invoiceStatus;
	
	private String lastUpdatedUser;
	
	private LocalDate lastUpdatedTime;
	
	private String remark;
	
	private String autoProcessingFlag;
	
	private String fileName;
	
	private int splitCount;
	
	private int totalsplitCount;
	
	private String message;

	private String assocAssignCode;
	
	private String contractNumber;
	
	private Money totalAdjustmentAmount;	
	
	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getAssocAssignCode() {
		return assocAssignCode;
	}

	public void setAssocAssignCode(String assocAssignCode) {
		this.assocAssignCode = assocAssignCode;
	}

	private Collection<MailInvoicMessageDetailVO> mailInvoiceMessageDetailVOs;
	


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAutoProcessingFlag() {
		return autoProcessingFlag;
	}

	public void setAutoProcessingFlag(String autoProcessingFlag) {
		this.autoProcessingFlag = autoProcessingFlag;
	}

	public int getSplitCount() {
		return splitCount;
	}

	public void setSplitCount(int splitCount) {
		this.splitCount = splitCount;
	}

	public int getTotalsplitCount() {
		return totalsplitCount;
	}

	public void setTotalsplitCount(int totalsplitCount) {
		this.totalsplitCount = totalsplitCount;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getPoaCode() {
		return poaCode;
	}

	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	public LocalDate getReportingPeriodFrom() {
		return reportingPeriodFrom;
	}

	public void setReportingPeriodFrom(LocalDate reportingPeriodFrom) {
		this.reportingPeriodFrom = reportingPeriodFrom;
	}

	public LocalDate getReportingPeriodTo() {
		return reportingPeriodTo;
	}

	public void setReportingPeriodTo(LocalDate reportingPeriodTo) {
		this.reportingPeriodTo = reportingPeriodTo;
	}

	public String getInvoiceReferenceNumber() {
		return invoiceReferenceNumber;
	}

	public void setInvoiceReferenceNumber(String invoiceReferenceNumber) {
		this.invoiceReferenceNumber = invoiceReferenceNumber;
	}

	public int getNumberOfMailbags() {
		return numberOfMailbags;
	}

	public void setNumberOfMailbags(int numberOfMailbags) {
		this.numberOfMailbags = numberOfMailbags;
	}

	public String getMessageReferenceNumber() {
		return messageReferenceNumber;
	}

	public void setMessageReferenceNumber(String messageReferenceNumber) {
		this.messageReferenceNumber = messageReferenceNumber;
	}

	public LocalDate getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(LocalDate messageDate) {
		this.messageDate = messageDate;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getPayeeCode() {
		return payeeCode;
	}

	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public String getPayerCode() {
		return payerCode;
	}

	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}

	public String getAssignedCarrier() {
		return assignedCarrier;
	}

	public void setAssignedCarrier(String assignedCarrier) {
		this.assignedCarrier = assignedCarrier;
	}

	public String getInvoiceAdvieType() {
		return invoiceAdvieType;
	}

	public void setInvoiceAdvieType(String invoiceAdvieType) {
		this.invoiceAdvieType = invoiceAdvieType;
	}

	public Money getTotalInvoiceAmount() {
		return totalInvoiceAmount;
	}

	public void setTotalInvoiceAmount(Money totalInvoiceAmout) {
		this.totalInvoiceAmount = totalInvoiceAmout;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Collection<MailInvoicMessageDetailVO> getMailInvoiceMessageDetailVOs() {
		return mailInvoiceMessageDetailVOs;
	}

	public void setMailInvoiceMessageDetailVOs(Collection<MailInvoicMessageDetailVO> mailInvoiceMessageDetailVOs) {
		this.mailInvoiceMessageDetailVOs = mailInvoiceMessageDetailVOs;
	}

	public Money getTotalAdjustmentAmount() {
		return totalAdjustmentAmount;
	}

	public void setTotalAdjustmentAmount(Money totalAdjustmentAmount) {
		this.totalAdjustmentAmount = totalAdjustmentAmount;
	}

	
	
	


}
