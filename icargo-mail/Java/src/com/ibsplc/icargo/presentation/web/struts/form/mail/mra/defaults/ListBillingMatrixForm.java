/*
 * ListBillingMatrixForm.java created on Feb 28, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2280
 *
 */
public class ListBillingMatrixForm extends ScreenModel{

	private static final String BUNDLE = "listbillingmatrix";

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID ="mailtracking.mra.defaults.listbillingmatrix";
	
	public static final String PAGINATION_MODE_FROM_FILTER="YES";//Added by A-5218 to enable Last Link in Pagination to start
	
	private String billingMatrixId="";
	private String poaCode="";
	private String poaName="";
	private String airlineCode="";
	private String validFrom="";
	private String validTo="";
	private String status="";
	private String displayPage="1";
	private String lastPageNum="0";
	private String changedStatus;
	private String canClose;
	private String [] checkboxes;
	private String selectedIndexes;
	/*for change status pop up*/

	private String popupStatus;
	private String fromPage;
	private String paginationMode;//Added by A-5218 to enable Last Link in Pagination to start
	
	
	/**
	 * @return the paginationMode
	 */
	public String getPaginationMode() {
		return paginationMode;
	}

	/**
	 * @param paginationMode the paginationMode to set
	 */
	public void setPaginationMode(String paginationMode) {
		this.paginationMode = paginationMode;
	}

	/**
	 * @return Returns the fromPage.
	 */
	public String getFromPage() {
		return fromPage;
	}

	/**
	 * @param fromPage The fromPage to set.
	 */
	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	/**
	 * @return Returns the popupStatus.
	 */
	public String getPopupStatus() {
		return popupStatus;
	}

	/**
	 * @param popupStatus The popupStatus to set.
	 */
	public void setPopupStatus(String popupStatus) {
		this.popupStatus = popupStatus;
	}

	/**
	 * @return Returns the selectedIndexes.
	 */
	public String getSelectedIndexes() {
		return selectedIndexes;
	}

	/**
	 * @param selectedIndexes The selectedIndexes to set.
	 */
	public void setSelectedIndexes(String selectedIndexes) {
		this.selectedIndexes = selectedIndexes;
	}

	/**
	 * 
	 */
	public String getScreenId() {
		// TODO Auto-generated method stub
		return SCREENID;
	}

	/**
	 * 
	 */
	public String getProduct() {
		// TODO Auto-generated method stub
		return PRODUCT;
	}

	/**
	 * 
	 */
	public String getSubProduct() {
		// TODO Auto-generated method stub
		return SUBPRODUCT;
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
	 * @return Returns the billingMatrixId.
	 */
	public String getBillingMatrixId() {
		return billingMatrixId;
	}

	/**
	 * @param billingMatrixId The billingMatrixId to set.
	 */
	public void setBillingMatrixId(String billingMatrixId) {
		this.billingMatrixId = billingMatrixId;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	

	/**
	 * @return Returns the poaCode.
	 */
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return Returns the poaName.
	 */
	public String getPoaName() {
		return poaName;
	}

	/**
	 * @param poaName The poaName to set.
	 */
	public void setPoaName(String poaName) {
		this.poaName = poaName;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the validFrom.
	 */
	@DateFieldId(id="ListBillingMatrixDateRange",fieldType="from")//Added By T-1925 for ICRD-9704
	public String getValidFrom() {
		return validFrom;
	}

	/**
	 * @param validFrom The validFrom to set.
	 */
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	/**
	 * @return Returns the validTo.
	 */
	@DateFieldId(id="ListBillingMatrixDateRange",fieldType="to")//Added By T-1925 for ICRD-9704
	public String getValidTo() {
		return validTo;
	}

	/**
	 * @param validTo The validTo to set.
	 */
	public void setValidTo(String validTo) {
		this.validTo = validTo;
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
	 * @return Returns the canClose.
	 */
	public String getCanClose() {
		return canClose;
	}

	/**
	 * @param canClose The canClose to set.
	 */
	public void setCanClose(String canClose) {
		this.canClose = canClose;
	}

	/**
	 * @return Returns the changedStatus.
	 */
	public String getChangedStatus() {
		return changedStatus;
	}

	/**
	 * @param changedStatus The changedStatus to set.
	 */
	public void setChangedStatus(String changedStatus) {
		this.changedStatus = changedStatus;
	}

	/**
	 * @return Returns the checkboxes.
	 */
	public String[] getCheckboxes() {
		return checkboxes;
	}

	/**
	 * @param checkboxes The checkboxes to set.
	 */
	public void setCheckboxes(String[] checkboxes) {
		this.checkboxes = checkboxes;
	}
	
	

	
	

	

	
	
}
