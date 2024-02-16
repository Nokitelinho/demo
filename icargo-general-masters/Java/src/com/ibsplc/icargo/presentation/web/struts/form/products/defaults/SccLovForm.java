/*
 * SccLovForm.java Created on Jun 28, 2005
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
public class SccLovForm extends ScreenModel {

	private String sccCode = "";

	private String sccDescription = "";

	private String lastPageNumber = "0";

	private String displayPage = "1";

	private String[] sccChecked;

	private String nextAction;

	private String selectedValues = "";

	private String selectedData = "N";
	private String saveSelectedValues = "";


	private static final String BUNDLE = "SCCLov"; // The key attribute specified in struts_config.xml file.

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
	 * @return Returns the sccCode.
	 */
	public String getSccCode() {
		return sccCode;
	}

	/**
	 * @param sccCode
	 *            The sccCode to set.
	 */
	public void setSccCode(String sccCode) {
		this.sccCode = sccCode;
	}

	/**
	 * @return Returns the sccDescription.
	 */
	public String getSccDescription() {
		return sccDescription;
	}

	/**
	 * @param sccDescription
	 *            The sccDescription to set.
	 */
	public void setSccDescription(String sccDescription) {
		this.sccDescription = sccDescription;
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
	 * @return Returns the sccChecked.
	 */
	public String[] getSccChecked() {
		return sccChecked;
	}

	/**
	 * @param sccChecked
	 *            The sccChecked to set.
	 */
	public void setSccChecked(String[] sccChecked) {
		this.sccChecked = sccChecked;
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
	public String getSaveSelectedValues() {
		return saveSelectedValues;
	}
	public void setSaveSelectedValues(String saveSelectedValues) {
		this.saveSelectedValues = saveSelectedValues;
	}

}
