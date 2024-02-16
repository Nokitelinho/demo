/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailVO.java
 *
 *	Created by	:	A-4809
 *	Created on	:	11-Nov-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	11-Nov-2021	:	Draft
 */
public class PaymentBatchDetailsVO extends AbstractVO{

	  private String companyCode;
	  private String batchID;
	  private String paCode;
	  private String batchStatus;
	  private long batchSequenceNum;
	  private String batchCurrency;
	  private Money batchAmount;
	  private Money appliedAmount;
	  private Money unappliedAmount;
	  private LocalDate batchDate;
	  private String remarks;
	  private String processID;
	  private String lstUpdatedUser;
	  private LocalDate lstUpdatedTime;
	  private String accTxnIdr;
	  private String source;
	  private String fileName;
	  private int recordCount;
	  private Collection<PaymentBatchSettlementDetailsVO> paymentBatchSettlementDetails;
	  private Collection<MRABatchUploadedVO> batchUploadedVOs;
	  private String accSource;
	  private String processedInvoice;
	  private Double currentBatchAmount;
	  private Double auditAmount;
	  private Double amountTobeApplied;
	  private String auditRemark;

	/**
	 * 	Getter for companyCode
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 *  @param companyCode the companyCode to set
	 * 	Setter for companyCode
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * 	Getter for batchID
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public String getBatchID() {
		return batchID;
	}
	
	public String getAccTxnIdr() {
		return accTxnIdr;
	}
	public void setAccTxnIdr(String accTxnIdr) {
		this.accTxnIdr = accTxnIdr;
	}
	/**
	 *  @param batchID the batchID to set
	 * 	Setter for batchID
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setBatchID(String batchID) {
		this.batchID = batchID;
	}
	/**
	 * 	Getter for paCode
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public String getPaCode() {
		return paCode;
	}
	/**
	 *  @param paCode the paCode to set
	 * 	Setter for paCode
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	/**
	 * 	Getter for batchStatus
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public String getBatchStatus() {
		return batchStatus;
	}
	/**
	 *  @param batchStatus the batchStatus to set
	 * 	Setter for batchStatus
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	/**
	 * 	Getter for batchCurrency
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public String getBatchCurrency() {
		return batchCurrency;
	}
	/**
	 *  @param batchCurrency the batchCurrency to set
	 * 	Setter for batchCurrency
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setBatchCurrency(String batchCurrency) {
		this.batchCurrency = batchCurrency;
	}
	/**
	 * 	Getter for batchAmount
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public Money getBatchAmount() {
		return batchAmount;
	}
	/**
	 *  @param batchAmount the batchAmount to set
	 * 	Setter for batchAmount
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setBatchAmount(Money batchAmount) {
		this.batchAmount = batchAmount;
	}
	/**
	 * 	Getter for appliedAmount
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public Money getAppliedAmount() {
		return appliedAmount;
	}
	/**
	 *  @param appliedAmount the appliedAmount to set
	 * 	Setter for appliedAmount
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setAppliedAmount(Money appliedAmount) {
		this.appliedAmount = appliedAmount;
	}
	/**
	 * 	Getter for unappliedAmount
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public Money getUnappliedAmount() {
		return unappliedAmount;
	}
	/**
	 *  @param unappliedAmount the unappliedAmount to set
	 * 	Setter for unappliedAmount
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setUnappliedAmount(Money unappliedAmount) {
		this.unappliedAmount = unappliedAmount;
	}
	/**
	 * 	Getter for batchDate
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public LocalDate getBatchDate() {
		return batchDate;
	}
	/**
	 *  @param batchDate the batchDate to set
	 * 	Setter for batchDate
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setBatchDate(LocalDate batchDate) {
		this.batchDate = batchDate;
	}
	/**
	 * 	Getter for remarks
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 *  @param remarks the remarks to set
	 * 	Setter for remarks
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 	Getter for processID
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public String getProcessID() {
		return processID;
	}
	/**
	 *  @param processID the processID to set
	 * 	Setter for processID
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setProcessID(String processID) {
		this.processID = processID;
	}
	/**
	 * 	Getter for lstUpdatedUser
	 *	Added by : A-4809 on 12-Nov-2021
	 * 	Used for :
	 */
	public String getLstUpdatedUser() {
		return lstUpdatedUser;
	}
	/**
	 *  @param lstUpdatedUser the lstUpdatedUser to set
	 * 	Setter for lstUpdatedUser
	 *	Added by : A-4809 on 12-Nov-2021
	 * 	Used for :
	 */
	public void setLstUpdatedUser(String lstUpdatedUser) {
		this.lstUpdatedUser = lstUpdatedUser;
	}
	/**
	 * 	Getter for lstUpdatedTime
	 *	Added by : A-4809 on 12-Nov-2021
	 * 	Used for :
	 */
	public LocalDate getLstUpdatedTime() {
		return lstUpdatedTime;
	}
	/**
	 *  @param lstUpdatedTime the lstUpdatedTime to set
	 * 	Setter for lstUpdatedTime
	 *	Added by : A-4809 on 12-Nov-2021
	 * 	Used for :
	 */
	public void setLstUpdatedTime(LocalDate lstUpdatedTime) {
		this.lstUpdatedTime = lstUpdatedTime;
	}
	/**
	 * 	Getter for source
	 *	Added by : A-4809 on 12-Nov-2021
	 * 	Used for :
	 */
	public String getSource() {
		return source;
	}
	/**
	 *  @param source the source to set
	 * 	Setter for source
	 *	Added by : A-4809 on 12-Nov-2021
	 * 	Used for :
	 */
	public void setSource(String source) {
		this.source = source;
	}


	public Collection<PaymentBatchSettlementDetailsVO> getPaymentBatchSettlementDetails() {
		return paymentBatchSettlementDetails;
	}


	public void setPaymentBatchSettlementDetails(Collection<PaymentBatchSettlementDetailsVO> paymentBatchSettlementDetails) {
		this.paymentBatchSettlementDetails = paymentBatchSettlementDetails;
	}
	public Collection<MRABatchUploadedVO> getBatchUploadedVOs() {
		return batchUploadedVOs;
	}
	public void setBatchUploadedVOs(Collection<MRABatchUploadedVO> batchUploadedVOs) {
		this.batchUploadedVOs = batchUploadedVOs;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the batchSequenceNum
	 */
	public long getBatchSequenceNum() {
		return batchSequenceNum;
	}
	/**
	 * @param batchSequenceNum the batchSequenceNum to set
	 */
	public void setBatchSequenceNum(long batchSequenceNum) {
		this.batchSequenceNum = batchSequenceNum;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}  

	/**
	 * 	Getter for accSource 
	 *	Added by : A-4809 on 12-Jan-2022
	 * 	Used for :
	 */
	public String getAccSource() {
		return accSource;
	}
	/**
	 *  @param accSource the accSource to set
	 * 	Setter for accSource 
	 *	Added by : A-4809 on 12-Jan-2022
	 * 	Used for :
	 */
	public void setAccSource(String accSource) {
		this.accSource = accSource;
	}
	/**
	 * Getter for processed Invoice
	 * @author A-10647
	 */
	public String getProcessedInvoice() {
		return processedInvoice;
	}
	/**
	 * 
	 * @param processedInvoice
	 * Setter for processedInvoice
	 * @author A-10647
	 */
	public void setProcessedInvoice(String processedInvoice) {
		this.processedInvoice = processedInvoice;
	}
	/**
	 * Getter for currentBatchAmount
	 * @author A-10647
	 * @return
	 */
	public Double getCurrentBatchAmount() {
		return currentBatchAmount;
	}
	/**
	 * Setter for currentBatchAmount
	 * @param currentBatchAmount
	 * @author A-10647
	 */
	public void setCurrentBatchAmount(Double currentBatchAmount) {
		this.currentBatchAmount = currentBatchAmount;
	}
	public Double getAuditAmount() {
		return auditAmount;
	}
	public void setAuditAmount(Double auditAmount) {
		this.auditAmount = auditAmount;
	}
	 /**
	 * Getter for amountTobeApplied
	 * @author A-10647
	 * @return
	 */
	public Double getAmountTobeApplied() {
		return amountTobeApplied;
	}
/**
	 * Setter for amountTobeApplied
	 * @param amountTobeApplied
	 * @author A-10647
	 */
	public void setAmountTobeApplied(Double amountTobeApplied) {
		this.amountTobeApplied = amountTobeApplied;
	}
	 /**
		 * Getter for auditRemark
		 * @author A-10647
		 * @return
		 */
public String getAuditRemark() {
	return auditRemark;
}
/**
 * Setter for auditRemark
 * @param auditRemark
 * @author A-10647
 */
public void setAuditRemark(String auditRemark) {
	this.auditRemark = auditRemark;
	}
	
	
	



}
