/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.consignment.ClosePopupCommand.java
 *
 *	Created by	:	A-7531
 *	Created on	:	18-Jul-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.consignment;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.consignment.ClosePopupCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	18-Jul-2017	:	Draft
 */
public class ClosePopupCommand extends BaseCommand  {
	
	private  Log log = LogFactory.getLogger("MAIL OPERATIONS");



	private static final String MODULE_NAME = "mail.operations";

	private static final String SCREENID = "mailtracking.defaults.consignment";
	
	private static final String CLOSE_SUCCESS = "close_success";

	
	 public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
		 
		 
		 log.entering("ClosePopupCommand","execute");
		  ConsignmentForm consignmentForm = (ConsignmentForm)invocationContext.screenModel;
		  ConsignmentSession consignmentSession = getScreenSession(MODULE_NAME,SCREENID); 
		  
		  consignmentSession.setConsignmentMailPopUpVO(null);
		  consignmentSession.setMultipleMailDetailsMap(null);
		  consignmentForm.setOrginOfficeOfExchange("");
		  consignmentForm.setDestOfficeOfExchange("");
		  consignmentForm.setMailCategory("");
		  consignmentForm.setMailClassType("");
		  consignmentForm.setMailSubClass("");
		  consignmentForm.setMailYear("");
		  consignmentForm.setMailDsn("");
		  consignmentForm.setHighestNumberIndicator("");
		  consignmentForm.setRegisteredIndicator("");
		
		  
		  
		 
			invocationContext.target=CLOSE_SUCCESS;
			log.exiting("ClosePopupCommand","execute");
	
	 }

}
