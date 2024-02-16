/*
 * AirlineExceptionsVO.java Created on Jun 15, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * TODO Add the purpose of this class
 *
 * @author A-2407
 *
 */

public class AirlineExceptionsVO  extends AbstractVO {
	
	private long mailSequenceNumber;

	private String operationalFlag;

	private String companyCode;

	private int airlineIdentifier;

	private String exceptionCode;

	private int serialNumber;

	private String clearancePeriod;

	private String interlineBlgType;

	private String airlineCode;

	private String despatchSerNo;

	private String receptacleSerNo;

	private int year;

	private String origin;

	private String orgOfficeOfExchange;

	private String destination;

	private String destOfficeOfExchange;

	private String mailSubClass;

	private String mailCategoryCode;

	private String invoiceNumber;

	private String assigneeCode;

	private LocalDate assignedDate;

	private String exceptionStatus;
	private String highestNumberIndicator;
	private String sectorFrom;
	public String getSectorFrom() {
		return sectorFrom;
	}

	public void setSectorFrom(String sectorFrom) {
		this.sectorFrom = sectorFrom;
	}

	public String getSectoTo() {
		return sectoTo;
	}

	public void setSectoTo(String sectoTo) {
		this.sectoTo = sectoTo;
	}

	private String sectoTo;
	public String getHighestNumberIndicator() {
		return highestNumberIndicator;
	}

	public void setHighestNumberIndicator(String highestNumberIndicator) {
		this.highestNumberIndicator = highestNumberIndicator;
	}

	public String getRegisteredIndicator() {
		return registeredIndicator;
	}

	public void setRegisteredIndicator(String registeredIndicator) {
		this.registeredIndicator = registeredIndicator;
	}

	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}

	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}

	private String registeredIndicator;
	private String receptacleSerialNumber;


	/** added for ailine exceptions screen by shivjith */

	private double provRate;

	private double provWeight;

	private double rptdWeight;

	private String memCode;
	
	private String lastUpdatedUser;
	
	private LocalDate lastUpdatedTime;
	
/** added for REJECTION MEMO  by DEEPTHI */
	
	private String contactCurrency;
	
	private String billingCurrency;
		
	private double rejectedAmt;
	
	private double provAmt;
	
	private double reportedAmt;
	
	private double reportedRate;
	
	private String billingBasis;
	
	private String csgDocNum;
	
	private int csgSeqNum;
	
	private String poaCode;
	
	private String memStaus;
	
	private String remark;
	
	private int seqNumber;
	

	/**
	 * @return the reportedAmt
	 */
	public double getReportedAmt() {
		return reportedAmt;
	}

	/**
	 * @param reportedAmt the reportedAmt to set
	 */
	public void setReportedAmt(double reportedAmt) {
		this.reportedAmt = reportedAmt;
	}

	/**
	 * @return the reportedRate
	 */
	public double getReportedRate() {
		return reportedRate;
	}

	/**
	 * @param reportedRate the reportedRate to set
	 */
	public void setReportedRate(double reportedRate) {
		this.reportedRate = reportedRate;
	}

	/**
	 * @return the provAmt
	 */
	public double getProvAmt() {
		return provAmt;
	}

	/**
	 * @param provAmt the provAmt to set
	 */
	public void setProvAmt(double provAmt) {
		this.provAmt = provAmt;
	}

	/**
	 * @return Returns the memCode.
	 */
	public String getMemCode() {
		return memCode;
	}

	/**
	 * @param memCode The memCode to set.
	 */
	public void setMemCode(String memCode) {
		this.memCode = memCode;
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
	 * @return Returns the provRate.
	 */
	public double getProvRate() {
		return provRate;
	}

	/**
	 * @param provRate The provRate to set.
	 */
	public void setProvRate(double provRate) {
		this.provRate = provRate;
	}

	/**
	 * @return Returns the provWeight.
	 */
	public double getProvWeight() {
		return provWeight;
	}

	/**
	 * @param provWeight The provWeight to set.
	 */
	public void setProvWeight(double provWeight) {
		this.provWeight = provWeight;
	}

	/**
	 * @return Returns the rptdWeight.
	 */
	public double getRptdWeight() {
		return rptdWeight;
	}

	/**
	 * @param rptdWeight The rptdWeight to set.
	 */
	public void setRptdWeight(double rptdWeight) {
		this.rptdWeight = rptdWeight;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}

	/**
	 * @return Returns the assignedDate.
	 */
	public LocalDate getAssignedDate() {
		return assignedDate;
	}

	/**
	 * @param assignedDate The assignedDate to set.
	 */
	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}

	/**
	 * @return Returns the assigneeCode.
	 */
	public String getAssigneeCode() {
		return assigneeCode;
	}

	/**
	 * @param assigneeCode The assigneeCode to set.
	 */
	public void setAssigneeCode(String assigneeCode) {
		this.assigneeCode = assigneeCode;
	}



	/**
	 * @return Returns the clearancePeriod.
	 */
	public String getClearancePeriod() {
		return clearancePeriod;
	}

	/**
	 * @param clearancePeriod The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
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
	 * @return Returns the despatchSerNo.
	 */
	public String getDespatchSerNo() {
		return despatchSerNo;
	}

	/**
	 * @param despatchSerNo The despatchSerNo to set.
	 */
	public void setDespatchSerNo(String despatchSerNo) {
		this.despatchSerNo = despatchSerNo;
	}

	/**
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return Returns the destOfficeOfExchange.
	 */
	public String getDestOfficeOfExchange() {
		return destOfficeOfExchange;
	}

	/**
	 * @param destOfficeOfExchange The destOfficeOfExchange to set.
	 */
	public void setDestOfficeOfExchange(String destOfficeOfExchange) {
		this.destOfficeOfExchange = destOfficeOfExchange;
	}


	/**
	 * @return Returns the exceptionCode.
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}

	/**
	 * @param exceptionCode The exceptionCode to set.
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}


	/**
	 * @return Returns the exceptionStatus.
	 */
	public String getExceptionStatus() {
		return exceptionStatus;
	}

	/**
	 * @param exceptionStatus The exceptionStatus to set.
	 */
	public void setExceptionStatus(String exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}

	/**
	 * @return Returns the interlineBlgType.
	 */
	public String getInterlineBlgType() {
		return interlineBlgType;
	}

	/**
	 * @param interlineBlgType The interlineBlgType to set.
	 */
	public void setInterlineBlgType(String interlineBlgType) {
		this.interlineBlgType = interlineBlgType;
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
	 * @return Returns the mailCategoryCode.
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return Returns the mailSubClass.
	 */
	public String getMailSubClass() {
		return mailSubClass;
	}

	/**
	 * @param mailSubClass The mailSubClass to set.
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}

	/**
	 * @return Returns the orgOfficeOfExchange.
	 */
	public String getOrgOfficeOfExchange() {
		return orgOfficeOfExchange;
	}

	/**
	 * @param orgOfficeOfExchange The orgOfficeOfExchange to set.
	 */
	public void setOrgOfficeOfExchange(String orgOfficeOfExchange) {
		this.orgOfficeOfExchange = orgOfficeOfExchange;
	}

	/**
	 * @return Returns the origin.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}



	/**
	 * @return Returns the receptacleSerNo.
	 */
	public String getReceptacleSerNo() {
		return receptacleSerNo;
	}

	/**
	 * @param receptacleSerNo The receptacleSerNo to set.
	 */
	public void setReceptacleSerNo(String receptacleSerNo) {
		this.receptacleSerNo = receptacleSerNo;
	}

	/**
	 * @return Returns the serialNumber.
	 */
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber The serialNumber to set.
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * @return Returns the year.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year The year to set.
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationalFlag() {
		return operationalFlag;
	}

	/**
	 * @param operationalFlag
	 * @return void
	 * @exception
	 */
	public void setOperationalFlag(String operationalFlag) {
		this.operationalFlag = operationalFlag;
	}
	/**
	 * @return the billingCurrency
	 */
	public String getBillingCurrency() {
		return billingCurrency;
	}

	/**
	 * @param billingCurrency the billingCurrency to set
	 */
	public void setBillingCurrency(String billingCurrency) {
		this.billingCurrency = billingCurrency;
	}

	/**
	 * @return the contactCurrency
	 */
	public String getContactCurrency() {
		return contactCurrency;
	}

	/**
	 * @param contactCurrency the contactCurrency to set
	 */
	public void setContactCurrency(String contactCurrency) {
		this.contactCurrency = contactCurrency;
	}

	/**
	 * @return the rejectedAmt
	 */
	public double getRejectedAmt() {
		return rejectedAmt;
	}

	/**
	 * @param rejectedAmt the rejectedAmt to set
	 */
	public void setRejectedAmt(double rejectedAmt) {
		this.rejectedAmt = rejectedAmt;
	}

	/**
	 * @return the billingBasis
	 */
	public String getBillingBasis() {
		return billingBasis;
	}

	/**
	 * @param billingBasis the billingBasis to set
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}

	/**
	 * @return the csgDocNum
	 */
	public String getCsgDocNum() {
		return csgDocNum;
	}

	/**
	 * @param csgDocNum the csgDocNum to set
	 */
	public void setCsgDocNum(String csgDocNum) {
		this.csgDocNum = csgDocNum;
	}

	/**
	 * @return the csgSeqNum
	 */
	public int getCsgSeqNum() {
		return csgSeqNum;
	}

	/**
	 * @param csgSeqNum the csgSeqNum to set
	 */
	public void setCsgSeqNum(int csgSeqNum) {
		this.csgSeqNum = csgSeqNum;
	}

	/**
	 * @return the poaCode
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode the poaCode to set
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return the memStaus
	 */
	public String getMemStaus() {
		return memStaus;
	}

	/**
	 * @param memStaus the memStaus to set
	 */
	public void setMemStaus(String memStaus) {
		this.memStaus = memStaus;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getSeqNumber() {
		return seqNumber;
	}

	public void setSeqNumber(int seqNumber) {
		this.seqNumber = seqNumber;
	}
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}

	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}

}
