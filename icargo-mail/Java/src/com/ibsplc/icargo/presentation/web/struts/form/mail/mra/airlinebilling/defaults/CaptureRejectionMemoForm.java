/*
 * CaptureRejectionMemoForm.java Created on Feb 16,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults;




import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author Ruby Abraham
 * Form for Capture Rejection Memo screen.
 *
 * Revision History
 *
 * Version        Date           		  Author          		    Description
 *
 *  0.1        Feb 16, 2007    			Ruby Abraham   	    	   Initial draft
 */

public class CaptureRejectionMemoForm extends ScreenModel {

    private static final String BUNDLE = "capturerejectionmemoresources";

	//private String bundle;

	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.capturerejectionmemo";



	private String airlineCode;

	private String invoiceNo;
	/**
	 * Clearance Period
	 */
	private String clearancePeriod;
	
	private String interlineBillingType;



	private String[] rejectionMemoNo;


	private String[] rejectionDate;
	
	private String[] invoiceNos;
	
	private String[] clearancePeriods;

	private double[] provisionalAmount;
	
	private double[] reportedAmount;
	
	private double[] rejectedAmount;
	
	private double[] previousDifferenceAmount;


	private String[] remarks;


	private boolean isSelectAll;

	private String[] select;



	private String[] selectedElements;

	private String[] operationalFlag;
	private String linkStatusFlag;
	private String  editFormOneFlag;
	private String statusFlag;

	//Variable for coming from other screens

	private String screenFlag;

	private String selectedRow;

	//Variable for checking from which screen this Form One screen is coming

	private String closeFlag;



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
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
	//	this.bundle = bundle;
	}



	/**
	 * @return Returns the clearancePeriod.
	 */
	public String getClearancePeriod() {
		return clearancePeriod;
	}

	/**
	 * @param clearancePeriod The clearancePeriod to set.
	 */
	public void setClearancePeriod(String clearancePeriod) {
		this.clearancePeriod = clearancePeriod;
	}

	/**
	 * @return Returns the interlineBillingType.
	 */
	public String getInterlineBillingType() {
		return interlineBillingType;
	}

	/**
	 * @param interlineBillingType The interlineBillingType to set.
	 */
	public void setInterlineBillingType(String interlineBillingType) {
		this.interlineBillingType = interlineBillingType;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}


	/**
	 * @return Returns the invoiceNo.
	 */
	public String getInvoiceNo() {
		return invoiceNo;
	}

	/**
	 * @param invoiceNo The invoiceNo to set.
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}


		/**
	 * @return Returns the rejectionMemoNo.
	 */
	public String[] getRejectionMemoNo() {
		return rejectionMemoNo;
	}

	/**
	 * @param rejectionMemoNo The rejectionMemoNo to set.
	 */
	public void setRejectionMemoNo(String[] rejectionMemoNo) {
		this.rejectionMemoNo = rejectionMemoNo;
	}

	/**
	 * @return Returns the rejectionDate.
	 */
	public String[] getRejectionDate() {
		return rejectionDate;
	}

	/**
	 * @param rejectionDate The rejectionDate to set.
	 */
	public void setRejectionDate(String[] rejectionDate) {
		this.rejectionDate = rejectionDate;
	}





	/**
	 * @return Returns the invoiceNos.
	 */
	public String[] getInvoiceNos() {
		return invoiceNos;
	}

	/**
	 * @param invoiceNos The invoiceNos to set.
	 */
	public void setInvoiceNos(String[] invoiceNos) {
		this.invoiceNos = invoiceNos;
	}

	/**
	 * @return Returns the clearancePeriods.
	 */
	public String[] getClearancePeriods() {
		return clearancePeriods;
	}

	/**
	 * @param clearancePeriods The clearancePeriods to set.
	 */
	public void setClearancePeriods(String[] clearancePeriods) {
		this.clearancePeriods = clearancePeriods;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String[] getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}


	/**
	 * @return Returns the provisionalAmount.
	 */
	public double[] getProvisionalAmount() {
		return provisionalAmount;
	}

	/**
	 * @param provisionalAmount The provisionalAmount to set.
	 */
	public void setProvisionalAmount(double[] provisionalAmount) {
		this.provisionalAmount = provisionalAmount;
	}


	/**
	 * @return Returns the rejectedAmount.
	 */
	public double[] getRejectedAmount() {
		return rejectedAmount;
	}

	/**
	 * @param rejectedAmount The rejectedAmount to set.
	 */
	public void setRejectedAmount(double[] rejectedAmount) {
		this.rejectedAmount = rejectedAmount;
	}

	/**
	 * @return Returns the reportedAmount.
	 */
	public double[] getReportedAmount() {
		return reportedAmount;
	}

	/**
	 * @param reportedAmount The reportedAmount to set.
	 */
	public void setReportedAmount(double[] reportedAmount) {
		this.reportedAmount = reportedAmount;
	}

	/**
	 * @return Returns the isSelectAll.
	 */
	public boolean isSelectAll() {
		return this.isSelectAll;
	}

	/**
	 * @param isSelectAll The isSelectAll to set.
	 */
	public void setSelectAll(boolean isSelectAll) {
		this.isSelectAll = isSelectAll;
	}

	/**
	 * @return Returns the select.
	 */
	public String[] getSelect() {
		return this.select;
	}

	/**
	 * @param select The select to set.
	 */
	public void setSelect(String[] select) {
		this.select = select;
	}




	/**
	 * @return Returns the operationalFlag.
	 */
	public String[] getOperationalFlag() {
		return this.operationalFlag;
	}

	/**
	 * @param operationalFlag The operationalFlag to set.
	 */
	public void setOperationalFlag(String[] operationalFlag) {
		this.operationalFlag = operationalFlag;
	}
	/**
	 *
	 * @return String
	 */
	public String getLinkStatusFlag() {
		return linkStatusFlag;
	}
	/**
	 *
	 * @param linkStatusFlag
	 */
	public void setLinkStatusFlag(String linkStatusFlag) {
		this.linkStatusFlag = linkStatusFlag;
	}

	/**
	 * @return Returns the screenFlag.
	 */
	public String getScreenFlag() {
		return screenFlag;
	}

	/**
	 * @param screenFlag The screenFlag to set.
	 */
	public void setScreenFlag(String screenFlag) {
		this.screenFlag = screenFlag;
	}

	/**
	 * @return Returns the selectedRow.
	 */
	public String getSelectedRow() {
		return selectedRow;
	}

	/**
	 * @param selectedRow The selectedRow to set.
	 */
	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}

	/**
	 * @return Returns the closeFlag.
	 */
	public String getCloseFlag() {
		return closeFlag;
	}

	/**
	 * @param closeFlag The closeFlag to set.
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}

	/**
	 * @return Returns the selectedElements.
	 */
	public String[] getSelectedElements() {
		return selectedElements;
	}

	/**
	 * @param selectedElements The selectedElements to set.
	 */
	public void setSelectedElements(String[] selectedElements) {
		this.selectedElements = selectedElements;
	}

	/**
	 * @return Returns the editFormOneFlag.
	 */
	public String getEditFormOneFlag() {
		return editFormOneFlag;
	}

	/**
	 * @param editFormOneFlag The editFormOneFlag to set.
	 */
	public void setEditFormOneFlag(String editFormOneFlag) {
		this.editFormOneFlag = editFormOneFlag;
	}

	/**
	 * @return Returns the statusFlag.
	 */
	public String getStatusFlag() {
		return statusFlag;
	}

	/**
	 * @param statusFlag The statusFlag to set.
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	/**
	 * @return Returns the previousDifferenceAmount.
	 */
	public double[] getPreviousDifferenceAmount() {
		return previousDifferenceAmount;
	}

	/**
	 * @param previousDifferenceAmount The previousDifferenceAmount to set.
	 */
	public void setPreviousDifferenceAmount(double[] previousDifferenceAmount) {
		this.previousDifferenceAmount = previousDifferenceAmount;
	}

}
