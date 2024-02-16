package com.ibsplc.xibase.server.framework.persistence.query;

public class ResultSetFetchSizeExceededException extends RuntimeException {
	
	public static final String ERR_CODE="framework.web.resultset.configuredsize.exceeded";
	
	 /**
     * @param message
     */
    public ResultSetFetchSizeExceededException() {
        super();
    }    
    
	/**
	 * @param message
	 */
	public ResultSetFetchSizeExceededException(String message) {
		super(message);
	}
	

}
