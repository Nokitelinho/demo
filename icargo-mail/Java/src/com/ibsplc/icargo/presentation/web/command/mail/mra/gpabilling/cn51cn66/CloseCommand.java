/*
 * CloseCommand.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51cn66;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListCN51CN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51CN66Form;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class CloseCommand extends BaseCommand {

	private   Log log = LogFactory.getLogger("MRA GPABILLING");

	private static final String CLASS_NAME = "CloseCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51cn66";

	private static final String INVOKE_SCREEN = "listCN51";

	private static final String CLOSE_SUCCESS = "close_success";

	private static final String CLOSE_CN51_SUCCESS = "closecn51_success";

	private static final String CLOSE_INVOICE="closeinv_success";

	private static final String CLOSE_TO_CRAPOSTING="closetocra_success";

	private static final String FROM_CRA = "fromCraPostingDetails";

	private static final String FROM_CN51_SCREEN = "ListCN51s_Screen";

	private static final String FROM_SCREEN = "listinvoice";
	
	/* A-5273 Added for ICRD-38227
	 * Constant for Parent screen Invoice Print
	 */ 
	private static final String PARENTSCREEN_INVOICEPRINT = "Invoice_Print";

	//private static final String CLOSE_FAILURE = "close_failure";

	/**
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");
    	ListCN51CN66Form listCN51CN66Form = (ListCN51CN66Form)invocationContext.screenModel;
    	String screen = listCN51CN66Form.getInvokingScreen();

    	ListCN51CN66Session listCN51CN66Session =
    		(ListCN51CN66Session)getScreenSession(MODULE_NAME, SCREENID);
    	log.log(Log.INFO, "selected screen-->", screen);
		String parentScreenFlag = listCN51CN66Session.getParentScreenFlag()==null?
				"":listCN51CN66Session.getParentScreenFlag().trim();
    	log.log(Log.INFO, "parentScreenFlag-->", parentScreenFlag);
		// clearing vos in session
    	listCN51CN66Session.removeAllAttributes();

    	if(FROM_CN51_SCREEN.equals(parentScreenFlag)){
    		log.log(Log.INFO, "selected CLOSE_CN51_SUCCESS-->", screen);
			invocationContext.target = CLOSE_CN51_SUCCESS;

    	}else if(INVOKE_SCREEN.equals(screen)){
            log.log(Log.INFO, "selected CLOSE_CN51_SUCCESS-->", screen);
			invocationContext.target = CLOSE_CN51_SUCCESS;
    	}
    	else if(FROM_SCREEN.equals(screen)){
            log
					.log(Log.INFO, "FROM_SCREEN----List gpa billing invoice",
							screen);
			invocationContext.target = CLOSE_INVOICE;
    	}else if(FROM_SCREEN.equals(parentScreenFlag)){
    		log.log(Log.INFO,
					"FROM_SCREEN----List gpa billing invoice--MATCH--",
					parentScreenFlag);
			invocationContext.target = CLOSE_INVOICE;
    	}
    	else if(FROM_CRA.equals(screen)){
            log.log(Log.INFO, "FROM_SCREEN----CRA....", screen);
			invocationContext.target = CLOSE_TO_CRAPOSTING;
    	}
    	/*A-5273 Added for ICRD-38227
    	 * Added for navigating back to Invoice Print Screen
    	 */
    	else if(PARENTSCREEN_INVOICEPRINT.equals(screen)){
    		 log.log(Log.INFO, "Navigating back to Invoice Print", screen);
			invocationContext.target = PARENTSCREEN_INVOICEPRINT;
    	}


    	else{
    		
    		log.log(Log.INFO, "selected CLOSE_SUCCESS-->", screen);
			invocationContext.target = CLOSE_SUCCESS;
    	}


    }

}
