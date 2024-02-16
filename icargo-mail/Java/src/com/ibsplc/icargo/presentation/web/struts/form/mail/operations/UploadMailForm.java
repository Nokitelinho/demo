/*
 * UploadMailForm.java Created on OCT 05, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.MeasureAnnotation;

/**
 * @author A-1556
 *
 */
public class UploadMailForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.uploadmail";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "uploadMailResources";


	private String[] selectAccept;
	private String[] selectArrive;
	private String[] selectReturn;
	private String[] selectTransfer;
	private String[] selectOffload;
	private String[] selectReassignmail;
	private String[] selectReassigndespatch;

	private String status;
	private String disableStat;
	private String preassignFlag;

	private String acceptInfo;
	private String arriveInfo;
	private String returnInfo;
	private String transferInfo;
	private String offloadInfo;
	private String reassignmailInfo;
	private String reassigndespatchInfo;

	private String[] excepInfoAccept;
	private String[] excepInfoArrive;
	private String[] excepInfoReturn;
	private String[] excepInfoTransfer;
	private String[] excepInfoOffload;
	private String[] excepInfoReassignmail;

	private String[] acceptOoe;
	private String[] acceptDoe;
	private String[] acceptCat;
	private String[] acceptSc;
	private String[] acceptYr;
	private String[] acceptDsn;
	private String[] acceptRsn;
	private String[] acceptHni;
	private String[] acceptRi;
	
	@MeasureAnnotation(mappedValue="acceptWtMeasure",unitType="MWT")
	private String[] acceptWt;
	private Measure[] acceptWtMeasure;
	
	private String[] acceptScanDate;
	private String[] acceptScanTime;

	private String[] arriveOoe;
	private String[] arriveDoe;
	private String[] arriveCat;
	private String[] arriveSc;
	private String[] arriveYr;
	private String[] arriveDsn;
	private String[] arriveRsn;
	private String[] arriveHni;
	private String[] arriveRi;
	@MeasureAnnotation(mappedValue="arriveWtMeasure",unitType="MWT")
	private String[] arriveWt;
	private Measure[] arriveWtMeasure;

	private String[] arriveScanDate;
	private String[] arriveScanTime;

	private String[] returnOoe;
	private String[] returnDoe;
	private String[] returnCat;
	private String[] returnSc;
	private String[] returnYr;
	private String[] returnDsn;
	private String[] returnRsn;
	private String[] returnHni;
	private String[] returnRi;
	
	@MeasureAnnotation(mappedValue="returnWtMeasure",unitType="MWT")
	private String[] returnWt;
	private Measure[] returnWtMeasure;
	
	private String[] returnScanDate;
	private String[] returnScanTime;
	private String[] returnReasonDescription;
	private String[] returnPostalCode;
	private String[] paDescription;

	private String[] transferOoe;
	private String[] transferDoe;
	private String[] transferCat;
	private String[] transferSc;
	private String[] transferYr;
	private String[] transferDsn;
	private String[] transferRsn;
	private String[] transferHni;
	private String[] transferRi;
	
	@MeasureAnnotation(mappedValue="transferWtMeasure",unitType="MWT")
	private String[] transferWt;
	private Measure[] transferWtMeasure;
	
	private String[] transferMailBagFltNo;
	private String[] transferMailBagFltDate;
	private String[] transferScanDate;
	private String[] transferScanTime;
	private String[] transferContainerFltNo;
	private String[] transferContainerFltDate;
	private String[] transferContainerNo;
	private String[] transferPou;

	private String[] offloadOoe;
	private String[] offloadDoe;
	private String[] offloadCat;
	private String[] offloadSc;
	private String[] offloadYr;
	private String[] offloadDsn;
	private String[] offloadRsn;
	private String[] offloadHni;
	private String[] offloadRi;
	
	@MeasureAnnotation(mappedValue="offloadWtMeasure",unitType="MWT")
	private String[] offloadWt;
	private Measure[] offloadWtMeasure;
	
	private String[] offloadMailBagFltNo;
	private String[] offloadMailBagFltDate;
	private String[] offloadScanDate;
	private String[] offloadScanTime;
	private String[] offloadReason;
	private String[] offloadReasonDescription;
	private String[] offloadRemark;
	
	private String[] reaasignmailOoe;
	private String[] reaasignmailDoe;
	private String[] reaasignmailCat;
	private String[] reaasignmailSc;
	private String[] reaasignmailYr;
	private String[] reaasignmailDsn;
	private String[] reaasignmailRsn;
	private String[] reaasignmailHni;
	private String[] reaasignmailRi;
	
	@MeasureAnnotation(mappedValue="reaasignmailWtMeasure",unitType="MWT")
	private String[] reaasignmailWt;
	private Measure[] reaasignmailWtMeasure;
	
	private String[] reaasignmailFltNo;
	private String[] reaasignmailFltDate;
	private String[] reaasignmailScanDate;
	private String[] reaasignmailScanTime;
	private String[] reaasignmailContainerFltNo;
	private String[] reaasignmailContainerFltDate;
	private String[] reaasignmailContainerNo;
	private String[] reaasignmailPou;
	
	
	//Offload Reason popup

	private String offloadCode;
	private String offloadRemarks;
	private String offloadFlag;
	private String offloadDetails;


	/**For Coloring text*/
	private String[] acceptColor;
	private String[] arriveColor;
	private String[] returnColor;
	private String[] transferColor;
	private String[] offloadColor;
	private String[] reassigndespatchColor;

	//Container Details popup

	private String containerType;
	private String containerNumber;
	private String pol;
	private String continerFlag;
	private String continerDetails;
	private String arrCarrierCode;
	private String arrFlightNumber;
	private String arrFlightDate;

	//Return Reason popup

	private String returnCode;
	private String remarks;
	private String returnFlag;
	private String returnDetails;
	private String[] ackFlag;
	private String[] paCode;

  //Flight Details popup

	private String assignToFlight;
	private String acpCarrierCode;
	private String acpFlightNumber;
	private String acpFlightDate;
	private String acpDestination;
	private String acpDestCarrier;
	private String acpPou;
	private String destnFlight;
	private String flightFlag;
	private String flightDetails;
	private String excepInfoForContainer;
	
	private String savemode;
	
	private boolean enablemode;
	
	private String densityFactor;


	public String getOffloadDetails() {
		return offloadDetails;
	}

	public void setOffloadDetails(String offloadDetails) {
		this.offloadDetails = offloadDetails;
	}

	/**
	 * @return the acceptWtMeasure
	 */
	public Measure[] getAcceptWtMeasure() {
		return acceptWtMeasure;
	}

	/**
	 * @param acceptWtMeasure the acceptWtMeasure to set
	 */
	public void setAcceptWtMeasure(Measure[] acceptWtMeasure) {
		this.acceptWtMeasure = acceptWtMeasure;
	}

	/**
	 * @return the arriveWtMeasure
	 */
	public Measure[] getArriveWtMeasure() {
		return arriveWtMeasure;
	}

	/**
	 * @param arriveWtMeasure the arriveWtMeasure to set
	 */
	public void setArriveWtMeasure(Measure[] arriveWtMeasure) {
		this.arriveWtMeasure = arriveWtMeasure;
	}

	/**
	 * @return the returnWtMeasure
	 */
	public Measure[] getReturnWtMeasure() {
		return returnWtMeasure;
	}

	/**
	 * @param returnWtMeasure the returnWtMeasure to set
	 */
	public void setReturnWtMeasure(Measure[] returnWtMeasure) {
		this.returnWtMeasure = returnWtMeasure;
	}

	/**
	 * @return the transferWtMeasure
	 */
	public Measure[] getTransferWtMeasure() {
		return transferWtMeasure;
	}

	/**
	 * @param transferWtMeasure the transferWtMeasure to set
	 */
	public void setTransferWtMeasure(Measure[] transferWtMeasure) {
		this.transferWtMeasure = transferWtMeasure;
	}

	/**
	 * @return the offloadWtMeasure
	 */
	public Measure[] getOffloadWtMeasure() {
		return offloadWtMeasure;
	}

	/**
	 * @param offloadWtMeasure the offloadWtMeasure to set
	 */
	public void setOffloadWtMeasure(Measure[] offloadWtMeasure) {
		this.offloadWtMeasure = offloadWtMeasure;
	}

	/**
	 * @return the reaasignmailWtMeasure
	 */
	public Measure[] getReaasignmailWtMeasure() {
		return reaasignmailWtMeasure;
	}

	/**
	 * @param reaasignmailWtMeasure the reaasignmailWtMeasure to set
	 */
	public void setReaasignmailWtMeasure(Measure[] reaasignmailWtMeasure) {
		this.reaasignmailWtMeasure = reaasignmailWtMeasure;
	}

	public String getOffloadFlag() {
		return offloadFlag;
	}

	public void setOffloadFlag(String offloadFlag) {
		this.offloadFlag = offloadFlag;
	}

	public String getOffloadRemarks() {
		return offloadRemarks;
	}

	public void setOffloadRemarks(String offloadRemarks) {
		this.offloadRemarks = offloadRemarks;
	}

	public String getOffloadCode() {
		return offloadCode;
	}

	public void setOffloadCode(String offloadCode) {
		this.offloadCode = offloadCode;
	}

	/**
	 * @return Returns the ackFlag.
	 */
	public String[] getAckFlag() {
		return ackFlag;
	}

	/**
	 * @param ackFlag The ackFlag to set.
	 */
	public void setAckFlag(String[] ackFlag) {
		this.ackFlag = ackFlag;
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
	 * @return Returns the selectAccept.
	 */
	public String[] getSelectAccept() {
		return this.selectAccept;
	}

	/**
	 * @param selectAccept The selectAccept to set.
	 */
	public void setSelectAccept(String[] selectAccept) {
		this.selectAccept = selectAccept;
	}

	/**
	 * @return Returns the selectArrive.
	 */
	public String[] getSelectArrive() {
		return this.selectArrive;
	}

	/**
	 * @param selectArrive The selectArrive to set.
	 */
	public void setSelectArrive(String[] selectArrive) {
		this.selectArrive = selectArrive;
	}

	/**
	 * @return Returns the selectReturn.
	 */
	public String[] getSelectReturn() {
		return this.selectReturn;
	}

	/**
	 * @param selectReturn The selectReturn to set.
	 */
	public void setSelectReturn(String[] selectReturn) {
		this.selectReturn = selectReturn;
	}

	/**
	 * @return Returns the selectTransfer.
	 */
	public String[] getSelectTransfer() {
		return this.selectTransfer;
	}

	/**
	 * @param selectTransfer The selectTransfer to set.
	 */
	public void setSelectTransfer(String[] selectTransfer) {
		this.selectTransfer = selectTransfer;
	}


	/**
	 * @return Returns the selectOffload.
	 */
	public String[] getSelectOffload() {
		return this.selectOffload;
	}

	/**
	 * @param selectOffload The selectOffload to set.
	 */
	public void setSelectOffload(String[] selectOffload) {
		this.selectOffload = selectOffload;
	}


	/**
	 * @return Returns the acceptInfo.
	 */
	public String getAcceptInfo() {
		return this.acceptInfo;
	}

	/**
	 * @param acceptInfo The acceptInfo to set.
	 */
	public void setAcceptInfo(String acceptInfo) {
		this.acceptInfo = acceptInfo;
	}

	/**
	 * @return Returns the arriveInfo.
	 */
	public String getArriveInfo() {
		return this.arriveInfo;
	}

	/**
	 * @param arriveInfo The arriveInfo to set.
	 */
	public void setArriveInfo(String arriveInfo) {
		this.arriveInfo = arriveInfo;
	}

	/**
	 * @return Returns the returnInfo.
	 */
	public String getReturnInfo() {
		return this.returnInfo;
	}

	/**
	 * @param returnInfo The returnInfo to set.
	 */
	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}

	/**
	 * @return Returns the excepInfoAccept.
	 */
	public String[] getExcepInfoAccept() {
		return this.excepInfoAccept;
	}

	/**
	 * @param excepInfoAccept The excepInfoAccept to set.
	 */
	public void setExcepInfoAccept(String[] excepInfoAccept) {
		this.excepInfoAccept = excepInfoAccept;
	}

	/**
	 * @return Returns the excepInfoArrive.
	 */
	public String[] getExcepInfoArrive() {
		return this.excepInfoArrive;
	}

	/**
	 * @param excepInfoArrive The excepInfoArrive to set.
	 */
	public void setExcepInfoArrive(String[] excepInfoArrive) {
		this.excepInfoArrive = excepInfoArrive;
	}

	/**
	 * @return Returns the excepInfoReturn.
	 */
	public String[] getExcepInfoReturn() {
		return this.excepInfoReturn;
	}

	/**
	 * @param excepInfoReturn The excepInfoReturn to set.
	 */
	public void setExcepInfoReturn(String[] excepInfoReturn) {
		this.excepInfoReturn = excepInfoReturn;
	}

	/**
	 * @return Returns the containerNumber.
	 */
	public String getContainerNumber() {
		return this.containerNumber;
	}

	/**
	 * @param containerNumber The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	/**
	 * @return Returns the containerType.
	 */
	public String getContainerType() {
		return this.containerType;
	}

	/**
	 * @param containerType The containerType to set.
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	/**
	 * @return Returns the continerDetails.
	 */
	public String getContinerDetails() {
		return this.continerDetails;
	}

	/**
	 * @param continerDetails The continerDetails to set.
	 */
	public void setContinerDetails(String continerDetails) {
		this.continerDetails = continerDetails;
	}

	/**
	 * @return Returns the continerFlag.
	 */
	public String getContinerFlag() {
		return this.continerFlag;
	}

	/**
	 * @param continerFlag The continerFlag to set.
	 */
	public void setContinerFlag(String continerFlag) {
		this.continerFlag = continerFlag;
	}

	/**
	 * @return Returns the pol.
	 */
	public String getPol() {
		return this.pol;
	}

	/**
	 * @param pol The pol to set.
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return this.remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the returnCode.
	 */
	public String getReturnCode() {
		return this.returnCode;
	}

	/**
	 * @param returnCode The returnCode to set.
	 */
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	/**
	 * @return Returns the returnDetails.
	 */
	public String getReturnDetails() {
		return this.returnDetails;
	}

	/**
	 * @param returnDetails The returnDetails to set.
	 */
	public void setReturnDetails(String returnDetails) {
		this.returnDetails = returnDetails;
	}

	/**
	 * @return Returns the returnFlag.
	 */
	public String getReturnFlag() {
		return this.returnFlag;
	}

	/**
	 * @param returnFlag The returnFlag to set.
	 */
	public void setReturnFlag(String returnFlag) {
		this.returnFlag = returnFlag;
	}

	/**
	 * @return Returns the disableStat.
	 */
	public String getDisableStat() {
		return disableStat;
	}

	/**
	 * @param disableStat The disableStat to set.
	 */
	public void setDisableStat(String disableStat) {
		this.disableStat = disableStat;
	}

	/**
	 * @return Returns the paCode.
	 */
	public String[] getPaCode() {
		return paCode;
	}

	/**
	 * @param paCode The paCode to set.
	 */
	public void setPaCode(String[] paCode) {
		this.paCode = paCode;
	}

	/**
	 * @return Returns the acceptCat.
	 */
	public String[] getAcceptCat() {
		return this.acceptCat;
	}

	/**
	 * @param acceptCat The acceptCat to set.
	 */
	public void setAcceptCat(String[] acceptCat) {
		this.acceptCat = acceptCat;
	}

	/**
	 * @return Returns the acceptDoe.
	 */
	public String[] getAcceptDoe() {
		return this.acceptDoe;
	}

	/**
	 * @param acceptDoe The acceptDoe to set.
	 */
	public void setAcceptDoe(String[] acceptDoe) {
		this.acceptDoe = acceptDoe;
	}

	/**
	 * @return Returns the acceptDsn.
	 */
	public String[] getAcceptDsn() {
		return this.acceptDsn;
	}

	/**
	 * @param acceptDsn The acceptDsn to set.
	 */
	public void setAcceptDsn(String[] acceptDsn) {
		this.acceptDsn = acceptDsn;
	}

	/**
	 * @return Returns the acceptHni.
	 */
	public String[] getAcceptHni() {
		return this.acceptHni;
	}

	/**
	 * @param acceptHni The acceptHni to set.
	 */
	public void setAcceptHni(String[] acceptHni) {
		this.acceptHni = acceptHni;
	}

	/**
	 * @return Returns the acceptOoe.
	 */
	public String[] getAcceptOoe() {
		return this.acceptOoe;
	}

	/**
	 * @param acceptOoe The acceptOoe to set.
	 */
	public void setAcceptOoe(String[] acceptOoe) {
		this.acceptOoe = acceptOoe;
	}

	/**
	 * @return Returns the acceptRi.
	 */
	public String[] getAcceptRi() {
		return this.acceptRi;
	}

	/**
	 * @param acceptRi The acceptRi to set.
	 */
	public void setAcceptRi(String[] acceptRi) {
		this.acceptRi = acceptRi;
	}

	/**
	 * @return Returns the acceptRsn.
	 */
	public String[] getAcceptRsn() {
		return this.acceptRsn;
	}

	/**
	 * @param acceptRsn The acceptRsn to set.
	 */
	public void setAcceptRsn(String[] acceptRsn) {
		this.acceptRsn = acceptRsn;
	}

	/**
	 * @return Returns the acceptSc.
	 */
	public String[] getAcceptSc() {
		return this.acceptSc;
	}

	/**
	 * @param acceptSc The acceptSc to set.
	 */
	public void setAcceptSc(String[] acceptSc) {
		this.acceptSc = acceptSc;
	}

	/**
	 * @return Returns the acceptScanDate.
	 */
	public String[] getAcceptScanDate() {
		return this.acceptScanDate;
	}

	/**
	 * @param acceptScanDate The acceptScanDate to set.
	 */
	public void setAcceptScanDate(String[] acceptScanDate) {
		this.acceptScanDate = acceptScanDate;
	}

	/**
	 * @return Returns the acceptScanTime.
	 */
	public String[] getAcceptScanTime() {
		return this.acceptScanTime;
	}

	/**
	 * @param acceptScanTime The acceptScanTime to set.
	 */
	public void setAcceptScanTime(String[] acceptScanTime) {
		this.acceptScanTime = acceptScanTime;
	}

	/**
	 * @return Returns the acceptWt.
	 */
	public String[] getAcceptWt() {
		return this.acceptWt;
	}

	/**
	 * @param acceptWt The acceptWt to set.
	 */
	public void setAcceptWt(String[] acceptWt) {
		this.acceptWt = acceptWt;
	}

	/**
	 * @return Returns the acceptYr.
	 */
	public String[] getAcceptYr() {
		return this.acceptYr;
	}

	/**
	 * @param acceptYear The acceptYr to set.
	 */
	public void setAcceptYr(String[] acceptYr) {
		this.acceptYr = acceptYr;
	}

	/**
	 * @return Returns the arriveCat.
	 */
	public String[] getArriveCat() {
		return this.arriveCat;
	}

	/**
	 * @param arriveCat The arriveCat to set.
	 */
	public void setArriveCat(String[] arriveCat) {
		this.arriveCat = arriveCat;
	}

	/**
	 * @return Returns the arriveDoe.
	 */
	public String[] getArriveDoe() {
		return this.arriveDoe;
	}

	/**
	 * @param arriveDoe The arriveDoe to set.
	 */
	public void setArriveDoe(String[] arriveDoe) {
		this.arriveDoe = arriveDoe;
	}

	/**
	 * @return Returns the arriveDsn.
	 */
	public String[] getArriveDsn() {
		return this.arriveDsn;
	}

	/**
	 * @param arriveDsn The arriveDsn to set.
	 */
	public void setArriveDsn(String[] arriveDsn) {
		this.arriveDsn = arriveDsn;
	}

	/**
	 * @return Returns the arriveHni.
	 */
	public String[] getArriveHni() {
		return this.arriveHni;
	}

	/**
	 * @param arriveHni The arriveHni to set.
	 */
	public void setArriveHni(String[] arriveHni) {
		this.arriveHni = arriveHni;
	}

	/**
	 * @return Returns the arriveOoe.
	 */
	public String[] getArriveOoe() {
		return this.arriveOoe;
	}

	/**
	 * @param arriveOoe The arriveOoe to set.
	 */
	public void setArriveOoe(String[] arriveOoe) {
		this.arriveOoe = arriveOoe;
	}

	/**
	 * @return Returns the arriveRi.
	 */
	public String[] getArriveRi() {
		return this.arriveRi;
	}

	/**
	 * @param arriveRi The arriveRi to set.
	 */
	public void setArriveRi(String[] arriveRi) {
		this.arriveRi = arriveRi;
	}

	/**
	 * @return Returns the arriveRsn.
	 */
	public String[] getArriveRsn() {
		return this.arriveRsn;
	}

	/**
	 * @param arriveRsn The arriveRsn to set.
	 */
	public void setArriveRsn(String[] arriveRsn) {
		this.arriveRsn = arriveRsn;
	}

	/**
	 * @return Returns the arriveSc.
	 */
	public String[] getArriveSc() {
		return this.arriveSc;
	}

	/**
	 * @param arriveSc The arriveSc to set.
	 */
	public void setArriveSc(String[] arriveSc) {
		this.arriveSc = arriveSc;
	}

	/**
	 * @return Returns the arriveScanDate.
	 */
	public String[] getArriveScanDate() {
		return this.arriveScanDate;
	}

	/**
	 * @param arriveScanDate The arriveScanDate to set.
	 */
	public void setArriveScanDate(String[] arriveScanDate) {
		this.arriveScanDate = arriveScanDate;
	}

	/**
	 * @return Returns the arriveScanTime.
	 */
	public String[] getArriveScanTime() {
		return this.arriveScanTime;
	}

	/**
	 * @param arriveScanTime The arriveScanTime to set.
	 */
	public void setArriveScanTime(String[] arriveScanTime) {
		this.arriveScanTime = arriveScanTime;
	}

	/**
	 * @return Returns the arriveWt.
	 */
	public String[] getArriveWt() {
		return this.arriveWt;
	}

	/**
	 * @param arriveWt The arriveWt to set.
	 */
	public void setArriveWt(String[] arriveWt) {
		this.arriveWt = arriveWt;
	}

	/**
	 * @return Returns the arriveYr.
	 */
	public String[] getArriveYr() {
		return this.arriveYr;
	}

	/**
	 * @param arriveYear The arriveYr to set.
	 */
	public void setArriveYr(String[] arriveYr) {
		this.arriveYr = arriveYr;
	}

	/**
	 * @return Returns the returnCat.
	 */
	public String[] getReturnCat() {
		return this.returnCat;
	}

	/**
	 * @param returnCat The returnCat to set.
	 */
	public void setReturnCat(String[] returnCat) {
		this.returnCat = returnCat;
	}

	/**
	 * @return Returns the returnDoe.
	 */
	public String[] getReturnDoe() {
		return this.returnDoe;
	}

	/**
	 * @param returnDoe The returnDoe to set.
	 */
	public void setReturnDoe(String[] returnDoe) {
		this.returnDoe = returnDoe;
	}

	/**
	 * @return Returns the returnDsn.
	 */
	public String[] getReturnDsn() {
		return this.returnDsn;
	}

	/**
	 * @param returnDsn The returnDsn to set.
	 */
	public void setReturnDsn(String[] returnDsn) {
		this.returnDsn = returnDsn;
	}

	/**
	 * @return Returns the returnHni.
	 */
	public String[] getReturnHni() {
		return this.returnHni;
	}

	/**
	 * @param returnHni The returnHni to set.
	 */
	public void setReturnHni(String[] returnHni) {
		this.returnHni = returnHni;
	}

	/**
	 * @return Returns the returnOoe.
	 */
	public String[] getReturnOoe() {
		return this.returnOoe;
	}

	/**
	 * @param returnOoe The returnOoe to set.
	 */
	public void setReturnOoe(String[] returnOoe) {
		this.returnOoe = returnOoe;
	}

	/**
	 * @return Returns the returnRi.
	 */
	public String[] getReturnRi() {
		return this.returnRi;
	}

	/**
	 * @param returnRi The returnRi to set.
	 */
	public void setReturnRi(String[] returnRi) {
		this.returnRi = returnRi;
	}

	/**
	 * @return Returns the returnRsn.
	 */
	public String[] getReturnRsn() {
		return this.returnRsn;
	}

	/**
	 * @param returnRsn The returnRsn to set.
	 */
	public void setReturnRsn(String[] returnRsn) {
		this.returnRsn = returnRsn;
	}

	/**
	 * @return Returns the returnSc.
	 */
	public String[] getReturnSc() {
		return this.returnSc;
	}

	/**
	 * @param returnSc The returnSc to set.
	 */
	public void setReturnSc(String[] returnSc) {
		this.returnSc = returnSc;
	}

	/**
	 * @return Returns the returnScanDate.
	 */
	public String[] getReturnScanDate() {
		return this.returnScanDate;
	}

	/**
	 * @param returnScanDate The returnScanDate to set.
	 */
	public void setReturnScanDate(String[] returnScanDate) {
		this.returnScanDate = returnScanDate;
	}

	/**
	 * @return Returns the returnScanTime.
	 */
	public String[] getReturnScanTime() {
		return this.returnScanTime;
	}

	/**
	 * @param returnScanTime The returnScanTime to set.
	 */
	public void setReturnScanTime(String[] returnScanTime) {
		this.returnScanTime = returnScanTime;
	}

	/**
	 * @return Returns the returnWt.
	 */
	public String[] getReturnWt() {
		return this.returnWt;
	}

	/**
	 * @param returnWt The returnWt to set.
	 */
	public void setReturnWt(String[] returnWt) {
		this.returnWt = returnWt;
	}

	/**
	 * @return Returns the returnYr.
	 */
	public String[] getReturnYr() {
		return this.returnYr;
	}

	/**
	 * @param returnYear The returnYr to set.
	 */
	public void setReturnYr(String[] returnYr) {
		this.returnYr = returnYr;
	}

	/**
	 * @return Returns the acpCarrierCode.
	 */
	public String getAcpCarrierCode() {
		return this.acpCarrierCode;
	}

	/**
	 * @param acpCarrierCode The acpCarrierCode to set.
	 */
	public void setAcpCarrierCode(String acpCarrierCode) {
		this.acpCarrierCode = acpCarrierCode;
	}

	/**
	 * @return Returns the acpDestCarrier.
	 */
	public String getAcpDestCarrier() {
		return this.acpDestCarrier;
	}

	/**
	 * @param acpDestCarrier The acpDestCarrier to set.
	 */
	public void setAcpDestCarrier(String acpDestCarrier) {
		this.acpDestCarrier = acpDestCarrier;
	}

	/**
	 * @return Returns the acpDestination.
	 */
	public String getAcpDestination() {
		return this.acpDestination;
	}

	/**
	 * @param acpDestination The acpDestination to set.
	 */
	public void setAcpDestination(String acpDestination) {
		this.acpDestination = acpDestination;
	}

	/**
	 * @return Returns the acpFlightDate.
	 */
	public String getAcpFlightDate() {
		return this.acpFlightDate;
	}

	/**
	 * @param acpFlightDate The acpFlightDate to set.
	 */
	public void setAcpFlightDate(String acpFlightDate) {
		this.acpFlightDate = acpFlightDate;
	}

	/**
	 * @return Returns the acpFlightNumber.
	 */
	public String getAcpFlightNumber() {
		return this.acpFlightNumber;
	}

	/**
	 * @param acpFlightNumber The acpFlightNumber to set.
	 */
	public void setAcpFlightNumber(String acpFlightNumber) {
		this.acpFlightNumber = acpFlightNumber;
	}

	/**
	 * @return Returns the acpPou.
	 */
	public String getAcpPou() {
		return this.acpPou;
	}

	/**
	 * @param acpPou The acpPou to set.
	 */
	public void setAcpPou(String acpPou) {
		this.acpPou = acpPou;
	}

	/**
	 * @return Returns the arrCarrierCode.
	 */
	public String getArrCarrierCode() {
		return this.arrCarrierCode;
	}

	/**
	 * @param arrCarrierCode The arrCarrierCode to set.
	 */
	public void setArrCarrierCode(String arrCarrierCode) {
		this.arrCarrierCode = arrCarrierCode;
	}

	/**
	 * @return Returns the arrFlightDate.
	 */
	public String getArrFlightDate() {
		return this.arrFlightDate;
	}

	/**
	 * @param arrFlightDate The arrFlightDate to set.
	 */
	public void setArrFlightDate(String arrFlightDate) {
		this.arrFlightDate = arrFlightDate;
	}

	/**
	 * @return Returns the arrFlightNumber.
	 */
	public String getArrFlightNumber() {
		return this.arrFlightNumber;
	}

	/**
	 * @param arrFlightNumber The arrFlightNumber to set.
	 */
	public void setArrFlightNumber(String arrFlightNumber) {
		this.arrFlightNumber = arrFlightNumber;
	}


	/**
	 * @return Returns the assignToFlight.
	 */
	public String getAssignToFlight() {
		return this.assignToFlight;
	}

	/**
	 * @param assignToFlight The assignToFlight to set.
	 */
	public void setAssignToFlight(String assignToFlight) {
		this.assignToFlight = assignToFlight;
	}

	/**
	 * @return Returns the flightDetails.
	 */
	public String getFlightDetails() {
		return this.flightDetails;
	}

	/**
	 * @param flightDetails The flightDetails to set.
	 */
	public void setFlightDetails(String flightDetails) {
		this.flightDetails = flightDetails;
	}

	/**
	 * @return Returns the flightFlag.
	 */
	public String getFlightFlag() {
		return this.flightFlag;
	}

	/**
	 * @param flightFlag The flightFlag to set.
	 */
	public void setFlightFlag(String flightFlag) {
		this.flightFlag = flightFlag;
	}

	/**
	 * @return Returns the preassignFlag.
	 */
	public String getPreassignFlag() {
		return this.preassignFlag;
	}

	/**
	 * @param preassignFlag The preassignFlag to set.
	 */
	public void setPreassignFlag(String preassignFlag) {
		this.preassignFlag = preassignFlag;
	}

	/**
	 * @return Returns the destnFlight.
	 */
	public String getDestnFlight() {
		return this.destnFlight;
	}

	/**
	 * @param destnFlight The destnFlight to set.
	 */
	public void setDestnFlight(String destnFlight) {
		this.destnFlight = destnFlight;
	}

	public String[] getAcceptColor() {
		return acceptColor;
	}

	public void setAcceptColor(String[] acceptColor) {
		this.acceptColor = acceptColor;
	}

	public String[] getArriveColor() {
		return arriveColor;
	}

	public void setArriveColor(String[] arriveColor) {
		this.arriveColor = arriveColor;
	}

	public String[] getReturnColor() {
		return returnColor;
	}

	public void setReturnColor(String[] returnColor) {
		this.returnColor = returnColor;
	}

	public String getExcepInfoForContainer() {
		return excepInfoForContainer;
	}

	public void setExcepInfoForContainer(String excepInfoForContainer) {
		this.excepInfoForContainer = excepInfoForContainer;
	}

	public String[] getExcepInfoTransfer() {
		return excepInfoTransfer;
	}

	public void setExcepInfoTransfer(String[] excepInfoTransfer) {
		this.excepInfoTransfer = excepInfoTransfer;
	}

	public String[] getExcepInfoOffload() {
		return excepInfoOffload;
	}

	public void setExcepInfoOffload(String[] excepInfoOffload) {
		this.excepInfoOffload = excepInfoOffload;
	}


	public String[] getTransferCat() {
		return transferCat;
	}

	public void setTransferCat(String[] transferCat) {
		this.transferCat = transferCat;
	}

	public String[] getTransferColor() {
		return transferColor;
	}

	public void setTransferColor(String[] transferColor) {
		this.transferColor = transferColor;
	}

	public String[] getTransferDoe() {
		return transferDoe;
	}

	public void setTransferDoe(String[] transferDoe) {
		this.transferDoe = transferDoe;
	}

	public String[] getTransferDsn() {
		return transferDsn;
	}

	public void setTransferDsn(String[] transferDsn) {
		this.transferDsn = transferDsn;
	}

	public String[] getTransferHni() {
		return transferHni;
	}

	public void setTransferHni(String[] transferHni) {
		this.transferHni = transferHni;
	}

	public String getTransferInfo() {
		return transferInfo;
	}

	public void setTransferInfo(String transferInfo) {
		this.transferInfo = transferInfo;
	}

	public String[] getTransferOoe() {
		return transferOoe;
	}

	public void setTransferOoe(String[] transferOoe) {
		this.transferOoe = transferOoe;
	}

	public String[] getTransferRi() {
		return transferRi;
	}

	public void setTransferRi(String[] transferRi) {
		this.transferRi = transferRi;
	}

	public String[] getTransferRsn() {
		return transferRsn;
	}

	public void setTransferRsn(String[] transferRsn) {
		this.transferRsn = transferRsn;
	}

	public String[] getTransferSc() {
		return transferSc;
	}

	public void setTransferSc(String[] transferSc) {
		this.transferSc = transferSc;
	}

	public String[] getTransferScanDate() {
		return transferScanDate;
	}

	public void setTransferScanDate(String[] transferScanDate) {
		this.transferScanDate = transferScanDate;
	}

	public String[] getTransferScanTime() {
		return transferScanTime;
	}

	public void setTransferScanTime(String[] transferScanTime) {
		this.transferScanTime = transferScanTime;
	}

	public String[] getTransferWt() {
		return transferWt;
	}

	public void setTransferWt(String[] transferWt) {
		this.transferWt = transferWt;
	}

	public String[] getTransferYr() {
		return transferYr;
	}

	public void setTransferYr(String[] transferYr) {
		this.transferYr = transferYr;
	}

	public String[] getTransferContainerFltDate() {
		return transferContainerFltDate;
	}

	public void setTransferContainerFltDate(String[] transferContainerFltDate) {
		this.transferContainerFltDate = transferContainerFltDate;
	}

	public String[] getTransferContainerFltNo() {
		return transferContainerFltNo;
	}

	public void setTransferContainerFltNo(String[] transferContainerFltNo) {
		this.transferContainerFltNo = transferContainerFltNo;
	}

	public String[] getTransferContainerNo() {
		return transferContainerNo;
	}

	public void setTransferContainerNo(String[] transferContainerNo) {
		this.transferContainerNo = transferContainerNo;
	}

	public String[] getTransferMailBagFltDate() {
		return transferMailBagFltDate;
	}

	public void setTransferMailBagFltDate(String[] transferMailBagFltDate) {
		this.transferMailBagFltDate = transferMailBagFltDate;
	}

	public String[] getTransferMailBagFltNo() {
		return transferMailBagFltNo;
	}

	public void setTransferMailBagFltNo(String[] transferMailBagFltNo) {
		this.transferMailBagFltNo = transferMailBagFltNo;
	}

	public String[] getTransferPou() {
		return transferPou;
	}

	public void setTransferPou(String[] transferPou) {
		this.transferPou = transferPou;
	}

	public String[] getOffloadDoe() {
		return offloadDoe;
	}

	public void setOffloadDoe(String[] offloadDoe) {
		this.offloadDoe = offloadDoe;
	}

	public String[] getOffloadDsn() {
		return offloadDsn;
	}

	public void setOffloadDsn(String[] offloadDsn) {
		this.offloadDsn = offloadDsn;
	}

	public String[] getOffloadHni() {
		return offloadHni;
	}

	public void setOffloadHni(String[] offloadHni) {
		this.offloadHni = offloadHni;
	}

	public String getOffloadInfo() {
		return offloadInfo;
	}

	public void setOffloadInfo(String offloadInfo) {
		this.offloadInfo = offloadInfo;
	}

	public String[] getOffloadOoe() {
		return offloadOoe;
	}

	public void setOffloadOoe(String[] offloadOoe) {
		this.offloadOoe = offloadOoe;
	}

	public String[] getOffloadRi() {
		return offloadRi;
	}

	public void setOffloadRi(String[] offloadRi) {
		this.offloadRi = offloadRi;
	}

	public String[] getOffloadRsn() {
		return offloadRsn;
	}

	public void setOffloadRsn(String[] offloadRsn) {
		this.offloadRsn = offloadRsn;
	}

	public String[] getOffloadSc() {
		return offloadSc;
	}

	public void setOffloadSc(String[] offloadSc) {
		this.offloadSc = offloadSc;
	}

	public String[] getOffloadScanDate() {
		return offloadScanDate;
	}

	public void setOffloadScanDate(String[] offloadScanDate) {
		this.offloadScanDate = offloadScanDate;
	}

	public String[] getOffloadScanTime() {
		return offloadScanTime;
	}

	public void setOffloadScanTime(String[] offloadScanTime) {
		this.offloadScanTime = offloadScanTime;
	}

	public String[] getOffloadWt() {
		return offloadWt;
	}

	public void setOffloadWt(String[] offloadWt) {
		this.offloadWt = offloadWt;
	}

	public String[] getOffloadYr() {
		return offloadYr;
	}

	public void setOffloadYr(String[] offloadYr) {
		this.offloadYr = offloadYr;
	}

	public String[] getOffloadColor() {
		return offloadColor;
	}

	public void setOffloadColor(String[] offloadColor) {
		this.offloadColor = offloadColor;
	}

	public String[] getOffloadCat() {
		return offloadCat;
	}

	public void setOffloadCat(String[] offloadCat) {
		this.offloadCat = offloadCat;
	}

	public String[] getOffloadMailBagFltDate() {
		return offloadMailBagFltDate;
	}

	public void setOffloadMailBagFltDate(String[] offloadMailBagFltDate) {
		this.offloadMailBagFltDate = offloadMailBagFltDate;
	}

	public String[] getOffloadMailBagFltNo() {
		return offloadMailBagFltNo;
	}

	public void setOffloadMailBagFltNo(String[] offloadMailBagFltNo) {
		this.offloadMailBagFltNo = offloadMailBagFltNo;
	}

	public String[] getOffloadReason() {
		return offloadReason;
	}

	public void setOffloadReason(String[] offloadReason) {
		this.offloadReason = offloadReason;
	}

	public String[] getOffloadRemark() {
		return offloadRemark;
	}

	public void setOffloadRemark(String[] offloadRemark) {
		this.offloadRemark = offloadRemark;
	}

	public String[] getOffloadReasonDescription() {
		return offloadReasonDescription;
	}

	public void setOffloadReasonDescription(String[] offloadReasonDescription) {
		this.offloadReasonDescription = offloadReasonDescription;
	}

	public String[] getReturnReasonDescription() {
		return returnReasonDescription;
	}

	public void setReturnReasonDescription(String[] returnReasonDescription) {
		this.returnReasonDescription = returnReasonDescription;
	}

	public String[] getReturnPostalCode() {
		return returnPostalCode;
	}

	public void setReturnPostalCode(String[] returnPostalCode) {
		this.returnPostalCode = returnPostalCode;
	}

	public String[] getPaDescription() {
		return paDescription;
	}

	public void setPaDescription(String[] paDescription) {
		this.paDescription = paDescription;
	}

	public String[] getExcepInfoReassignmail() {
		return excepInfoReassignmail;
	}

	public void setExcepInfoReassignmail(String[] excepInfoReassignmail) {
		this.excepInfoReassignmail = excepInfoReassignmail;
	}

	public String[] getReaasignmailCat() {
		return reaasignmailCat;
	}

	public void setReaasignmailCat(String[] reaasignmailCat) {
		this.reaasignmailCat = reaasignmailCat;
	}

	public String[] getReaasignmailContainerFltDate() {
		return reaasignmailContainerFltDate;
	}

	public void setReaasignmailContainerFltDate(
			String[] reaasignmailContainerFltDate) {
		this.reaasignmailContainerFltDate = reaasignmailContainerFltDate;
	}

	public String[] getReaasignmailContainerFltNo() {
		return reaasignmailContainerFltNo;
	}

	public void setReaasignmailContainerFltNo(String[] reaasignmailContainerFltNo) {
		this.reaasignmailContainerFltNo = reaasignmailContainerFltNo;
	}

	public String[] getReaasignmailContainerNo() {
		return reaasignmailContainerNo;
	}

	public void setReaasignmailContainerNo(String[] reaasignmailContainerNo) {
		this.reaasignmailContainerNo = reaasignmailContainerNo;
	}

	public String[] getReaasignmailDoe() {
		return reaasignmailDoe;
	}

	public void setReaasignmailDoe(String[] reaasignmailDoe) {
		this.reaasignmailDoe = reaasignmailDoe;
	}

	public String[] getReaasignmailDsn() {
		return reaasignmailDsn;
	}

	public void setReaasignmailDsn(String[] reaasignmailDsn) {
		this.reaasignmailDsn = reaasignmailDsn;
	}

	public String[] getReaasignmailHni() {
		return reaasignmailHni;
	}

	public void setReaasignmailHni(String[] reaasignmailHni) {
		this.reaasignmailHni = reaasignmailHni;
	}

	public String[] getReaasignmailFltDate() {
		return reaasignmailFltDate;
	}

	public void setReaasignmailMailBagFltDate(String[] reaasignmailMailBagFltDate) {
		this.reaasignmailFltDate = reaasignmailMailBagFltDate;
	}

	public String[] getReaasignmailMailBagFltNo() {
		return reaasignmailFltNo;
	}

	public void setReaasignmailMailBagFltNo(String[] reaasignmailMailBagFltNo) {
		this.reaasignmailFltNo = reaasignmailMailBagFltNo;
	}

	public String[] getReaasignmailOoe() {
		return reaasignmailOoe;
	}

	public void setReaasignmailOoe(String[] reaasignmailOoe) {
		this.reaasignmailOoe = reaasignmailOoe;
	}

	public String[] getReaasignmailPou() {
		return reaasignmailPou;
	}

	public void setReaasignmailPou(String[] reaasignmailPou) {
		this.reaasignmailPou = reaasignmailPou;
	}

	public String[] getReaasignmailRi() {
		return reaasignmailRi;
	}

	public void setReaasignmailRi(String[] reaasignmailRi) {
		this.reaasignmailRi = reaasignmailRi;
	}

	public String[] getReaasignmailRsn() {
		return reaasignmailRsn;
	}

	public void setReaasignmailRsn(String[] reaasignmailRsn) {
		this.reaasignmailRsn = reaasignmailRsn;
	}

	public String[] getReaasignmailSc() {
		return reaasignmailSc;
	}

	public void setReaasignmailSc(String[] reaasignmailSc) {
		this.reaasignmailSc = reaasignmailSc;
	}

	public String[] getReaasignmailScanDate() {
		return reaasignmailScanDate;
	}

	public void setReaasignmailScanDate(String[] reaasignmailScanDate) {
		this.reaasignmailScanDate = reaasignmailScanDate;
	}

	public String[] getReaasignmailScanTime() {
		return reaasignmailScanTime;
	}

	public void setReaasignmailScanTime(String[] reaasignmailScanTime) {
		this.reaasignmailScanTime = reaasignmailScanTime;
	}

	public String[] getReaasignmailWt() {
		return reaasignmailWt;
	}

	public void setReaasignmailWt(String[] reaasignmailWt) {
		this.reaasignmailWt = reaasignmailWt;
	}

	public String[] getReaasignmailYr() {
		return reaasignmailYr;
	}

	public void setReaasignmailYr(String[] reaasignmailYr) {
		this.reaasignmailYr = reaasignmailYr;
	}

	public String getReassignmailInfo() {
		return reassignmailInfo;
	}

	public void setReassignmailInfo(String reassignmailInfo) {
		this.reassignmailInfo = reassignmailInfo;
	}

	public String[] getSelectReassignmail() {
		return selectReassignmail;
	}

	public void setSelectReassignmail(String[] selectReassignmail) {
		this.selectReassignmail = selectReassignmail;
	}

	public String[] getReassigndespatchColor() {
		return reassigndespatchColor;
	}

	public void setReassigndespatchColor(String[] reassigndespatchColor) {
		this.reassigndespatchColor = reassigndespatchColor;
	}

	public String getReassigndespatchInfo() {
		return reassigndespatchInfo;
	}

	public void setReassigndespatchInfo(String reassigndespatchInfo) {
		this.reassigndespatchInfo = reassigndespatchInfo;
	}

	public String[] getSelectReassigndespatch() {
		return selectReassigndespatch;
	}

	public void setSelectReassigndespatch(String[] selectReassigndespatch) {
		this.selectReassigndespatch = selectReassigndespatch;
	}

	public String getSavemode() {
		return savemode;
	}

	public void setSavemode(String savemode) {
		this.savemode = savemode;
	}

	public boolean getEnablemode() {
		return enablemode;
	}

	public void setEnablemode(boolean enablemode) {
		this.enablemode = enablemode;
	}

	public String getDensityFactor() {
		return densityFactor;
	}

	public void setDensityFactor(String densityFactor) {
		this.densityFactor = densityFactor;
	}

	


}