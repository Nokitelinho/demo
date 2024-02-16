/*
 * ClearCommand.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.billingentries;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingEntriesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingEntriesForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class ClearCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("GPABillingEntries ClearCommand");

	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.billingentries.listgpabillingentries";
	private static final String ACTION_SUCCESS = "screenload_success";
	private static final String CLEAR="clear";
	//Added by A-6991 for ICRD-137019 Starts
	private static final String FLAG_YES="Y";
	//Added by A-6991 for ICRD-137019 Ends
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		GPABillingEntriesSession session=null;
		session=(GPABillingEntriesSession) getScreenSession(MODULE_NAME,SCREEN_ID);
		GPABillingEntriesForm form=(GPABillingEntriesForm)invocationContext.screenModel;

		session.removeGpaBillingDetails();
		session.removeSelectedRows();
		form.setFromDate("");
		form.setToDate("");
		form.setGpaCodeFilter("");
		form.setGpaName("");
		form.setOriginOfficeOfExchange("");
		form.setMailbagId("");//Added as part of ICRD-205027 
		form.setDestinationOfficeOfExchange("");
		form.setMailCategoryCode("");
		form.setMailSubclass("");
		form.setYear("");
		form.setRecepatableSerialNumber("");
		form.setHighestNumberIndicator("");
		form.setRegisteredIndicator("");
		form.setCountry("");
		form.setStatus("");   		
		form.setDsn("");
		form.setConsignmentNumber("");
//Added by A-4809 for CR ICRD-258393.. Starts
		form.setOrigin("");
		form.setDestination("");		
		//Added by A-4809 for CR ICRD-258393.. Ends
//Added by A-6991 for ICRD-137019 Starts
		form.setContractRate(FLAG_YES);
		form.setUPURate(FLAG_YES);
//Added by A-6991 for ICRD-137019 Ends		
		
		//added by A-7866 for CR ICRD-189046 starts
		session.setGPABillingEntriesFilterVO(null);
		//added by A-7866 for CR ICRD-189046 ends
		session.setSelectedRows(null);
		session.setSelectedVoidMailbags(null);
		
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}

}
