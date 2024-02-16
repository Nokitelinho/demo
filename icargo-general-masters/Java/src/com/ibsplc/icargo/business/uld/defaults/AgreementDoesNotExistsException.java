/*
 * AgreementDoesNotExistsException.java Created on Dec 20, 2006
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
	 *  0.1					Dec 20, 2006      A-1950          To be reviewed
	 */
	public class AgreementDoesNotExistsException extends BusinessException {
			/**
			 * 
			 */
			public static final String AGREEMENT_DOES_NOT_EXISTS=
				"uld.defaults.agreementdoesnot.exists";	
			
			/**
			 * 
			 */
			public static final String CURRENCY_CONVERSION_IS_NOT_DEFINED =
				"uld.defaults.currencyconversionisnotdefined";	
			
			/**
			 * 
			 * @param errorCode
			 * @param exceptionCause
			 */
			
			public AgreementDoesNotExistsException(){
				super();
			}
			/**
			 * 
			 * @param errorCode
			 * @param exceptionCause
			 */
			public AgreementDoesNotExistsException(String errorCode, Object[] exceptionCause) {
			    super(errorCode, exceptionCause);
			    // To be reviewed Auto-generated constructor stub 
			}
			}
		




