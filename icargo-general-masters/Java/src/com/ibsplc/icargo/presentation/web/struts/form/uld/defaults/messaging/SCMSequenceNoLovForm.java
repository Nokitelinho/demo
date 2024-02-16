/*
 * SCMSequenceNoLovForm.java Created on Aug 01, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * 
 * @author A-2046
 * 
 */
public class SCMSequenceNoLovForm extends ScreenModel {
	/**
	 * SCREEN_ID
	 */
	private static final String SCREEN_ID = "uld.defaults.scmseqnolov";

	/**
	 * PRODUCT_NAME
	 */
	private static final String PRODUCT_NAME = "uld";

	/**
	 * SUBPRODUCT_NAME
	 */
	private static final String SUBPRODUCT_NAME = "defaults";

	/**
	 * BUNDLE
	 */
	private static final String BUNDLE = "scmReconcileLovResources";

	private String companyCode;

	private String formNumber;

	private String textfiledObj;

	private String textfiledDesc;
	
	private String textfiledDate;
	private String textfiledTime;

	private String rowCount;

	private String displayPage = "1";

	private String lastPageNum = "0";

	private String sequenceNo;
	
	private String airline;
	
	private String airportCode;
	
	private String listSuccess;
	

	/**
	 * bundle
	 */
	private String bundle;
/**
 * 
 */
	public String getBundle() {
		return BUNDLE;
	}
/**
 * 
 * @param bundle
 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
/**
 * 
 * @return
 */
	public String getCompanyCode() {
		return companyCode;
	}
/**
 * 
 * @param companyCode
 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
/**
 * 
 * @return
 */
	public String getDisplayPage() {
		return displayPage;
	}
/**
 * 
 * @param displayPage
 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
/**
 * 
 * @return
 */
	public String getFormNumber() {
		return formNumber;
	}
/**
 * 
 * @param formNumber
 */
	public void setFormNumber(String formNumber) {
		this.formNumber = formNumber;
	}
/**
 * 
 * @return
 */
	public String getLastPageNum() {
		return lastPageNum;
	}
/**
 * 
 * @param lastPageNum
 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}
/**
 * 
 * @return
 */
	public String getRowCount() {
		return rowCount;
	}
/**
 * 
 * @param rowCount
 */
	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}
/**
 * 
 * @return
 */
	public String getSequenceNo() {
		return sequenceNo;
	}
/**
 * 
 * @param sequenceNo
 */
	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
/**
 * 
 * @return
 */
	public String getTextfiledDesc() {
		return textfiledDesc;
	}
/**
 * 
 * @param textfiledDesc
 */
	public void setTextfiledDesc(String textfiledDesc) {
		this.textfiledDesc = textfiledDesc;
	}
/**
 * 
 * @return
 */
	public String getTextfiledObj() {
		return textfiledObj;
	}
/**
 * 
 * @param textfiledObj
 */
	public void setTextfiledObj(String textfiledObj) {
		this.textfiledObj = textfiledObj;
	}

/**
 * 
 */
	public String getScreenId() {
		return SCREEN_ID;
	}

/**
 * 
 */
	public String getProduct() {
		return PRODUCT_NAME;
	}

/**
 * 
 */
	public String getSubProduct() {
		return SUBPRODUCT_NAME;
	}
/**
 * 
 * @return
 */
	public String getAirline() {
		return airline;
	}
/**
 * 
 * @param airline
 */
	public void setAirline(String airline) {
		this.airline = airline;
	}
/**
 * 
 * @return
 */
	public String getAirportCode() {
		return airportCode;
	}
/**
 * 
 * @param airportCode
 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
/**
 * @return Returns the textfiledDate.
 */
public String getTextfiledDate() {
	return textfiledDate;
}
/**
 * @param textfiledDate The textfiledDate to set.
 */
public void setTextfiledDate(String textfiledDate) {
	this.textfiledDate = textfiledDate;
}
/**
 * @return Returns the textfiledTime.
 */
public String getTextfiledTime() {
	return textfiledTime;
}
/**
 * @param textfiledTime The textfiledTime to set.
 */
public void setTextfiledTime(String textfiledTime) {
	this.textfiledTime = textfiledTime;
}
/**
 * @return the listSuccess
 */
public String getListSuccess() {
	return listSuccess;
}
/**
 * @param listSuccess the listSuccess to set
 */
public void setListSuccess(String listSuccess) {
	this.listSuccess = listSuccess;
}

}
