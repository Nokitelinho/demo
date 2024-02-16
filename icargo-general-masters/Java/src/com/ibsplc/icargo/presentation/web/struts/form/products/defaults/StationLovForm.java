/*
 * StationLovForm.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.products.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1754
 *
 */
public class StationLovForm extends ScreenModel {

	private String code = "";

	private String description = "";

	private String lastPageNumber = "0";

	private String displayPage = "1";

	private String[] stationChecked;

	private String selectedValues = "";

	private String nextAction;

	private String selectedData = "N";
	private String saveSelectedValues = "";

private static final String BUNDLE = "StationLov"; // The key attribute specified in struts_config.xml file.

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
	 * @return Returns the stationChecked.
	 */
	public String[] getStationChecked() {
		return stationChecked;
	}

	/**
	 * @param stationChecked
	 *            The stationChecked to set.
	 */
	public void setStationChecked(String[] stationChecked) {
		this.stationChecked = stationChecked;
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
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the selectedValues.
	 */
	public String getSelectedValues() {
		return selectedValues;
	}

	/**
	 * @param selectedValues
	 *            The selectedValues to set.
	 */
	public void setSelectedValues(String selectedValues) {
		this.selectedValues = selectedValues;
	}

	/**
	 * The overiden function to return the screen id
	 *
	 * @return String screenid
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
	public String getSaveSelectedValues() {
		return saveSelectedValues;
	}
	public void setSaveSelectedValues(String saveSelectedValues) {
		this.saveSelectedValues = saveSelectedValues;
	}
}
