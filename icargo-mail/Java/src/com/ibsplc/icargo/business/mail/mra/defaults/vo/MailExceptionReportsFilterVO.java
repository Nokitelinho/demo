/*
 * MailExceptionReportsFilterVO.java Created on Sep 15, 2010
 *
 * Copyright 2010 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo; 

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * 
 * @author A-2414
 *
 */
public class MailExceptionReportsFilterVO extends AbstractVO {
	
	/**
	 * Mail Operations Report
	 */
	public static final String MAIL_OPERATIONS_REPORT = "MailOperationsReport";
	
	/**
	 * Proration Exception Count Report
	 */
	public static final String PRORATE_EXCEPTION_COUNT = "ProrateExceptionCount";
	
	/**
	 * Flown Accounting Not Trigerred Report
	 */
	public static final String FLOWN_ACCOUNTING_NOT_TRIGGERED = "FlownAccountingNotTriggered";
	
	/**
	 * Consignment Not Captured Report
	 */
	public static final String CONSIGNMENT_NOT_CAPTURED = "ConsignmentNotCaptured";
	
	/**
	 * Flights Not Closed Report
	 */
	public static final String FLIGHTS_NOT_CLOSED = "FlightsNotClosed";
	
	private String companyCode;
	
	private int ownerIdentifier;
	
	private LocalDate fromDate;
	
	private LocalDate toDate;
	
	private String reportType;

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the fromDate.
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate The fromDate to set.
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the reportType.
	 */
	public String getReportType() {
		return reportType;
	}

	/**
	 * @param reportType The reportType to set.
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	/**
	 * @return Returns the toDate.
	 */
	public LocalDate getToDate() {
		return toDate;
	}

	/**
	 * @param toDate The toDate to set.
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return Returns the ownerIdentifier.
	 */
	public int getOwnerIdentifier() {
		return ownerIdentifier;
	}

	/**
	 * @param ownerIdentifier The ownerIdentifier to set.
	 */
	public void setOwnerIdentifier(int ownerIdentifier) {
		this.ownerIdentifier = ownerIdentifier;
	}

}
