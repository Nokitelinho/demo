/*
 * UploadMailDetailsForm.java Created on Nov 17, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author a-3817
 *
 */
public class UploadMailDetailsForm extends ScreenModel{
	private static final String BUNDLE = "uploadMailDetailsResources";

	private static final String SCREENID = "mailtracking.defaults.batchmailupload";

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "operations";
	
	
	
	private String[] selectFlightRadio;
	
	private String selectedScannedVOIndx;
	
	private String[] selectedExceptions;
	
	private String inboundCarrierCode;
	
	private String inboundFlightNumber;
	
	private String inboundFlightDate;
	
	private String arrivalPort;
	
	private String flightNumber;
	private String carrierCode;
	private String flightDate;
	private String flightTime;
	private String inboundFlightCloseflag;
	
	/*
	 * For Upload Modify popup CR
	 */
	private String outboundFlightNumber;
	private String outboundFlightCarrierCode;
	private String outboundFlightDate;
	private String toBulkFlag;
	private String toContainer;
	private String fromBulkFlag;
	private String fromContainer;
	private String pol;
	private String pou;
	private String outboundCarrierCode;
	private String dest;
	private String exportPort;
	
	private String processPoints[];
	private String savedCount[];
	private String selectedProcessPoint;
	
	private String uploadContinerModifyOverideFlag;
	
	public String getBundle() {
		return BUNDLE;
	}
	
	public String getProduct() {
		return PRODUCT;
	}
	
	public String getScreenId() {
		return SCREENID;
	}
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	/**
	 * @return the selectFlightRadio
	 */
	public String[] getSelectFlightRadio() {
		return selectFlightRadio;
	}

	/**
	 * @param selectFlightRadio the selectFlightRadio to set
	 */
	public void setSelectFlightRadio(String[] selectFlightRadio) {
		this.selectFlightRadio = selectFlightRadio;
	}

	/**
	 * @return the selectedScannedVOIndx
	 */
	public String getSelectedScannedVOIndx() {
		return selectedScannedVOIndx;
	}

	/**
	 * @param selectedScannedVOIndx the selectedScannedVOIndx to set
	 */
	public void setSelectedScannedVOIndx(String selectedScannedVOIndx) {
		this.selectedScannedVOIndx = selectedScannedVOIndx;
	}

	/**
	 * @return the selectedExceptions
	 */
	public String[] getSelectedExceptions() {
		return selectedExceptions;
	}

	/**
	 * @param selectedExceptions the selectedExceptions to set
	 */
	public void setSelectedExceptions(String[] selectedExceptions) {
		this.selectedExceptions = selectedExceptions;
	}

	/**
	 * @return the inboundCarrierCode
	 */
	public String getInboundCarrierCode() {
		return inboundCarrierCode;
	}

	/**
	 * @param inboundCarrierCode the inboundCarrierCode to set
	 */
	public void setInboundCarrierCode(String inboundCarrierCode) {
		this.inboundCarrierCode = inboundCarrierCode;
	}

	/**
	 * @return the inboundFlightDate
	 */
	public String getInboundFlightDate() {
		return inboundFlightDate;
	}

	/**
	 * @param inboundFlightDate the inboundFlightDate to set
	 */
	public void setInboundFlightDate(String inboundFlightDate) {
		this.inboundFlightDate = inboundFlightDate;
	}

	/**
	 * @return the inboundFlightNumber
	 */
	public String getInboundFlightNumber() {
		return inboundFlightNumber;
	}

	/**
	 * @param inboundFlightNumber the inboundFlightNumber to set
	 */
	public void setInboundFlightNumber(String inboundFlightNumber) {
		this.inboundFlightNumber = inboundFlightNumber;
	}

	/**
	 * @return the arrivalPort
	 */
	public String getArrivalPort() {
		return arrivalPort;
	}

	/**
	 * @param arrivalPort the arrivalPort to set
	 */
	public void setArrivalPort(String arrivalPort) {
		this.arrivalPort = arrivalPort;
	}

	/**
	 * @return the flightDate
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return the flightTime
	 */
	public String getFlightTime() {
		return flightTime;
	}

	/**
	 * @param flightTime the flightTime to set
	 */
	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}

	/**
	 * @return the inboundFlightCloseflag
	 */
	public String getInboundFlightCloseflag() {
		return inboundFlightCloseflag;
	}

	/**
	 * @param inboundFlightCloseflag the inboundFlightCloseflag to set
	 */
	public void setInboundFlightCloseflag(String inboundFlightCloseflag) {
		this.inboundFlightCloseflag = inboundFlightCloseflag;
	}

	/**
	 * @return the fromContainer
	 */
	public String getFromContainer() {
		return fromContainer;
	}

	/**
	 * @param fromContainer the fromContainer to set
	 */
	public void setFromContainer(String fromContainer) {
		this.fromContainer = fromContainer;
	}

	/**
	 * @return the outboundCarrierCode
	 */
	public String getOutboundCarrierCode() {
		return outboundCarrierCode;
	}

	/**
	 * @param outboundCarrierCode the outboundCarrierCode to set
	 */
	public void setOutboundCarrierCode(String outboundCarrierCode) {
		this.outboundCarrierCode = outboundCarrierCode;
	}

	/**
	 * @return the outboundFlightDate
	 */
	public String getOutboundFlightDate() {
		return outboundFlightDate;
	}

	/**
	 * @param outboundFlightDate the outboundFlightDate to set
	 */
	public void setOutboundFlightDate(String outboundFlightDate) {
		this.outboundFlightDate = outboundFlightDate;
	}

	/**
	 * @return the outboundFlightNumber
	 */
	public String getOutboundFlightNumber() {
		return outboundFlightNumber;
	}

	/**
	 * @param outboundFlightNumber the outboundFlightNumber to set
	 */
	public void setOutboundFlightNumber(String outboundFlightNumber) {
		this.outboundFlightNumber = outboundFlightNumber;
	}

	/**
	 * @return the pol
	 */
	public String getPol() {
		return pol;
	}

	/**
	 * @param pol the pol to set
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}

	/**
	 * @return the pou
	 */
	public String getPou() {
		return pou;
	}

	/**
	 * @param pou the pou to set
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}

	/**
	 * @return the selectedProcessPoint
	 */
	public String getSelectedProcessPoint() {
		return selectedProcessPoint;
	}

	/**
	 * @param selectedProcessPoint the selectedProcessPoint to set
	 */
	public void setSelectedProcessPoint(String selectedProcessPoint) {
		this.selectedProcessPoint = selectedProcessPoint;
	}

	/**
	 * @return the toContainer
	 */
	public String getToContainer() {
		return toContainer;
	}

	/**
	 * @param toContainer the toContainer to set
	 */
	public void setToContainer(String toContainer) {
		this.toContainer = toContainer;
	}

	/**
	 * @return the dest
	 */
	public String getDest() {
		return dest;
	}

	/**
	 * @param dest the dest to set
	 */
	public void setDest(String dest) {
		this.dest = dest;
	}

	/**
	 * @return the exportPort
	 */
	public String getExportPort() {
		return exportPort;
	}

	/**
	 * @param exportPort the exportPort to set
	 */
	public void setExportPort(String exportPort) {
		this.exportPort = exportPort;
	}

	/**
	 * @return the fromBulkFlag
	 */
	public String getFromBulkFlag() {
		return fromBulkFlag;
	}

	/**
	 * @param fromBulkFlag the fromBulkFlag to set
	 */
	public void setFromBulkFlag(String fromBulkFlag) {
		this.fromBulkFlag = fromBulkFlag;
	}

	/**
	 * @return the toBulkFlag
	 */
	public String getToBulkFlag() {
		return toBulkFlag;
	}

	/**
	 * @param toBulkFlag the toBulkFlag to set
	 */
	public void setToBulkFlag(String toBulkFlag) {
		this.toBulkFlag = toBulkFlag;
	}

	/**
	 * @return the outboundFlightCarrierCode
	 */
	public String getOutboundFlightCarrierCode() {
		return outboundFlightCarrierCode;
	}

	/**
	 * @param outboundFlightCarrierCode the outboundFlightCarrierCode to set
	 */
	public void setOutboundFlightCarrierCode(String outboundFlightCarrierCode) {
		this.outboundFlightCarrierCode = outboundFlightCarrierCode;
	}

	/**
	 * @return the processPoints
	 */
	public String[] getProcessPoints() {
		return processPoints;
	}

	/**
	 * @param processPoints the processPoints to set
	 */
	public void setProcessPoints(String[] processPoints) {
		this.processPoints = processPoints;
	}

	/**
	 * @return the savedCount
	 */
	public String[] getSavedCount() {
		return savedCount;
	}

	/**
	 * @param savedCount the savedCount to set
	 */
	public void setSavedCount(String[] savedCount) {
		this.savedCount = savedCount;
	}

	/**
	 * @return the uploadContinerModifyOverideFlag
	 */
	public String getUploadContinerModifyOverideFlag() {
		return uploadContinerModifyOverideFlag;
	}

	/**
	 * @param uploadContinerModifyOverideFlag the uploadContinerModifyOverideFlag to set
	 */
	public void setUploadContinerModifyOverideFlag(
			String uploadContinerModifyOverideFlag) {
		this.uploadContinerModifyOverideFlag = uploadContinerModifyOverideFlag;
	}

}
