/*
 * ReusableOutputStream.java Created on 14-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.tools.utils;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			14-Jan-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public interface ReusableOutputStream {

	public void reset();
	
	public byte[] byteArray();
	
	public int size();
	
}
