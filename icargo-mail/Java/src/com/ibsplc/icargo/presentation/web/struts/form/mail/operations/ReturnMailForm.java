/*
 * ReturnMailForm.java Created on July 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1861
 *
 */
public class ReturnMailForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.returnmail";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "returnMailResources";

	private String[] damageCode;
	private String[] damageRemarks;
	private String returnCheckAll;
	private String[] returnSubCheck;
	private boolean isReturnMail;
	private String postalAdmin;

	private String fromScreen;
	private String selectedContainers;

	private String successFlag;

	private String paBuiltFlag;

	private String flagSBReturn;

	/**
	 * @return Returns the selectedContainers.
	 */
	public String getSelectedContainers() {
		return selectedContainers;
	}

	/**
	 * @param selectedContainers The selectedContainers to set.
	 */
	public void setSelectedContainers(String selectedContainers) {
		this.selectedContainers = selectedContainers;
	}

	/**
	 * @return Returns the fromScreen.
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen The fromScreen to set.
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return Returns the damageCode.
	 */
	public String[] getDamageCode() {
		return damageCode;
	}

	/**
	 * @param damageCode The damageCode to set.
	 */
	public void setDamageCode(String[] damageCode) {
		this.damageCode = damageCode;
	}

	/**
	 * @return Returns the damageRemarks.
	 */
	public String[] getDamageRemarks() {
		return damageRemarks;
	}

	/**
	 * @param damageRemarks The damageRemarks to set.
	 */
	public void setDamageRemarks(String[] damageRemarks) {
		this.damageRemarks = damageRemarks;
	}

	/**
	 * @return Returns the postalAdmin.
	 */
	public String getPostalAdmin() {
		return postalAdmin;
	}

	/**
	 * @param postalAdmin The postalAdmin to set.
	 */
	public void setPostalAdmin(String postalAdmin) {
		this.postalAdmin = postalAdmin;
	}

	/**
	 * @return Returns the returnCheckAll.
	 */
	public String getReturnCheckAll() {
		return returnCheckAll;
	}

	/**
	 * @param returnCheckAll The returnCheckAll to set.
	 */
	public void setReturnCheckAll(String returnCheckAll) {
		this.returnCheckAll = returnCheckAll;
	}

	/**
	 * @return Returns the isReturnMail.
	 */
	public boolean isReturnMail() {
		return isReturnMail;
	}

	/**
	 * @param isReturnMail The isReturnMail to set.
	 */
	public void setReturnMail(boolean isReturnMail) {
		this.isReturnMail = isReturnMail;
	}

	/**
	 * @return Returns the returnSubCheck.
	 */
	public String[] getReturnSubCheck() {
		return returnSubCheck;
	}

	/**
	 * @param returnSubCheck The returnSubCheck to set.
	 */
	public void setReturnSubCheck(String[] returnSubCheck) {
		this.returnSubCheck = returnSubCheck;
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

	public String getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

	/**
	 * @return Returns the flagSBReturn.
	 */
	public String getFlagSBReturn() {
		return flagSBReturn;
	}

	/**
	 * @param flagSBReturn The flagSBReturn to set.
	 */
	public void setFlagSBReturn(String flagSBReturn) {
		this.flagSBReturn = flagSBReturn;
	}

	/**
	 * @return the paBuiltFlag
	 */
	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}

	/**
	 * @param paBuiltFlag the paBuiltFlag to set
	 */
	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}

}
