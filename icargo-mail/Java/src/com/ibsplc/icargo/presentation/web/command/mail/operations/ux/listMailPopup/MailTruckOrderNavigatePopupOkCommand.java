/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.listMailPopup.MailTruckOrderNavigatePopupOkCommand.java
 *
 *	Created by	:	A-8215
 *	Created on	:	05-Nov-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.listMailPopup;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ListMailbagPopupForm;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.listMailPopup.MailTruckOrderNavigatePopupOkCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8215	:	05-Nov-2018	:	Draft
 */
public class MailTruckOrderNavigatePopupOkCommand extends BaseCommand{
	
	private static final String SUCCESS="truckOrderNavigatePopupOksuccess";
    private Log log = LogFactory.getLogger("OPERATIONS FLTHANDLING");
    /*
     * The Module Name
     */
    private static final String MODULE_NAME = "mail.operations";
    /*
     * The ScreenID for  Checkin screen
     */
    private static final String SCREEN_ID = "mail.operations.ux.listmailbagpopup";

	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		ListMailbagPopupForm listMailbagPopupForm = (ListMailbagPopupForm) invocationContext.screenModel;
	    
		listMailbagPopupForm.setRefreshParent("YES");
		
		invocationContext.target = SUCCESS;
		
		
	}

}
