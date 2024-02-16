/*
 * ExportManifestModel.java Created on Jul 17, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.model.mail.operations.common;




/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jul 17, 2017	     A-1753		First draft
 */

public class OnwardRouting {	
    
    private String operationFlag;
    private String onwardCarrierCode;
    private int onwardCarrierId;
    private String onwardFlightNumber;
    private String onwardFlightDate;
    private String pou;
    private String assignmenrPort;
    private String companyCode;
    private String containerNumber;
    private int carrierId;
    private String flightNumber;
    private long flightSequenceNumber;
    private int legSerialNumber;
    private int routingSerialNumber;
    private String carrierCode;
    private String pol;
    private String operationalStatus;
   private String flightDate;
    
public String getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
}
	public String getOperationFlag() {
		return operationFlag;
	}
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	public String getOnwardCarrierCode() {
		return onwardCarrierCode;
	}
	public void setOnwardCarrierCode(String onwardCarrierCode) {
		this.onwardCarrierCode = onwardCarrierCode;
	}
	public int getOnwardCarrierId() {
		return onwardCarrierId;
	}
	public void setOnwardCarrierId(int onwardCarrierId) {
		this.onwardCarrierId = onwardCarrierId;
	}
	public String getOnwardFlightNumber() {
		return onwardFlightNumber;
	}
	public void setOnwardFlightNumber(String onwardFlightNumber) {
		this.onwardFlightNumber = onwardFlightNumber;
	}
	public String getOnwardFlightDate() {
		return onwardFlightDate;
	}
	public void setOnwardFlightDate(String onwardFlightDate) {
		this.onwardFlightDate = onwardFlightDate;
	}
	public String getPou() {
		return pou;
	}
	public void setPou(String pou) {
		this.pou = pou;
	}
	public String getAssignmenrPort() {
		return assignmenrPort;
	}
	public void setAssignmenrPort(String assignmenrPort) {
		this.assignmenrPort = assignmenrPort;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public int getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getCarrierCode() {
		return carrierCode;
}
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getPol() {
		return pol;
	}
	public void setPol(String pol) {
		this.pol = pol;
	}
	public String getOperationalStatus() {
		return operationalStatus;
	}
	public void setOperationalStatus(String operationalStatus) {
		this.operationalStatus = operationalStatus;
	}
	
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	public int getLegSerialNumber() {
		return legSerialNumber;
	}
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}
	public int getRoutingSerialNumber() {
		return routingSerialNumber;
	}
	public void setRoutingSerialNumber(int routingSerialNumber) {
		this.routingSerialNumber = routingSerialNumber;
	}
    
	
}
