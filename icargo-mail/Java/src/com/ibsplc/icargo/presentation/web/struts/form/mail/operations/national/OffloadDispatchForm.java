/**
 *  OffloadDispatchForm Created on January 10, 2012
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
public class OffloadDispatchForm extends ScreenModel{
	private static final String SCREEN_ID = "mailtracking.defaults.national.offload";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "nationaloffloadResources";
	
	@MeasureAnnotation(mappedValue="weightMeasure",unitType="MWT")
	private String weight;
	private Measure weightMeasure;
	
	private String pieces;
	private String reasonCode;
	private String offloadRemarks;
	private String closeFlag;
	private String fromScreen;
	
/**
 * 
 * @return weightMeasure
 */
	public Measure getWeightMeasure() {
		return weightMeasure;
	}

/**
 * 
 * @param weightMeasure
 */
	public void setWeightMeasure(Measure weightMeasure) {
		this.weightMeasure = weightMeasure;
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
	public  String getBundle() {
		return BUNDLE;
	}


	/**
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}


	/**
	 * @param reasonCode the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}


	/**
	 * @return the offloadRemarks
	 */
	public String getOffloadRemarks() {
		return offloadRemarks;
	}


	/**
	 * @param offloadRemarks the offloadRemarks to set
	 */
	public void setOffloadRemarks(String offloadRemarks) {
		this.offloadRemarks = offloadRemarks;
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

}
