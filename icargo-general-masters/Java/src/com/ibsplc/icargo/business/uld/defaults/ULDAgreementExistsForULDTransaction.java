/*
 * ULDAgreementExistsForULDTransaction.java Created on Aug 1, 2005
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
	public class ULDAgreementExistsForULDTransaction extends BusinessException {
		/**
		 * 
		 * @param errorCode
		 * @param exceptionCause
		 */
		
		
		public static final String AGREEMENT_NOT_INVOICED = 
			"uld.defaults.agreementnotinvoiced";
		
		public ULDAgreementExistsForULDTransaction(){
			super();
		}
		public ULDAgreementExistsForULDTransaction(String errorCode, Object[] exceptionCause) {
		    super(errorCode, exceptionCause);
		  
		}
		}
	

