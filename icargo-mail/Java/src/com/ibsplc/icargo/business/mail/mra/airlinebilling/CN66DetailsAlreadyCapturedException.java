/*
 * CN66DetailsAlreadyCapturedException.java created on Feb 24, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * 
 * @author a-2518
 * 
 */
public class CN66DetailsAlreadyCapturedException extends BusinessException {
	/**
	 * CN66 Details already captured constant
	 */
	public static final String MAILTRACKING_MRA_AIRLINEBILLING_CN66DETAILS_FOUND

	= "mailtracking.mra.airlinebilling.cn66detailsfound";

	public CN66DetailsAlreadyCapturedException() {
		super();
	}

	public CN66DetailsAlreadyCapturedException(
			BusinessException businessException) {
		super(businessException);
	}

	public CN66DetailsAlreadyCapturedException(String arg0, Object[] arg1) {
		super(arg0, arg1);
	}

	public CN66DetailsAlreadyCapturedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CN66DetailsAlreadyCapturedException(String arg0) {
		super(arg0);
	}

	public CN66DetailsAlreadyCapturedException(Throwable arg0) {
		super(arg0);
	}

}
