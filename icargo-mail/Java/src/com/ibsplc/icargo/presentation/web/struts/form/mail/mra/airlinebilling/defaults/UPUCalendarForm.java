/* * UPUCalendarForm.java Created on Sep 28, 2006 * * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. * * This software is the proprietary information of IBS Software Services (P) Ltd. * Use is subject to license terms. */package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;import com.ibsplc.icargo.framework.model.ScreenModel;
/** *  *  * @author a-2521 * */
public class UPUCalendarForm extends ScreenModel{
	private static final String BUNDLE = "UPUCalendarResources";	private String[] rowCount;	
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra.airlinebilling";
	private static final String SCREENID =		"mailtracking.mra.airlinebilling.defaults.upucalendar";	private String clrHsCodeLst;
    private String fromDateLst;
    private String toDateLst;        private String[] billingPeriod;        //   private String[] nonichBillingPeriod;        private String[] fromDate;        private String[] toDate;        private String[] submissionDate;        private String[] generateAfterToDate;    private String[] operationalFlag;        private String[]lastUpdateUser;        private String[]lastUpdateTime;
    
	/**	 * @return Returns the operationalFlag.	 */	public String[] getOperationalFlag() {		return operationalFlag;	}	/**	 * @param operationalFlag The operationalFlag to set.	 */	public void setOperationalFlag(String[] operationalFlag) {		this.operationalFlag = operationalFlag;	}   	/**
	 * Constructor
	 */
	public UPUCalendarForm() {
		super();
	}
	/**	 * @return SCREENID	 */
	public String getScreenId() {
        return SCREENID;
    }  	/**	 * @return PRODUCT	 */
    public String getProduct() {
        return PRODUCT;
    }    /**	 * @return SUBPRODUCT	 */
    public String getSubProduct() {
        return SUBPRODUCT;
    }

	/**	 * Returns the bundle.	 * 
	 * @return BUNDLE 
	 */
	public String getBundle() {
		return BUNDLE;
	}	/**	 * @return Returns the clrHsCodeLst.	 */	public String getClrHsCodeLst() {		return clrHsCodeLst;	}	/**	 * @param clrHsCodeLst The clrHsCodeLst to set.	 */	public void setClrHsCodeLst(String clrHsCodeLst) {		this.clrHsCodeLst = clrHsCodeLst;	}	/**	 * @return Returns the fromDate.	 */	public String[] getFromDate() {		return fromDate;	}	/**	 * @param fromDate The fromDate to set.	 */	public void setFromDate(String[] fromDate) {		this.fromDate = fromDate;	}	/**	 * @return Returns the fromDateLst.	 */	@ DateFieldId(id="UPUCalenderDateRange",fieldType="from")/*Added By A-5131 for ICRD-9704*/	public String getFromDateLst() {		return fromDateLst;	}	/**	 * @param fromDateLst The fromDateLst to set.	 */	public void setFromDateLst(String fromDateLst) {		this.fromDateLst = fromDateLst;	}	/**	 * @return Returns the generateAfterToDate.	 */	public String[] getGenerateAfterToDate() {		return generateAfterToDate;	}	/**	 * @param generateAfterToDate The generateAfterToDate to set.	 */	public void setGenerateAfterToDate(String[] generateAfterToDate) {		this.generateAfterToDate = generateAfterToDate;	}    	/**	 * @return the lastUpdateTime	 */	public String[] getLastUpdateTime() {		return lastUpdateTime;	}	/**	 * @param lastUpdateTime the lastUpdateTime to set	 */	public void setLastUpdateTime(String[] lastUpdateTime) {		this.lastUpdateTime = lastUpdateTime;	}	/**	 * @return the lastUpdateUser	 */	public String[] getLastUpdateUser() {		return lastUpdateUser;	}	/**	 * @param lastUpdateUser the lastUpdateUser to set	 */	public void setLastUpdateUser(String[] lastUpdateUser) {		this.lastUpdateUser = lastUpdateUser;	}	/**	 * @return Returns the rowCount.	 */	public String[] getRowCount() {		return rowCount;	}	/**	 * @param rowCount The rowCount to set.	 */	public void setRowCount(String[] rowCount) {		this.rowCount = rowCount;	}	/**	 * @return Returns the submissionDate.	 */	public String[] getSubmissionDate() {		return submissionDate;	}	/**	 * @param submissionDate The submissionDate to set.	 */	public void setSubmissionDate(String[] submissionDate) {		this.submissionDate = submissionDate;	}	/**	 * @return Returns the toDate.	 */	public String[] getToDate() {		return toDate;	}	/**	 * @param toDate The toDate to set.	 */	public void setToDate(String[] toDate) {		this.toDate = toDate;	}	/**	 * @return Returns the toDateLst.	 */	@ DateFieldId(id="UPUCalenderDateRange",fieldType="to")/*Added By A-5131 for ICRD-9704*/	public String getToDateLst() {		return toDateLst;	}	/**	 * @param toDateLst The toDateLst to set.	 */	public void setToDateLst(String toDateLst) {		this.toDateLst = toDateLst;	}	/**	 * @return Returns the billingPeriod.	 */	public String[] getBillingPeriod() {		return billingPeriod;	}	/**	 * @param billingPeriod The billingPeriod to set.	 */	public void setBillingPeriod(String[] billingPeriod) {		this.billingPeriod = billingPeriod;	}}