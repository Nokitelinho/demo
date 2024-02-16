/*
 * UCMINOUTForm.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * 
 * @author A-2046
 * 
 */
public class UCMINOUTForm extends ScreenModel {

	private static final String BUNDLE = "ucminoutResources";

	private String bundle;

	private static final String PRODUCT = "uld";

	private static final String SUBPRODUCT = "defaults";

	private static final String SCREENID = "uld.defaults.ucminoutmessaging";

	private String carrierCode;

	private String flightDate;

	private String flightNo;

	private String ucmIn;

	private String ucmOut;

	private String route;

	private String origin;

	private String destination;

	private String duplicateFlightStatus;

	private String arrivalTime;

	private String departureTime;

	private String[] selectedRows;

	private String[] pou;

	private String[] damageCodes;

	private String damagedStatus;

	private String viewUldStatus;

	private String[] uldNumbers;

	private String[] contentInd;

	private String[] operationFlag;

	private String linkStatus;

	// for checking of ucm out
	private String outConfirmStatus;

	// for calling in out delegate method
	private String ucmVOStatus;

	// for enabling and disabling of radio button
	private String messageTypeStatus;

	// for changing readonly mode of pou in uld detaiuls table
	private String pouStatus;

	// for enabling and disabling of origin and destination fields
	private String orgDestStatus;

	// for highlighting uld errors
	private String[] errorCodes;

	// for displaying ucm errors if any after save

	private String errorDesc;

	// for blocking sending of ucm
	private String ucmBlockStatus;

	// for setting focus after adding a new row
	private String addStatus;

	// for View ULD check
	private String isUldViewed;

	// for disabling Add/Delete link after send
	private String isUcmSent;
	//Added by a-7359 as part of ICRD-192413 starts
	private String[] uldSource;
	private String[]  uldStatus ;
	//Added by a-7359 as part of ICRD-192413 ends
	/**
	 * 
	 * 	Method		:	UCMINOUTForm.getUldSource
	 *	Added by 	:	A-7359 on 24-Aug-2017
	 * 	Used for 	:	ICRD-192413
	 *	Parameters	:	@return 
	 *	Return type	: 	String[]
	 */
	public String[] getUldSource() {
		return uldSource;
	}
    /**
     * 
     * 	Method		:	UCMINOUTForm.setUldSource
     *	Added by 	:	A-7359 on 24-Aug-2017
     * 	Used for 	:   ICRD-192413
     *	Parameters	:	@param uldSource 
     *	Return type	: 	void
     */
	public void setUldSource(String[] uldSource) {
		this.uldSource = uldSource;
	}
	/**
	 * 
	 * 	Method		:	UCMINOUTForm.getUldStatus
	 *	Added by 	:	A-7359 on 24-Aug-2017
	 * 	Used for 	:	ICRD-192413
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String[] getUldStatus() {
		return uldStatus;
	}
	/**
	 * 
	 * 	Method		:	UCMINOUTForm.setUldStatus
	 *	Added by 	:	A-7359 on 24-Aug-2017
	 * 	Used for 	:	ICRD-192413
	 *	Parameters	:	@param uldStatus 
	 *	Return type	: 	void
	 */
	public void setUldStatus(String[] uldStatus) {
		this.uldStatus = uldStatus;
	}
	private String[] hiddenOpFlag;

	/**
	 * @return Returns the hiddenOpFlag.
	 */
	public String[] getHiddenOpFlag() {
		return hiddenOpFlag;
	}

	/**
	 * @param hiddenOpFlag
	 *            The hiddenOpFlag to set.
	 */
	public void setHiddenOpFlag(String[] hiddenOpFlag) {
		this.hiddenOpFlag = hiddenOpFlag;
	}

	/**
	 * 
	 * @return
	 */
	public String getAddStatus() {
		return addStatus;
	}

	/**
	 * 
	 * @param addStatus
	 */
	public void setAddStatus(String addStatus) {
		this.addStatus = addStatus;
	}

	/**
	 * 
	 * @return ucmBlockStatus
	 */
	public String getUcmBlockStatus() {
		return ucmBlockStatus;
	}

	/**
	 * 
	 * @param ucmBlockStatus
	 */
	public void setUcmBlockStatus(String ucmBlockStatus) {
		this.ucmBlockStatus = ucmBlockStatus;
	}

	/**
	 * 
	 * @return
	 */
	public String getErrorDesc() {
		return errorDesc;
	}

	/**
	 * 
	 * @param errorDesc
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getErrorCodes() {
		return errorCodes;
	}

	/**
	 * 
	 * @param errorCodes
	 */
	public void setErrorCodes(String[] errorCodes) {
		this.errorCodes = errorCodes;
	}

	/**
	 * 
	 * @return
	 */
	public String getOrgDestStatus() {
		return orgDestStatus;
	}

	/**
	 * 
	 * @param orgDestStatus
	 */
	public void setOrgDestStatus(String orgDestStatus) {
		this.orgDestStatus = orgDestStatus;
	}

	/**
	 * 
	 * @return
	 */
	public String getMessageTypeStatus() {
		return messageTypeStatus;
	}

	/**
	 * 
	 * @param messageTypeStatus
	 */
	public void setMessageTypeStatus(String messageTypeStatus) {
		this.messageTypeStatus = messageTypeStatus;
	}

	/**
	 * 
	 * @return
	 */
	public String getUcmVOStatus() {
		return ucmVOStatus;
	}

	/**
	 * 
	 * @return
	 */
	public String getPouStatus() {
		return pouStatus;
	}

	/**
	 * 
	 * @param pouStatus
	 */
	public void setPouStatus(String pouStatus) {
		this.pouStatus = pouStatus;
	}

	/**
	 * 
	 * @param ucmVOStatus
	 */
	public void setUcmVOStatus(String ucmVOStatus) {
		this.ucmVOStatus = ucmVOStatus;
	}

	/**
	 * 
	 * @return
	 */
	public String getOutConfirmStatus() {
		return outConfirmStatus;
	}

	/**
	 * 
	 * @param outConfirmStatus
	 */
	public void setOutConfirmStatus(String outConfirmStatus) {
		this.outConfirmStatus = outConfirmStatus;
	}

	/**
	 * 
	 * @return
	 */
	public String getLinkStatus() {
		return linkStatus;
	}

	/**
	 * 
	 * @param linkStatus
	 */
	public void setLinkStatus(String linkStatus) {
		this.linkStatus = linkStatus;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getOperationFlag() {
		return operationFlag;
	}

	/**
	 * 
	 * @param operationFlag
	 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getContentInd() {
		return contentInd;
	}

	/**
	 * 
	 * @param contentInd
	 */
	public void setContentInd(String[] contentInd) {
		this.contentInd = contentInd;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getUldNumbers() {
		return uldNumbers;
	}

	/**
	 * 
	 * @param uldNumbers
	 */
	public void setUldNumbers(String[] uldNumbers) {
		this.uldNumbers = uldNumbers;
	}

	/**
	 * 
	 * @return
	 */
	public String getViewUldStatus() {
		return viewUldStatus;
	}

	/**
	 * 
	 * @param viewUldStatus
	 */
	public void setViewUldStatus(String viewUldStatus) {
		this.viewUldStatus = viewUldStatus;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getDamageCodes() {
		return damageCodes;
	}

	/**
	 * 
	 * @param damageCodes
	 */
	public void setDamageCodes(String[] damageCodes) {
		this.damageCodes = damageCodes;
	}

	/**
	 * 
	 * @return
	 */
	public String getDamagedStatus() {
		return damagedStatus;
	}

	/**
	 * 
	 * @param damagedStatus
	 */
	public void setDamagedStatus(String damagedStatus) {
		this.damagedStatus = damagedStatus;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getPou() {
		return pou;
	}

	/**
	 * 
	 * @param pou
	 */
	public void setPou(String[] pou) {
		this.pou = pou;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getSelectedRows() {
		return selectedRows;
	}

	/**
	 * 
	 * @param selectedRows
	 */
	public void setSelectedRows(String[] selectedRows) {
		this.selectedRows = selectedRows;
	}

	/**
	 * 
	 * @return
	 */
	public String getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * 
	 * @param arrivalTime
	 */
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/**
	 * 
	 * @return
	 */
	public String getDepartureTime() {
		return departureTime;
	}

	/**
	 * 
	 * @param departureTime
	 */
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	/**
	 * 
	 * @return
	 */
	public String getDuplicateFlightStatus() {
		return duplicateFlightStatus;
	}

	/**
	 * 
	 * @param duplicateFlightStatus
	 */
	public void setDuplicateFlightStatus(String duplicateFlightStatus) {
		this.duplicateFlightStatus = duplicateFlightStatus;
	}

	/**
	 * 
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * 
	 * @param bundle
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * 
	 * @return
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * 
	 * @param carrierCode
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * 
	 * @return
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * 
	 * @param destination
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * 
	 * @return
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * 
	 * @param flightDate
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * 
	 * @return
	 */
	public String getFlightNo() {
		return flightNo;
	}

	/**
	 * 
	 * @param flightNo
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	/**
	 * 
	 * @return
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * 
	 * @param origin
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * 
	 * @return
	 */
	public String getRoute() {
		return route;
	}

	/**
	 * 
	 * @param route
	 */
	public void setRoute(String route) {
		this.route = route;
	}

	/**
	 * 
	 * @return
	 */
	public String getUcmIn() {
		return ucmIn;
	}

	/**
	 * 
	 * @param ucmIn
	 */
	public void setUcmIn(String ucmIn) {
		this.ucmIn = ucmIn;
	}

	/**
	 * 
	 * @return
	 */
	public String getUcmOut() {
		return ucmOut;
	}

	/**
	 * 
	 * @param ucmOut
	 */
	public void setUcmOut(String ucmOut) {
		this.ucmOut = ucmOut;
	}

	/**
	 * Method to return the product the screen is associated with
	 * 
	 * @return String
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * Method to return the sub product the screen is associated with
	 * 
	 * @return String
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	/**
	 * Method to return the id the screen is associated with
	 * 
	 * @return String
	 */
	public String getScreenId() {
		return SCREENID;
	}

	public String getIsUldViewed() {
		return isUldViewed;
	}

	public void setIsUldViewed(String isUldViewed) {
		this.isUldViewed = isUldViewed;
	}

	/**
	 * @return the isUcmSent
	 */
	public String getIsUcmSent() {
		return isUcmSent;
	}

	/**
	 * @param isUcmSent the isUcmSent to set
	 */
	public void setIsUcmSent(String isUcmSent) {
		this.isUcmSent = isUcmSent;
	}

}
