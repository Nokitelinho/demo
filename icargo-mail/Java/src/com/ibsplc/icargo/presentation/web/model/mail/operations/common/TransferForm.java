/*
 * TransferContainerForm.java Created on Oct 04, 2006 by A-7929
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

public class TransferForm{

	private static final String SCREEN_ID = "mailtracking.defaults.transfercontainer";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "transferContainerResources";

	private String reassignedto;
	private String flightCarrierCode;
	private String flightNumber;
	private String flightDate;
	private String flightPou;
	private long flightSeqNumber;
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
	
	private String scanDate;
	private String mailScanTime;
	
	private String similarCarrier;
	private String printTransferManifestFlag;
	private String isScreenLoad; 
	private String isReportNeeded;
	private String embargoFlag; //Added by A-8164  
	private String closeFlag;
	
	private String reassignFromDate;
	private String reassignToDate;
	private String uldToBarrow;
	public String getEmbargoFlag() {
		return embargoFlag;
	}

	public void setEmbargoFlag(String embargoFlag) {
		this.embargoFlag = embargoFlag;
	}
	
	
	public String getCloseFlag() {
		return closeFlag;
	}

	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}

	/**
	 * 
	 * @return
	 */
	public String getIsReportNeeded() {
		return isReportNeeded;
	}
/**
 * 
 * @param isReportNeeded
 */
	public void setIsReportNeeded(String isReportNeeded) {
		this.isReportNeeded = isReportNeeded;
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
	 * @return Returns the assignedto.
	 */
	public String getAssignedto() {
		return assignedto;
	}

	/**
	 * @param assignedto The assignedto to set.
	 */
	public void setAssignedto(String assignedto) {
		this.assignedto = assignedto;
	}

	/**
	 * @return Returns the fromScreen.
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen The fromScreen to set.
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return Returns the depDate.
	 */
	public String[] getDepDate() {
		return depDate;
	}

	/**
	 * @param depDate The depDate to set.
	 */
	public void setDepDate(String[] depDate) {
		this.depDate = depDate;
	}

	/**
	 * @return Returns the fltCarrier.
	 */
	public String[] getFltCarrier() {
		return fltCarrier;
	}

	/**
	 * @param fltCarrier The fltCarrier to set.
	 */
	public void setFltCarrier(String[] fltCarrier) {
		this.fltCarrier = fltCarrier;
	}

	/**
	 * @return Returns the fltNo.
	 */
	public String[] getFltNo() {
		return fltNo;
	}

	/**
	 * @param fltNo The fltNo to set.
	 */
	public void setFltNo(String[] fltNo) {
		this.fltNo = fltNo;
	}

	/**
	 * @return Returns the pointOfUnlading.
	 */
	public String[] getPointOfUnlading() {
		return pointOfUnlading;
	}

	/**
	 * @param pointOfUnlading The pointOfUnlading to set.
	 */
	public void setPointOfUnlading(String[] pointOfUnlading) {
		this.pointOfUnlading = pointOfUnlading;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the carrier.
	 */
	public String getCarrier() {
		return carrier;
	}

	/**
	 * @param carrier The carrier to set.
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
		
	/**
	 * @return Returns the flightCarrierCode.
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	/**
	 * @return Returns the flightDate.
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the flightPou.
	 */
	public String getFlightPou() {
		return flightPou;
	}

	/**
	 * @param flightPou The flightPou to set.
	 */
	public void setFlightPou(String flightPou) {
		this.flightPou = flightPou;
	}

	/**
	 * @return Returns the reassignCheckAll.
	 */
	public String getReassignCheckAll() {
		return reassignCheckAll;
	}

	/**
	 * @param reassignCheckAll The reassignCheckAll to set.
	 */
	public void setReassignCheckAll(String reassignCheckAll) {
		this.reassignCheckAll = reassignCheckAll;
	}

	/**
	 * @return Returns the reassignedto.
	 */
	public String getReassignedto() {
		return reassignedto;
	}

	/**
	 * @param reassignedto The reassignedto to set.
	 */
	public void setReassignedto(String reassignedto) {
		this.reassignedto = reassignedto;
	}

	/**
	 * @return Returns the reassignSubCheck.
	 */
	public String[] getReassignSubCheck() {
		return reassignSubCheck;
	}

	/**
	 * @param reassignSubCheck The reassignSubCheck to set.
	 */
	public void setReassignSubCheck(String[] reassignSubCheck) {
		this.reassignSubCheck = reassignSubCheck;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
     * @return SCREEN_ID - String
     */
    public String getScreenId() {
        return SCREEN_ID;
    }

    /**
     * @return PRODUCT_NAME - String
     */
    public String getProduct() {
        return PRODUCT_NAME;
    }

    /**
     * @return SUBPRODUCT_NAME - String
     */
    public String getSubProduct() {
        return SUBPRODUCT_NAME;
    }

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	public String getHideRadio() {
		return hideRadio;
	}

	public void setHideRadio(String hideRadio) {
		this.hideRadio = hideRadio;
	}

	public String getMailScanTime() {
		return mailScanTime;
	}

	public void setMailScanTime(String mailScanTime) {
		this.mailScanTime = mailScanTime;
	}

	public String getScanDate() {
		return scanDate;
	}

	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}

	/**
	 * @return the printTransferManifestFlag
	 */
	public String getPrintTransferManifestFlag() {
		return printTransferManifestFlag;
	}

	/**
	 * @param printTransferManifestFlag the printTransferManifestFlag to set
	 */
	public void setPrintTransferManifestFlag(String printTransferManifestFlag) {
		this.printTransferManifestFlag = printTransferManifestFlag;
	}

	/**
	 * @return the similarCarrier
	 */
	public String getSimilarCarrier() {
		return similarCarrier;
	}

	/**
	 * @param similarCarrier the similarCarrier to set
	 */
	public void setSimilarCarrier(String similarCarrier) {
		this.similarCarrier = similarCarrier;
	}

	/**
	 * @return Returns the isScreenLoad.
	 */
	public String getIsScreenLoad() {
		return isScreenLoad;
	}

	/**
	 * @param isScreenLoad The isScreenLoad to set.
	 */
	public void setIsScreenLoad(String isScreenLoad) {
		this.isScreenLoad = isScreenLoad;
	}
	/**
	 * @return the reassignFromDate
	 */
	public String getReassignFromDate() {
		return reassignFromDate;
	}
	/**
	 * @param reassignFromDate the reassignFromDate to set
	 */
	public void setReassignFromDate(String reassignFromDate) {
		this.reassignFromDate = reassignFromDate;
	}
	/**
	 * @return the reassignToDate
	 */
	public String getReassignToDate() {
		return reassignToDate;
	}
	/**
	 * @param reassignToDate the reassignToDate to set
	 */
	public void setReassignToDate(String reassignToDate) {
		this.reassignToDate = reassignToDate;
	}
	public String getUldToBarrow() {
		return uldToBarrow;
	}
	public void setUldToBarrow(String uldToBarrow) {
		this.uldToBarrow = uldToBarrow;
	}

	public long getFlightSeqNumber() {
		return flightSeqNumber;
	}

	public void setFlightSeqNumber(long flightSeqNumber) {
		this.flightSeqNumber = flightSeqNumber;
	}
	
	

}
