/*
 *  ListMailFormOneForm.java Created on June 18, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.util.currency.Money;

/**
 * @author A-3434
 * 
 */
public class ListMailFormOneForm extends ScreenModel {

	private static final String BUNDLE = "listForm1bundle";

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra.airlinebilling";

	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.listform1";

	private String clearancePeriod;

	private String airlineCodeFilterField;

	private String[] airlineCode;

	private String airlineNumber;

	private String[] classType;

	// private Page formOnePage;
	private String lastPageNum;

	private int pageNumber = 1;

	private String displayPage;

	private String multiselect;

	private String selectedValues;

	private String[] selectCheckBox;

	private String absIdx;

	private String fromScreen;

	// Added by A-2414 for ANZ CR
	private Money totalAmountInListingCurrency;

	private Money totalAmountInBillingCurrency;

	private String select;

	/**
	 * @return the select
	 */
	public String getSelect() {
		return select;
	}

	/**
	 * @param select
	 *            the select to set
	 */
	public void setSelect(String select) {
		this.select = select;
	}

	/**
	 * @return Returns the absIdx.
	 */
	public String getAbsIdx() {
		return absIdx;
	}

	/**
	 * @param absIdx
	 *            The absIdx to set.
	 */
	public void setAbsIdx(String absIdx) {
		this.absIdx = absIdx;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String[] getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode
	 *            The airlineCode to set.
	 */
	public void setAirlineCode(String[] airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the airlineCodeFilterField.
	 */
	public String getAirlineCodeFilterField() {
		return airlineCodeFilterField;
	}

	/**
	 * @param airlineCodeFilterField
	 *            The airlineCodeFilterField to set.
	 */
	public void setAirlineCodeFilterField(String airlineCodeFilterField) {
		this.airlineCodeFilterField = airlineCodeFilterField;
	}

	/**
	 * @return Returns the airlineNumber.
	 */
	public String getAirlineNumber() {
		return airlineNumber;
	}

	/**
	 * @param airlineNumber
	 *            The airlineNumber to set.
	 */
	public void setAirlineNumber(String airlineNumber) {
		this.airlineNumber = airlineNumber;
	}

	/**
	 * @return Returns the classType.
	 */
	public String[] getClassType() {
		return classType;
	}

	/**
	 * @param classType
	 *            The classType to set.
	 */
	public void setClassType(String[] classType) {
		this.classType = classType;
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
	 * @return Returns the fromScreen.
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen
	 *            The fromScreen to set.
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return Returns the lastPageNum.
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * @param lastPageNum
	 *            The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return Returns the multiselect.
	 */
	public String getMultiselect() {
		return multiselect;
	}

	/**
	 * @param multiselect
	 *            The multiselect to set.
	 */
	public void setMultiselect(String multiselect) {
		this.multiselect = multiselect;
	}

	/**
	 * @return Returns the pageNumber.
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber
	 *            The pageNumber to set.
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return Returns the selectCheckBox.
	 */
	public String[] getSelectCheckBox() {
		return selectCheckBox;
	}

	/**
	 * @param selectCheckBox
	 *            The selectCheckBox to set.
	 */
	public void setSelectCheckBox(String[] selectCheckBox) {
		this.selectCheckBox = selectCheckBox;
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
	 * @return Returns the totalAmountInBillingCurrency.
	 */
	public Money getTotalAmountInBillingCurrency() {
		return totalAmountInBillingCurrency;
	}

	/**
	 * @param totalAmountInBillingCurrency
	 *            The totalAmountInBillingCurrency to set.
	 */
	public void setTotalAmountInBillingCurrency(
			Money totalAmountInBillingCurrency) {
		this.totalAmountInBillingCurrency = totalAmountInBillingCurrency;
	}

	/**
	 * @return Returns the totalAmountInListingCurrency.
	 */
	public Money getTotalAmountInListingCurrency() {
		return totalAmountInListingCurrency;
	}

	/**
	 * @param totalAmountInListingCurrency
	 *            The totalAmountInListingCurrency to set.
	 */
	public void setTotalAmountInListingCurrency(
			Money totalAmountInListingCurrency) {
		this.totalAmountInListingCurrency = totalAmountInListingCurrency;
	}

	/**
	 * Constructor
	 */
	public ListMailFormOneForm() {
		super();
		selectCheckBox = null;
		displayPage = "1";
		lastPageNum = "0";
		multiselect = "Y";
		selectedValues = "";
	}

	/**
	 * @return Returns the clearancePeriod.
	 */
	public String getClearancePeriod() {
		return clearancePeriod;
	}

	/**
	 * @param clearancePeriod
	 *            The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
	}

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
}
