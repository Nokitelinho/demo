/*
 * ListRateAuditForm.java Created on Jun 20, 2008	
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * @author A-3108
 *
 */
public class ListRateAuditForm extends ScreenModel{
	
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mailtracking.mra.defaults.listrateaudit";
	
    private static final String BUNDLE = "mralistrateaudit";
    
    private String dsn;
    private String dsnDate;
    private String gpaCode;
    private String dsnStatus;
    private String carrierCode;
    private String flightNo;
    private String flightDate;
    private String subClass;
    private String fromDate;
    private String toDate;
    
    private int rowId;
    private int selectedRowIndex;
    private String selectedRows;
    //Added for AirNZ CR 174
    private String applyAutd;
    private String[] applyAutdits;
    private String rateauditFlag;
    private String lastPageNumber = "0";
    private String displayPageNum = "1";
    private String[] saveDsnStatus;
	
    private String[] excepFlg;
	

	/**
	 * @return the excepFlg
	 */
	public String[] getExcepFlg() {
		return excepFlg;
	}

	/**
	 * @param excepFlg the excepFlg to set
	 */
	public void setExcepFlg(String[] excepFlg) {
		this.excepFlg = excepFlg;
	}

	

	/**
	 * @return Returns the rateauditFlag.
	 */
	public String getRateauditFlag() {
		return rateauditFlag;
	}

	/**
	 * @param rateauditFlag The rateauditFlag to set.
	 */
	public void setRateauditFlag(String rateauditFlag) {
		this.rateauditFlag = rateauditFlag;
	}

	/**
	 * @return Returns the applyAutd.
	 */
	public String getApplyAutd() {
		return applyAutd;
	}

	/**
	 * @param applyAutd The applyAutd to set.
	 */
	public void setApplyAutd(String applyAutd) {
		this.applyAutd = applyAutd;
	}

	/**
	 * @return Returns the applyAutdits.
	 */
	public String[] getApplyAutdits() {
		return applyAutdits;
	}

	/**
	 * @param applyAutdits The applyAutdits to set.
	 */
	public void setApplyAutdits(String[] applyAutdits) {
		this.applyAutdits = applyAutdits;
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

	public String getDsn() {
		return dsn;
	}

	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	public String getDsnDate() {
		return dsnDate;
	}

	public void setDsnDate(String dsnDate) {
		this.dsnDate = dsnDate;
	}

	public String getDsnStatus() {
		return dsnStatus;
	}

	public void setDsnStatus(String dsnStatus) {
		this.dsnStatus = dsnStatus;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	

	public String getGpaCode() {
		return gpaCode;
	}

	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	public String getSubClass() {
		return subClass;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public int getSelectedRowIndex() {
		return selectedRowIndex;
	}

	public void setSelectedRowIndex(int selectedRowIndex) {
		this.selectedRowIndex = selectedRowIndex;
	}

	public String getSelectedRows() {
		return selectedRows;
	}

	public void setSelectedRows(String selectedRows) {
		this.selectedRows = selectedRows;
	}

	/**
	 * @return Returns the saveDsnStatus.
	 */
	public String[] getSaveDsnStatus() {
		return saveDsnStatus;
	}

	/**
	 * @param saveDsnStatus The saveDsnStatus to set.
	 */
	public void setSaveDsnStatus(String[] saveDsnStatus) {
		this.saveDsnStatus = saveDsnStatus;
	}

	/**
	 * @return Returns the displayPageNum.
	 */
	public String getDisplayPageNum() {
		return displayPageNum;
	}

	/**
	 * @param displayPageNum The displayPageNum to set.
	 */
	public void setDisplayPageNum(String displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	/**
	 * @return Returns the lastPageNumber.
	 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}

	/**
	 * @param lastPageNumber The lastPageNumber to set.
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	
}
