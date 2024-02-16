/*
 * ConsoleLogger.java Created on 18-Jun-2018
 *
 * Copyright 2017 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			18-Jun-2018       		Jens J P 			First Draft
 */
/**
 * @author Jens J P
 *
 */
public class ConsoleLogger implements Logger{
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.ebl.maven.plugins.xdyna.Logger#info(java.lang.CharSequence)
	 */
	@Override
	public void info(CharSequence data) {
		System.out.println("INFO : " + data);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.ebl.maven.plugins.xdyna.Logger#debug(java.lang.CharSequence)
	 */
	@Override
	public void debug(CharSequence data) {
		System.out.println("DEBUG : " + data);
	}

	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.ebl.maven.plugins.xdyna.Logger#warn(java.lang.CharSequence)
	 */
	@Override
	public void warn(CharSequence data) {
		System.out.println("WARNING : " + data);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.ebl.maven.plugins.xdyna.Logger#error(java.lang.CharSequence)
	 */
	@Override
	public void error(CharSequence data) {
		System.err.println("ERROR : " + data);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.ebl.maven.plugins.xdyna.Logger#error(java.lang.CharSequence, java.lang.Throwable)
	 */
	@Override
	public void error(CharSequence data, Throwable t) {
		System.err.println("ERROR : " + data);
		t.printStackTrace();
	}

	
}
