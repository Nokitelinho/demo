/*
 * BillingmatrixAuditForm.java Created on Aug 5, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-5255 
 * @version	0.1, Aug 5, 2015
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Aug 5, 2015	     A-5255		First draft
 */

public class BillingmatrixAuditForm extends ScreenModel{
	private static final String BUNDLE = "billingmatrix";

	//private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID =
		"mailtracking.mra.defaults.billingmatrixaudit";

	
	
	private String blgMatrixID;
	private String txnFromDate;
	private String txnToDate;
	/**
	  * @author A-5255
	  * @return
	  * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
	  */
	
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
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return the blgMatrixID
	 */
	public String getBlgMatrixID() {
		return blgMatrixID;
	}

	/**
	 * @param blgMatrixID the blgMatrixID to set
	 */
	public void setBlgMatrixID(String blgMatrixID) {
		this.blgMatrixID = blgMatrixID;
	}

	/**
	 * @return the txnFromDate
	 */
	public String getTxnFromDate() {
		return txnFromDate;
	}

	/**
	 * @param txnFromDate the txnFromDate to set
	 */
	public void setTxnFromDate(String txnFromDate) {
		this.txnFromDate = txnFromDate;
	}

	/**
	 * @return the txnToDate
	 */
	public String getTxnToDate() {
		return txnToDate;
	}

	/**
	 * @param txnToDate the txnToDate to set
	 */
	public void setTxnToDate(String txnToDate) {
		this.txnToDate = txnToDate;
	}

}
