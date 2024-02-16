/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.receivablemanagement.AdvancePaymentModel.java
 *
 *	Created by	:	A-4809
 *	Created on	:	18-Oct-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.model.mail.mra.receivablemanagement;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.AddPaymentFilter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.PaymentBatchDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.PaymentBatchFilter;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.mra.receivablemanagement.AdvancePaymentModel.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	18-Oct-2021	:	Draft
 */
public class AdvancePaymentModel extends AbstractScreenModel{
	
	
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mail.mra.receivablemanagement.ux.advancepayment";
	
	private Map<String, Collection<OneTime>> oneTimeValues;
	private Map<String, String> systemParameters;
	private String maxPageCount;
	private PaymentBatchFilter paymentBatchFilter;
	private PageResult<PaymentBatchDetails> paymentBatchDetails;
	private AddPaymentFilter addPaymentFilter;
	private PaymentBatchDetails paymentBatchDetail;

	@Override
	public String getProduct() {
		return PRODUCT;
	}

	@Override
	public String getScreenId() {
		return SUBPRODUCT;
	}

	@Override
	public String getSubProduct() {
		return SCREENID;
	}

	public Map<String, Collection<OneTime>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(Map<String, Collection<OneTime>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}

	public Map<String, String> getSystemParameters() {
		return systemParameters;
	}

	public void setSystemParameters(Map<String, String> systemParameters) {
		this.systemParameters = systemParameters;
	}

	public String getMaxPageCount() {
		return maxPageCount;
	}

	public void setMaxPageCount(String maxPageCount) {
		this.maxPageCount = maxPageCount;
	}
	/**
	 * 	Getter for paymentBatchDetails 
	 *	Added by : A-4809 on 03-Dec-2021
	 * 	Used for :
	 */
	public PageResult<PaymentBatchDetails> getPaymentBatchDetails() {
		return paymentBatchDetails;
	}

	/**
	 *  @param paymentBatchDetails the paymentBatchDetails to set
	 * 	Setter for paymentBatchDetails 
	 *	Added by : A-4809 on 03-Dec-2021
	 * 	Used for :
	 */
	public void setPaymentBatchDetails(PageResult<PaymentBatchDetails> paymentBatchDetails) {
		this.paymentBatchDetails = paymentBatchDetails;
	}
	/**
	 * 	Getter for paymentBatchFilter 
	 *	Added by : A-4809 on 21-Oct-2021
	 * 	Used for :
	 */
	public PaymentBatchFilter getPaymentBatchFilter() {
		return paymentBatchFilter;
	}

	/**
	 *  @param paymentBatchFilter the paymentBatchFilter to set
	 * 	Setter for paymentBatchFilter 
	 *	Added by : A-4809 on 21-Oct-2021
	 * 	Used for :
	 */
	public void setPaymentBatchFilter(PaymentBatchFilter paymentBatchFilter) {
		this.paymentBatchFilter = paymentBatchFilter;
	}

	/**

	 * 	Getter for addPaymentFilter 
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for : 
	 */
	public AddPaymentFilter getAddPaymentFilter() {
		return addPaymentFilter;
	}
	/**
	 *  @param addPaymentFilter the addPaymentFilter to set
	 * 	Setter for addPaymentFilter 
	 *	Added by : A-4809 on 11-Nov-2021
	 * 	Used for :
	 */
	public void setAddPaymentFilter(AddPaymentFilter addPaymentFilter) {
		this.addPaymentFilter = addPaymentFilter;
	}
	/**
	 * 	Getter for paymentBatchDetail 
	 *	Added by : A-4809 on 03-Dec-2021
	 * 	Used for :
	 */
	public PaymentBatchDetails getPaymentBatchDetail() {
		return paymentBatchDetail;
	}
	/**
	 * @param paymentBatchDetail the paymentBatchDetail to set
	 * 	Setter for paymentBatchDetail 
	 *	Added by : A-4809 on 03-Dec-2021
	 * 	Used for :
	 */
	public void setPaymentBatchDetail(PaymentBatchDetails paymentBatchDetail) {
		this.paymentBatchDetail = paymentBatchDetail;
	}

}
