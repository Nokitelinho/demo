/*
 * WarehouseSetUpException.java Created on Dec 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

	/**
	 *
	 * To be reviewed
	 *
	 * @author A-1950
	 *
	 */
	/*
	 *  Revision History
	 * ---------------------------------------------------------------
	 *  Revision 			Date          Author			Description
	 * ----------------------------------------------------------------
	 *  0.1					Sep 10, 2007      A-1950          To be reviewed
	 */
	public class WarehouseSetUpException extends BusinessException {

			/**
			 *
			 */
			public static final String WAREHOUSE_SET_UP_EXCEPTION =
				"uld.defaults.warehousesetupexception";


			/**
			 *
			 * @param errorCode
			 * @param exceptionCause
			 */

			public WarehouseSetUpException(){
				super();
			}
			/**
			 *
			 * @param errorCode
			 */
			public WarehouseSetUpException(String errorCode) {
			    super(errorCode);
			    // To be reviewed Auto-generated constructor stub
			}
			/**
			 *
			 * @param errorCode
			 * @param exceptionCause
			 */
			public WarehouseSetUpException(String errorCode, Object[] exceptionCause) {
			    super(errorCode, exceptionCause);
			    // To be reviewed Auto-generated constructor stub
			}
	}





