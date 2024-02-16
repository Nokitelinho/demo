/*
 * CarditPreAdviceReportForm.java Created on Jul 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.operations;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1944
 *
 */
public class CarditPreAdviceReportForm extends ScreenModel {

	private static final String SCREEN_ID = "mailtracking.defaults.carditpreadvicereport";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String BUNDLE = "carditpreadvicereport";

	private String airportCode;
	private String mailDate;
	private String invokingScreen;

	/**
     * @return SCREEN_ID - String
     */
    public String getScreenId() {
        return SCREEN_ID;
    }

    /**
     * @return PRODUCT_NAME - String
     */
    public String getProduct() {
        return PRODUCT_NAME;
    }

    /**
     * @return SUBPRODUCT_NAME - String
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
	 * @return the mailDate
	 */
	public String getMailDate() {
		return mailDate;
	}

	/**
	 * @param mailDate the mailDate to set
	 */
	public void setMailDate(String mailDate) {
		this.mailDate = mailDate;
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
	 * @return the invokingScreen
	 */
	public String getInvokingScreen() {
		return invokingScreen;
	}

	/**
	 * @param invokingScreen the invokingScreen to set
	 */
	public void setInvokingScreen(String invokingScreen) {
		this.invokingScreen = invokingScreen;
	}

}
