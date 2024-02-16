/*
 * ListBillingEntriesForm.java Created on Nov 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2270
 *
 */
public class ListBillingEntriesForm extends ScreenModel {

	private static final String BUNDLE ="listbillingentries";

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.defaults.listbillingentries";

	private String fromDate;

	private String toDate;

	private String[] consignmentDate;

	private String[] consignmentNumber;
	
	private String[] mailCategoryCode;

	private String[] route;

	private String[] sector;

	private String[] totalWeight;

	private String[] totalAmount;
	
	private String select;

	private String[] check;

	private String screenStatus;

	private String[] rowId;

	private String allCheck;

	/**
	 * @return Returns the allCheck.
	 */
	public String getAllCheck() {
		return allCheck;
	}

	/**
	 * @param allCheck The allCheck to set.
	 */
	public void setAllCheck(String allCheck) {
		this.allCheck = allCheck;
	}

	/**
	 * @return Returns the rowId.
	 */
	public String[] getRowId() {
		return rowId;
	}

	/**
	 * @param rowId The rowId to set.
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
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

	
	/**
	 * @return Returns the product.
	 */
	public String getPRODUCT() {
		return PRODUCT;
	}

	/**
	 * @return Returns the SUBPRODUCT.
	 */
	public String getSUBPRODUCT() {
		return SUBPRODUCT;
	}
	/**
	 * @return Returns screenId.
	 */
    public String getScreenId() {
        return SCREENID;
    }

    /**
	 * @return Returns the product.
	 */
    public String getProduct() {
        return PRODUCT;
    }

    /**
	 * @return Returns the subproduct.
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
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		//this.bundle = bundle;
	}



	
	/**
	 * @return Returns the fromDate.
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return Returns the toDate.
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return Returns the consignmentNumber.
	 */
	public String[] getConsignmentNumber() {
		return consignmentNumber;
	}

	/**
	 * @param consignmentNumber The consignmentNumber to set.
	 */
	public void setConsignmentNumber(String[] consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

	/**
	 * @return Returns the mailCategoryCode.
	 */
	public String[] getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String[] mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return the consignmentDate
	 */
	public String[] getConsignmentDate() {
		return consignmentDate;
	}

	/**
	 * @param consignmentDate the consignmentDate to set
	 */
	public void setConsignmentDate(String[] consignmentDate) {
		this.consignmentDate = consignmentDate;
	}

	/**
	 * @return the route
	 */
	public String[] getRoute() {
		return route;
	}

	/**
	 * @param route the route to set
	 */
	public void setRoute(String[] route) {
		this.route = route;
	}

	/**
	 * @return the sector
	 */
	public String[] getSector() {
		return sector;
	}

	/**
	 * @param sector the sector to set
	 */
	public void setSector(String[] sector) {
		this.sector = sector;
	}

	/**
	 * @return the totalAmount
	 */
	public String[] getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(String[] totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the totalWeight
	 */
	public String[] getTotalWeight() {
		return totalWeight;
	}

	/**
	 * @param totalWeight the totalWeight to set
	 */
	public void setTotalWeight(String[] totalWeight) {
		this.totalWeight = totalWeight;
	}

	
}
