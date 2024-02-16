/*
 * ULDDoesNotExistsException.java Created on Dec 19, 2005
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
 * @author A-1347
 *
 */
	public class ULDDoesNotExistsException extends BusinessException {
		/**
		 * 
		 */
		public static final String ULD_DOES_NOT_EXISTS =
			"uld.defaults.ulddoesnot.exists";	
		
		/**
		 * 
		 */
		public static final String ULD_IS_NOT_IN_AIRPORT_ERROR =
			"uld.defaults.error.uldisnotinairport";	
		
		/**
		 * 
		 */
		public static final String ULD_IS_NOT_IN_AIRPORT_WARNING =
			"uld.defaults.warning.uldisnotinairport";	
		/**
		 * 
		 */
		public static final String ULD_IS_NOT_IN_THE_SYSTEM_ERROR =
			"uld.defaults.error.uldisnotinthesystem";	
		/**
		 * 
		 */public static final String PALLETULDS_NOT_IN_THE_AIRPORT_WARNING =
			"uld.defaults.warn.palletuldsnotinairport";	
		/**
		* 
		*/public static final String PALLETULDS_NONOPERATIONAL_ERROR =
				"uld.defaults.error.palletuldsnonoperational";	
		/**
		* 
		*/public static final String PALLETULDS_NOT_IN_THE_SYSTEM =
				"uld.defaults.error.palletuldsnotinsystem";
		/**
		 * 
		 */
		public static final String ULD_IS_NOT_IN_THE_SYSTEM_WARNING =
			"uld.defaults.warning.uldisnotinthesystem";	
		/**
		 * 
		 */
		public static final String ULD_IS_NOT_OPERATIONAL_ERROR =
			"uld.defaults.error.uldisnotoperational";	
		/**
		 * 
		 */
		public static final String ULD_IS_NOT_OPERATIONAL_WARNING =
			"uld.defaults.warning.uldisnotoperational";	
		/**
		 * 
		 */
		public static final String ULD_IS_LOST_ERROR =
			"uld.defaults.error.uldislost";	
		/**
		 * 
		 */
		public static final String ULD_IS_LOST_WARNING =
			"uld.defaults.warning.uldislost";	
		/**
		 * 
		 */
		public static final String ULD_NOT_IN_AIRLINE_STOCK_ERROR =
			"uld.defaults.error.uldnotinairlinestock";	
		/**
		 * 
		 */
		 
		public static final String ULD_NOT_IN_AIRLINE_STOCK_WARNING =
			"uld.defaults.warning.uldnotinairlinestock";	
		
		/**
		 * 
		 * @param errorCode
		 * @param exceptionCause
		 */
		
		public ULDDoesNotExistsException(){
			super();
		}
		public ULDDoesNotExistsException(String errorCode, Object[] exceptionCause) {
		    super(errorCode, exceptionCause);
		    // To be reviewed Auto-generated constructor stub 
		}
		}
	


