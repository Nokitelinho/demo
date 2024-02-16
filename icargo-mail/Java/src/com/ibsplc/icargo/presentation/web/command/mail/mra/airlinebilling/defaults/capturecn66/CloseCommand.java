/*
 * CloseCommand.java Created on Jan 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN66Form;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author a-2408
 *
 */
public class CloseCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("AirlineBilling CloseCommand");

	private static final String CLASS_NAME = "CloseCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.capturecn51";

	private static final String SCREENID_VIEWCN51 = "mailtracking.mra.airlinebilling.defaults.listCN51s";

	private static final String FROM_CRA ="fromCraPostingDetails";

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
     */
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering(CLASS_NAME, "execute");
    	CaptureCN66Form form=(CaptureCN66Form)invocationContext.screenModel;
    	CaptureCN66Session session=null;
   		session=(CaptureCN66Session) getScreenSession(MODULE_NAME,SCREEN_ID);

   		log.log(Log.INFO, "inside CloseCOMMAND==FromScreenStatus==>>>>", form.getFromScreenStatus());
		String closeSUCCESS = "close_success";
   		//String fromstatus=form.getFromScreenStatus();
   		if(SCREENID.equals(session.getParentId())){
   			closeSUCCESS="cn51screen_success";
   			//form.setScreenStatus("list");
   			//form.setFromScreenStatus("");
   			session.removeParentId();
   		}
   		else if(SCREENID_VIEWCN51.equals(session.getParentId())){
   			closeSUCCESS="viewcn51_screen";
   			session.removeParentId();
   		}

   		else if(FROM_CRA.equals(session.getParentId())){
   			closeSUCCESS="tocra_screen";
   			session.removeParentId();
   		}
   		else{
   			session.removeAllAttributes();
   		}
   		/*if("cn51screen".equals(fromstatus))
   			closeSUCCESS="cn51screen_success";*/


   		invocationContext.target = closeSUCCESS;	// sets target
		log.exiting(CLASS_NAME,"execute");
    }

}
