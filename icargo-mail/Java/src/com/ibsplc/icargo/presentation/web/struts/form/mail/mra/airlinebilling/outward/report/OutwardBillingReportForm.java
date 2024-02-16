/*
 * OutwardBillingReportForm.java Created on March 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.report;




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

public class OutwardBillingReportForm extends ScreenModel {

    private static final String BUNDLE = "outwardreport";

	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.reports";
	
	private String reportID;
	
	private String billingType;
	
	private String rpt010AirlineCode;

	private String rpt010AirlineNum;

	private String rpt010ClrPrd;
	
	private String rpt011AirlineCode;

	private String rpt011AirlineNum;

	private String rpt011BillingType;

	private String rpt011FromDate;

	private String rpt011ToDate;
	
	private String rpt012AirlineCode;

	private String rpt012AirlineNum;

	private String rpt012ClrPrd;
	
	private String rpt013AirlineCode;

	private String rpt013AirlineNum;

	private String rpt013BillingType;

	private String rpt013FromDate;

	private String rpt013ToDate;
	
	private String rpt014AirlineCode;

	private String rpt014InvoiceNum;

	private String rpt014ClrPrd;
	
	private String rpt040AirlineCode;

	private String rpt040AirlineNum;

	private String rpt040ClrPrd;
	
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
	 * @return Returns the rpt010AirlineCode.
	 */
	public String getRpt010AirlineCode() {
		return rpt010AirlineCode;
	}

	/**
	 * @param rpt010AirlineCode The rpt010AirlineCode to set.
	 */
	public void setRpt010AirlineCode(String rpt010AirlineCode) {
		this.rpt010AirlineCode = rpt010AirlineCode;
	}

	/**
	 * @return Returns the rpt010AirlineNum.
	 */
	public String getRpt010AirlineNum() {
		return rpt010AirlineNum;
	}

	/**
	 * @param rpt010AirlineNum The rpt010AirlineNum to set.
	 */
	public void setRpt010AirlineNum(String rpt010AirlineNum) {
		this.rpt010AirlineNum = rpt010AirlineNum;
	}

	/**
	 * @return Returns the rpt010ClrPrd.
	 */
	public String getRpt010ClrPrd() {
		return rpt010ClrPrd;
	}

	/**
	 * @param rpt010ClrPrd The rpt010ClrPrd to set.
	 */
	public void setRpt010ClrPrd(String rpt010ClrPrd) {
		this.rpt010ClrPrd = rpt010ClrPrd;
	}

	/**
	 * @return Returns the rpt011AirlineCode.
	 */
	public String getRpt011AirlineCode() {
		return rpt011AirlineCode;
	}

	/**
	 * @param rpt011AirlineCode The rpt011AirlineCode to set.
	 */
	public void setRpt011AirlineCode(String rpt011AirlineCode) {
		this.rpt011AirlineCode = rpt011AirlineCode;
	}

	/**
	 * @return Returns the rpt011AirlineNum.
	 */
	public String getRpt011AirlineNum() {
		return rpt011AirlineNum;
	}

	/**
	 * @param rpt011AirlineNum The rpt011AirlineNum to set.
	 */
	public void setRpt011AirlineNum(String rpt011AirlineNum) {
		this.rpt011AirlineNum = rpt011AirlineNum;
	}

	/**
	 * @return Returns the rpt011BillingType.
	 */
	public String getRpt011BillingType() {
		return rpt011BillingType;
	}

	/**
	 * @param rpt011BillingType The rpt011BillingType to set.
	 */
	public void setRpt011BillingType(String rpt011BillingType) {
		this.rpt011BillingType = rpt011BillingType;
	}

	/**
	 * @return Returns the rpt011FromDate.
	 */
	public String getRpt011FromDate() {
		return rpt011FromDate;
	}

	/**
	 * @param rpt011FromDate The rpt011FromDate to set.
	 */
	public void setRpt011FromDate(String rpt011FromDate) {
		this.rpt011FromDate = rpt011FromDate;
	}

	/**
	 * @return Returns the rpt011ToDate.
	 */
	public String getRpt011ToDate() {
		return rpt011ToDate;
	}

	/**
	 * @param rpt011ToDate The rpt011ToDate to set.
	 */
	public void setRpt011ToDate(String rpt011ToDate) {
		this.rpt011ToDate = rpt011ToDate;
	}

	/**
	 * @return Returns the rpt012AirlineCode.
	 */
	public String getRpt012AirlineCode() {
		return rpt012AirlineCode;
	}

	/**
	 * @param rpt012AirlineCode The rpt012AirlineCode to set.
	 */
	public void setRpt012AirlineCode(String rpt012AirlineCode) {
		this.rpt012AirlineCode = rpt012AirlineCode;
	}

	/**
	 * @return Returns the rpt012AirlineNum.
	 */
	public String getRpt012AirlineNum() {
		return rpt012AirlineNum;
	}

	/**
	 * @param rpt012AirlineNum The rpt012AirlineNum to set.
	 */
	public void setRpt012AirlineNum(String rpt012AirlineNum) {
		this.rpt012AirlineNum = rpt012AirlineNum;
	}

	/**
	 * @return Returns the rpt012ClrPrd.
	 */
	public String getRpt012ClrPrd() {
		return rpt012ClrPrd;
	}

	/**
	 * @param rpt012ClrPrd The rpt012ClrPrd to set.
	 */
	public void setRpt012ClrPrd(String rpt012ClrPrd) {
		this.rpt012ClrPrd = rpt012ClrPrd;
	}

	/**
	 * @return Returns the rpt013AirlineCode.
	 */
	public String getRpt013AirlineCode() {
		return rpt013AirlineCode;
	}

	/**
	 * @param rpt013AirlineCode The rpt013AirlineCode to set.
	 */
	public void setRpt013AirlineCode(String rpt013AirlineCode) {
		this.rpt013AirlineCode = rpt013AirlineCode;
	}

	/**
	 * @return Returns the rpt013AirlineNum.
	 */
	public String getRpt013AirlineNum() {
		return rpt013AirlineNum;
	}

	/**
	 * @param rpt013AirlineNum The rpt013AirlineNum to set.
	 */
	public void setRpt013AirlineNum(String rpt013AirlineNum) {
		this.rpt013AirlineNum = rpt013AirlineNum;
	}

	/**
	 * @return Returns the rpt013BillingType.
	 */
	public String getRpt013BillingType() {
		return rpt013BillingType;
	}

	/**
	 * @param rpt013BillingType The rpt013BillingType to set.
	 */
	public void setRpt013BillingType(String rpt013BillingType) {
		this.rpt013BillingType = rpt013BillingType;
	}

	/**
	 * @return Returns the rpt013FromDate.
	 */
	public String getRpt013FromDate() {
		return rpt013FromDate;
	}

	/**
	 * @param rpt013FromDate The rpt013FromDate to set.
	 */
	public void setRpt013FromDate(String rpt013FromDate) {
		this.rpt013FromDate = rpt013FromDate;
	}

	/**
	 * @return Returns the rpt013ToDate.
	 */
	public String getRpt013ToDate() {
		return rpt013ToDate;
	}

	/**
	 * @param rpt013ToDate The rpt013ToDate to set.
	 */
	public void setRpt013ToDate(String rpt013ToDate) {
		this.rpt013ToDate = rpt013ToDate;
	}

	/**
	 * @return Returns the rpt014AirlineCode.
	 */
	public String getRpt014AirlineCode() {
		return rpt014AirlineCode;
	}

	/**
	 * @param rpt014AirlineCode The rpt014AirlineCode to set.
	 */
	public void setRpt014AirlineCode(String rpt014AirlineCode) {
		this.rpt014AirlineCode = rpt014AirlineCode;
	}

	/**
	 * @return Returns the rpt014ClrPrd.
	 */
	public String getRpt014ClrPrd() {
		return rpt014ClrPrd;
	}

	/**
	 * @param rpt014ClrPrd The rpt014ClrPrd to set.
	 */
	public void setRpt014ClrPrd(String rpt014ClrPrd) {
		this.rpt014ClrPrd = rpt014ClrPrd;
	}

	/**
	 * @return Returns the rpt014InvoiceNum.
	 */
	public String getRpt014InvoiceNum() {
		return rpt014InvoiceNum;
	}

	/**
	 * @param rpt014InvoiceNum The rpt014InvoiceNum to set.
	 */
	public void setRpt014InvoiceNum(String rpt014InvoiceNum) {
		this.rpt014InvoiceNum = rpt014InvoiceNum;
	}

	/**
	 * @return Returns the rpt040AirlineCode.
	 */
	public String getRpt040AirlineCode() {
		return rpt040AirlineCode;
	}

	/**
	 * @param rpt040AirlineCode The rpt040AirlineCode to set.
	 */
	public void setRpt040AirlineCode(String rpt040AirlineCode) {
		this.rpt040AirlineCode = rpt040AirlineCode;
	}

	/**
	 * @return Returns the rpt040AirlineNum.
	 */
	public String getRpt040AirlineNum() {
		return rpt040AirlineNum;
	}

	/**
	 * @param rpt040AirlineNum The rpt040AirlineNum to set.
	 */
	public void setRpt040AirlineNum(String rpt040AirlineNum) {
		this.rpt040AirlineNum = rpt040AirlineNum;
	}

	/**
	 * @return Returns the rpt040ClrPrd.
	 */
	public String getRpt040ClrPrd() {
		return rpt040ClrPrd;
	}

	/**
	 * @param rpt040ClrPrd The rpt040ClrPrd to set.
	 */
	public void setRpt040ClrPrd(String rpt040ClrPrd) {
		this.rpt040ClrPrd = rpt040ClrPrd;
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
