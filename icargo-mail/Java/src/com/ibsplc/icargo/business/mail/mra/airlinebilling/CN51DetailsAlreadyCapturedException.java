/*
 * CN51DetailsAlreadyCapturedException.java created on Feb 16, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author a-2049
 *
 */
public class CN51DetailsAlreadyCapturedException extends BusinessException {
	
	/**
	 * error code to be used if CN51Details is already captured for the
	 * month's ending.......
	 */
	public static final String MAILTRACKING_MRA_AIRLINEBILLING_CN51DETAILS_FOUND
	
				= "mailtracking.mra.airlinebilling.CN51DetailsFound";
	
	

	public CN51DetailsAlreadyCapturedException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CN51DetailsAlreadyCapturedException(BusinessException arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public CN51DetailsAlreadyCapturedException(String arg0, Object[] arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public CN51DetailsAlreadyCapturedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public CN51DetailsAlreadyCapturedException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public CN51DetailsAlreadyCapturedException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	

}
