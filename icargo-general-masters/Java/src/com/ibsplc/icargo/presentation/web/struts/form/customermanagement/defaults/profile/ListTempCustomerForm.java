/*
 * ListTempCustomerForm.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1496
 *
 */
public class ListTempCustomerForm extends ScreenModel {


	private static final String BUNDLE = "listtempcustomerform";
	private static final String PRODUCT = "customermanagement";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "customermanagement.defaults.listtempcustomerform";
	public static final String PAGINATION_MODE_FROM_FILTER="YES";//Added by A-5218 to enable Last Link in Pagination

	private String bundle;
	private String station;
	private String listTempId;
	private String custCode;
	private String customerName;
	private String status;
	private String validFrom;
	private String validTo;
	private String displayPageNum="1";
	private String lastPageNum="0";
	private String fromList;
	private String[] rowId;
	private String detailsFlag;
	private String checkFlag;
	private String custCodeForAttach;
	private String attachFlag;
	private String customerCode;
	private String flag;
	private String closeStatus;
	private String paginationMode;//Added by A-5218 to enable Last Link in Pagination
	
		
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
	 * 
	 * @return
	 */
	public String getCloseStatus() {
		return closeStatus;
	}
	/**
	 * 
	 * @param closeStatus
	 */
	public void setCloseStatus(String closeStatus) {
		this.closeStatus = closeStatus;
	}
	/**
	 * 
	 * @return
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * 
	 * @param flag
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	/**
	 * @return Returns the customerCode.
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode The customerCode to set.
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return Returns the attachFlag.
	 */
	public String getAttachFlag() {
		return attachFlag;
	}
	/**
	 * @param attachFlag The attachFlag to set.
	 */
	public void setAttachFlag(String attachFlag) {
		this.attachFlag = attachFlag;
	}
	/**
	 * @return Returns the custCodeForAttach.
	 */
	public String getCustCodeForAttach() {
		return custCodeForAttach;
	}
	/**
	 * @param custCodeForAttach The custCodeForAttach to set.
	 */
	public void setCustCodeForAttach(String custCodeForAttach) {
		this.custCodeForAttach = custCodeForAttach;
	}
	/**
	 * @return Returns the checkFlag.
	 */
	public String getCheckFlag() {
		return checkFlag;
	}
	/**
	 * @param checkFlag The checkFlag to set.
	 */
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}
	/**
	 * 
	 * @return detailsFlag
	 */
	public String getDetailsFlag() {
		return detailsFlag;
	}
	/**
	 * 
	 * @param detailsFlag
	 */
	public void setDetailsFlag(String detailsFlag) {
		this.detailsFlag = detailsFlag;
	}
	
	/**
	 * 
	 * @return rowId
	 */
	public String[] getRowId() {
		return rowId;
	}
	/**
	 * 
	 * @param rowId
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}
	/**
	 * 
	 * @return displayPage
	 */
	public String getDisplayPageNum() {
		return displayPageNum;
	}
/**
 * 
 * @param displayPageNum
 */
	public void setDisplayPageNum(String displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
	/**
	 * 
	 * @return fromList
	 */
	public String getFromList() {
		return fromList;
	}
	/**
	 * 
	 * @param fromList
	 */
	public void setFromList(String fromList) {
		this.fromList = fromList;
	}
	/**
	 * 
	 * @return lastPageNum
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
	 * @return validFrom
	 */
	@ DateFieldId(id="ListTemporaryCustomersDateRange",fieldType="from")/*Added By A-5131 for ICRD-9704*/
	public String getValidFrom() {
		return validFrom;
	}
	/**
	 * 
	 * @param validFrom
	 */
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	/**
	 * 
	 * @return validTo
	 */
	@ DateFieldId(id="ListTemporaryCustomersDateRange",fieldType="to")/*Added By A-5131 for ICRD-9704*/
	public String getValidTo() {
		return validTo;
	}
	/**
	 * 
	 * @param validTo
	 */
	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}
	/**
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 
	 * @return customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * 
	 * @param customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * 
	 * @return tempId
	 */
	public String getListTempId() {
		return listTempId;
	}
/**
 * 
 * @param listTempId
 */
	public void setListTempId(String listTempId) {
		this.listTempId = listTempId;
	}
	/**
	 * 
	 * @return station
	 */
	public String getStation() {
		return station;
	}
	/**
	 * 
	 * @param station
	 */
	public void setStation(String station) {
		this.station = station;
	}

/**
 * 
 */
    public String getScreenId() {
        return SCREENID;
    }

  /**
   * 
   */
    public String getProduct() {
        return PRODUCT;
    }

  /**
   * 
   */
    public String getSubProduct() {
        return SUBPRODUCT;
    }
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
	public String getCustCode() {
		return custCode;
	}
/**
 * 
 * @param custCode
 */
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
}
