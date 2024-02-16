/*
 * ULDDiscrepancyReportForm.java Created on Mar 20, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults;

import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;
import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author a-3045
 *
 */
public class ULDDiscrepancyReportForm extends ScreenModel {
	
	private static final String BUNDLE = "ulddiscrepancyreport";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.ulddiscrepancyreport";
	
	private String fromDate;
	private String toDate;
	private String uldNumber;
	private String airlineCode;
	private String reportingAirportCode;
		
	public String getProduct() {
		
		return PRODUCT;
	}	
	public String getScreenId() {
		
		return SCREENID;
	}	
	public String getSubProduct() {
		
		return SUBPRODUCT;
	}	
	public String getBundle() {
		
		    return BUNDLE;
	}
	/**
	 * @return the airlineCode
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode the airlineCode to set
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}	
	/**
	 * @return the reportingAirportCode
	 */
	public String getReportingAirportCode() {
		return reportingAirportCode;
	}
	/**
	 * @param reportingAirportCode the reportingAirportCode to set
	 */
	public void setReportingAirportCode(String reportingAirportCode) {
		this.reportingAirportCode = reportingAirportCode;
	}	
	/**
	 * @return the fromDate
	 */
	@DateFieldId(id="ULDDiscrepancyReportDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	@DateFieldId(id="ULDDiscrepancyReportDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the uldNumber
	 */
	public String getUldNumber() {
		return uldNumber;
	}
	/**
	 * @param uldNumber the uldNumber to set
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
}
