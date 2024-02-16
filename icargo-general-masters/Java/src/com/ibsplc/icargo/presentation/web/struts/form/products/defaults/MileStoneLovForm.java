/*
 * MileStoneLovForm.java Created on July 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.products.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1883
 *
 */
public class MileStoneLovForm extends ScreenModel {
	private String mileStoneCode = "";

	private String mileStoneDescription = "";

	private String lastPageNumber = "0";

	private String displayPage = "1";

	private String[] mileStoneChecked;

	private String parentSession;

	private String nextAction;

	private String selectedData = "N";


	private static final String BUNDLE = "MileStoneLov"; // The key attribute specified in struts_config.xml file.

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
	 * @return Returns the selectedData.
	 */
	public String getSelectedData() {
		return selectedData;
	}

	/**
	 * @param selectedData
	 *            The selectedData to set.
	 */
	public void setSelectedData(String selectedData) {
		this.selectedData = selectedData;
	}

	/**
	 * @return Returns the nextAction.
	 */
	public String getNextAction() {
		return nextAction;
	}

	/**
	 * @param nextAction
	 *            The nextAction to set.
	 */
	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	/**
	 * @return Returns the parentSession.
	 */
	public String getParentSession() {
		return parentSession;
	}

	/**
	 * @param parentSession
	 *            The parentSession to set.
	 */
	public void setParentSession(String parentSession) {
		this.parentSession = parentSession;
	}

	/**
	 * @return Returns the mileStoneCode.
	 */
	public String getMileStoneCode() {
		return mileStoneCode;
	}

	/**
	 * @param mileStoneCode
	 *            The mileStoneCode to set.
	 */
	public void setMileStoneCode(String mileStoneCode) {
		this.mileStoneCode = mileStoneCode;
	}

	/**
	 * @return Returns the mileStoneDescription.
	 */
	public String getMileStoneDescription() {
		return mileStoneDescription;
	}

	/**
	 * @param mileStoneDescription
	 *            The mileStoneDescription to set.
	 */
	public void setMileStoneDescription(String mileStoneDescription) {
		this.mileStoneDescription = mileStoneDescription;
	}

	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage
	 *            The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return Returns the lastPageNumber.
	 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}

	/**
	 * @param lastPageNumber
	 *            The lastPageNumber to set.
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	/**
	 * @return Returns the mileStoneChecked.
	 */
	public String[] getMileStoneChecked() {
		return mileStoneChecked;
	}

	/**
	 * @param mileStoneChecked
	 *            The mileStoneChecked to set.
	 */
	public void setMileStoneChecked(String[] mileStoneChecked) {
		this.mileStoneChecked = mileStoneChecked;
	}

	/**
	 * The overiden function to return the screen id
	 *
	 * @return products.defaults.maintainproduct
	 */
	public String getScreenId() {
		return "products.defaults.maintainproduct";
	}

	/**
	 * The overriden function to return the product name
	 *
	 * @return products
	 */
	public String getProduct() {
		return "products";
	}

	/**
	 * The overriden function to return the sub product name
	 *
	 * @return defaults
	 */
	public String getSubProduct() {
		return "defaults";
	}

}
