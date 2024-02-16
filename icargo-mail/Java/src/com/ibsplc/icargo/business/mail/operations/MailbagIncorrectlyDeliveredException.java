/*
 * MailbagIncorrectlyDeliveredException.java Created on Aug 25, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1739
 *
 */
public class MailbagIncorrectlyDeliveredException extends
		BusinessException {
	/**
	 * The ErrorCode For MailBagIncorrectlyDelivered
	 */
	public static final String MAILBAG_INCORRECTLY_DELIVERED = 
		"mailtracking.defaults.err.mailbagsincorrectlydelivered";
	/**
	 * The ErrorCode ForDespatchIncorrectlyDelivered
	 */
	public static final String DESPATCH_INCORRECTLY_DELIVERED = 
		"mailtracking.defaults.err.despatchesincorrectlydelivered"; 
	
	
	/**
	 * The ErrorCode For MailBagIncorrectlyDelivered
	 */
	public static final String MAILBAG_INCORRECTLY_TRANSFERRED = 
		"mailtracking.defaults.err.mailbagsincorrectlytransferred";

	/**
	 * @param errorCode
	 */
	public MailbagIncorrectlyDeliveredException(String errorCode) {
		super(errorCode);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param errorCode
	 * @param errorData
	 */
	public MailbagIncorrectlyDeliveredException(String errorCode, Object[] errorData) {
		super(errorCode, errorData);
	}

}
