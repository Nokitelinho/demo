/*
 * SurchargeBillingDetailsForm.java Created on Jul 15, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-5255 
 * @version	0.1, Jul 15, 2015
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jul 15, 2015	     A-5255		First draft
 */

public class SurchargeBillingDetailsForm extends ScreenModel{
	private static final String BUNDLE = "surchargebillingdetailsresources";
	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID =  "mailtracking.mra.gpabilling.surcharge.surchargepopup";
	private String totalAmount;
	private String overrideRounding;//Added for ICRD-189966

	/**
	  * @author A-5255
	  * @return
	  * @see com.ibsplc.icargo.framework.web.ScreenData#getProduct()
	  */
	
	@Override
	public String getProduct() {
		
		return PRODUCT;
	}

	/**
	  * @author A-5255
	  * @return
	  * @see com.ibsplc.icargo.framework.web.ScreenData#getScreenId()
	  */
	
	@Override
	public String getScreenId() {
		
		return SCREENID;
	}

	/**
	  * @author A-5255
	  * @return
	  * @see com.ibsplc.icargo.framework.web.ScreenData#getSubProduct()
	  */
	
	@Override
	public String getSubProduct() {
		return SUBPRODUCT;
	}
	/**
	 * @return
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return the totalAmount
	 */
	public String getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the overrideRounding
	 */
	public String getOverrideRounding() {
		return overrideRounding;
	}

	/**
	 * @param overrideRounding the overrideRounding to set
	 */
	public void setOverrideRounding(String overrideRounding) {
		this.overrideRounding = overrideRounding;
	}
	


}
