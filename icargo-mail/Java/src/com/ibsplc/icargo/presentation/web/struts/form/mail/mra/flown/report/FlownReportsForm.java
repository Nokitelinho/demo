/*
 * FlownReportsForm.java Created on Feb 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.report;

import com.ibsplc.icargo.framework.model.ScreenModel;
import com.ibsplc.icargo.framework.client.daterange.notation.DateFieldId;

/**
 * @author a-2449
 * @author A-2270 Added Fileds for Mail Revenue Report
 *
 */

public class FlownReportsForm extends ScreenModel{

	
	private static final String BUNDLE = "flownreportsresources";

	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = "mra.flown.flownreports";
	
	private String reportId;
	
	private String carrierCodeForListOfFlights;
	
	private String flightNumberForListOfFlights;
	
	private String fromDateForListOfFlights;
	
	private String todateForListOfFlights;
	
	private String flightStatus;
	
	private String carrierCodeForListOfFlownMails;
	
	private String flightNumberForListOfFlownMails;
	
	private String fromdateForListOfFlownMails;
	
	private String todateForListOfFlownMails;
	
	private String flightOrigin;
	
	private String flightDestination;
	
	private String mailOrigin;
	
	private String mailDestination;
	
	/*
	 * For mail revenue report
	 */
	
	private String accountMonth;
	
	
	private String flightNumberForRevReport;
	
	private String carrierCodeForRevReport;
	
	private String fromDateForRevReport;
	
	private String toDateForRevReport;
	
	private String comboFlag;
	
	

	
	public String getFlightNumberForRevReport() {
		return flightNumberForRevReport;
	}
	public void setFlightNumberForRevReport(String flightNumberForRevReport) {
		this.flightNumberForRevReport = flightNumberForRevReport;
	}
	@DateFieldId(id="FlownReportsRevenueDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getFromDateForRevReport() {
		return fromDateForRevReport;
	}
	public void setFromDateForRevReport(String fromDateForRevReport) {
		this.fromDateForRevReport = fromDateForRevReport;
	}
	@DateFieldId(id="FlownReportsRevenueDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getToDateForRevReport() {
		return toDateForRevReport;
	}
	public void setToDateForRevReport(String toDateForRevReport) {
		this.toDateForRevReport = toDateForRevReport;
	}
	

	public String getAccountMonth() {
		return accountMonth;
	}
	public void setAccountMonth(String accountMonth) {
		this.accountMonth = accountMonth;
	}
	/**
	 * @return Returns the carriercode
	 */
	public String getCarrierCodeForListOfFlights() {
		return carrierCodeForListOfFlights;
	}

	/**
	 * @param carrierCodeForListOfFlights
	 */
	public void setCarrierCodeForListOfFlights(String carrierCodeForListOfFlights) {
		this.carrierCodeForListOfFlights = carrierCodeForListOfFlights;
	}

	/**
	 * @return Returns the carriercode
	 */
	public String getCarrierCodeForListOfFlownMails() {
		return carrierCodeForListOfFlownMails;
	}

	/**
	 * @param carrierCodeForListOfFlownMails
	 */
	public void setCarrierCodeForListOfFlownMails(
			String carrierCodeForListOfFlownMails) {
		this.carrierCodeForListOfFlownMails = carrierCodeForListOfFlownMails;
	}

	/**
	 * @return Returns the flightdestination
	 */
	public String getFlightDestination() {
		return flightDestination;
	}

	/**
	 * @param flightDestination
	 */
	public void setFlightDestination(String flightDestination) {
		this.flightDestination = flightDestination;
	}

	/**
	 * @return Returns the flightnumber
	 */
	public String getFlightNumberForListOfFlights() {
		return flightNumberForListOfFlights;
	}

	/**
	 * @param flightNumberForListOfFlights
	 */
	public void setFlightNumberForListOfFlights(String flightNumberForListOfFlights) {
		this.flightNumberForListOfFlights = flightNumberForListOfFlights;
	}

	/**
	 * @return Returns the flightnumber
	 */
	public String getFlightNumberForListOfFlownMails() {
		return flightNumberForListOfFlownMails;
	}

	/**
	 * @param flightNumberForListOfFlownMails
	 */
	public void setFlightNumberForListOfFlownMails(
			String flightNumberForListOfFlownMails) {
		this.flightNumberForListOfFlownMails = flightNumberForListOfFlownMails;
	}

	/**
	 * @return Returns the flightorigin
	 */
	public String getFlightOrigin() {
		return flightOrigin;
	}

	/**
	 * @param flightOrigin
	 */
	public void setFlightOrigin(String flightOrigin) {
		this.flightOrigin = flightOrigin;
	}

	/**
	 * @return Returns the flightstatus
	 */
	public String getFlightStatus() {
		return flightStatus;
	}

	/**
	 * @param flightStatus
	 */
	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}

	/**
	 * @return Returns the maildestination
	 */
	public String getMailDestination() {
		return mailDestination;
	}

	/**
	 * @param mailDestination
	 */
	public void setMailDestination(String mailDestination) {
		this.mailDestination = mailDestination;
	}

	/**
	 * @return Returns the mailorigin
	 */
	public String getMailOrigin() {
		return mailOrigin;
	}

	/**
	 * @param mailOrigin
	 */
	public void setMailOrigin(String mailOrigin) {
		this.mailOrigin = mailOrigin;
	}

	/**
	 * @return Returns the reportid
	 */
	public String getReportId() {
		return reportId;
	}

	/**
	 * @param reportId
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return Returns the bundle
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return Returns the product
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * @return Returns the screenid
	 */
	public String getScreenId() {
		return SCREENID;
	}

	/**
	 * @return Returns the subproduct
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	/**
	 * @return Returns the fromdate
	 */
	@DateFieldId(id="FlownReportsDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getFromDateForListOfFlights() {
		return fromDateForListOfFlights;
	}

	/**
	 * @param fromDateForListOfFlights
	 */
	public void setFromDateForListOfFlights(String fromDateForListOfFlights) {
		this.fromDateForListOfFlights = fromDateForListOfFlights;
	}

	/**
	 * @return Returns the fromdate
	 */
	@DateFieldId(id="FlownReportsMailsDateRange",fieldType="from") //Added by T-1927 for ICRD-9704
	public String getFromdateForListOfFlownMails() {
		return fromdateForListOfFlownMails;
	}

	/**
	 * @param fromdateForListOfFlownMails
	 */
	public void setFromdateForListOfFlownMails(String fromdateForListOfFlownMails) {
		this.fromdateForListOfFlownMails = fromdateForListOfFlownMails;
	}

	/**
	 * @return Returns the todate
	 */
	@DateFieldId(id="FlownReportsDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getTodateForListOfFlights() {
		return todateForListOfFlights;
	}

	/**
	 * @param todateForListOfFlights
	 */
	public void setTodateForListOfFlights(String todateForListOfFlights) {
		this.todateForListOfFlights = todateForListOfFlights;
	}

	/**
	 * @return Returns the todate
	 */
	@DateFieldId(id="FlownReportsMailsDateRange",fieldType="to") //Added by T-1927 for ICRD-9704
	public String getTodateForListOfFlownMails() {
		return todateForListOfFlownMails;
	}

	/**
	 * @param todateForListOfFlownMails
	 */
	public void setTodateForListOfFlownMails(String todateForListOfFlownMails) {
		this.todateForListOfFlownMails = todateForListOfFlownMails;
	}
	public String getCarrierCodeForRevReport() {
		return carrierCodeForRevReport;
	}
	public void setCarrierCodeForRevReport(String carrierCodeForRevReport) {
		this.carrierCodeForRevReport = carrierCodeForRevReport;
	}
	/**
	 * @return the comboFlag
	 */
	public String getComboFlag() {
		return comboFlag;
	}
	/**
	 * @param comboFlag the comboFlag to set
	 */
	public void setComboFlag(String comboFlag) {
		this.comboFlag = comboFlag;
	}
	
}
