/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.customermanagement.agentbilling.ListSettlementbatchsActionController.java
 *
 *	Created by	:	A-8227
 *	Created on	:	Sep 4, 2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.model.customermanagement.defaults;

import java.util.Collection;
import java.util.List;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.CustomerConsoleModel.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8227	:	Sep 4, 2018	:	Draft
 */
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.common.BillingInvoiceDetails;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.common.CCADetails;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.common.CustomerDetails;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.common.InvoiceDetails;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.common.PaymentDetails;
public class CustomerConsoleModel extends AbstractScreenModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * The constant variable for product customermanagement
	 */
	private static final String PRODUCT = "customermanagement";
	/*
	 * The constant for sub product defaults
	 */
	private static final String SUBPRODUCT = "defaults";
	/*
	 * The constant for screen id
	 */
	private static final String SCREENID = "customermanagement.defaults.ux.customerconsole";
	
	private String customerCode;
	
	private String invoiceNumber;
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	private CustomerDetails customerDetails;
	private int pageNumber;
	private String exportMode;
	
	/**
	 * 	Getter for pageNumber 
	 *	Added by : A-8360 on May 14, 2019
	 * 	Used for :
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 *  @param pageNumber the pageNumber to set
	 * 	Setter for pageNumber
	 *	Added by : A-8360 on May 14, 2019
	 * 	Used for :
	 */
	void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	//Added by A-8360 for pageSize
	private int pageSize;
	/**
	 * 	Getter for pageSize 
	 *	Added by : A-8360 on May 14, 2019
	 * 	Used for :
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 *  @param pageSize the pageSize to set
	 * 	Setter for pageSize
	 *	Added by : A-8360 on May 14, 2019
	 * 	Used for :
	 */
	void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	private InvoiceDetails invoiceDetails;
	private BillingInvoiceDetails billingInvoiceDetails;	
	
	private List<CCADetails> ccaDetails;
	
	public List<CCADetails> getCcaDetails() {
		return ccaDetails;
	}
	public void setCcaDetails(List<CCADetails> ccaDetails) {
		this.ccaDetails = ccaDetails;
	}
	public Collection<PaymentDetails> getPaymentDetails() {
		return paymentDetails;
	}
	public void setPaymentDetails(Collection<PaymentDetails> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	private Collection<PaymentDetails> paymentDetails;
	
	private String reminderListApprovalParameter;
	

	public String getReminderListApprovalParameter() {
		return reminderListApprovalParameter;
	}
	public void setReminderListApprovalParameter(String reminderListApprovalParameter) {
		this.reminderListApprovalParameter = reminderListApprovalParameter;
	}
	public BillingInvoiceDetails getBillingInvoiceDetails() {
		return billingInvoiceDetails;
	}
	public void setBillingInvoiceDetails(BillingInvoiceDetails billingInvoiceDetails) {
		this.billingInvoiceDetails = billingInvoiceDetails;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}
	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.spring.model.ScreenModel#getProduct()
	 *	Added by 			: A-8227 on Sep 4, 2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	@Override
	public String getProduct() {
		return PRODUCT;
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.spring.model.ScreenModel#getScreenId()
	 *	Added by 			: A-8227 on Sep 4, 2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	@Override
	public String getScreenId() {
		return SCREENID;
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.spring.model.ScreenModel#getSubProduct()
	 *	Added by 			: A-8227 on Sep 4, 2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	@Override
	public String getSubProduct() {
		return SUBPRODUCT;
	}
	/**
	 * @return the invoiceDetails
	 */
	public InvoiceDetails getInvoiceDetails() {
		return invoiceDetails;
	}
	/**
	 * @param invoiceDetails the invoiceDetails to set
	 */
	public void setInvoiceDetails(InvoiceDetails invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}
	//Added for IASCB-45950 starts
	/**
	 * Gets the export mode.
	 *
	 * @return the export mode
	 */
	public String getExportMode() {
		return exportMode;
	}
	
	/**
	 * Sets the export mode.
	 *
	 * @param exportMode the new export mode
	 */
	public void setExportMode(String exportMode) {
		this.exportMode = exportMode;
	}
	
	//Added for IASCB-45950 ends
	
	
}
