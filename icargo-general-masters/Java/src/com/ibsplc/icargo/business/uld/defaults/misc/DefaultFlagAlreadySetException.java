/*
 * DefaultFlagAlreadySetException.java Created on jul 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-2048
 *
 */
public class DefaultFlagAlreadySetException
extends BusinessException{
    
		/**
		 * 
		 */
		public static final String DEFAULTFLAG_ALREADY_SET = 
			"uld.defaults.defaultflagalreadyset";	
		/**
         * @param errorCode
         */
        public DefaultFlagAlreadySetException(String errorCode) {
            super(errorCode);

        }

        /**
         * @param errorCode
         * @param errorData
         */
        public DefaultFlagAlreadySetException(
        		String errorCode, Object[] errorData) {
            super(errorCode, errorData);

        }

        /**
         * 
         */
        public DefaultFlagAlreadySetException() {
            super();

        }

        /**
         * @param exception
         */
        public DefaultFlagAlreadySetException(Throwable exception) {
            super(exception);

        }

        /**
         * @param errorCode
         * @param exception
         */
        public DefaultFlagAlreadySetException(
        		String errorCode, Throwable exception) {
            super(errorCode, exception);

        }

        /**
         * @param businessException
         */
        public DefaultFlagAlreadySetException(
        		BusinessException businessException) {
            super(businessException);

        }


}
