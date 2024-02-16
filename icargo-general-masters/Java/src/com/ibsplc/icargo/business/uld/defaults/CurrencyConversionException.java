/*
 * CurrencyConversionException.java Created on Dec 20, 2006
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
	 *  0.1					Jan 3, 2007      A-1950          To be reviewed
	 */
	public class CurrencyConversionException extends BusinessException {

			/**
			 *
			 */
			public static final String CURRENCY_CONVERSION_IS_NOT_DEFINED =
				"uld.defaults.currencyconversionisnotdefined";
			public static final String INVALID_ROUNDINGUNIT = 
				"uld.defaults.invalidroundingunit";
			public static final String INVALID_ROUNDBASIS = 
				"uld.defaults.invalidroundbasis";
			public static final String CURCFGDFORSTN = 
				"uld.defaults.currencyconfiguredforstation";
			public static final String EXCHANGE_RATE_NOT_OBTIANED = 
				"uld.defaults.exgRateNotObtained";

			
			/**
			 *
			 * @param errorCode
			 * @param exceptionCause
			 */

			public CurrencyConversionException(){
				super();
			}
			/**
			 * 
			 * @param errorCode
			 */
			public CurrencyConversionException(String errorCode) {
			    super(errorCode);
			    // To be reviewed Auto-generated constructor stub
			}
			/**
			 *
			 * @param errorCode
			 * @param exceptionCause
			 */
			public CurrencyConversionException(String errorCode, Object[] exceptionCause) {
			    super(errorCode, exceptionCause);
			    // To be reviewed Auto-generated constructor stub
			}
	}





