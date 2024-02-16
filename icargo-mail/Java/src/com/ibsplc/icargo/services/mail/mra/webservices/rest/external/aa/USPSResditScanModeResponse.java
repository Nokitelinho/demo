/**
 *	Java file	: 	com.ibsplc.icargo.services.mail.mra.webservices.rest.external.aa.USPSResditScanModeResponse.java
 *
 *	Created by	:	A-7540
 *	Created on	:	08-May-2019
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.services.mail.mra.webservices.rest.external.aa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author A-7540
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class USPSResditScanModeResponse {
	
	private String scanUtcDtm;
	private String scanModeCode;
	private String scanSiteID;
	private String validForPayInd;
	private String exceptionCode;
	
	
	
	/**
	 * @return the scanUtcDtm
	 */
	public String getScanUtcDtm() {
		return scanUtcDtm;
	}
	/**
	 * @param scanUtcDtm the scanUtcDtm to set
	 */
	public void setScanUtcDtm(String scanUtcDtm) {
		this.scanUtcDtm = scanUtcDtm;
	}
	/**
	 * @return the scanModeCode
	 */
	public String getScanModeCode() {
		return scanModeCode;
	}
	/**
	 * @param scanModeCode the scanModeCode to set
	 */
	public void setScanModeCode(String scanModeCode) {
		this.scanModeCode = scanModeCode;
	}
	/**
	 * @return the scanSiteID
	 */
	public String getScanSiteID() {
		return scanSiteID;
	}
	/**
	 * @param scanSiteID the scanSiteID to set
	 */
	public void setScanSiteID(String scanSiteID) {
		this.scanSiteID = scanSiteID;
	}
	/**
	 * @return the validForPayInd
	 */
	public String getValidForPayInd() {
		return validForPayInd;
	}
	/**
	 * @param validForPayInd the validForPayInd to set
	 */
	public void setValidForPayInd(String validForPayInd) {
		this.validForPayInd = validForPayInd;
	}
	/**
	 * @return the exceptionCode
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}
	/**
	 * @param exceptionCode the exceptionCode to set
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	

}
