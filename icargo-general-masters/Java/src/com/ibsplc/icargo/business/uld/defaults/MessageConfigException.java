/*
 * MessageConfigException.java Created on Sep 10, 2007
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
	public class MessageConfigException extends BusinessException {

			/**
			 *
			 */
			public static final String MESSAGE_CONFIG_EXCEPTION_FOR_LUC =
				"uld.defaults.messageconfigurationexceptionforluc";
			
			/**
			 * 
			 */
			public static final String MESSAGE_CONFIG_EXCEPTION_FOR_SCM =
				"uld.defaults.messageconfigurationexceptionforscm";
			
			/**
			 * @author a-3278 for QF1178 on 17Dec08
			 */
			public static final String ULD_NOT_IN_STOCK =
				"uld.defaults.uld.doesnot.exists";
			/**
			 *  @author a-7359 for ICRD-220123 
			 */
			public static final String OAL_ULD_NOT_IN_STOCK =
					"uld.defaults.oaluld.doesnot.exists";
			
			/**@author A-5165
			 * If ALl ULDs are not in system
			 * ICRD-262160
			 */
			public static final String ALL_INVALID_ULDS = 
					"msgbroker.message.allinvalidulds";
			
			/**
			 *
			 * @param errorCode
			 * @param exceptionCause
			 */

			public MessageConfigException(){
				super();
			}
			/**
			 *
			 * @param errorCode
			 */
			public MessageConfigException(String errorCode) {
			    super(errorCode);
			    // To be reviewed Auto-generated constructor stub
			}
			/**
			 *
			 * @param errorCode
			 * @param exceptionCause
			 */
			public MessageConfigException(String errorCode, Object[] exceptionCause) {
			    super(errorCode, exceptionCause);
			    // To be reviewed Auto-generated constructor stub
			}
	}





