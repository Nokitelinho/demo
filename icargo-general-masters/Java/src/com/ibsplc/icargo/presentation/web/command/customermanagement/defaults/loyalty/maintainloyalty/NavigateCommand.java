/*
 * NavigateCommand.java Created on Apr 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.maintainloyalty;

import java.util.ArrayList;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.MaintainLoyaltySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.MaintainLoyaltyForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to navigate Maintain Loyalty
 * 
 * @author A-1862
 * 
 */


public class NavigateCommand extends BaseCommand{
	
	
	/**
	 * The Module Name
	 */
	private static final String MODULE = "customermanagement.defaults";
	
	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID =
		"customermanagement.defaults.maintainloyalty";
	private static final String NAVIGATE_SUCCESS = "navigate_success";
	
	 
	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	Log log = LogFactory.getLogger("Navigate Command");
    	log.entering("Navigate Command","Navigate Command");
    	MaintainLoyaltySession maintainLoyaltySession =
			(MaintainLoyaltySession)getScreenSession(MODULE,
					SCREENID);
    	MaintainLoyaltyForm maintainLoyaltyForm =
					(MaintainLoyaltyForm)invocationContext.screenModel;
			
    	 ArrayList<String> loyaltyNames = maintainLoyaltySession.getLoyaltyNames();
    	
    	 log.log(Log.FINE,"Current Page--------------->"+Integer.parseInt(maintainLoyaltyForm.getDisplayPage()));
    	String loyaltyName = loyaltyNames.get(Integer.parseInt(maintainLoyaltyForm.getDisplayPage())-1);
    
    	 log.log(Log.FINE,"loyaltyName--------------->"+loyaltyName);
    	 
    	 maintainLoyaltyForm.setLoyaltyName(loyaltyName);
    	
    	 maintainLoyaltyForm.setPageURL(maintainLoyaltySession.getPageURL());
    	 maintainLoyaltyForm.setNavigate(true);
    	 maintainLoyaltyForm.setSaved(false);
    	
    	if(maintainLoyaltySession.getPageURL()!=null){
    		//maintainLoyaltyForm.setScreenStatusFlag("SCREENLOAD");
    		maintainLoyaltyForm.setScreenStatusValue("SCREENLOAD");
    		
    		maintainLoyaltySession.setLoyaltyProgrammeVO(null);
    		maintainLoyaltySession.setLoyaltyProgrammeLovVOs(null);	
        	//maintainLoyaltyForm.setScreenStatusFlag("SCREENLOAD");
        	maintainLoyaltyForm.setScreenStatusValue("SCREENLOAD");
        	maintainLoyaltySession.setAttributeValue(null);
    		maintainLoyaltySession.setUnitValue(null);
    		maintainLoyaltySession.setAmountValue(null);
    		maintainLoyaltySession.setPointsValue(null);
    		maintainLoyaltySession.setParameterVOsForDisplay(null);
    		maintainLoyaltySession.setParameterVOsForLOV(null);
    		
    		}
    	
    	
    	invocationContext.target=NAVIGATE_SUCCESS;
    }
    

   
}
