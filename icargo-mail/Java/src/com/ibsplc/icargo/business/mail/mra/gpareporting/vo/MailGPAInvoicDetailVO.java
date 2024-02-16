/*
 * MailGPAInvoicDetailVO.java Created on Nov 20, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;

import java.util.Calendar;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-8464
 *
 */
public class MailGPAInvoicDetailVO  extends AbstractVO{
	
    private String companyCode;     
    private String poaCode; 
    private long mailSequenceNumber;
    private long versionNumber;
    
    private String mailIdr;
    private String invoicRefNum;
    private Calendar receivedDate;
    private String mailClass;
    private double weight;
    private double appliedRate;
    private double invoicAmount;
    private double dueAmount;
    private double claimAmount;
    private String processStatus;
    private String invoicPaymentStatus;
    private String claimStatus;
    private String remark;
    private String lastUpdatedUser;
	private Calendar lastUpdatedTime;
	private long serialNumber;
	
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getPoaCode() {
		return poaCode;
	}
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	public long getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(long versionNumber) {
		this.versionNumber = versionNumber;
	}
	public String getMailIdr() {
		return mailIdr;
	}
	public void setMailIdr(String mailIdr) {
		this.mailIdr = mailIdr;
	}
	public String getInvoicRefNum() {
		return invoicRefNum;
	}
	public void setInvoicRefNum(String invoicRefNum) {
		this.invoicRefNum = invoicRefNum;
	}
	public Calendar getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Calendar receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getMailClass() {
		return mailClass;
	}
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getAppliedRate() {
		return appliedRate;
	}
	public void setAppliedRate(double appliedRate) {
		this.appliedRate = appliedRate;
	}
	public double getInvoicAmount() {
		return invoicAmount;
	}
	public void setInvoicAmount(double invoicAmount) {
		this.invoicAmount = invoicAmount;
	}
	public double getDueAmount() {
		return dueAmount;
	}
	public void setDueAmount(double dueAmount) {
		this.dueAmount = dueAmount;
	}
	public double getClaimAmount() {
		return claimAmount;
	}
	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public String getInvoicPaymentStatus() {
		return invoicPaymentStatus;
	}
	public void setInvoicPaymentStatus(String invoicPaymentStatus) {
		this.invoicPaymentStatus = invoicPaymentStatus;
	}
	public String getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * @return the serialNumber
	 */
	public long getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}

}
