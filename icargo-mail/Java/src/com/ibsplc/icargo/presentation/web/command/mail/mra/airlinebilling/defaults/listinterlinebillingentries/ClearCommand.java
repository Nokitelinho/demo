/*
 * ClearCommand.java Created on Aug 9, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.listinterlinebillingentries;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 * 
 */
public class ClearCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA AIRLINEBILLING");

	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";

	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String CLEAR_FAILURE = "clear_failure";

	private static final String BLANK = "";

	/**
	 * Method to implement the clear operation
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");

    	ListInterlineBillingEntriesForm  listInterlineBillingEntriesForm = ( ListInterlineBillingEntriesForm)invocationContext.screenModel;

    	ListInterlineBillingEntriesSession listInterlineBillingEntriesSession =
    		(ListInterlineBillingEntriesSession)getScreenSession(MODULE_NAME, SCREENID);

    	// clearing vos in session
    	listInterlineBillingEntriesSession.removeAirlineBillingFilterVO();
    	listInterlineBillingEntriesSession.removeDocumentBillingDetailVOs();
    	
    	listInterlineBillingEntriesSession.setFromScreen(BLANK);
    	listInterlineBillingEntriesSession.setCloseFlag(BLANK);
    	listInterlineBillingEntriesForm.setOriginOfficeOfExchange(BLANK);
    	listInterlineBillingEntriesForm.setDestinationOfficeOfExchange(BLANK);
    	listInterlineBillingEntriesForm.setMailCategory(BLANK);
    	listInterlineBillingEntriesForm.setSubClass(BLANK);
    	listInterlineBillingEntriesForm.setDsn(BLANK);
    	listInterlineBillingEntriesForm.setYear(BLANK);
    	listInterlineBillingEntriesForm.setReceptacleSerialNumber(BLANK);
    	listInterlineBillingEntriesForm.setHighestNumberIndicator(BLANK);
    	listInterlineBillingEntriesForm.setRegisteredIndicator(BLANK);
    	listInterlineBillingEntriesForm.setFromDate(BLANK);
    	listInterlineBillingEntriesForm.setToDate(BLANK);
    	listInterlineBillingEntriesForm.setBillingStatus(BLANK);
    	listInterlineBillingEntriesForm.setBillingType(BLANK);
    	listInterlineBillingEntriesForm.setAirlineCode(BLANK);
    	listInterlineBillingEntriesForm.setSectorFrom(BLANK);
    	listInterlineBillingEntriesForm.setSectorTo(BLANK);
    	listInterlineBillingEntriesForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
    	invocationContext.target = CLEAR_SUCCESS;
    }

}
