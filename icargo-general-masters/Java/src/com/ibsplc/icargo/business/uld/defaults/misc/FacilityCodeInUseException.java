/*
 * FacilityCodeInUseException.java Created on jul 18, 2006
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
public class FacilityCodeInUseException
extends BusinessException{
    
		/**
		 * 
		 */
		public static final String FACILITYCODE_IN_USE = 
			"uld.defaults.facilitycodeinuse";	
		public static final String DUPLICATE_FACILITY_CODE = 
			"uld.defaults.duplicatefacilitycode";
		/**
         * @param errorCode
         */
        public FacilityCodeInUseException(String errorCode) {
            super(errorCode);

        }

        /**
         * @param errorCode
         * @param errorData
         */
        public FacilityCodeInUseException(
        		String errorCode, Object[] errorData) {
            super(errorCode, errorData);

        }

        /**
         * 
         */
        public FacilityCodeInUseException() {
            super();

        }

        /**
         * @param exception
         */
        public FacilityCodeInUseException(Throwable exception) {
            super(exception);

        }

        /**
         * @param errorCode
         * @param exception
         */
        public FacilityCodeInUseException(
        		String errorCode, Throwable exception) {
            super(errorCode, exception);

        }

        /**
         * @param businessException
         */
        public FacilityCodeInUseException(
        		BusinessException businessException) {
            super(businessException);

        }


}
