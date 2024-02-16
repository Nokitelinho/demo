/*
 * ValidityPeriodOverlapException.java Created on Dec 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1496
 *
 */
public class ValidityPeriodOverlapException extends BusinessException{
    

        /**
         * @param errorCode
         */
        public ValidityPeriodOverlapException(String errorCode) {
            super(errorCode);

        }

        /**
         * @param errorCode
         * @param errorData
         */
        public ValidityPeriodOverlapException(String errorCode, Object[] errorData) {
            super(errorCode, errorData);

        }

        /**
         * 
         */
        public ValidityPeriodOverlapException() {
            super();

        }

        /**
         * @param exception
         */
        public ValidityPeriodOverlapException(Throwable exception) {
            super(exception);

        }

        /**
         * @param errorCode
         * @param exception
         */
        public ValidityPeriodOverlapException(String errorCode, Throwable exception) {
            super(errorCode, exception);

        }

        /**
         * @param businessException
         */
        public ValidityPeriodOverlapException(BusinessException businessException) {
            super(businessException);

        }


}
