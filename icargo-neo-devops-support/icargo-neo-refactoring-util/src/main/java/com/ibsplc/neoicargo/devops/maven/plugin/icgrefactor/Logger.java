/*
 * Logger.java Created on 18-Jun-2018
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
public interface Logger {

	public void info(CharSequence data);
	
	public void debug(CharSequence data);
	
	public void error(CharSequence data);
	
	public void warn(CharSequence data);
	
	public void error(CharSequence data, Throwable t);
	
}
