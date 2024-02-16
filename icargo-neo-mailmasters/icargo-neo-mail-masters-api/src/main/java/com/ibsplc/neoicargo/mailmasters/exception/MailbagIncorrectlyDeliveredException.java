package com.ibsplc.neoicargo.mailmasters.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * @author A-1739
 */
public class MailbagIncorrectlyDeliveredException extends BusinessException {
	/** 
	* The ErrorCode For MailBagIncorrectlyDelivered
	*/
	public static final String MAILBAG_INCORRECTLY_DELIVERED = "mailtracking.defaults.err.mailbagsincorrectlydelivered";
	/** 
	* The ErrorCode ForDespatchIncorrectlyDelivered
	*/
	public static final String DESPATCH_INCORRECTLY_DELIVERED = "mailtracking.defaults.err.despatchesincorrectlydelivered";
	/** 
	* The ErrorCode For MailBagIncorrectlyDelivered
	*/
	public static final String MAILBAG_INCORRECTLY_TRANSFERRED = "mailtracking.defaults.err.mailbagsincorrectlytransferred";

	/** 
	* @param errorCode
	*/
	public MailbagIncorrectlyDeliveredException(String errorCode) {
		super(errorCode, "Not Found");
	}

	/** 
	* @param errorCode
	* @param errorData
	*/
	public MailbagIncorrectlyDeliveredException(String errorCode, Object[] errorData) {
		super(errorCode, errorCode);
	}
}
