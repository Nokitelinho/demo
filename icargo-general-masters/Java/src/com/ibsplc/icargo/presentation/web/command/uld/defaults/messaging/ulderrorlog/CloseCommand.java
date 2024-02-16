/*
 * CloseCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ulderrorlog;

//import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up of the 
 * ScreenLoadULDErrorLogCommand screen
 * 
 * @author A-1862
 */

public class CloseCommand extends BaseCommand {
    
	/**
	 * Logger for ULD Error Log
	 */
	private Log log = LogFactory.getLogger("UCM Error Log");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/**
	 * Screen Id of ucm error log
	 */
	private static final String SCREENID =
		"uld.defaults.ulderrorlog";
	private static final String SCREENID_UCMERRORLOG =
		"uld.defaults.ucmerrorlog";
	
	private static final String CLOSE_SUCCESS = "close_success";
	private static final String UCMERRORLOG_SUCCESS = "ucmerrorlog_success";
    

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		//Commented by Manaf for INT ULD510
		//LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//String  compCode = logonAttributes.getCompanyCode();
		ULDErrorLogSession uldErrorLogSession = 
			(ULDErrorLogSession)getScreenSession(MODULE,SCREENID);
		UCMErrorLogSession ucmErrorLogSession = 
			(UCMErrorLogSession)getScreenSession(MODULE,SCREENID_UCMERRORLOG);		
		//Below line commented as part of bug ICRD-231110
		//ucmErrorLogSession.setULDFlightMessageReconcileVOs(null);
		log.log(Log.FINE, "uldErrorLogSession.getPageURL() ---> ",
				uldErrorLogSession.getPageURL());
		if((("fromucmerrorlog").equals(uldErrorLogSession.getPageURL()))
				|| (("fromucmerrorlog").equals(uldErrorLogSession.getForPic()))){
			ucmErrorLogSession.setPageURL("fromulderrorlog");
			uldErrorLogSession.removeAllAttributes();
			invocationContext.target = UCMERRORLOG_SUCCESS;
			return;
			
		}//Modifed by A-7359 for ICRD-225848
		else if(("fromSendUCMInOut").equals(uldErrorLogSession.getPageURL())
				||(("fromSendUCMInOut").equals(uldErrorLogSession.getForPic()))){
			uldErrorLogSession.removeAllAttributes();
			invocationContext.target = UCMERRORLOG_SUCCESS; 
			return;
		}
		
		uldErrorLogSession.removeAllAttributes();
		invocationContext.target = CLOSE_SUCCESS;
        
    }
 
    
}
