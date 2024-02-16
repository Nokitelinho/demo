/*
 * InvoiceLovFilterVO.java Created on Feb 15,2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2521
 *
 */
public class InvoiceLovFilterVO extends AbstractVO {

	private String companycode;
	private int airlineidr;
	private String interlinebillingtype;
	private String invoicenumber ;
	private String airlineCode;
	private String clearanceperiod;
	private int pageNumber;
	private String clearingHouse;


	/**
	 * @return Returns the airlineidr.
	 */
	public int getAirlineidr() {
		return airlineidr;
	}
	/**
	 * @param airlineidr The airlineidr to set.
	 */
	public void setAirlineidr(int airlineidr) {
		this.airlineidr = airlineidr;
	}

	/**
	 * @return Returns the clearanceperiod.
	 */
	public String getClearanceperiod() {
		return clearanceperiod;
	}
	/**
	 * @param clearanceperiod The clearanceperiod to set.
	 */
	public void setClearanceperiod(String clearanceperiod) {
		this.clearanceperiod = clearanceperiod;
	}
	/**
	 * @return Returns the companycode.
	 */
	public String getCompanycode() {
		return companycode;
	}
	/**
	 * @param companycode The companycode to set.
	 */
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	/**
	 * @return Returns the interlinebillingtype.
	 */
	public String getInterlinebillingtype() {
		return interlinebillingtype;
	}
	/**
	 * @param interlinebillingtype The interlinebillingtype to set.
	 */
	public void setInterlinebillingtype(String interlinebillingtype) {
		this.interlinebillingtype = interlinebillingtype;
	}
	/**
	 * @return Returns the invoicenumber.
	 */
	public String getInvoicenumber() {
		return invoicenumber;
	}
	/**
	 * @param invoicenumber The invoicenumber to set.
	 */
	public void setInvoicenumber(String invoicenumber) {
		this.invoicenumber = invoicenumber;
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
	 * @return Returns the pageNumber.
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	
	/**
	 * @param pageNumber The pageNumber to set.
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return Returns the clearingHouse.
	 */
	public String getClearingHouse() {
		return clearingHouse;
	}
	
	/**
	 * @param clearingHouse The clearingHouse to set.
	 */
	public void setClearingHouse(String clearingHouse) {
		this.clearingHouse = clearingHouse;
	}

}