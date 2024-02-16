/**
 *	Java file	: 	com.ibsplc.icargo.services.mail.mra.webservices.rest.external.aa.USPSResditReceiptResponse.java
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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.icargo.framework.util.time.LocalDate;

/**
 * @author A-7540
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class USPSResditReceiptResponse {

	@JsonProperty(value="resditScan")
	private USPSResditScanModeResponse[] scanModeResponses;
    private LocalDate responseDTM;
    private int scanCount;
    private String receptacleID;
    private String carrierCode;
	/**
	 * @return the scanModeResponses
	 */
	public USPSResditScanModeResponse[] getScanModeResponses() {
		return scanModeResponses;
	}
	/**
	 * @param scanModeResponses the scanModeResponses to set
	 */
	public void setScanModeResponses(USPSResditScanModeResponse[] scanModeResponses) {
		this.scanModeResponses = scanModeResponses;
	}
	/**
	 * @return the responseDTM
	 */
	public LocalDate getResponseDTM() {
		return responseDTM;
	}
	/**
	 * @param responseDTM the responseDTM to set
	 */
	public void setResponseDTM(LocalDate responseDTM) {
		this.responseDTM = responseDTM;
	}
	/**
	 * @return the scanCount
	 */
	public int getScanCount() {
		return scanCount;
	}
	/**
	 * @param scanCount the scanCount to set
	 */
	public void setScanCount(int scanCount) {
		this.scanCount = scanCount;
	}
	/**
	 * @return the receptacleID
	 */
	public String getReceptacleID() {
		return receptacleID;
	}
	/**
	 * @param receptacleID the receptacleID to set
	 */
	public void setReceptacleID(String receptacleID) {
		this.receptacleID = receptacleID;
	}
	/**
	 * @return the carrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	/**
	 * @param carrierCode the carrierCode to set
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
    
}
