package com.ibsplc.neoicargo.framework.icgsupport.utils.log;

public class LogFactory {

	public static Log getLogger(String loggerName){
		return new Log(loggerName);
	}
}
