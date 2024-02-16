/*
 * CloseCommand.java Created on Apr 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.maintainloyalty;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.MaintainLoyaltySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.MaintainLoyaltyForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to close Loyalty
 * @author A-1862
 */
public class CloseCommand extends BaseCommand {
	
	
	private Log log = LogFactory.getLogger("Maintain Loyalty");
	
	private static final String MODULE = "customermanagement.defaults";
	
	
	private static final String SCREENID =
		"customermanagement.defaults.maintainloyalty";
	
	
	private static final String CLOSELISTLOYALTY_SUCCESS = "close_listloyalty_success";
    
    private static final String CLOSE_SUCCESS = "close_success";
    
    
    

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		
		MaintainLoyaltyForm maintainLoyaltyForm = 
			(MaintainLoyaltyForm) invocationContext.screenModel;
		MaintainLoyaltySession maintainLoyaltySession = 
			(MaintainLoyaltySession)getScreenSession(MODULE,SCREENID);
		
		
		
		log.log(Log.FINE, "maintainLoyaltySession.getPageURL()",
				maintainLoyaltySession.getPageURL());
		maintainLoyaltyForm.setSaved(false);
         if(maintainLoyaltySession.getPageURL()!=null){
        	 
        	 invocationContext.target=CLOSELISTLOYALTY_SUCCESS;


         }
         else{
        	 invocationContext.target=CLOSE_SUCCESS;
         }
         
         maintainLoyaltySession.setLoyaltyProgrammeVO(null);
 		maintainLoyaltySession.setLoyaltyNames(null);
 		maintainLoyaltySession.setPageURL(null);
 		maintainLoyaltySession.setParentScreenId(null);
 		maintainLoyaltySession.setLoyaltyProgrammeLovVOs(null);	
 		maintainLoyaltySession.setAttributeValue(null);
 		maintainLoyaltySession.setUnitValue(null);
 		maintainLoyaltySession.setAmountValue(null);
 		maintainLoyaltySession.setPointsValue(null);
         	
        
    }
   
	


}
