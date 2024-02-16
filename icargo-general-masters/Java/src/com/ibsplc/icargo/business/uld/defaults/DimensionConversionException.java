/*
 * DimensionConversionException.java Created on Aug 31,2006
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
	 * @author A-2667
	 *
	 */
	public class DimensionConversionException extends BusinessException {

			/**
			 *
			 */
			public static final String DIMENSION_CONVERSION_IS_NOT_DEFINED = "uld.defaults.dimensionConversionisNotDefined";
			
			/**
			 *
			 * @param errorCode
			 * @param exceptionCause
			 */

			public DimensionConversionException(){
				super();
			}
			/**
			 * 
			 * @param errorCode
			 */
			public DimensionConversionException(String errorCode) {
			    super(errorCode);
			    // To be reviewed Auto-generated constructor stub
			}
			/**
			 *
			 * @param errorCode
			 * @param exceptionCause
			 */
			public DimensionConversionException(String errorCode, Object[] exceptionCause) {
			    super(errorCode, exceptionCause);
			    // To be reviewed Auto-generated constructor stub
			}
	}





