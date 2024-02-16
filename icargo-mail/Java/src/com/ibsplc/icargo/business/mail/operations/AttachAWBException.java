/*
 * AttachAWBException.java Created on Mar 23, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

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
 * 0.1     		  Mar 23, 2007			A-1739		Created
 */
public class AttachAWBException extends BusinessException {

	public static final String AWB_ATTACHED_DIFF_SCC = 
		"mailtracking.defaults.err.awbattacheddiffscc";
	/*Different Origin Destination Pair */
	public static final String AWB_ORG_DST_DIFF = 
		"mailtracking.defaults.err.origindestinationdiff"; 
	
	public static final String DSNS_NOTATTACHED_AWB = 
		"mailtracking.defaults.warn.dsnsnotattachedtoawb"; 
	
	public static final String MAILBAGS_ALREADY_ATTACHED = 
			"mailtracking.defaults.attachawb.msg.err.mailbagsalreadyattached"; 
	
	/**
	 * @param arg0
	 * @param arg1
	 */
	public AttachAWBException(String arg0, Object[] arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public AttachAWBException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
