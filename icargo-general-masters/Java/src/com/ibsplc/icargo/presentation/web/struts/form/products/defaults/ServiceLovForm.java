/*
 * ServiceLovForm.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.products.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1358
 *
 */
public class ServiceLovForm extends ScreenModel {

	private String serviceCode = "";

	private String serviceDescription = "";

	private String lastPageNumber = "0";

	private String displayPage = "1";

	private String[] serviceChecked;

	private String nextAction;

	private String selectedValues = "";

	private String saveSelectedValues = "";
	private String selectedData = "N";

	private static final String BUNDLE = "ServicesLov"; // The key attribute specified in struts_config.xml file.

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
	 *
	 * @return serviceDescription
	 */
	public String getServiceDescription() {
		return serviceDescription;
	}

	/**
	 *
	 * @param serviceDescription
	 */
	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	/**
	 *
	 * @return serviceCode
	 */
	public String getServiceCode() {
		return serviceCode;
	}

	/**
	 *
	 * @param serviceCode
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	/**
	 *
	 * @return displayPage
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 *
	 * @param displayPage
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 *
	 * @return lastPageNumber
	 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}

	/**
	 *
	 * @param lastPageNumber
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	/**
	 *
	 * @return serviceChecked
	 */
	public String[] getServiceChecked() {
		return serviceChecked;
	}

	/**
	 *
	 * @param serviceChecked
	 */
	public void setServiceChecked(String[] serviceChecked) {
		this.serviceChecked = serviceChecked;
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
	public String getSaveSelectedValues() {
		return saveSelectedValues;
	}
	public void setSaveSelectedValues(String saveSelectedValues) {
		this.saveSelectedValues = saveSelectedValues;
	}

}
