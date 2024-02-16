/*
 * Command.java Created on 22-Apr-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.tools;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			22-Apr-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 * An interface for modelling command line operations
 */
public interface Command {

	/**
	 * The name of the command
	 * @return
	 */
	String name();
	
	/**
	 * Usage or help information
	 * @return
	 */
	String usage();
	
	/**
	 * Execute the command based on the arguments specified.
	 * @param args
	 * @throws Exception
	 */
	void execute(String[] args) throws Exception;
	
}
