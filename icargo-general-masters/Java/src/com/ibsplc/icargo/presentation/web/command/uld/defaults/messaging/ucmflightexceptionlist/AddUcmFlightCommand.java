/*
 * AddUcmFlightCommand.java Created on Jul 20,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucmflightexceptionlist;













import com.ibsplc.icargo.business.uld.defaults.vo.UCMExceptionFlightVO;




import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;





import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMFlightExceptionListSession;

import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMFlightExceptionListForm;


import com.ibsplc.xibase.server.framework.vo.AbstractVO;


import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * This command class is used to list the UCM error logs
 * @author A-1862
 */

public class AddUcmFlightCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("ListUcmFlightException Error Logs");
	private static final String MODULE = "uld.defaults";
	/**
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID =
		"uld.defaults.ucmflightexceptionlist";
    
	private static final String ADD_SUCCESS = "add_success";
	private static final String ADD_FAILURE = "add_failure";
 
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	if(invocationContext.getErrors() != null
    			&& invocationContext.getErrors().size() > 0) {
    		invocationContext.target = ADD_FAILURE;
    		return;
    	}
    	
		UCMFlightExceptionListForm ucmFlightExceptionListForm = 
			(UCMFlightExceptionListForm) invocationContext.screenModel;
		UCMFlightExceptionListSession ucmFlightExceptionListSession = 
			(UCMFlightExceptionListSession)getScreenSession(MODULE,SCREENID);
		ucmFlightExceptionListForm.setDuplicateFlightStatus(
				AbstractVO.FLAG_NO);
		ucmFlightExceptionListForm.setActionStatus(
				"");
		UCMExceptionFlightVO ucmExceptionFlightVO = new UCMExceptionFlightVO();
		ucmExceptionFlightVO.setOpeartionalFlag(
				UCMExceptionFlightVO.OPERATION_FLAG_INSERT);
		ucmFlightExceptionListSession.getUcmExceptionFlightVOs().add(ucmExceptionFlightVO);	
		invocationContext.target = ADD_SUCCESS;
        
    }
  
	
}
