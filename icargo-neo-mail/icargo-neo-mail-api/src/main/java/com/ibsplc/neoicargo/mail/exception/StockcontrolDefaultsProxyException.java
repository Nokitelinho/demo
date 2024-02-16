package com.ibsplc.neoicargo.mail.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;

/** 
 * @author A-1739
 */
public class StockcontrolDefaultsProxyException extends BusinessException {
	public static final String NO_AWBSTOCK = "mailtracking.defaults.attachawb.msg.err.noawbstockforstockholder";
	public static final String STOCKHOLDER_NO_STOCK = "mailtracking.defaults.attachawb.msg.err.nostockforstockholder";
	public static final String NO_STHFOR_AGENT = "mailtracking.defaults.attachawb.msg.err.nostockholderforagent";
	public static final String INVALID_STOCKHOLDER = "mailtracking.defaults.attachawb.msg.err.invalidstockholder";
	public static final String NOAWB_INSTOCK = "mailtracking.defaults.attachawb.msg.err.noawbinstock";
	public static final String DOC_NOTOF_STH = "mailtracking.defaults.attachawb.msg.err.documentnotofstockholder";
	public static final String NO_AGENT_CODE = "mailtracking.defaults.attachawb.msg.err.noAgentCode";

	/** 
	* Creates a new instance of StockcontrolDefaultsProxyException
	*/
	public StockcontrolDefaultsProxyException() {
		super("NOT_FOUND", "Not Found");
	}

	public StockcontrolDefaultsProxyException(String errorCode) {
		super(errorCode, errorCode);
	}

	/** 
	*/
	public StockcontrolDefaultsProxyException(String errorCode, Object[] errorData) {
		super(errorCode, errorCode);
	}
}
