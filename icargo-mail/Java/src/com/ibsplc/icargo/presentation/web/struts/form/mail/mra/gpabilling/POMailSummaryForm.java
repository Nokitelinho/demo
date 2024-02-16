/**
 *  POMailSummaryForm.java Created on May 11, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author a-4823
 *
 */
public class POMailSummaryForm  extends ScreenModel{
	private static final String BUNDLE = "poMailSummaryResources";

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID ="mailtracking.mra.gpabilling.pomailsummary";
	private String locationType;
	private String location;
	public String getLocationType() {
		return locationType;
	}



	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}

	private String fromDate;
	private String toDate;
	
	
	public  String getBundle() {
		return BUNDLE;
	}

	
	
	public String getProduct() {
		
		return PRODUCT;
	}

	@Override
	public String getScreenId() {
		// TODO Auto-generated method stub
		return SCREENID;
	}

	

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	@Override
	public String getSubProduct() {
		// TODO Auto-generated method stub
		return SUBPRODUCT;
	}

}
