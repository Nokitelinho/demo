/*
 * ULDAvailabilityForm.java Created on Mar 31, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-3278
 * 
 */
public class ULDAvailabilityForm extends ScreenModel {

	/**
	 * BUNDLE
	 */
	private static final String BUNDLE = "uldAvailabilityResources";

	/**
	 * PRODUCT
	 */
	private static final String PRODUCT = "uld";

	/**
	 * SUBPRODUCT
	 */
	private static final String SUBPRODUCT = "defaults";

	/**
	 * SCREENID
	 */
	private static final String SCREENID = "uld.defaults.uldavailability";

	private String companyCode;

	private String stationCode;

	private String uldTypeCode;

	private String partyType;

	private String partyCode;

	private String comboFlag;

	private String toPartyName;
	
	private String isPreview;

	private String bundle;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
	 */
	public String getScreenId() {
		return SCREENID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.ScreenData#getSubProduct()
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
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
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the partyType
	 */
	public String getPartyType() {
		return partyType;
	}

	/**
	 * @param partyType
	 *            the partyType to set
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	/**
	 * @return the partyCode
	 */
	public String getPartyCode() {
		return partyCode;
	}

	/**
	 * @param partyCode
	 *            the partyCode to set
	 */
	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}

	/**
	 * @return the stationCode
	 */
	public String getStationCode() {
		return stationCode;
	}

	/**
	 * @param stationCode
	 *            the stationCode to set
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	/**
	 * @return the uldTypeCode
	 */
	public String getUldTypeCode() {
		return uldTypeCode;
	}

	/**
	 * @param uldTypeCode
	 *            the uldTypeCode to set
	 */
	public void setUldTypeCode(String uldTypeCode) {
		this.uldTypeCode = uldTypeCode;
	}

	/**
	 * @return the comboFlag
	 */
	public String getComboFlag() {
		return comboFlag;
	}

	/**
	 * @param comboFlag
	 *            the comboFlag to set
	 */
	public void setComboFlag(String comboFlag) {
		this.comboFlag = comboFlag;
	}

	/**
	 * @return the toPartyName
	 */
	public String getToPartyName() {
		return toPartyName;
	}

	/**
	 * @param toPartyName
	 *            the toPartyName to set
	 */
	public void setToPartyName(String toPartyName) {
		this.toPartyName = toPartyName;
	}

	/**
	 * @return the isPreview
	 */
	public String getIsPreview() {
		return isPreview;
	}

	/**
	 * @param isPreview the isPreview to set
	 */
	public void setIsPreview(String isPreview) {
		this.isPreview = isPreview;
	}

}
