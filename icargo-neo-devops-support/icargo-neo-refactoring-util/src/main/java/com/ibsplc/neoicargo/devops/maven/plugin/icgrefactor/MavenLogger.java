/*
 * MavenLogger.java Created on 18-Jun-2018
 *
 * Copyright 2017 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor;

import org.apache.maven.plugin.logging.Log;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			18-Jun-2018       		Jens J P 			First Draft
 */
/**
 * @author Jens J P
 *
 */
public class MavenLogger implements Logger{

	private final Log delegate;
	
	public MavenLogger(Log delegate) {
		this.delegate = delegate;
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.ebl.maven.plugins.xdyna.Logger#info(java.lang.CharSequence)
	 */
	@Override
	public void info(CharSequence data) {
		delegate.info(data);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.ebl.maven.plugins.xdyna.Logger#debug(java.lang.CharSequence)
	 */
	@Override
	public void debug(CharSequence data) {
		delegate.debug(data);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.ebl.maven.plugins.xdyna.Logger#error(java.lang.CharSequence)
	 */
	@Override
	public void error(CharSequence data) {
		delegate.error(data);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.ebl.maven.plugins.xdyna.Logger#error(java.lang.CharSequence, java.lang.Throwable)
	 */
	@Override
	public void error(CharSequence data, Throwable t) {
		delegate.error(data, t);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.ebl.maven.plugins.xdyna.Logger#warn(java.lang.CharSequence)
	 */
	@Override
	public void warn(CharSequence data) {
		delegate.warn(data);
	}
	
}
