/*
 * HandledCarrierSetupForm.java Created on Dec 5, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc;

import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author A-2883
 *
 */
public class HandledCarrierSetupForm extends ScreenModel{
	
	private static final String PRODUCT = "uld";
	
	private static final String SUBPRODUCT = "defaults";

	private static final String BUNDLE = "carrierSetupResources";
	
	private String bundle;   
	
	private static final String SCREENID = "uld.defaults.misc.handledcarriersetup";
	
	
	
	
	private String statusFlag;
	
	private String selectAll;
	
	private String selectFlag;
	
	private String airlineCode;
	
	private String stationCode;
	
	
	private String[] templateStationCode;
	
	private String[] templateAirlineCode;
	
	private String[] templateAirlineName;
	
	private String[] templateOperationFlag;
	
	
	private String ajaxAirlineCode;
	
	private String errorStatusFlag;
	
	private String ajaxAirlineName;
	
	
	
	
	
	/**
	 * @return the ajaxAirlineName
	 */
	public String getAjaxAirlineName() {
		return ajaxAirlineName;
	}

	/**
	 * @param ajaxAirlineName the ajaxAirlineName to set
	 */
	public void setAjaxAirlineName(String ajaxAirlineName) {
		this.ajaxAirlineName = ajaxAirlineName;
	}

	/**
	 * @return the errorStatusFlag
	 */
	public String getErrorStatusFlag() {
		return errorStatusFlag;
	}

	/**
	 * @param errorStatusFlag the errorStatusFlag to set
	 */
	public void setErrorStatusFlag(String errorStatusFlag) {
		this.errorStatusFlag = errorStatusFlag;
	}

	/**
	 * @return the ajaxAirlineCode
	 */
	public String getAjaxAirlineCode() {
		return ajaxAirlineCode;
	}

	/**
	 * @param ajaxAirlineCode the ajaxAirlineCode to set
	 */
	public void setAjaxAirlineCode(String ajaxAirlineCode) {
		this.ajaxAirlineCode = ajaxAirlineCode;
	}

	/**
	 * @return Returns the templateAirlineCode.
	 */
	public String[] getTemplateAirlineCode() {
		return templateAirlineCode;
	}

	/**
	 * @param templateAirlineCode
	 */
	public void setTemplateAirlineCode(String[] templateAirlineCode) {
		this.templateAirlineCode = templateAirlineCode;
	}

	/**
	 * @return Returns the templateAirlineName.
	 */
	public String[] getTemplateAirlineName() {
		return templateAirlineName;
	}

	/**
	 * @param templateAirlineName
	 */
	public void setTemplateAirlineName(String[] templateAirlineName) {
		this.templateAirlineName = templateAirlineName;
	}

	
	/**
	 * @return Returns the templateOperationFlag.
	 */
	public String[] getTemplateOperationFlag() {
		return templateOperationFlag;
	}

	/**
	 * @param templateOperationFlag
	 */
	public void setTemplateOperationFlag(String[] templateOperationFlag) {
		this.templateOperationFlag = templateOperationFlag;
	}

	/**
	 * @return Returns the templateStationCode.
	 */
	public String[] getTemplateStationCode() {
		return templateStationCode;
	}

	/**
	 * @param templateStationCode
	 */
	public void setTemplateStationCode(String[] templateStationCode) {
		this.templateStationCode = templateStationCode;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode  The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the stationCode.
	 */
	public String getStationCode() {
		return stationCode;
	}

	/**
	 * @param stationCode The stationCode to set.
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	/**
	 * @return Returns the selectFlag.
	 */
	public String getSelectFlag() {
		return selectFlag;
	}

	/**
	 * @param selectFlag The selectFlag to set.
	 */
	public void setSelectFlag(String selectFlag) {
		this.selectFlag = selectFlag;
	}

	/**
	 * @return Returns the selectAll.
	 */
	public String getSelectAll() {
		return selectAll;
	}

	/**
	 * @param selectAll The selectAll to set.
	 */
	public void setSelectAll(String selectAll) {
		this.selectAll = selectAll;
	}

	

	
	/**
	 * @return Returns the statusFlag.
	 */
	public String getStatusFlag() {
		return statusFlag;
	}

	/**
	 * @param statusFlag The statusFlag to set.
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	/**
     * Method to return the product the screen is associated with
     * 
     * @return String
     */
    public String getProduct() {
        return PRODUCT;
    }
    
    /**
     * Method to return the sub product the screen is associated with
     * 
     * @return String
     */
    public String getSubProduct() {
        return SUBPRODUCT;
    }
	
    /**
     * Method to return the id the screen is associated with
     * 
     * @return String
     */
    public String getScreenId() {
        return SCREENID;
    }

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


}
