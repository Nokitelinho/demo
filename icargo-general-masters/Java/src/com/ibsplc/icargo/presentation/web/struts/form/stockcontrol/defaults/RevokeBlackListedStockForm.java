/*
 * RevokeBlackListedStockForm.java Created on Sep 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 *
 * @author A-1927
 *
 */
public class RevokeBlackListedStockForm extends ScreenModel {


	private String docType;

	private String subType;

	private String rangeFrom;

	private String rangeTo;

	private String remarks;

	private String checkbox;
	
	private String revokeSuccessful;

	// The key attribute specified in struts_config.xml file.
	private static final String BUNDLE = "revokeblacklistedstockresources";

	private String bundle;


	/**
	* @return Returns the bundle.
	*/
	public String getBundle() {
		return BUNDLE;
	}
	/**
	* @param bundle The bundle to set.
	*/
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * @return Returns the rangeFrom.
	 */
	public String getRangeFrom(){
		return rangeFrom;
	}

	/**
	 * @param rangeFrom The rangeFrom to set.
	 */
	public void setRangeFrom(String rangeFrom){
		this.rangeFrom=rangeFrom;
	}

	/**
	 * @return Returns the rangeTo.
	 */
	public String getRangeTo(){
		return rangeTo;
	}

	/**
	 * @param rangeTo The rangeTo to set.
	 */
	public void setRangeTo(String rangeTo){
		this.rangeTo=rangeTo;
	}

	/**
	 * @return Returns the docType.
	 */
	public String getDocType(){
		return docType;
	}

	/**
	 * @param docType The docType to set.
	 */
	public void setDocType(String docType){
		this.docType=docType;
	}

	/**
	 * @return Returns the subType.
	 */
	public String getSubType(){
		return subType;
	}

	/**
	 * @param subType The subType to set.
	 */
	public void setSubType(String subType){
		this.subType=subType;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks(){
		return remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks){
		this.remarks=remarks;
	}

	/**
	 * @return Returns the checkbox.
	 */
	public String getCheckbox(){
		return checkbox;
	}

	/**
	 * @param checkbox The checkbox to set.
	 */
	public void setCheckbox(String checkbox){
		this.checkbox=checkbox;
	}

	/**
	 * @return Returns the screenId.
	 */
	public String getScreenId(){
		return "stockcontrol.defaults.revokeblacklistedstock";
	}

	/**
	 * @return Returns the product.
	 */
	public String getProduct(){
		return "stockcontrol";
	}

	/**
	 * @return Returns the subProduct.
	 */
	public String getSubProduct(){
		return "defaults";
	}
	/**
	 * 
	 * @return
	 */
	public String getRevokeSuccessful() {
		return revokeSuccessful;
	}
	/**
	 * 
	 * @param revokeSuccessful
	 */
	public void setRevokeSuccessful(String revokeSuccessful) {
		this.revokeSuccessful = revokeSuccessful;
	}


}
