/**
 *  ReassignDispatchForm Created on January 10, 2012
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
public class ReassignDispatchForm extends ScreenModel{
	private static final String SCREEN_ID = "mailtracking.defaults.national.reassign";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "nationalreassignResources";


	private String flightCarrierCode;  
	private String flightNo;

	private String flightDate;

	private String pou;
	@MeasureAnnotation(mappedValue="weightMeasure",unitType="MWT")
	private String weight;
	private Measure weightMeasure;

	private String pieces;

	private String closeFlag;
	private String fromScreen;
	private String remarks;

	/**
	 * @return the PRODUCT_NAME
	 */
	public String getProduct() {

		return PRODUCT_NAME;
	}

	/**
	 * @return the SCREEN_ID
	 */
	public String getScreenId() {

		return SCREEN_ID;
	}

	/**
	 * @return the SUBPRODUCT_NAME
	 */
	public String getSubProduct() {

		return SUBPRODUCT_NAME;
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
	 * @return the weightMeasure
	 */
	public Measure getWeightMeasure() {
		return weightMeasure;
	}

	/**
	 * @param weightMeasure the weightMeasure to set
	 */
	public void setWeightMeasure(Measure weightMeasure) {
		this.weightMeasure = weightMeasure;
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
	public String getPou() {
		return pou;
	}


	/**
	 * @return the bundle
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @param pou the pou to set
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}




	/**
	 * @return the closeFlag
	 */
	public String getCloseFlag() {
		return closeFlag;
	}

	/**
	 * @param closeFlag the closeFlag to set
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}


	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * @param pieces the pieces to set
	 */
	public void setPieces(String pieces) {
		this.pieces = pieces;
	}

	/**
	 * @return the weight
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * @return the pieces
	 */
	public String getPieces() {
		return pieces;
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
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


}
