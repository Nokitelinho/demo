/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.common.ReassignContainer.java
 *
 *	Created by	:	a-7779
 *	Created on	:	25-Sep-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import java.util.List;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.common.ReassignContainer.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7779	:	25-Sep-2018	:	Draft
 */
public class ReassignContainer {

	private String reassignFilterType;
	private String carrierCode;
	private String flightNumber;
	private long flightSeqNumber;
	private String flightDate;
	private String flightPou;
	
		
	private String carrier;
	private String destination;
	
	private String reassignCheckAll;
	private String[] reassignSubCheck;
	private String[] fltCarrier;
	private String[] fltNo;
	private String[] depDate;
	private String[] pointOfUnlading;
	
	private String remarks;
	
	private String status;
	private String fromScreen;
	private String assignedto;
	private String hideRadio;
	private String fromFlightCarrierCode;
	private String fromFlightNumber;
	private String frmFlightDate;
	private String fromdestination;
	
	private String scanDate;
	private String mailScanTime;
	private List<OnwardRouting> onwardRouting;
	
	private String fromDate;
	private String reassignToDate;
	
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	public String getFlightPou() {
		return flightPou;
	}
	public void setFlightPou(String flightPou) {
		this.flightPou = flightPou;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getReassignCheckAll() {
		return reassignCheckAll;
	}
	public void setReassignCheckAll(String reassignCheckAll) {
		this.reassignCheckAll = reassignCheckAll;
	}
	public String[] getReassignSubCheck() {
		return reassignSubCheck;
	}
	public void setReassignSubCheck(String[] reassignSubCheck) {
		this.reassignSubCheck = reassignSubCheck;
	}
	public String[] getFltCarrier() {
		return fltCarrier;
	}
	public void setFltCarrier(String[] fltCarrier) {
		this.fltCarrier = fltCarrier;
	}
	public String[] getFltNo() {
		return fltNo;
	}
	public void setFltNo(String[] fltNo) {
		this.fltNo = fltNo;
	}
	public String[] getDepDate() {
		return depDate;
	}
	public void setDepDate(String[] depDate) {
		this.depDate = depDate;
	}
	public String[] getPointOfUnlading() {
		return pointOfUnlading;
	}
	public void setPointOfUnlading(String[] pointOfUnlading) {
		this.pointOfUnlading = pointOfUnlading;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFromScreen() {
		return fromScreen;
	}
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}
	public String getAssignedto() {
		return assignedto;
	}
	public void setAssignedto(String assignedto) {
		this.assignedto = assignedto;
	}
	public String getHideRadio() {
		return hideRadio;
	}
	public void setHideRadio(String hideRadio) {
		this.hideRadio = hideRadio;
	}
	public String getFromFlightCarrierCode() {
		return fromFlightCarrierCode;
	}
	public void setFromFlightCarrierCode(String fromFlightCarrierCode) {
		this.fromFlightCarrierCode = fromFlightCarrierCode;
	}
	public String getFromFlightNumber() {
		return fromFlightNumber;
	}
	public void setFromFlightNumber(String fromFlightNumber) {
		this.fromFlightNumber = fromFlightNumber;
	}
	public String getFrmFlightDate() {
		return frmFlightDate;
	}
	public void setFrmFlightDate(String frmFlightDate) {
		this.frmFlightDate = frmFlightDate;
	}
	public String getFromdestination() {
		return fromdestination;
	}
	public void setFromdestination(String fromdestination) {
		this.fromdestination = fromdestination;
	}
	public String getScanDate() {
		return scanDate;
	}
	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}
	public String getMailScanTime() {
		return mailScanTime;
	}
	public void setMailScanTime(String mailScanTime) {
		this.mailScanTime = mailScanTime;
	}
	public String getReassignFilterType() {
		return reassignFilterType;
	}
	public void setReassignFilterType(String reassignFilterType) {
		this.reassignFilterType = reassignFilterType;
	}
	public List<OnwardRouting> getOnwardRouting() {
		return onwardRouting;
	}
	public void setOnwardRouting(List<OnwardRouting> onwardRouting) {
		this.onwardRouting = onwardRouting;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getReassignToDate() {
		return reassignToDate;
	}
	public void setReassignToDate(String reassignToDate) {
		this.reassignToDate = reassignToDate;
	}
	public long getFlightSeqNumber() {
		return flightSeqNumber;
	}
	public void setFlightSeqNumber(long flightSeqNumber) {
		this.flightSeqNumber = flightSeqNumber;
	}
	
	
   	
	

}
