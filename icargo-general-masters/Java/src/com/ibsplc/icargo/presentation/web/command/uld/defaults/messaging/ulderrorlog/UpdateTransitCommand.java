/*
 * UpdateTransitCommand.java Created on AUG 25, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ulderrorlog;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.ULDErrorLogForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on 
 * UpdateTransitCommand 
 * 
 * @author A-1862
 */

public class UpdateTransitCommand extends BaseCommand {
    
	/**
	 * Logger for ULD Error Log
	 */
	private Log log = LogFactory.getLogger("ULD Error Log");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	private static final String SCREENID_ULDERRORLOG =
		"uld.defaults.ulderrorlog";
	
	private static final String TRANSIT_SUCCESS = "transit_success";
    

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
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String  compCode = logonAttributes.getCompanyCode();		
		ULDErrorLogSession uldErrorLogSession = 
			(ULDErrorLogSession)getScreenSession(MODULE,SCREENID_ULDERRORLOG);
		ULDErrorLogForm uldErrorLogForm = 
			(ULDErrorLogForm) invocationContext.screenModel;
		ULDFlightMessageReconcileDetailsVO uldDetailsVO = new ULDFlightMessageReconcileDetailsVO();
		uldDetailsVO=uldErrorLogSession.getULDFlightMessageReconcileDetailsVO();	
		
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
			new ULDDefaultsDelegate().updateULDTransitStatus(compCode,uldDetailsVO.getUldNumber());
		}
		catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			exception = handleDelegateException(businessDelegateException);
		}
		
			
		log.log(Log.FINE, "\n \n getULDFlightMessageReconcileDetailsVO()",
				uldErrorLogSession.getULDFlightMessageReconcileDetailsVO());
		try {
			log.log(Log.FINE, "\n reconcile  delegate " );
			new ULDDefaultsDelegate().reconcileUCMULDError(uldErrorLogSession.getULDFlightMessageReconcileDetailsVO());
		}
		catch(BusinessDelegateException businessDelegateException) {				
			businessDelegateException.getMessage();
			exception = handleDelegateException(businessDelegateException);
		}				     
	 	 
		
		uldErrorLogForm.setErrorCode("");
		uldErrorLogSession.setULDFlightMessageReconcileDetailsVO(null);
		
		invocationContext.target = TRANSIT_SUCCESS;
        
    }
 
    
}
