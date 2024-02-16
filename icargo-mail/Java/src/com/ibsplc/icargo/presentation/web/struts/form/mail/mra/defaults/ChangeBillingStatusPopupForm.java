/*
 * ChangeBillingStatusPopupForm.java Created on Jan 3, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-3434
 *
 */
public class ChangeBillingStatusPopupForm extends ScreenModel {

private static final String SCREEN_ID = "mailtracking.mra.defaults.changestatus";
	
private static final String PRODUCT = "mail";
	
private static final String SUBPRODUCT = "mra.defaults";
	
private static final String BUNDLE = "changestatusBundle";

private String popupRemarks;
private String select;
private String screenStatus;
private String fromScreen;
private String selectedrows;
private String popupDespatchNumber;
private String despatchNumbers;
private String[] despatchNumber;
private String[] dsn;
private String billingStatus;
private String isBillable;
/**
 * @return Returns the selectedrows.
 */
public String getSelectedrows() {
	return selectedrows;
}

/**
 * @param selectedrows The selectedrows to set.
 */
public void setSelectedrows(String selectedrows) {
	this.selectedrows = selectedrows;
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
 * @return Returns the screenStatus.
 */
public String getScreenStatus() {
	return screenStatus;
}

/**
 * @param screenStatus The screenStatus to set.
 */
public void setScreenStatus(String screenStatus) {
	this.screenStatus = screenStatus;
}
/**
 * @return Returns the popupRemarks.
 */
public String getPopupRemarks() {
	return popupRemarks;
}

/**
 * @param popupRemarks The popupRemarks to set.
 */
public void setPopupRemarks(String popupRemarks) {
	this.popupRemarks = popupRemarks;
}


/**
 * @return Returns the select.
 */
public String getSelect() {
	return select;
}

/**
 * @param select The select to set.
 */
public void setSelect(String select) {
	this.select = select;
}

/* (non-Javadoc)
 * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
 */
public String getScreenId() {
	return SCREEN_ID;
}

/* (non-Javadoc)
 * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
 */
public String getProduct() {
	return PRODUCT;
}

/* (non-Javadoc)
 * @see com.ibsplc.icargo.framework.web.ScreenData#getSubProduct()
 */
public String getSubProduct() {
	return SUBPRODUCT;
}

public String getBundle() {
	return BUNDLE;
}

public String[] getDespatchNumber() {
	return despatchNumber;
}

public void setDespatchNumber(String[] despatchNumber) {
	this.despatchNumber = despatchNumber;
}

public String getDespatchNumbers() {
	return despatchNumbers;
}

public void setDespatchNumbers(String despatchNumbers) {
	this.despatchNumbers = despatchNumbers;
}

public String getPopupDespatchNumber() {
	return popupDespatchNumber;
}

public void setPopupDespatchNumber(String popupDespatchNumber) {
	this.popupDespatchNumber = popupDespatchNumber;
}

public String[] getDsn() {
	return dsn;
}

public void setDsn(String[] dsn) {
	this.dsn = dsn;
}

public String getBillingStatus() {
	return billingStatus;
}

public void setBillingStatus(String billingStatus) {
	this.billingStatus = billingStatus;
}

public String getIsBillable() {
	return isBillable;
}

public void setIsBillable(String isBillable) {
	this.isBillable = isBillable;
}

}
