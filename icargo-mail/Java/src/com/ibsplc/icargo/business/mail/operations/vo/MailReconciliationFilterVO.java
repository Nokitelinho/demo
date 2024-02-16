/*
 * MailReconciliationFilterVO.java Created on Jun 30, 2016
 *
 * Copyright 2010 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-3109
 * 
 */
public class MailReconciliationFilterVO extends AbstractVO {
	
	private String companyCode;
	private String originOE;
	private String destinationOE; 
	private String category;
	private String subclass;
	private int year; 
	private String DSN; 
	private String RSN; 
	private Collection<String> eventCodes; 
	private String controlReferenceNumber;
	private String consignmentDocumentNumber;
	private String paCode;
	private String carditOrigin;
	private String carditDestination;
	private String eventPortCode;
	private LocalDate operationFromDate;
	private LocalDate operationToDate; 
	private boolean isDelayPeriodRequired;
	private int pageNumber;
	//private double weight;
	private Measure weight;//added by A-7371
	private Collection<String> msgMissing; 
	private int delayPeriodValue;
	private String carditStatus;
    private int totalRecords;
	/**
	 * 
	 * @return weight
	 */
	public Measure getWeight() {
		return weight;
	}
    /**
     * 
     * @param weight
     */
	public void setWeight(Measure weight) {
		this.weight = weight;
	}
	
	/**
	 * @return Returns the totalRecords.
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords The totalRecords to set.
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
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
	 * @return the originOE
	 */
	public String getOriginOE() {
		return originOE;
	}
	/**
	 * @param originOE the originOE to set
	 */
	public void setOriginOE(String originOE) {
		this.originOE = originOE;
	}
	/**
	 * @return the destinationOE
	 */
	public String getDestinationOE() {
		return destinationOE;
	}
	/**
	 * @param destinationOE the destinationOE to set
	 */
	public void setDestinationOE(String destinationOE) {
		this.destinationOE = destinationOE;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the subclass
	 */
	public String getSubclass() {
		return subclass;
	}
	/**
	 * @param subclass the subclass to set
	 */
	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return the dSN
	 */
	public String getDSN() {
		return DSN;
	}
	/**
	 * @param dsn the dSN to set
	 */
	public void setDSN(String dsn) {
		DSN = dsn;
	}
	/**
	 * @return the rSN
	 */
	public String getRSN() {
		return RSN;
	}
	/**
	 * @param rsn the rSN to set
	 */
	public void setRSN(String rsn) {
		RSN = rsn;
	}
	/**
	 * @return the eventCodes
	 */
	public Collection<String> getEventCodes() {
		return eventCodes;
	}
	/**
	 * @param eventCodes the eventCodes to set
	 */
	public void setEventCodes(Collection<String> eventCodes) {
		this.eventCodes = eventCodes;
	}
	/**
	 * @return the controlReferenceNumber
	 */
	public String getControlReferenceNumber() {
		return controlReferenceNumber;
	}
	/**
	 * @param controlReferenceNumber the controlReferenceNumber to set
	 */
	public void setControlReferenceNumber(String controlReferenceNumber) {
		this.controlReferenceNumber = controlReferenceNumber;
	}
	/**
	 * @return the consignmentDocumentNumber
	 */
	public String getConsignmentDocumentNumber() {
		return consignmentDocumentNumber;
	}
	/**
	 * @param consignmentDocumentNumber the consignmentDocumentNumber to set
	 */
	public void setConsignmentDocumentNumber(String consignmentDocumentNumber) {
		this.consignmentDocumentNumber = consignmentDocumentNumber;
	}
	/**
	 * @return the paCode
	 */
	public String getPaCode() {
		return paCode;
	}
	/**
	 * @param paCode the paCode to set
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	/**
	 * @return the carditOrigin
	 */
	public String getCarditOrigin() {
		return carditOrigin;
	}
	/**
	 * @param carditOrigin the carditOrigin to set
	 */
	public void setCarditOrigin(String carditOrigin) {
		this.carditOrigin = carditOrigin;
	}
	/**
	 * @return the carditDestination
	 */
	public String getCarditDestination() {
		return carditDestination;
	}
	/**
	 * @param carditDestination the carditDestination to set
	 */
	public void setCarditDestination(String carditDestination) {
		this.carditDestination = carditDestination;
	}
	/**
	 * @return the eventPortCode
	 */
	public String getEventPortCode() {
		return eventPortCode;
	}
	/**
	 * @param eventPortCode the eventPortCode to set
	 */
	public void setEventPortCode(String eventPortCode) {
		this.eventPortCode = eventPortCode;
	}
	/**
	 * @return the operationFromDate
	 */
	public LocalDate getOperationFromDate() {
		return operationFromDate;
	}
	/**
	 * @param operationFromDate the operationFromDate to set
	 */
	public void setOperationFromDate(LocalDate operationFromDate) {
		this.operationFromDate = operationFromDate;
	}
	/**
	 * @return the operationToDate
	 */
	public LocalDate getOperationToDate() {
		return operationToDate;
	}
	/**
	 * @param operationToDate the operationToDate to set
	 */
	public void setOperationToDate(LocalDate operationToDate) {
		this.operationToDate = operationToDate;
	}
	/**
	 * @return the isDelayPeriodRequired
	 */
	public boolean isDelayPeriodRequired() {
		return isDelayPeriodRequired;
	}
	/**
	 * @param isDelayPeriodRequired the isDelayPeriodRequired to set
	 */
	public void setDelayPeriodRequired(boolean isDelayPeriodRequired) {
		this.isDelayPeriodRequired = isDelayPeriodRequired;
	}
	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	/**
	 * @return the weight
	 */
	/*public double getWeight() {
		return weight;
	}
	*//**
	 * @param weight the weight to set
	 *//*
	public void setWeight(double weight) {
		this.weight = weight;
	}*/
	/**
	 * @return the msgMissing
	 */
	public Collection<String> getMsgMissing() {
		return msgMissing;
	}
	/**
	 * @param msgMissing the msgMissing to set
	 */
	public void setMsgMissing(Collection<String> msgMissing) {
		this.msgMissing = msgMissing;
	}
	/**
	 * @return the delayPeriodValue
	 */
	public int getDelayPeriodValue() {
		return delayPeriodValue;
	}
	/**
	 * @param delayPeriodValue the delayPeriodValue to set
	 */
	public void setDelayPeriodValue(int delayPeriodValue) {
		this.delayPeriodValue = delayPeriodValue;
	}
	/**
	 * @return the carditStatus
	 */
	public String getCarditStatus() {
		return carditStatus;
	}
	/**
	 * @param carditStatus the carditStatus to set
	 */
	public void setCarditStatus(String carditStatus) {
		this.carditStatus = carditStatus;
	}
	
}

