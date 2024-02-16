/*
 * ClearDmgRefNoLovCommand.java Created on Feb 07, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.
													maintaindamagereport;

//import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.DamageRefNoLovSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.DamageRefNoLovForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked clearing the screen
 *
 * @author A-1862
 */
public class ClearDmgRefNoLovCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("ClearDmgRefNoLovCommand");
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID =
		"uld.defaults.damagerefnolov";
	private static final String CLEAR_SUCCESS = "clear_success";

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
    	//Commented by Manaf for INT ULD510
    	//LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//String  compCode = logonAttributes.getCompanyCode();
		DamageRefNoLovForm form = 
			(DamageRefNoLovForm)invocationContext.screenModel;
		
		DamageRefNoLovSession session = 
    			getScreenSession(MODULE, SCREENID);
    	form.setUldNo("");
    	form.setDisplayPage("1");
		
		if(form.getListPage()!=null){
			form.setListPage(null);
		}
		session.removeULDDamageReferenceNumberLovVOs();
			
    	invocationContext.target = CLEAR_SUCCESS;

    }
   
	
}
