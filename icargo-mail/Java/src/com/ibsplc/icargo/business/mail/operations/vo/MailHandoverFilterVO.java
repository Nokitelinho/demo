/*
 * MailHandoverFilterVO.java Created on JUL 03, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *  
 * @author A-6986
 * 
 */

public class MailHandoverFilterVO extends AbstractVO {

	private String companyCode;
	private String gpaCode;
	private String airportCode;
	private String mailClass;
	private String exchangeOffice;
	private String mailSubClass;
	private int recordsPerPage;
	private int totalRecords;
	private int pageNumber;
	//Added by A-8527 for ICRD-352718
	private int defaultPageSize;
	public int getDefaultPageSize() {
		return defaultPageSize;
	}
	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}
	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return the gpaCode
	 */
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 * @param gpaCode the gpaCode to set
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 * @return the airportCode
	 */
	public String getAirportCode() {
		return airportCode;
	}
	/**
	 * @param airportCode the airportCode to set
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
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
	 * @return the recordsPerPage
	 */
	public int getRecordsPerPage() {
		return recordsPerPage;
	}
	/**
	 * @param recordsPerPage the recordsPerPage to set
	 */
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}
	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	/**
	 * @return the mailClass
	 */
	public String getMailClass() {
		return mailClass;
	}
	/**
	 * @param mailClass the mailClass to set
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	/**
	 * @return the exchangeOffice
	 */
	public String getExchangeOffice() {
		return exchangeOffice;
	}
	/**
	 * @param exchangeOffice the exchangeOffice to set
	 */
	public void setExchangeOffice(String exchangeOffice) {
		this.exchangeOffice = exchangeOffice;
	}
	/**
	 * @return the mailSubClass
	 */
	public String getMailSubClass() {
		return mailSubClass;
	}
	/**
	 * @param mailSubClass the mailSubClass to set
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}
	
	
}
