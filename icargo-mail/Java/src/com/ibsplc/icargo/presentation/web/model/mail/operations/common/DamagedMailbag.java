/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailbagFilter.java Created on	:	08-Jun-2018
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import com.ibsplc.icargo.framework.util.time.LocalDate;

/**
 * Java file :
 * com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailbagFilter.java
 * Version : Name : Date : Updation
 * ---------------------------------------------------
 *  0.1 : A-2257 :  08-Jun-2018 : Draft
 */
public class DamagedMailbag {
	
	private String companyCode;   
	private long mailSequenceNumber;
    private String dsn;
    private String originExchangeOffice;
    private String destinationExchangeOffice;
    private String mailClass;
    private int year; 
    private String mailbagId;
    private String damageCode;
    private String airportCode;
    private String damageDescription;
    private String userCode;
    private LocalDate damageDate;
    private String remarks;
    private String operationType;
    private String operationFlag;    
    private String returnedFlag;
    private String paCode;  
    private String damageType;
    private String fileName; 
    private byte[] fileData; 
    
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	public String getDsn() {
		return dsn;
	}
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	public String getOriginExchangeOffice() {
		return originExchangeOffice;
	}
	public void setOriginExchangeOffice(String originExchangeOffice) {
		this.originExchangeOffice = originExchangeOffice;
	}
	public String getDestinationExchangeOffice() {
		return destinationExchangeOffice;
	}
	public void setDestinationExchangeOffice(String destinationExchangeOffice) {
		this.destinationExchangeOffice = destinationExchangeOffice;
	}
	public String getMailClass() {
		return mailClass;
	}
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getMailbagId() {
		return mailbagId;
	}
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}
	public String getDamageCode() {
		return damageCode;
	}
	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public String getDamageDescription() {
		return damageDescription;
	}
	public void setDamageDescription(String damageDescription) {
		this.damageDescription = damageDescription;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public LocalDate getDamageDate() {
		return damageDate;
	}
	public void setDamageDate(LocalDate damageDate) {
		this.damageDate = damageDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getOperationFlag() {
		return operationFlag;
	}
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	public String getReturnedFlag() {
		return returnedFlag;
	}
	public void setReturnedFlag(String returnedFlag) {
		this.returnedFlag = returnedFlag;
	}
	public String getPaCode() {
		return paCode;
	}
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	public String getDamageType() {
		return damageType;
	}
	public void setDamageType(String damageType) {
		this.damageType = damageType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getFileData() {
		return fileData;
	}
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	
    
    
  
}
