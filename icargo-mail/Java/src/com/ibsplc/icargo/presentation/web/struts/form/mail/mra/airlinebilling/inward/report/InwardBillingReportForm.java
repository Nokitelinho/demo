/*
 * InwardBillingReportForm.java Created on March 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.report;




import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author Shivjith
 * Form for assign exceptions screen.
 *
 * Revision History
 *
 * Version      Date          	 Author          	 Description
 *
 *  0.1         March 14, 2007   Shivjith			Initial draft
 *  
 */

public class InwardBillingReportForm extends ScreenModel {

    private static final String BUNDLE = "inwardreport";

	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.reports";
	
	private String reportID;
	
	private String billingType;
	
	private String rpt017AirlineCode;

	private String rpt017AirlineNum;

	private String rpt017ClrPrd;
	
	private String rpt018AirlineCode;

	private String rpt018AirlineNum;

	private String rpt018BillingType;

	private String rpt018FromDate;

	private String rpt018ToDate;
	
	private String rpt019AirlineCode;

	private String rpt019AirlineNum;

	private String rpt019ClrPrd;
	
	private String rpt020AirlineCode;

	private String rpt020AirlineNum;

	private String rpt020BillingType;

	private String rpt020FromDate;

	private String rpt020ToDate;
	
	
	
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

	/**
	 * @return Returns the rpt017AirlineCode.
	 */
	public String getRpt017AirlineCode() {
		return rpt017AirlineCode;
	}

	/**
	 * @param rpt017AirlineCode The rpt017AirlineCode to set.
	 */
	public void setRpt017AirlineCode(String rpt017AirlineCode) {
		this.rpt017AirlineCode = rpt017AirlineCode;
	}

	/**
	 * @return Returns the rpt017AirlineNum.
	 */
	public String getRpt017AirlineNum() {
		return rpt017AirlineNum;
	}

	/**
	 * @param rpt017AirlineNum The rpt017AirlineNum to set.
	 */
	public void setRpt017AirlineNum(String rpt017AirlineNum) {
		this.rpt017AirlineNum = rpt017AirlineNum;
	}

	/**
	 * @return Returns the rpt018AirlineCode.
	 */
	public String getRpt018AirlineCode() {
		return rpt018AirlineCode;
	}

	/**
	 * @param rpt018AirlineCode The rpt018AirlineCode to set.
	 */
	public void setRpt018AirlineCode(String rpt018AirlineCode) {
		this.rpt018AirlineCode = rpt018AirlineCode;
	}

	/**
	 * @return Returns the rpt018AirlineNum.
	 */
	public String getRpt018AirlineNum() {
		return rpt018AirlineNum;
	}

	/**
	 * @param rpt018AirlineNum The rpt018AirlineNum to set.
	 */
	public void setRpt018AirlineNum(String rpt018AirlineNum) {
		this.rpt018AirlineNum = rpt018AirlineNum;
	}

	/**
	 * @return Returns the rpt017ClrPrd.
	 */
	public String getRpt017ClrPrd() {
		return rpt017ClrPrd;
	}

	/**
	 * @param rpt017ClrPrd The rpt017ClrPrd to set.
	 */
	public void setRpt017ClrPrd(String rpt017ClrPrd) {
		this.rpt017ClrPrd = rpt017ClrPrd;
	}

	/**
	 * @return Returns the rpt018BillingType.
	 */
	public String getRpt018BillingType() {
		return rpt018BillingType;
	}

	/**
	 * @param rpt018BillingType The rpt018BillingType to set.
	 */
	public void setRpt018BillingType(String rpt018BillingType) {
		this.rpt018BillingType = rpt018BillingType;
	}

	/**
	 * @return Returns the rpt018FromDate.
	 */
	public String getRpt018FromDate() {
		return rpt018FromDate;
	}

	/**
	 * @param rpt018FromDate The rpt018FromDate to set.
	 */
	public void setRpt018FromDate(String rpt018FromDate) {
		this.rpt018FromDate = rpt018FromDate;
	}

	/**
	 * @return Returns the rpt018ToDate.
	 */
	public String getRpt018ToDate() {
		return rpt018ToDate;
	}

	/**
	 * @param rpt018ToDate The rpt018ToDate to set.
	 */
	public void setRpt018ToDate(String rpt018ToDate) {
		this.rpt018ToDate = rpt018ToDate;
	}

	/**
	 * @return Returns the rpt019AirlineCode.
	 */
	public String getRpt019AirlineCode() {
		return rpt019AirlineCode;
	}

	/**
	 * @param rpt019AirlineCode The rpt019AirlineCode to set.
	 */
	public void setRpt019AirlineCode(String rpt019AirlineCode) {
		this.rpt019AirlineCode = rpt019AirlineCode;
	}

	/**
	 * @return Returns the rpt019AirlineNum.
	 */
	public String getRpt019AirlineNum() {
		return rpt019AirlineNum;
	}

	/**
	 * @param rpt019AirlineNum The rpt019AirlineNum to set.
	 */
	public void setRpt019AirlineNum(String rpt019AirlineNum) {
		this.rpt019AirlineNum = rpt019AirlineNum;
	}

	/**
	 * @return Returns the rpt019ClrPrd.
	 */
	public String getRpt019ClrPrd() {
		return rpt019ClrPrd;
	}

	/**
	 * @param rpt019ClrPrd The rpt019ClrPrd to set.
	 */
	public void setRpt019ClrPrd(String rpt019ClrPrd) {
		this.rpt019ClrPrd = rpt019ClrPrd;
	}

	/**
	 * @return Returns the rpt020AirlineCode.
	 */
	public String getRpt020AirlineCode() {
		return rpt020AirlineCode;
	}

	/**
	 * @param rpt020AirlineCode The rpt020AirlineCode to set.
	 */
	public void setRpt020AirlineCode(String rpt020AirlineCode) {
		this.rpt020AirlineCode = rpt020AirlineCode;
	}

	/**
	 * @return Returns the rpt020AirlineNum.
	 */
	public String getRpt020AirlineNum() {
		return rpt020AirlineNum;
	}

	/**
	 * @param rpt020AirlineNum The rpt020AirlineNum to set.
	 */
	public void setRpt020AirlineNum(String rpt020AirlineNum) {
		this.rpt020AirlineNum = rpt020AirlineNum;
	}

	/**
	 * @return Returns the rpt020BillingType.
	 */
	public String getRpt020BillingType() {
		return rpt020BillingType;
	}

	/**
	 * @param rpt020BillingType The rpt020BillingType to set.
	 */
	public void setRpt020BillingType(String rpt020BillingType) {
		this.rpt020BillingType = rpt020BillingType;
	}

	/**
	 * @return Returns the rpt020FromDate.
	 */
	public String getRpt020FromDate() {
		return rpt020FromDate;
	}

	/**
	 * @param rpt020FromDate The rpt020FromDate to set.
	 */
	public void setRpt020FromDate(String rpt020FromDate) {
		this.rpt020FromDate = rpt020FromDate;
	}

	/**
	 * @return Returns the rpt020ToDate.
	 */
	public String getRpt020ToDate() {
		return rpt020ToDate;
	}

	/**
	 * @param rpt020ToDate The rpt020ToDate to set.
	 */
	public void setRpt020ToDate(String rpt020ToDate) {
		this.rpt020ToDate = rpt020ToDate;
	}

	/**
	 * @return Returns the reportID.
	 */
	public String getReportID() {
		return reportID;
	}

	/**
	 * @param reportID The reportID to set.
	 */
	public void setReportID(String reportID) {
		this.reportID = reportID;
	}

	/**
	 * @return Returns the billingType.
	 */
	public String getBillingType() {
		return billingType;
	}

	/**
	 * @param billingType The billingType to set.
	 */
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}
}
