/*
 * FuelSurchargeForm.java Created on APR 23,2009
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;


import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2391
 *
 */
public class FuelSurchargeForm extends ScreenModel{

private static final String BUNDLE = "fuelSurchargebundle";

	//private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID =
		"mailtracking.mra.defaults.fuelsurcharge";

	private String[] operationFlag;
	
	private String gpaCode;
	private String gpaName;
	private String country;
	private String headChk;
	
	private String[] checkBoxes;
	private String[] rateCharge;
	private String[] values;
	private String[] currency;
	private String[] validFrom;
	private String[] validTo;
	private String[] seqNum;
	

	/**
	 * @return Returns the seqnum.
	 */
	public String[] getSeqNum() {
		return seqNum;
	}

	/**
	 * @param seqnum The seqnum to set.
	 */
	public void setSeqNum(String[] seqnum) {
		this.seqNum = seqnum;
	}

	/**
	 * @return Returns the checkBox.
	 */
	public String[] getCheckBoxes() {
		return checkBoxes;
	}

	/**
	 * @param checkBox The checkBox to set.
	 */
	public void setCheckBoxes(String[] checkBoxes) {
		this.checkBoxes = checkBoxes;
	}

	/**
	 * @return Returns the country.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country The country to set.
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return Returns the currency.
	 */
	public String[] getCurrency() {
		return currency;
	}

	/**
	 * @param currency The currency to set.
	 */
	public void setCurrency(String[] currency) {
		this.currency = currency;
	}

	/**
	 * @return Returns the gpaName.
	 */
	public String getGpaName() {
		return gpaName;
	}

	/**
	 * @param gpaName The gpaName to set.
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}

	/**
	 * @return Returns the headChk.
	 */
	public String getHeadChk() {
		return headChk;
	}

	/**
	 * @param headChk The headChk to set.
	 */
	public void setHeadChk(String headChk) {
		this.headChk = headChk;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String[] getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the rateCharge.
	 */
	public String[] getRateCharge() {
		return rateCharge;
	}

	/**
	 * @param rateCharge The rateCharge to set.
	 */
	public void setRateCharge(String[] rateCharge) {
		this.rateCharge = rateCharge;
	}

	/**
	 * @return Returns the validFrom.
	 */
	public String[] getValidFrom() {
		return validFrom;
	}

	/**
	 * @param validFrom The validFrom to set.
	 */
	public void setValidFrom(String[] validFrom) {
		this.validFrom = validFrom;
	}

	/**
	 * @return Returns the validTo.
	 */
	public String[] getValidTo() {
		return validTo;
	}

	/**
	 * @param validTo The validTo to set.
	 */
	public void setValidTo(String[] validTo) {
		this.validTo = validTo;
	}

	/**
	 * @return Returns the values.
	 */
	public String[] getValues() {
		return values;
	}

	/**
	 * @param values The values to set.
	 */
	public void setValues(String[] values) {
		this.values = values;
	}

	/**
	* @return Returns the gpaCode.
	*/
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	* @param gpaCode The gpaCode to set.
	*/
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	

	/**
	 *
	 */
	public String getScreenId() {
		// TODO Auto-generated method stub
		return SCREENID;
	}

	/**
	 *
	 */
	public String getProduct() {
		// TODO Auto-generated method stub
		return PRODUCT;
	}

	/**
	 *
	 */
	public String getSubProduct() {
		// TODO Auto-generated method stub
		return SUBPRODUCT;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}




	
}
