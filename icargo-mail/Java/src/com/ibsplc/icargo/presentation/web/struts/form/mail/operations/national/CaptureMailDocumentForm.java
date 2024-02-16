/*
 ConsignmentForm.java Created on Jan 04, 2011
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.MeasureAnnotation;

/**
 * @author A-4826
 *
 */
public class CaptureMailDocumentForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.national.consignment";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "nationalconsignmentResources";

	
	private String conDocNo;
	private String paCode;
	private String direction;
	private String conDate;
	private String type;
	private String mailCategory;
	private String origin;
	private String destination;
	
	
	private String[] selectRoute;
	private String flightNo;
	private String[] flightCarrierCode;
	private String[] flightNumber;
	private String[] flightDate;
	private String[] depDate;
	private String[] pou;
	private String[] pol;
	private String[] noOfPcs;
	@MeasureAnnotation(mappedValue="weightMeasure",unitType="MWT")
	private String[] weight;
	private Measure[] weightMeasure;
	
	private String[] operationFlag;
	private String[] invalidFlightFlag;
	
	private String[] acceptanceOperationFlag;
	private String[] acceptanceFlightNumber;
	private String[] acceptanceCarrierCode;
	private String[] acceptanceFlightDate;
	private String[] acceptancePieces;
	@MeasureAnnotation(mappedValue="acceptanceWeightMeasure",unitType="MWT")
	private String[] acceptanceWeight;
	private Measure[] acceptanceWeightMeasure;
	private String[] acceptancePol;
	private String[] acceptancePou;
	private String[] selectAcceptance;
	private String[] invalidAcceptanceFlightFlag;
	
	private String consignmentOriginFlag;
	private String dataFlag;
	private String screenLoadFlag;
	private String remarks;
	//Added as part of bug-fix -icrd-12637 by A-4810
	private String noconDocNo;
	private String noError;
	//Added as part of bug-fix -icrd-13564 by A-4810
	private String[] rowId; 
	private String popupFlag;
	private String fromScreen;
	private String[] rtgRemarks;
	private String[] accpRemarks;
	//Added by A-4810 for icrd-15420
	private String validateAcceptance;
	private String deleteFlag;
	
	
	
	
	
	
	public String[] getInvalidFlightFlag() {
		return invalidFlightFlag;
	}

	public void setInvalidFlightFlag(String[] invalidFlightFlag) {
		this.invalidFlightFlag = invalidFlightFlag;
	}

	/**
	 * @return the weightMeasure
	 */
	public Measure[] getWeightMeasure() {
		return weightMeasure;
	}

	/**
	 * @param weightMeasure the weightMeasure to set
	 */
	public void setWeightMeasure(Measure[] weightMeasure) {
		this.weightMeasure = weightMeasure;
	}

	/**
	 * @return the acceptanceWeightMeasure
	 */
	public Measure[] getAcceptanceWeightMeasure() {
		return acceptanceWeightMeasure;
	}

	/**
	 * @param acceptanceWeightMeasure the acceptanceWeightMeasure to set
	 */
	public void setAcceptanceWeightMeasure(Measure[] acceptanceWeightMeasure) {
		this.acceptanceWeightMeasure = acceptanceWeightMeasure;
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
	public String getConDocNo() {
		return conDocNo;
	}
	public void setConDocNo(String conDocNo) {
		this.conDocNo = conDocNo;
	}
	public String getPaCode() {
		return paCode;
	}
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getConDate() {
		return conDate;
	}
	public void setConDate(String conDate) {
		this.conDate = conDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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

	public String[] getSelectRoute() {
		return selectRoute;
	}
	public void setSelectRoute(String[] selectRoute) {
		this.selectRoute = selectRoute;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String[] getFlightCarrierCode() {
		return flightCarrierCode;
	}
	public void setFlightCarrierCode(String[] flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}
	public String[] getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String[] flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String[] getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(String[] flightDate) {
		this.flightDate = flightDate;
	}
	public String[] getDepDate() {
		return depDate;
	}
	public void setDepDate(String[] depDate) {
		this.depDate = depDate;
	}
	public String[] getPou() {
		return pou;
	}
	public void setPou(String[] pou) {
		this.pou = pou;
	}
	public String[] getPol() {
		return pol;
	}
	public void setPol(String[] pol) {
		this.pol = pol;
	}
	public String[] getNoOfPcs() {
		return noOfPcs;
	}
	public void setNoOfPcs(String[] noOfPcs) {
		this.noOfPcs = noOfPcs;
	}
	
	

	public String[] getOperationFlag() {
		return operationFlag;
	}

	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}

	public String[] getWeight() {
		return weight;
	}

	public void setWeight(String[] weight) {
		this.weight = weight;
	}

	public String[] getAcceptanceFlightNumber() {
		return acceptanceFlightNumber;
	}

	public void setAcceptanceFlightNumber(String[] acceptanceFlightNumber) {
		this.acceptanceFlightNumber = acceptanceFlightNumber;
	}

	public String[] getAcceptanceCarrierCode() {
		return acceptanceCarrierCode;
	}

	public void setAcceptanceCarrierCode(String[] acceptanceCarrierCode) {
		this.acceptanceCarrierCode = acceptanceCarrierCode;
	}

	public String[] getAcceptanceFlightDate() {
		return acceptanceFlightDate;
	}

	public void setAcceptanceFlightDate(String[] acceptanceFlightDate) {
		this.acceptanceFlightDate = acceptanceFlightDate;
	}

	public String[] getAcceptancePieces() {
		return acceptancePieces;
	}

	public void setAcceptancePieces(String[] acceptancePieces) {
		this.acceptancePieces = acceptancePieces;
	}

	public String[] getAcceptanceWeight() {
		return acceptanceWeight;
	}

	public void setAcceptanceWeight(String[] acceptanceWeight) {
		this.acceptanceWeight = acceptanceWeight;
	}

	public String[] getAcceptancePol() {
		return acceptancePol;
	}

	public void setAcceptancePol(String[] acceptancePol) {
		this.acceptancePol = acceptancePol;
	}

	public String[] getAcceptancePou() {
		return acceptancePou;
	}

	public void setAcceptancePou(String[] acceptancePou) {
		this.acceptancePou = acceptancePou;
	}

	public String[] getAcceptanceOperationFlag() {
		return acceptanceOperationFlag;
	}

	public void setAcceptanceOperationFlag(String[] acceptanceOperationFlag) {
		this.acceptanceOperationFlag = acceptanceOperationFlag;
	}

	public String[] getSelectAcceptance() {
		return selectAcceptance;
	}

	public void setSelectAcceptance(String[] selectAcceptance) {
		this.selectAcceptance = selectAcceptance;
	}

	public String[] getInvalidAcceptanceFlightFlag() {
		return invalidAcceptanceFlightFlag;
	}

	public void setInvalidAcceptanceFlightFlag(String[] invalidAcceptanceFlightFlag) {
		this.invalidAcceptanceFlightFlag = invalidAcceptanceFlightFlag;
	}

	public String getConsignmentOriginFlag() {
		return consignmentOriginFlag;
	}

	public void setConsignmentOriginFlag(String consignmentOriginFlag) {
		this.consignmentOriginFlag = consignmentOriginFlag;
	}

	

	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getScreenLoadFlag() {
		return screenLoadFlag;
	}

	public void setScreenLoadFlag(String screenLoadFlag) {
		this.screenLoadFlag = screenLoadFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getNoconDocNo() {
		return noconDocNo;
	}
	public void setNoconDocNo(String noconDocNo) {
		this.noconDocNo = noconDocNo;
	}
	public String getNoError() {
		return noError;
	}
	public void setNoError(String noError) {
		this.noError = noError;
	}

	

	/**
	 * @return the popupFlag
	 */
	public String getPopupFlag() {
		return popupFlag;
	}

	/**
	 * @param popupFlag the popupFlag to set
	 */
	public void setPopupFlag(String popupFlag) {
		this.popupFlag = popupFlag;
	}

	/**
	 * @return the rowId
	 */
	public String[] getRowId() {
		return rowId;
	}

	/**
	 * @param rowId the rowId to set
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}

	/**
	 * @return the fromScreen
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen the fromScreen to set
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return the rtgRemarks
	 */
	public String[] getRtgRemarks() {
		return rtgRemarks;
	}

	/**
	 * @param rtgRemarks the rtgRemarks to set
	 */
	public void setRtgRemarks(String[] rtgRemarks) {
		this.rtgRemarks = rtgRemarks;
	}

	/**
	 * @return the accpRemarks
	 */
	public String[] getAccpRemarks() {
		return accpRemarks;
	}

	/**
	 * @param accpRemarks the accpRemarks to set
	 */
	public void setAccpRemarks(String[] accpRemarks) {
		this.accpRemarks = accpRemarks;
	}

	/**
	 * @return the validateAcceptance
	 */
	public String getValidateAcceptance() {
		return validateAcceptance;
	}

	/**
	 * @param validateAcceptance the validateAcceptance to set
	 */
	public void setValidateAcceptance(String validateAcceptance) {
		this.validateAcceptance = validateAcceptance;
	}

	/**
	 * @return the deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * @param deleteFlag the deleteFlag to set
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	

	
	

	




}
