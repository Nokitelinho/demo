/*
 * ResditGenerationForm.java Created on Oct 06, 2010
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
 * @author A-2122
 *
 */
public class ResditGenerationForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.resditgeneration";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "resditGenerationResources";

	
	
	private String resditType;
	private String flightNumber;
	private String flightCarrierCode;
	private String flightDate;
	private String eventLocation;
	private String eventDate;
	private String eventTime;
	private String returnReasonCode;
	private String offloadReasonCode;
	private String receivedFromCarrier;
	private String transferredToCarrier;
	private String uldNumber;
	private String[] originOE;
	private String[] destOE;
	private String[] category;
	private String[] subClass;
	private int[] year;
	private String[] dsn;
	private String[] rsn;
	private String[] hni;
	private String[] ri;
	
	@MeasureAnnotation(mappedValue="weightMeasure",unitType="MWT")
	private String[] weight;
	private Measure[] weightMeasure;

	
	private boolean isSelectAll;
	private String[] select;	
	private String[] selectedElements;	
	private String[] operationFlag; 	
	private String[] hiddenOperationFlag;
	private String duplicateFlightStatus;
	private String fromScreen;
	private String mailBagDetails;
	
	
	/**
	 * @return screenId
	 */
	public String getScreenId() {
		return SCREEN_ID;
	}

	/**
	 * @return product
	 */
	public String getProduct() {
		return PRODUCT_NAME;
	}

	/**
	 * @return subProduct
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
	 * @return the category
	 */
	public String[] getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String[] category) {
		this.category = category;
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
	 * @return the destOE
	 */
	public String[] getDestOE() {
		return destOE;
	}

	/**
	 * @param destOE the destOE to set
	 */
	public void setDestOE(String[] destOE) {
		this.destOE = destOE;
	}

	/**
	 * @return the dsn
	 */
	public String[] getDsn() {
		return dsn;
	}

	/**
	 * @param dsn the dsn to set
	 */
	public void setDsn(String[] dsn) {
		this.dsn = dsn;
	}

	/**
	 * @return the eventDate
	 */
	public String getEventDate() {
		return eventDate;
	}

	/**
	 * @param eventDate the eventDate to set
	 */
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	/**
	 * @return the eventLocation
	 */
	public String getEventLocation() {
		return eventLocation;
	}

	/**
	 * @param eventLocation the eventLocation to set
	 */
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	/**
	 * @return the eventTime
	 */
	public String getEventTime() {
		return eventTime;
	}

	/**
	 * @param eventTime the eventTime to set
	 */
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
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


	 * @return the hni
	 */
	public String[] getHni() {
		return hni;
	}

	/**
	 * @param hni the hni to set
	 */
	public void setHni(String[] hni) {
		this.hni = hni;
	}

	/**
	 * @return the offloadReasonCode
	 */
	public String getOffloadReasonCode() {
		return offloadReasonCode;
	}

	/**
	 * @param offloadReasonCode the offloadReasonCode to set
	 */
	public void setOffloadReasonCode(String offloadReasonCode) {
		this.offloadReasonCode = offloadReasonCode;
	}

	/**
	 * @return the originOE
	 */
	public String[] getOriginOE() {
		return originOE;
	}

	/**
	 * @param originOE the originOE to set
	 */
	public void setOriginOE(String[] originOE) {
		this.originOE = originOE;
	}

	/**
	 * @return the receivedFromCarrier
	 */
	public String getReceivedFromCarrier() {
		return receivedFromCarrier;
	}

	/**
	 * @param receivedFromCarrier the receivedFromCarrier to set
	 */
	public void setReceivedFromCarrier(String receivedFromCarrier) {
		this.receivedFromCarrier = receivedFromCarrier;
	}

	/**
	 * @return the resditType
	 */
	public String getResditType() {
		return resditType;
	}

	/**
	 * @param resditType the resditType to set
	 */
	public void setResditType(String resditType) {
		this.resditType = resditType;
	}

	/**
	 * @return the returnReasonCode
	 */
	public String getReturnReasonCode() {
		return returnReasonCode;
	}

	/**
	 * @param returnReasonCode the returnReasonCode to set
	 */
	public void setReturnReasonCode(String returnReasonCode) {
		this.returnReasonCode = returnReasonCode;
	}

	/**
	 * @return the ri
	 */
	public String[] getRi() {
		return ri;
	}

	/**
	 * @param ri the ri to set
	 */
	public void setRi(String[] ri) {
		this.ri = ri;
	}

	/**
	 * @return the rsn
	 */
	public String[] getRsn() {
		return rsn;
	}

	/**
	 * @param rsn the rsn to set
	 */
	public void setRsn(String[] rsn) {
		this.rsn = rsn;
	}

	/**
	 * @return the subClass
	 */
	public String[] getSubClass() {
		return subClass;
	}

	/**
	 * @param subClass the subClass to set
	 */
	public void setSubClass(String[] subClass) {
		this.subClass = subClass;
	}

	/**
	 * @return the transferredToCarrier
	 */
	public String getTransferredToCarrier() {
		return transferredToCarrier;
	}

	/**
	 * @param transferredToCarrier the transferredToCarrier to set
	 */
	public void setTransferredToCarrier(String transferredToCarrier) {
		this.transferredToCarrier = transferredToCarrier;
	}

	/**
	 * @return the uldNumber
	 */
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber the uldNumber to set
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return the weight
	 */
	public String[] getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String[] weight) {
		this.weight = weight;
	}

	/**
	 * @return the year
	 */
	public int[] getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int[] year) {
		this.year = year;
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
	 * @return the selectedElements
	 */
	public String[] getSelectedElements() {
		return selectedElements;
	}

	/**
	 * @param selectedElements the selectedElements to set
	 */
	public void setSelectedElements(String[] selectedElements) {
		this.selectedElements = selectedElements;
	}

	/**
	 * @return the hiddenOperationFlag
	 */
	public String[] getHiddenOperationFlag() {
		return hiddenOperationFlag;
	}

	/**
	 * @param hiddenOperationFlag the hiddenOperationFlag to set
	 */
	public void setHiddenOperationFlag(String[] hiddenOperationFlag) {
		this.hiddenOperationFlag = hiddenOperationFlag;
	}
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
	 * @return the isSelectAll
	 */
	public boolean isSelectAll() {
		return isSelectAll;
	}
	/**
	 * @param isSelectAll the isSelectAll to set
	 */
	public void setSelectAll(boolean isSelectAll) {
		this.isSelectAll = isSelectAll;
	}
	/**
	 * @return the select
	 */
	public String[] getSelect() {
		return select;
	}
	/**
	 * @param select the select to set
	 */
	public void setSelect(String[] select) {
		this.select = select;
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
	 * @return the mailBagDetails
	 */
	public String getMailBagDetails() {
		return mailBagDetails;
	}

	/**
	 * @param mailBagDetails the mailBagDetails to set
	 */
	public void setMailBagDetails(String mailBagDetails) {
		this.mailBagDetails = mailBagDetails;
	}

	/**
	 * @return the mailBagDetails
	 */
	
}
