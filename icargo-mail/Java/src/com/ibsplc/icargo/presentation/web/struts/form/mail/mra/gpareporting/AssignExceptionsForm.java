/*
 * AssignExceptionsForm.java Created on Feb 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 *  
 * @author A-2257
 * @author A-2245
 * 
 /*
 * 
 * Revision History
 * 
 * Version      Date           		Author          	Description
 * 
 *  0.1         Feb 13, 2007  		Meera Vijayan		Initial draft
 *  
 *  0.2         Feb 21, 2007  		Divya S P			Revised draft
 *  
 */
public class AssignExceptionsForm extends ScreenModel {
   
    private static final String BUNDLE = "mraassignexceptions";
	
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mailtracking.mra.gpareporting.assignexceptions";
	
	/*Filter fields*/
	private String gpaCode="";
	private String gpaName="";
	private String countryCode="";
	private String fromDate="";
	private String toDate="";
	private String exceptionCode="";
	private String assignee="";
	
	/*Table fields*/
	private String[] assignedUser;
	private String[] assignedDate;
	private String[] resolvedDate;
	
	/*Hidden fields*/
	private String[] operationFlag;
	private String[] rowId;
	private String currentDialogOption = "";
	private String currentDialogId = "";
	private String saveFlag = "";
	private String windowFlag = "";
	
	/*Pagination Fields*/
	private String displayPage = "1";
	private String lastPageNum = "0";
	
	/*
	 * For ANZ CR : AirNZ1011 
	 */		
	private String mode;

    /**
	 * @return Returns the assignedDate.
	 */
	public String[] getAssignedDate() {
		return assignedDate;
	}

	/**
	 * @param assignedDate The assignedDate to set.
	 */
	public void setAssignedDate(String[] assignedDate) {
		this.assignedDate = assignedDate;
	}

	/**
	 * @return Returns the assignedUser.
	 */
	public String[] getAssignedUser() {
		return assignedUser;
	}

	/**
	 * @param assignedUser The assignedUser to set.
	 */
	public void setAssignedUser(String[] assignedUser) {
		this.assignedUser = assignedUser;
	}

	/**
	 * @return Returns the assignee.
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * @param assignee The assignee to set.
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	/**
	 * @return Returns the countryCode.
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode The countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return Returns the fromDate.
	 */
	@DateFieldId(id="AssignExceptionsDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
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
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	/**
	 * @return Returns the gpaName.
	 */
	public String getGpaName() {
		return gpaName;
	}

	/**
	 * @param gpaName The gpaName to set.
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String[] getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the resolvedDate.
	 */
	public String[] getResolvedDate() {
		return resolvedDate;
	}

	/**
	 * @param resolvedDate The resolvedDate to set.
	 */
	public void setResolvedDate(String[] resolvedDate) {
		this.resolvedDate = resolvedDate;
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
	 * @return Returns the toDate.
	 */
	@DateFieldId(id="AssignExceptionsDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

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
	 * @return Returns the exceptionCode.
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}

	/**
	 * @param exceptionCode The exceptionCode to set.
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return Returns the lastPageNum.
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * @param lastPageNum The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return Returns the currentDialogId.
	 */
	public String getCurrentDialogId() {
		return currentDialogId;
	}

	/**
	 * @param currentDialogId The currentDialogId to set.
	 */
	public void setCurrentDialogId(String currentDialogId) {
		this.currentDialogId = currentDialogId;
	}

	/**
	 * @return Returns the currentDialogOption.
	 */
	public String getCurrentDialogOption() {
		return currentDialogOption;
	}

	/**
	 * @param currentDialogOption The currentDialogOption to set.
	 */
	public void setCurrentDialogOption(String currentDialogOption) {
		this.currentDialogOption = currentDialogOption;
	}

	/**
	 * @return Returns the saveFlag.
	 */
	public String getSaveFlag() {
		return saveFlag;
	}

	/**
	 * @param saveFlag The saveFlag to set.
	 */
	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	/**
	 * @return Returns the windowFlag.
	 */
	public String getWindowFlag() {
		return windowFlag;
	}

	/**
	 * @param windowFlag The windowFlag to set.
	 */
	public void setWindowFlag(String windowFlag) {
		this.windowFlag = windowFlag;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}



}	