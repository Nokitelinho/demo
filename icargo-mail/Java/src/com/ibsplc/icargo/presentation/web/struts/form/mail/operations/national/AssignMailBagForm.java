/**
 * AssignMailBagForm Created on January 10, 2012
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
 * @author a-4823
 *
 */
public class AssignMailBagForm extends ScreenModel{

	private static final String SCREEN_ID = "mailtracking.defaults.national.assignmailbag";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "nationalassignMailbagResources";


	private String flightCarrierCode;  
	private String flightNo;

	private String flightDate;

	private String[] pou;
	@MeasureAnnotation(mappedValue="weightMeasure",unitType="MWT")
	private String weight;
	private Measure weightMeasure;

	private String pieces;


	private String departurePort;
	private String[] consignmentDocNo;
	private String[] dsnNo; 
	private String[] origin;
	private String[] destination;
	private String category;
	private String returnRemarksCode;
	private String fromScreen;
	private String[] operationFlag;	
	private String[] rowId; 	
	private String[] mailBagStatus;	
	private String selectedMailBag;
	private String operationalStatus="";
	private String selectedRow;
	private String printType;

	private String popupFlag;
	private String statusFlag;
	private String duplicateFlightStatus;
	private String  dataFlag;





	/**
	 * @return the flightCarrierCode
	 */
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}


	/**
	 * @param flightCarrierCode the flightCarrierCode to set
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}


	/**
	 * @return the flightNo
	 */
	public String getFlightNo() {
		return flightNo;
	}


	/**
	 * @param flightNo the flightNo to set
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
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
	 * @return the pou
	 */
	public String[] getPou() {
		return pou;
	}


	/**
	 * @param pou the pou to set
	 */
	public void setPou(String[] pou) {
		this.pou = pou;
	}



	/**
	 * @return the departurePort
	 */
	public String getDeparturePort() {
		return departurePort;
	}


	/**
	 * @param departurePort the departurePort to set
	 */
	public void setDeparturePort(String departurePort) {
		this.departurePort = departurePort;
	}


	/**
	 * @return the consignmentDocNo
	 */
	public String[] getConsignmentDocNo() {
		return consignmentDocNo;
	}


	/**
	 * @param consignmentDocNo the consignmentDocNo to set
	 */
	public void setConsignmentDocNo(String[] consignmentDocNo) {
		this.consignmentDocNo = consignmentDocNo;
	}


	/**
	 * @return the dsnNo
	 */
	public String[] getDsnNo() {
		return dsnNo;
	}


	/**
	 * @param dsnNo the dsnNo to set
	 */
	public void setDsnNo(String[] dsnNo) {
		this.dsnNo = dsnNo;
	}


	/**
	 * @return the origin
	 */
	public String[] getOrigin() {
		return origin;
	}


	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String[] origin) {
		this.origin = origin;
	}


	/**
	 * @return the destination
	 */
	public String[] getDestination() {
		return destination;
	}


	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String[] destination) {
		this.destination = destination;
	}





	public String getProduct() {

		return PRODUCT_NAME;
	}


	public String getScreenId() {

		return SCREEN_ID;
	}


	public String getSubProduct() {

		return SUBPRODUCT_NAME;
	}


	/**
	 * @return the bundle
	 */
	public String getBundle() {
		return BUNDLE;
	}


	/**
	 * @return the returnRemarksCode
	 */
	public String getReturnRemarksCode() {
		return returnRemarksCode;
	}


	/**
	 * @param returnRemarksCode the returnRemarksCode to set
	 */
	public void setReturnRemarksCode(String returnRemarksCode) {
		this.returnRemarksCode = returnRemarksCode;
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
	 * @return the operationFlag
	 */
	public String[] getOperationFlag() {
		return operationFlag;
	}


	/**
	 * @param operationFlag the operationFlag to set
	 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
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
	 * @return the mailBagStatus
	 */
	public String[] getMailBagStatus() {
		return mailBagStatus;
	}


	/**
	 * @param mailBagStatus the mailBagStatus to set
	 */
	public void setMailBagStatus(String[] mailBagStatus) {
		this.mailBagStatus = mailBagStatus;
	}


	/**
	 * @return the selectedMailBag
	 */
	public String getSelectedMailBag() {
		return selectedMailBag;
	}


	/**
	 * @param selectedMailBag the selectedMailBag to set
	 */
	public void setSelectedMailBag(String selectedMailBag) {
		this.selectedMailBag = selectedMailBag;
	}


	/**
	 * @return the operationalStatus
	 */
	public String getOperationalStatus() {
		return operationalStatus;
	}


	/**
	 * @param operationalStatus the operationalStatus to set
	 */
	public void setOperationalStatus(String operationalStatus) {
		this.operationalStatus = operationalStatus;
	}


	/**
	 * @return the selectedRow
	 */
	public String getSelectedRow() {
		return selectedRow;
	}


	/**
	 * @param selectedRow the selectedRow to set
	 */
	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}


	/**
	 * @return the printType
	 */
	public String getPrintType() {
		return printType;
	}


	/**
	 * @param printType the printType to set
	 */
	public void setPrintType(String printType) {
		this.printType = printType;
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
	 * @return the weight
	 */
	public String getWeight() {
		return weight;
	}


	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}


	/**
	 * @return the pieces
	 */
	public String getPieces() {
		return pieces;
	}


	/**
	 * @param pieces the pieces to set
	 */
	public void setPieces(String pieces) {
		this.pieces = pieces;
	}


	/**
	 * @return the statusFlag
	 */
	public String getStatusFlag() {
		return statusFlag;
	}


	/**
	 * @param statusFlag the statusFlag to set
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}


	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}


	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}


	/**
	 * @return the duplicateFlightStatus
	 */
	public String getDuplicateFlightStatus() {
		return duplicateFlightStatus;
	}


	/**
	 * @param duplicateFlightStatus the duplicateFlightStatus to set
	 */
	public void setDuplicateFlightStatus(String duplicateFlightStatus) {
		this.duplicateFlightStatus = duplicateFlightStatus;
	}


	/**
	 * @return the dataFlag
	 */
	public String getDataFlag() {
		return dataFlag;
	}


	/**
	 * @param dataFlag the dataFlag to set
	 */
	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}







}
