/*
 * CapacityBookingProxyException.java Created on Sep 7, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 *  
 * @author A-1739
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Sep 7, 2007			a-1739		Created
 */
public class CapacityBookingProxyException extends BusinessException {

	public CapacityBookingProxyException(String message, ProxyException ex) {
		super(message,ex);
	}

	public CapacityBookingProxyException(BusinessException ex) {
		super(ex);
	}

}
