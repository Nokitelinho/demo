/*
 * ListCustomerAuditVersionsCommand.java Created on August 27, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

/**
 * The Class ListCustomerAuditVersionsCommand.
 *
 * @author 		:	A-5163
 * Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer.ListCustomerAuditVersionsCommand.java
 * Version		:	Name	:	Date			:	Added for 	: 	Status
 * -----------------------------------------------------------------------
 * 0.1			:	A-5163	:	August 27, 2014	:	ICRD-67442	:	Draft
 */
public class ListCustomerAuditVersionsCommand extends BaseCommand{

	/** The Constant LIST_SUCCESS. */
	private static final String LIST_SUCCESS = "list_success";
	
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: 	A-5163 on August 27, 2014
	 * 	Used for 	:	ICRD-67442
	 *	Parameters	:	@param invocationContext
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		invocationContext.target = LIST_SUCCESS;
    	return;
	}
	
}
