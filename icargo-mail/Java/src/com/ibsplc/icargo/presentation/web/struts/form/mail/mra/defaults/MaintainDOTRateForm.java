/*
 * MaintainDOTRateForm.java Created on Aug 02,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2408
 *
 */
public class MaintainDOTRateForm extends ScreenModel {

    private static final String BUNDLE = "maintaindotrate";

	private static final String PRODUCT = "mail";
	
	private static final String SUBPRODUCT = "mra";
	
	private static final String SCREENID = "mailtracking.mra.defaults.maintaindotrate";
	
	private String sectorOriginCode;
	
	private String sectorDestinationCode;
	
	private String greatCircleMiles;
	
	private String rateCodeFilter;
	
	private String[] regionCode;
	
	private String[] lineHaulRate;
	
	private String[] terminalHandlingRate;
	
	private String[] dotRate;
	
	private String[] originCode;
	
	private String[] destinationCode;
	
	private String[] circleMiles;
	
	private String[] rateCode;
	
	private String[] rateDescription;
	
	private String[] check;
	
	private String screenFlag;
	
	private String[] operationFlag;
	/**
	 * @return Returns the SCREENID.
	 */
    public String getScreenId() {
        return SCREENID;
    }

    /**
	 * @return Returns the PRODUCT.
	 */
    public String getProduct() {
        return PRODUCT;
    }
    /**
	 * @return Returns the SUBPRODUCT.
	 */
    public String getSubProduct() {
        return SUBPRODUCT;
    }
    /**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return Returns the greatCircleMiles.
	 */
	public String getGreatCircleMiles() {
		return greatCircleMiles;
	}

	/**
	 * @param greatCircleMiles The greatCircleMiles to set.
	 */
	public void setGreatCircleMiles(String greatCircleMiles) {
		this.greatCircleMiles = greatCircleMiles;
	}

	/**
	



	/**
	 * @return Returns the sectorDestinationCode.
	 */
	public String getSectorDestinationCode() {
		return sectorDestinationCode;
	}

	/**
	 * @param sectorDestinationCode The sectorDestinationCode to set.
	 */
	public void setSectorDestinationCode(String sectorDestinationCode) {
		this.sectorDestinationCode = sectorDestinationCode;
	}

	/**
	 * @return Returns the sectorOriginCode.
	 */
	public String getSectorOriginCode() {
		return sectorOriginCode;
	}

	/**
	 * @param sectorOriginCode The sectorOriginCode to set.
	 */
	public void setSectorOriginCode(String sectorOriginCode) {
		this.sectorOriginCode = sectorOriginCode;
	}

	/**
	 * @return Returns the check.
	 */
	public String[] getCheck() {
		return check;
	}

	/**
	 * @param check The check to set.
	 */
	public void setCheck(String[] check) {
		this.check = check;
	}

	/**
	 * @return Returns the circleMiles.
	 */
	public String[] getCircleMiles() {
		return circleMiles;
	}

	/**
	 * @param circleMiles The circleMiles to set.
	 */
	public void setCircleMiles(String[] circleMiles) {
		this.circleMiles = circleMiles;
	}

	/**
	 * @return Returns the destinationCode.
	 */
	public String[] getDestinationCode() {
		return destinationCode;
	}

	/**
	 * @param destinationCode The destinationCode to set.
	 */
	public void setDestinationCode(String[] destinationCode) {
		this.destinationCode = destinationCode;
	}

	/**
	 * @return Returns the lineHaulRate.
	 */
	public String[] getLineHaulRate() {
		return lineHaulRate;
	}

	/**
	 * @param lineHaulRate The lineHaulRate to set.
	 */
	public void setLineHaulRate(String[] lineHaulRate) {
		this.lineHaulRate = lineHaulRate;
	}

	/**
	 * @return Returns the originCode.
	 */
	public String[] getOriginCode() {
		return originCode;
	}

	/**
	 * @param originCode The originCode to set.
	 */
	public void setOriginCode(String[] originCode) {
		this.originCode = originCode;
	}

	

	/**
	 * @return Returns the dotRate.
	 */
	public String[] getDotRate() {
		return dotRate;
	}

	/**
	 * @param dotRate The dotRate to set.
	 */
	public void setDotRate(String[] dotRate) {
		this.dotRate = dotRate;
	}

	/**
	 * @return Returns the rateDescription.
	 */
	public String[] getRateDescription() {
		return rateDescription;
	}

	/**
	 * @param rateDescription The rateDescription to set.
	 */
	public void setRateDescription(String[] rateDescription) {
		this.rateDescription = rateDescription;
	}

	/**
	 * @return Returns the rateCode.
	 */
	public String[] getRateCode() {
		return rateCode;
	}

	/**
	 * @param rateCode The rateCode to set.
	 */
	public void setRateCode(String[] rateCode) {
		this.rateCode = rateCode;
	}

	/**
	 * @return Returns the rateCodeFilter.
	 */
	public String getRateCodeFilter() {
		return rateCodeFilter;
	}

	/**
	 * @param rateCodeFilter The rateCodeFilter to set.
	 */
	public void setRateCodeFilter(String rateCodeFilter) {
		this.rateCodeFilter = rateCodeFilter;
	}

	/**
	 * @return Returns the regionCode.
	 */
	public String[] getRegionCode() {
		return regionCode;
	}

	/**
	 * @param regionCode The regionCode to set.
	 */
	public void setRegionCode(String[] regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * @return Returns the terminalHandlingRate.
	 */
	public String[] getTerminalHandlingRate() {
		return terminalHandlingRate;
	}

	/**
	 * @param terminalHandlingRate The terminalHandlingRate to set.
	 */
	public void setTerminalHandlingRate(String[] terminalHandlingRate) {
		this.terminalHandlingRate = terminalHandlingRate;
	}

	/**
	 * @return Returns the screenFlag.
	 */
	public String getScreenFlag() {
		return screenFlag;
	}

	/**
	 * @param screenFlag The screenFlag to set.
	 */
	public void setScreenFlag(String screenFlag) {
		this.screenFlag = screenFlag;
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

	
	
	
}