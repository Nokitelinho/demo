package com.ibsplc.neoicargo.mail.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * @author A-1739
 */
public class InventoryForArrivalFailedException extends BusinessException {
	public static final String AWB_ATTACHED_DIFF_SCC = "mailtracking.defaults.err.awbattacheddiffscc";
	public static final String AWB_ORG_DST_DIFF = "mailtracking.defaults.err.origindestinationdiff";

	/** 
	* @param arg0
	* @param arg1
	*/
	public InventoryForArrivalFailedException(String arg0, Object[] arg1) {
		super(arg0, arg0);
	}

	/** 
	* @param arg0
	*/
	public InventoryForArrivalFailedException(String arg0) {
		super(arg0, "Not Found");
	}

	public InventoryForArrivalFailedException(BusinessException ex) {
		super("NOT_FOUND", "Not Found");
	}
}
