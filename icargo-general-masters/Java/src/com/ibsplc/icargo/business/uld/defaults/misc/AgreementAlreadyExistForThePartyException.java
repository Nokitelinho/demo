/*
 * AgreementAlreadyExistForThePartyException.java Created on Dec 20, 2005
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
public class AgreementAlreadyExistForThePartyException
extends BusinessException{
    
		/**
		 * 
		 */
		public static final String ULDAGREEMENT_ALREADY_EXISTS = 
			"uld.defaults.uldagreementalreadyexistsexception";	
		/**
         * @param errorCode
         */
        public AgreementAlreadyExistForThePartyException(String errorCode) {
            super(errorCode);

        }

        /**
         * @param errorCode
         * @param errorData
         */
        public AgreementAlreadyExistForThePartyException(
        		String errorCode, Object[] errorData) {
            super(errorCode, errorData);

        }

        /**
         * 
         */
        public AgreementAlreadyExistForThePartyException() {
            super();

        }

        /**
         * @param exception
         */
        public AgreementAlreadyExistForThePartyException(Throwable exception) {
            super(exception);

        }

        /**
         * @param errorCode
         * @param exception
         */
        public AgreementAlreadyExistForThePartyException(
        		String errorCode, Throwable exception) {
            super(errorCode, exception);

        }

        /**
         * @param businessException
         */
        public AgreementAlreadyExistForThePartyException(
        		BusinessException businessException) {
            super(businessException);

        }


}
