package com.ibsplc.neoicargo.mail.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * @author A-1739
 */
public class AttachAWBException extends BusinessException {
	public static final String AWB_ATTACHED_DIFF_SCC = "mailtracking.defaults.err.awbattacheddiffscc";
	public static final String AWB_ORG_DST_DIFF = "mailtracking.defaults.err.origindestinationdiff";
	public static final String DSNS_NOTATTACHED_AWB = "mailtracking.defaults.warn.dsnsnotattachedtoawb";
	public static final String MAILBAGS_ALREADY_ATTACHED = "mailtracking.defaults.attachawb.msg.err.mailbagsalreadyattached";

	/** 
	* @param arg0
	* @param arg1
	*/
	public AttachAWBException(String arg0, Object[] arg1) {
		super(arg0, arg0);
	}

	/** 
	* @param arg0
	*/
	public AttachAWBException(String arg0) {
		super(arg0, arg0);
	}
}
