/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.common.ConsginmentDetails.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Feb 13, 2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.model.mail.mra.common;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.common.ConsginmentDetails.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Feb 13, 2019	:	Draft
 */
public class ConsignmentDetails {
	
	private String companyCode;
	private String consignmentNumber;
	private String currency;
	private String dsnNumber;
	private String mailSubClass;
	private String mailCategory;
	private String origin;
	private String destination;
	private String originOE;
	private String destinationOE;
	private double rate;
	private String paCode;
	private String isUSPSPerformed;
	private String mcaNumber;
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getConsignmentNumber() {
		return consignmentNumber;
	}
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getDsnNumber() {
		return dsnNumber;
	}
	public void setDsnNumber(String dsnNumber) {
		this.dsnNumber = dsnNumber;
	}
	public String getMailSubClass() {
		return mailSubClass;
	}
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}
	public String getMailCategory() {
		return mailCategory;
	}
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getOriginOE() {
		return originOE;
	}
	public void setOriginOE(String originOE) {
		this.originOE = originOE;
	}
	public String getDestinationOE() {
		return destinationOE;
	}
	public void setDestinationOE(String destinationOE) {
		this.destinationOE = destinationOE;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getPaCode() {
		return paCode;
	}
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	public String getIsUSPSPerformed() {
		return isUSPSPerformed;
	}
	public void setIsUSPSPerformed(String isUSPSPerformed) {
		this.isUSPSPerformed = isUSPSPerformed;
	}
	public String getMcaNumber() {
		return mcaNumber;
	}
	public void setMcaNumber(String mcaNumber) {
		this.mcaNumber = mcaNumber;
	}

}
