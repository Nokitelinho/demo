/*
 * ListPointsAccumulatedFilterVO.java Created on Dec 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo;



import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class ListPointsAccumulatedFilterVO  extends AbstractVO {

    private String companyCode;
    private String customerCode;
    private String awbNumber;
    private String houseAwbNumber;
    private int masterOwnerIdentifier;
    private int awbDuplicateNumber;
    private int awbSequenceNumber;
    private LocalDate fromDate;
    private LocalDate toDate;
    private int absoluteIndex;
    //Added by : A-5175 on 24-Oct-2012 starts
    private int pageNumber;
    private int totalRecordCount;
    //Added by : A-5175 on 24-Oct-2012 ends
    
	/**
	 * 	Getter for pageNumber 
	 *	Added by : A-5175 on 24-Oct-2012
	 * 	Used for : icrd-22065
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 *  @param pageNumber the pageNumber to set
	 * 	Setter for pageNumber 
	 *	Added by : A-5175 on 24-Oct-2012
	 * 	Used for : icrd-22065
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	/**
	 * 	Getter for totalRecordCount 
	 *	Added by : A-5175 on 24-Oct-2012
	 * 	Used for : icrd-22065
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	/**
	 *  @param totalRecordCount the totalRecordCount to set
	 * 	Setter for totalRecordCount 
	 *	Added by : A-5175 on 24-Oct-2012
	 * 	Used for : icrd-22065
	 */
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	/**
	 * @return Returns the awbDuplicateNumber.
	 */
	public int getAwbDuplicateNumber() {
		return awbDuplicateNumber;
	}
	/**
	 * @param awbDuplicateNumber The awbDuplicateNumber to set.
	 */
	public void setAwbDuplicateNumber(int awbDuplicateNumber) {
		this.awbDuplicateNumber = awbDuplicateNumber;
	}
	/**
	 * @return Returns the awbNumber.
	 */
	public String getAwbNumber() {
		return awbNumber;
	}
	/**
	 * @param awbNumber The awbNumber to set.
	 */
	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}
	/**
	 * @return Returns the awbSequenceNumber.
	 */
	public int getAwbSequenceNumber() {
		return awbSequenceNumber;
	}
	/**
	 * @param awbSequenceNumber The awbSequenceNumber to set.
	 */
	public void setAwbSequenceNumber(int awbSequenceNumber) {
		this.awbSequenceNumber = awbSequenceNumber;
	}
	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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
	 * @return Returns the fromDate.
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return Returns the houseAwbNumber.
	 */
	public String getHouseAwbNumber() {
		return houseAwbNumber;
	}
	/**
	 * @param houseAwbNumber The houseAwbNumber to set.
	 */
	public void setHouseAwbNumber(String houseAwbNumber) {
		this.houseAwbNumber = houseAwbNumber;
	}
	/**
	 * @return Returns the masterOwnerIdentifier.
	 */
	public int getMasterOwnerIdentifier() {
		return masterOwnerIdentifier;
	}
	/**
	 * @param masterOwnerIdentifier The masterOwnerIdentifier to set.
	 */
	public void setMasterOwnerIdentifier(int masterOwnerIdentifier) {
		this.masterOwnerIdentifier = masterOwnerIdentifier;
	}
	/**
	 * @return Returns the toDate.
	 */
	public LocalDate getToDate() {
		return toDate;
	}
	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return Returns the absoluteIndex.
	 */
	public int getAbsoluteIndex() {
		return absoluteIndex;
	}
	/**
	 * @param absoluteIndex The absoluteIndex to set.
	 */
	public void setAbsoluteIndex(int absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}

}
