/*
 * DamagedMailBagForm.java Created on July 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-2047
 *
 */
public class DamagedMailBagForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.damagedmailbag";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "damagedMailbagResources";
	
	private String bundle;
	private String mailbagId;
	
	/**
	 * @return screenId
	 */
	public String getScreenId() {
		return SCREEN_ID;
	}

	/**
	 * @return product
	 */
	public String getProduct() {
		return PRODUCT_NAME;
	}

	/**
	 * @return subProduct
	 */
	public String getSubProduct() {
		return SUBPRODUCT_NAME;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * @return Returns the mailbagId.
	 */
	public String getMailbagId() {
		return mailbagId;
	}

	/**
	 * @param mailbagId The mailbagId to set.
	 */
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}

}
