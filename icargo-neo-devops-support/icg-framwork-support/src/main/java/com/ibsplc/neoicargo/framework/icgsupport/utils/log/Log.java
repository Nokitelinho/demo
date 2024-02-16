/**
 * 
 */
package com.ibsplc.neoicargo.framework.icgsupport.utils.log;

import lombok.extern.slf4j.Slf4j;

/**
 * @author A-1759
 *
 */
@Slf4j
public class Log {
	
	  public static final int SEVERE = 7;
	  
	  public static final int WARNING = 6;
	  
	  public static final int INFO = 5;
	  
	  public static final int CONFIG = 4;
	  
	  public static final int FINE = 3;
	  
	  public static final int FINER = 2;
	  
	  public static final int FINEST = 1;
	  
	  public static final int OFF = 0;
	  
	  public static final int ALL = 8;

	String loggerName;

	public Log(String loggerName) {
		this.loggerName = loggerName;
	}

	public void log(int paramInt, String paramString) {
		if (paramInt < 5) {
			debug(paramString);
		}else if(paramInt ==5){
			info(paramString);
		}else if(paramInt ==6){
			warn(paramString);
		}else {
			error(paramString);
		}

	}

	private void info(String paramString) {
		log.info(this.loggerName + " : " + paramString);
	}

	private void debug(String paramString) {
		log.debug(this.loggerName + " : " + paramString);
	}

	private void error(String paramString) {
		log.error(this.loggerName + " : " + paramString);
	}

	private void warn(String paramString) {
		log.warn(this.loggerName + " : " + paramString);
	}

	public void entering(String paramString1, String paramString2) {
		log.debug(paramString1 + " : "  + paramString2 + " ENTERING" );
	}

	public void exiting(String paramString1, String paramString2) {
		log.debug(paramString1 + " : "  + paramString2 + " EXITING" );
	}

	public void log(int paramInt, Object... paramVarArgs) {
		StringBuilder builder = new StringBuilder();
		for(Object param : paramVarArgs){
			builder.append(String.valueOf(param) + " ");
		}
		log(paramInt,builder.toString());
	}

}
