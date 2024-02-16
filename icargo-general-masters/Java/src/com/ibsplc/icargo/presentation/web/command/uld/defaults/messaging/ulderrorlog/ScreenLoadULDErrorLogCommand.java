/*
 * ScreenLoadULDErrorLogCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ulderrorlog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.ULDErrorLogForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up of the 
 * ScreenLoadULDErrorLogCommand screen
 * 
 * @author A-1862
 */

public class ScreenLoadULDErrorLogCommand extends BaseCommand {
    
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
	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
    
	private static final String CONTENT_ONETIME="uld.defaults.contentcodes";
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
		//Commented by Manaf for INT ULD510
		//String  compCode = logonAttributes.getCompanyCode();
		ULDErrorLogSession uldErrorLogSession = 
			(ULDErrorLogSession)getScreenSession(MODULE,SCREENID);
		uldErrorLogSession.removeAllAttributes();
		ULDErrorLogForm uldErrorLogForm = 
			(ULDErrorLogForm) invocationContext.screenModel;
		uldErrorLogForm.setCarrierCode("");
		uldErrorLogForm.setUlderrorlogAirport("");
		uldErrorLogForm.setUlderrorlogULDNo("");
		uldErrorLogForm.setFlightDate("");
		uldErrorLogForm.setFlightNo("");
		uldErrorLogForm.setCurrentPageNum("");
		uldErrorLogForm.setDisplayPage("1");
		uldErrorLogForm.setMessageType("OUT");
		uldErrorLogForm.setLastPageNumber("0");
		uldErrorLogForm.setScreenFlag("screenload");
		
		/*if(logonAttributes.isAirlineUser()){    		
			uldErrorLogForm.setUldDisableStat("airline");*/
			uldErrorLogForm.setCarrierCode(logonAttributes.getOwnAirlineCode());
    	/*}
    	else{*/
    		uldErrorLogForm.setUlderrorlogAirport(logonAttributes.getAirportCode());
    		/*uldErrorLogForm.setUldDisableStat("GHA");
    	}*/
		
		 Collection<String> onetimeColl= new ArrayList<String>();
		 
 	    Map<String,Collection<OneTimeVO>> oneTimeValues=null;
 	    onetimeColl.add(CONTENT_ONETIME);
        Collection<ErrorVO> err = new ArrayList<ErrorVO>();
		   try{
 	     oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(),onetimeColl);
		   }catch(BusinessDelegateException ex){
			 ex.getMessage();
			 err = handleDelegateException(ex);
		   }
 	  Collection<OneTimeVO> contentOneTimeValues = oneTimeValues.get(CONTENT_ONETIME);
 	  log.log(Log.FINE, "content reurned is ====", contentOneTimeValues);
	uldErrorLogSession.setContent((ArrayList<OneTimeVO>)contentOneTimeValues);	
		invocationContext.target = SCREENLOAD_SUCCESS;
        
    }
    
   
 
    
}
