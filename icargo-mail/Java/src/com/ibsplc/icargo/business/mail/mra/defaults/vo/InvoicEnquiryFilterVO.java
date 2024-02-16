/*
 * InvoicEnquiryFilterVO.java Created on Apr 13, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-3434
 */



public class InvoicEnquiryFilterVO  extends AbstractVO{
	
	private String invoiceKey;
	private String mode;
	private String mailBagID;
	private int totalRecordCount;//Added by A-5218 to enable Last Link in Pagination
	private String companyCode;
	private int displayPage;
	
	
	
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
	 * @return the displayPage
	 */
	public int getDisplayPage() {
		return displayPage;
	}
	/**
	 * @param displayPage the displayPage to set
	 */
	public void setDisplayPage(int displayPage) {
		this.displayPage = displayPage;
	}
	/**
	 * @return the totalRecordCount
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	/**
	 * @param totalRecordCount the totalRecordCount to set
	 */
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	/**
	 * @return the invoiceKey
	 */
	public String getInvoiceKey() {
		return invoiceKey;
	}
	/**
	 * @param invoiceKey the invoiceKey to set
	 */
	public void setInvoiceKey(String invoiceKey) {
		this.invoiceKey = invoiceKey;
	}
	/**
	 * @return the mailBagID
	 */
	public String getMailBagID() {
		return mailBagID;
	}
	/**
	 * @param mailBagID the mailBagID to set
	 */
	public void setMailBagID(String mailBagID) {
		this.mailBagID = mailBagID;
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
