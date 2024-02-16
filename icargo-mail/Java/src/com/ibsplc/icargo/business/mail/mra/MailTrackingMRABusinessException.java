/*
 * MailTrackingMRABusinessException.java Created on Jan 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1556
 *
 *
 */
public class MailTrackingMRABusinessException extends BusinessException {

	public static final String MTK_MRA_GPABILLING_STATUS_NOTOK =
		"mailtracking.mra.gpabilling.generateinvoice.procedure.status.notok";
	public static final String MTK_MRA_GPABILLING_STATUS_INVALRGEN =
		"mailtracking.mra.gpabilling.generateinvoice.procedure.status.invoice_already_generated";

	public static final String MTK_MRA_GPAREPORTING_PROCESS_STATUS_NOTOK =
		"mailtracking.mra.gpareporting.processgpareport.procedure.status.notok";

	public static final String NO_DATA_FOUND =
		"mailtracking.mra.gpabilling.nodatafound";
	public static final String NO_DATA ="mailtracking.mra.nodata";
	public static final String MAILTACKING_MRA_EXCEPTION_NOREPORTDATA=
		"mailtracking.mra.assignexceptions.report.nodata";
	public static final String MAILTACKING_MRA_DEFAULTS_NOREPORTDATA=
		"mailtracking.mra.defaults.report.err.nodata";
	public static final String MAILTACKING_MRA_GPABILLING_NOREPORTDATA=
		"mailtracking.mra.gpabilling.report.err.nodata";
	public static final String MAILTACKING_MRA_EXCEPTION_PROCESSMAIL_CLRPERIOD=
		"mailtracking.mra.airlinebilling.defaults.processmail.clrperiodnotfound";
	public static final String MAILTACKING_MRA_EXCEPTION_PROCESSMAIL_NOSMYDETAILS=
		"mailtracking.mra.airlinebilling.defaults.processmail.nosmydetails";
	public static final String MAILTACKING_MRA_EXCEPTION_PROCESSMAIL_NORECORDS=
		"mailtracking.mra.airlinebilling.defaults.processmail.norecords";
	public static final String MAILTACKING_MRA_EXCEPTION_PROCESSMAIL_FAILED=
		"mailtracking.mra.airlinebilling.defaults.processmail.failed";
	public static final String MAILTACKING_MRA_AIRLINEBILLING_NOREPORTDATA=
		"mailtracking.mra.airlinebilling.report.err.nodata";
	public static final String MAILTACKING_MRA_EXCEPTION_GENINV_FAILED=
		"mailtracking.mra.airlinebilling.outward.generateinvoice.failed";

	public static final String MAILTACKING_MRA_EXCEPTION_INVALRGEN=
		"mailtracking.mra.airlinebilling.outward.generateinvoice.invalrgen";

	public static final String MAILTACKING_MRA_EXCEPTION_GENINV_NOBLBREC=
		"mailtracking.mra.airlinebilling.outward.generateinvoice.noblbrec";


	public static final String MAILTRACKING_MRA_EXCEPTION_PROCESSFLIGHT_FAILED =
		"mailtracking.mra.flown.processflight.procedure.notok";
	public static final String MTK_MRA_GPABILLING_NO_BILLABLE_DSNS =
		"mailtracking.mra.gpabilling.generateinvoice.procedure.status.notbillable";
	public static final String MTK_MRA_DEFAULTS_NO_EXCHANGE_RATE =
		"mailtracking.mra.defaults.error.noexchangerate";
	public static final String MAILTRACKING_MRA_EXCEPTION_UNACCOUNTEDDSNACCOUNTING_FAILED =
		"mailtracking.mra.defaults.unaccounteddsnaccounting.procedure.notok";
	public static final String NOT_VALID_BILLINGPERIOD =
		"mailtracking.mra.gpabilling.notvalidbillingperiod";

	public static final String MAILTACKING_MRA_EXCEPTION_GENCSVRPT=
		"mailtracking.mra.defaults.report.error.generatereport.failed";

	public static final String MAILTACKING_MRA_EXCEPTION_GENINV_NOEXGRATBAS =
			"mailtracking.mra.airlinebilling.outward.generateinvoice.noexchangeratebasis";

	public static final String DUPLICATE_FILE=
			"mail.mra.receivablemanagement.duplicatefile";

	public MailTrackingMRABusinessException() {
		super();
	}

	public MailTrackingMRABusinessException(BusinessException errorCode) {
		super(errorCode);
	}

	public MailTrackingMRABusinessException(String errorCode, Object[] exceptionCause) {
		super(errorCode, exceptionCause);
	}

	public MailTrackingMRABusinessException(String errorCode, Throwable exceptionCause) {
		super(errorCode, exceptionCause);
	}

	public MailTrackingMRABusinessException(String errorCode) {
		super(errorCode);
	}

	public MailTrackingMRABusinessException(Throwable errorCode) {
		super(errorCode); 
	}

}
