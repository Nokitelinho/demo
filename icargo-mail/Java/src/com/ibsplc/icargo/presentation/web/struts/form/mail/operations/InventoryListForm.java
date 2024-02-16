/*
 * InventoryListForm.java Created on Jan 17,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1862
 *
 */
public class InventoryListForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.inventorylist";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "inventorylistResources";

	private String carrierCode="";
	private String carrierID="";
	private String depPort="";
	private String pageurl="";
	private String statusFlag="";
	private String selectedMails="";
	private String selectMode;
	private String[] selectContainer;
	private String[] childContainer;
	
	private String mailtracking;
	private String container;
	private String deliverWarning;
	
	private String hiddenContainer;
	
	private String[] paBuiltFlag;

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public String getMailtracking() {
		return mailtracking;
	}

	public void setMailtracking(String mailtracking) {
		this.mailtracking = mailtracking;
	}

	/**
     * @return SCREEN_ID - String
     */
    public String getScreenId() {
        return SCREEN_ID;
    }

    /**
     * @return PRODUCT_NAME - String
     */
    public String getProduct() {
        return PRODUCT_NAME;
    }

    /**
     * @return SUBPRODUCT_NAME - String
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
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the depPort.
	 */
	public String getDepPort() {
		return depPort;
	}

	/**
	 * @param depPort The depPort to set.
	 */
	public void setDepPort(String depPort) {
		this.depPort = depPort;
	}

	/**
	 * @return Returns the pageurl.
	 */
	public String getPageurl() {
		return pageurl;
	}

	/**
	 * @param pageurl The pageurl to set.
	 */
	public void setPageurl(String pageurl) {
		this.pageurl = pageurl;
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
	 * @return Returns the selectContainer.
	 */
	public String[] getSelectContainer() {
		return selectContainer;
	}

	/**
	 * @param selectContainer The selectContainer to set.
	 */
	public void setSelectContainer(String[] selectContainer) {
		this.selectContainer = selectContainer;
	}

	/**
	 * @return Returns the carrierID.
	 */
	public String getCarrierID() {
		return carrierID;
	}

	/**
	 * @param carrierID The carrierID to set.
	 */
	public void setCarrierID(String carrierID) {
		this.carrierID = carrierID;
	}

	/**
	 * @return Returns the selectedMails.
	 */
	public String getSelectedMails() {
		return selectedMails;
	}

	/**
	 * @param selectedMails The selectedMails to set.
	 */
	public void setSelectedMails(String selectedMails) {
		this.selectedMails = selectedMails;
	}

	/**
	 * @return Returns the childContainer.
	 */
	public String[] getChildContainer() {
		return childContainer;
	}

	/**
	 * @param childContainer The childContainer to set.
	 */
	public void setChildContainer(String[] childContainer) {
		this.childContainer = childContainer;
	}

	public String getSelectMode() {
		return selectMode;
	}

	public void setSelectMode(String selectMode) {
		this.selectMode = selectMode;
	}

	public String getDeliverWarning() {
		return deliverWarning;
	}

	public void setDeliverWarning(String deliverWarning) {
		this.deliverWarning = deliverWarning;
	}

	public String getHiddenContainer() {
		return hiddenContainer;
	}

	public void setHiddenContainer(String hiddenContainer) {
		this.hiddenContainer = hiddenContainer;
	}

	public String[] getPaBuiltFlag() {
		return paBuiltFlag;
	}

	public void setPaBuiltFlag(String[] paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}

	

	


}
