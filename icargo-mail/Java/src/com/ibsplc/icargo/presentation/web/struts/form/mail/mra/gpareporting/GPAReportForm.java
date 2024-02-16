/*
 * GPAReportForm.java Created on Mar 7, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting;

import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * 
 * @author A-2245
 * 
 * 
 * Revision History
 * 
 * Version      Date           		Author          	Description
 * 
 *  0.1         Mar 7, 2007  		A-2245				Initial draft
 *  
 */
public class GPAReportForm extends ScreenModel {
   
    private static final String BUNDLE = "gpareportResources";
	
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mailtracking.mra.gpareporting.gpareport";
	
	/*Filter fields*/
	private String reportId="";
	/*
	 * for exception report by assignee details
	 */
	private String assigneeDetailsGPACode="";
	private String assigneeDetailsGPAName="";
	private String assigneeDetailsCountryCode="";
	private String assigneeDetailsFromDate="";
	private String assigneeDetailsToDate="";
	private String assigneeDetailsExceptionCode="";
	private String assigneeDetailsAssignee="";

	/*
	 * for exception report by assignee summary
	 */
	private String assigneeSummaryGPACode="";
	private String assigneeSummaryGPAName="";
	private String assigneeSummaryCountryCode="";
	private String assigneeSummaryFromDate="";
	private String assigneeSummaryToDate="";
	private String assigneeSummaryExceptionCode="";
	private String assigneeSummaryAssignee="";

	/*
	 * for exception report details
	 */
	private String exceptionDetailsGPACode="";
	private String exceptionDetailsGPAName="";
	private String exceptionDetailsCountryCode="";
	private String exceptionDetailsFromDate="";
	private String exceptionDetailsToDate="";
	private String exceptionDetailsExceptionCode="";
	private String exceptionDetailsAssignee="";

	/*
	 * for exception report summary
	 */
	private String exceptionSummaryGPACode="";
	private String exceptionSummaryGPAName="";
	private String exceptionSummaryCountryCode="";
	private String exceptionSummaryFromDate="";
	private String exceptionSummaryToDate="";
	private String exceptionSummaryExceptionCode="";


	/** (non-Javadoc)
     * @return SCREENID  String
     */
    public String getScreenId() {
        return SCREENID;
    }

    /** (non-Javadoc)
     * @return PRODUCT  String
     */
    public String getProduct() {
        return PRODUCT;
    }

    /** (non-Javadoc)
     * @return SUBPRODUCT  String
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
	 * @return Returns the assigneeDetailsAssignee.
	 */
	public String getAssigneeDetailsAssignee() {
		return assigneeDetailsAssignee;
	}

	/**
	 * @param assigneeDetailsAssignee The assigneeDetailsAssignee to set.
	 */
	public void setAssigneeDetailsAssignee(String assigneeDetailsAssignee) {
		this.assigneeDetailsAssignee = assigneeDetailsAssignee;
	}

	/**
	 * @return Returns the assigneeDetailsCountryCode.
	 */
	public String getAssigneeDetailsCountryCode() {
		return assigneeDetailsCountryCode;
	}

	/**
	 * @param assigneeDetailsCountryCode The assigneeDetailsCountryCode to set.
	 */
	public void setAssigneeDetailsCountryCode(String assigneeDetailsCountryCode) {
		this.assigneeDetailsCountryCode = assigneeDetailsCountryCode;
	}

	/**
	 * @return Returns the assigneeDetailsExceptionCode.
	 */
	public String getAssigneeDetailsExceptionCode() {
		return assigneeDetailsExceptionCode;
	}

	/**
	 * @param assigneeDetailsExceptionCode The assigneeDetailsExceptionCode to set.
	 */
	public void setAssigneeDetailsExceptionCode(String assigneeDetailsExceptionCode) {
		this.assigneeDetailsExceptionCode = assigneeDetailsExceptionCode;
	}

	/**
	 * @return Returns the assigneeDetailsFromDate.
	 */
	public String getAssigneeDetailsFromDate() {
		return assigneeDetailsFromDate;
	}

	/**
	 * @param assigneeDetailsFromDate The assigneeDetailsFromDate to set.
	 */
	public void setAssigneeDetailsFromDate(String assigneeDetailsFromDate) {
		this.assigneeDetailsFromDate = assigneeDetailsFromDate;
	}

	/**
	 * @return Returns the assigneeDetailsGPACode.
	 */
	public String getAssigneeDetailsGPACode() {
		return assigneeDetailsGPACode;
	}

	/**
	 * @param assigneeDetailsGPACode The assigneeDetailsGPACode to set.
	 */
	public void setAssigneeDetailsGPACode(String assigneeDetailsGPACode) {
		this.assigneeDetailsGPACode = assigneeDetailsGPACode;
	}

	/**
	 * @return Returns the assigneeDetailsGPAName.
	 */
	public String getAssigneeDetailsGPAName() {
		return assigneeDetailsGPAName;
	}

	/**
	 * @param assigneeDetailsGPAName The assigneeDetailsGPAName to set.
	 */
	public void setAssigneeDetailsGPAName(String assigneeDetailsGPAName) {
		this.assigneeDetailsGPAName = assigneeDetailsGPAName;
	}

	/**
	 * @return Returns the assigneeDetailsToDate.
	 */
	public String getAssigneeDetailsToDate() {
		return assigneeDetailsToDate;
	}

	/**
	 * @param assigneeDetailsToDate The assigneeDetailsToDate to set.
	 */
	public void setAssigneeDetailsToDate(String assigneeDetailsToDate) {
		this.assigneeDetailsToDate = assigneeDetailsToDate;
	}

	/**
	 * @return Returns the reportId.
	 */
	public String getReportId() {
		return reportId;
	}

	/**
	 * @param reportId The reportId to set.
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return Returns the assigneeSummaryAssignee.
	 */
	public String getAssigneeSummaryAssignee() {
		return assigneeSummaryAssignee;
	}

	/**
	 * @param assigneeSummaryAssignee The assigneeSummaryAssignee to set.
	 */
	public void setAssigneeSummaryAssignee(String assigneeSummaryAssignee) {
		this.assigneeSummaryAssignee = assigneeSummaryAssignee;
	}

	/**
	 * @return Returns the assigneeSummaryCountryCode.
	 */
	public String getAssigneeSummaryCountryCode() {
		return assigneeSummaryCountryCode;
	}

	/**
	 * @param assigneeSummaryCountryCode The assigneeSummaryCountryCode to set.
	 */
	public void setAssigneeSummaryCountryCode(String assigneeSummaryCountryCode) {
		this.assigneeSummaryCountryCode = assigneeSummaryCountryCode;
	}

	/**
	 * @return Returns the assigneeSummaryExceptionCode.
	 */
	public String getAssigneeSummaryExceptionCode() {
		return assigneeSummaryExceptionCode;
	}

	/**
	 * @param assigneeSummaryExceptionCode The assigneeSummaryExceptionCode to set.
	 */
	public void setAssigneeSummaryExceptionCode(String assigneeSummaryExceptionCode) {
		this.assigneeSummaryExceptionCode = assigneeSummaryExceptionCode;
	}

	/**
	 * @return Returns the assigneeSummaryFromDate.
	 */
	public String getAssigneeSummaryFromDate() {
		return assigneeSummaryFromDate;
	}

	/**
	 * @param assigneeSummaryFromDate The assigneeSummaryFromDate to set.
	 */
	public void setAssigneeSummaryFromDate(String assigneeSummaryFromDate) {
		this.assigneeSummaryFromDate = assigneeSummaryFromDate;
	}

	/**
	 * @return Returns the assigneeSummaryGPACode.
	 */
	public String getAssigneeSummaryGPACode() {
		return assigneeSummaryGPACode;
	}

	/**
	 * @param assigneeSummaryGPACode The assigneeSummaryGPACode to set.
	 */
	public void setAssigneeSummaryGPACode(String assigneeSummaryGPACode) {
		this.assigneeSummaryGPACode = assigneeSummaryGPACode;
	}

	/**
	 * @return Returns the assigneeSummaryGPAName.
	 */
	public String getAssigneeSummaryGPAName() {
		return assigneeSummaryGPAName;
	}

	/**
	 * @param assigneeSummaryGPAName The assigneeSummaryGPAName to set.
	 */
	public void setAssigneeSummaryGPAName(String assigneeSummaryGPAName) {
		this.assigneeSummaryGPAName = assigneeSummaryGPAName;
	}

	/**
	 * @return Returns the assigneeSummaryToDate.
	 */
	public String getAssigneeSummaryToDate() {
		return assigneeSummaryToDate;
	}

	/**
	 * @param assigneeSummaryToDate The assigneeSummaryToDate to set.
	 */
	public void setAssigneeSummaryToDate(String assigneeSummaryToDate) {
		this.assigneeSummaryToDate = assigneeSummaryToDate;
	}

	/**
	 * @return Returns the exceptionDetailsAssignee.
	 */
	public String getExceptionDetailsAssignee() {
		return exceptionDetailsAssignee;
	}

	/**
	 * @param exceptionDetailsAssignee The exceptionDetailsAssignee to set.
	 */
	public void setExceptionDetailsAssignee(String exceptionDetailsAssignee) {
		this.exceptionDetailsAssignee = exceptionDetailsAssignee;
	}

	/**
	 * @return Returns the exceptionDetailsCountryCode.
	 */
	public String getExceptionDetailsCountryCode() {
		return exceptionDetailsCountryCode;
	}

	/**
	 * @param exceptionDetailsCountryCode The exceptionDetailsCountryCode to set.
	 */
	public void setExceptionDetailsCountryCode(String exceptionDetailsCountryCode) {
		this.exceptionDetailsCountryCode = exceptionDetailsCountryCode;
	}

	/**
	 * @return Returns the exceptionDetailsExceptionCode.
	 */
	public String getExceptionDetailsExceptionCode() {
		return exceptionDetailsExceptionCode;
	}

	/**
	 * @param exceptionDetailsExceptionCode The exceptionDetailsExceptionCode to set.
	 */
	public void setExceptionDetailsExceptionCode(
			String exceptionDetailsExceptionCode) {
		this.exceptionDetailsExceptionCode = exceptionDetailsExceptionCode;
	}

	/**
	 * @return Returns the exceptionDetailsFromDate.
	 */
	public String getExceptionDetailsFromDate() {
		return exceptionDetailsFromDate;
	}

	/**
	 * @param exceptionDetailsFromDate The exceptionDetailsFromDate to set.
	 */
	public void setExceptionDetailsFromDate(String exceptionDetailsFromDate) {
		this.exceptionDetailsFromDate = exceptionDetailsFromDate;
	}

	/**
	 * @return Returns the exceptionDetailsGPACode.
	 */
	public String getExceptionDetailsGPACode() {
		return exceptionDetailsGPACode;
	}

	/**
	 * @param exceptionDetailsGPACode The exceptionDetailsGPACode to set.
	 */
	public void setExceptionDetailsGPACode(String exceptionDetailsGPACode) {
		this.exceptionDetailsGPACode = exceptionDetailsGPACode;
	}

	/**
	 * @return Returns the exceptionDetailsGPAName.
	 */
	public String getExceptionDetailsGPAName() {
		return exceptionDetailsGPAName;
	}

	/**
	 * @param exceptionDetailsGPAName The exceptionDetailsGPAName to set.
	 */
	public void setExceptionDetailsGPAName(String exceptionDetailsGPAName) {
		this.exceptionDetailsGPAName = exceptionDetailsGPAName;
	}

	/**
	 * @return Returns the exceptionDetailsToDate.
	 */
	public String getExceptionDetailsToDate() {
		return exceptionDetailsToDate;
	}

	/**
	 * @param exceptionDetailsToDate The exceptionDetailsToDate to set.
	 */
	public void setExceptionDetailsToDate(String exceptionDetailsToDate) {
		this.exceptionDetailsToDate = exceptionDetailsToDate;
	}

	/**
	 * @return Returns the exceptionSummaryCountryCode.
	 */
	public String getExceptionSummaryCountryCode() {
		return exceptionSummaryCountryCode;
	}

	/**
	 * @param exceptionSummaryCountryCode The exceptionSummaryCountryCode to set.
	 */
	public void setExceptionSummaryCountryCode(String exceptionSummaryCountryCode) {
		this.exceptionSummaryCountryCode = exceptionSummaryCountryCode;
	}

	/**
	 * @return Returns the exceptionSummaryExceptionCode.
	 */
	public String getExceptionSummaryExceptionCode() {
		return exceptionSummaryExceptionCode;
	}

	/**
	 * @param exceptionSummaryExceptionCode The exceptionSummaryExceptionCode to set.
	 */
	public void setExceptionSummaryExceptionCode(
			String exceptionSummaryExceptionCode) {
		this.exceptionSummaryExceptionCode = exceptionSummaryExceptionCode;
	}

	/**
	 * @return Returns the exceptionSummaryFromDate.
	 */
	public String getExceptionSummaryFromDate() {
		return exceptionSummaryFromDate;
	}

	/**
	 * @param exceptionSummaryFromDate The exceptionSummaryFromDate to set.
	 */
	public void setExceptionSummaryFromDate(String exceptionSummaryFromDate) {
		this.exceptionSummaryFromDate = exceptionSummaryFromDate;
	}

	/**
	 * @return Returns the exceptionSummaryGPACode.
	 */
	public String getExceptionSummaryGPACode() {
		return exceptionSummaryGPACode;
	}

	/**
	 * @param exceptionSummaryGPACode The exceptionSummaryGPACode to set.
	 */
	public void setExceptionSummaryGPACode(String exceptionSummaryGPACode) {
		this.exceptionSummaryGPACode = exceptionSummaryGPACode;
	}

	/**
	 * @return Returns the exceptionSummaryGPAName.
	 */
	public String getExceptionSummaryGPAName() {
		return exceptionSummaryGPAName;
	}

	/**
	 * @param exceptionSummaryGPAName The exceptionSummaryGPAName to set.
	 */
	public void setExceptionSummaryGPAName(String exceptionSummaryGPAName) {
		this.exceptionSummaryGPAName = exceptionSummaryGPAName;
	}

	/**
	 * @return Returns the exceptionSummaryToDate.
	 */
	public String getExceptionSummaryToDate() {
		return exceptionSummaryToDate;
	}

	/**
	 * @param exceptionSummaryToDate The exceptionSummaryToDate to set.
	 */
	public void setExceptionSummaryToDate(String exceptionSummaryToDate) {
		this.exceptionSummaryToDate = exceptionSummaryToDate;
	}

}	