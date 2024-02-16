/*
 * CN51SummaryFilterVO.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author Philip
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Jan 8, 2007 Philip Initial draft 0.2 Jan 17,2007 Kiran Added 2 new fields
 * gpaName & airlineAlphaCode for ListCN51s screen
 * 
 */
public class CN51SummaryFilterVO extends AbstractVO {

	/**
	 * companyCode
	 */
	private String companyCode;

	/**
	 * startDate for picking the billingDate
	 */
	private LocalDate fromDate;

	/**
	 * end Date for picking the billingDate
	 */
	private LocalDate toDate;

	/**
	 * the GPACode
	 */
	private String gpaCode;

	/* temporary fields not currently used */
	/**
	 * the gpaName
	 */
	private String gpaName;

	/**
	 * airlineAlphaCode for which the CN51s are to be picked
	 */
	private String airlineAlphaCode;

	/* temporary fields not currently used */

	/**
	 * @author a-3447 for int bug fix
	 */
	private String invoiceNumber;

	private String invoiceStatus;
	private int pageNumber;

	private boolean fetchAllResult;
	
	private String periodNumber;
	private String passFileName;
	private boolean isPASS;

	public String getBranchOffice() {
		return branchOffice;
	}

	public void setBranchOffice(String branchOffice) {
		this.branchOffice = branchOffice;
	}

	private  String branchOffice;

	public boolean isAddNew() {
		return isAddNew;
	}

	public void setAddNew(boolean addNew) {
		isAddNew = addNew;
	}

	private boolean isAddNew;


/**
	 * @author a-3447 bug fix
	 */

	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber
	 *            the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the invoiceStatus
	 */
	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	/**
	 * @param invoiceStatus
	 *            the invoiceStatus to set
	 */
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the fromDate.
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            The fromDate to set.
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode
	 *            The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	/**
	 * @return Returns the toDate.
	 */
	public LocalDate getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            The toDate to set.
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return Returns the airlineAlphaCode.
	 */
	public String getAirlineAlphaCode() {
		return airlineAlphaCode;
	}

	/**
	 * @param airlineAlphaCode
	 *            The airlineAlphaCode to set.
	 */
	public void setAirlineAlphaCode(String airlineAlphaCode) {
		this.airlineAlphaCode = airlineAlphaCode;
	}

	/**
	 * @return Returns the gpaName.
	 */
	public String getGpaName() {
		return gpaName;
	}

	/**
	 * @param gpaName
	 *            The gpaName to set.
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}

	/**
	 * 	Getter for fetchAllResult 
	 *	Added by : A-8061 on 05-May-2021
	 * 	Used for :
	 */
	public boolean isFetchAllResult() {
		return fetchAllResult;
	}

	/**
	 *  @param fetchAllResult the fetchAllResult to set
	 * 	Setter for fetchAllResult 
	 *	Added by : A-8061 on 05-May-2021
	 * 	Used for :
	 */
	public void setFetchAllResult(boolean fetchAllResult) {
		this.fetchAllResult = fetchAllResult;
	}

	/**
	 * 	Getter for periodNumber 
	 *	Added by : A-8061 on 06-May-2021
	 * 	Used for :
	 */
	public String getPeriodNumber() {
		return periodNumber;
	}

	/**
	 *  @param periodNumber the periodNumber to set
	 * 	Setter for periodNumber 
	 *	Added by : A-8061 on 06-May-2021
	 * 	Used for :
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	public String getPassFileName() {
		return passFileName;
	}

	public void setPassFileName(String passFileName) {
		this.passFileName = passFileName;
	}

	public boolean isPASS() {
		return isPASS;
	}

	public void setPASS(boolean pass) {
		isPASS = pass;
	}
}
