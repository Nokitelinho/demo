/**
 *  ReturnDispatchForm Created on January 10, 2012
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
public class ReturnDispatchForm extends ScreenModel{
	private static final String SCREEN_ID = "mailtracking.defaults.national.return";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "nationalreturnResources";
	private String returnCode;
	private String returnRemarks;
	@MeasureAnnotation(mappedValue="weightMeasure",unitType="MWT")
	private String weight;
	private Measure weightMeasure;
	
	private String pieces;
	private String fromScreen;
	private String closeFlag;

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
	 * @return the returnRemarks
	 */
	public String getReturnRemarks() {
		return returnRemarks;
	}


	/**
	 * @param returnRemarks the returnRemarks to set
	 */
	public void setReturnRemarks(String returnRemarks) {
		this.returnRemarks = returnRemarks;
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
	 * @return the bundle
	 */
	public String getBundle() {
		return BUNDLE;
	}


	/**
	 * @return the returnCode
	 */
	public String getReturnCode() {
		return returnCode;
	}


	/**
	 * @param returnCode the returnCode to set
	 */
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
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

}
