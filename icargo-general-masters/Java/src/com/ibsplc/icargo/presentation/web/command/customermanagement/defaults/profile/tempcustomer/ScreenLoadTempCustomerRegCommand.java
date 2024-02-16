/*
 * ScreenLoadTempCustomerRegCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.tempcustomer;


import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListtempCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainTempCustomerForm;

/**
 * @author A-1496
 *
 */
public class ScreenLoadTempCustomerRegCommand  extends BaseCommand {

    private static final String SCREENLOAD_SUCCESS = "screenload_success";
    private static final String SCREENLOAD_FAILURE = "screenload_failure";
    private static final String PRODUCT = "customermanagement";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "customermanagement.defaults.maintaintempcustomerform";
	
	private static final String MODULE = "customermanagement.defaults";

/**
 * @param invocationContext
 * @throws CommandInvocationException
 * 
 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	MaintainTempCustomerForm actionForm =
			(MaintainTempCustomerForm) invocationContext.screenModel;    	
     	ListtempCustomerSession listtempCustomerSession =
    		getScreenSession(MODULE, SCREENID);
     
    	actionForm.setScreenStatusFlag(
    			ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	listtempCustomerSession.removeTempCustomerDetails();
    	listtempCustomerSession.removeListtempcustomerregistration();   
    	listtempCustomerSession.removeListCustomerRegistration();
    	listtempCustomerSession.removeTempIdVOLovVOs();  
    	listtempCustomerSession.setTempIDs(null);
    	listtempCustomerSession.setCustCodeFlag(null);
    	listtempCustomerSession.setPageURL(null);   	
    	actionForm.setStation(logonAttributes.getStationCode());
    	if("listtempcustomerformfromcreate".equals(actionForm.getCloseFlag())){
    		listtempCustomerSession.setPageURL("listtempcustomerformfromcreate");
    		actionForm.setDisplayPage("1");
    		actionForm.setCloseFlag("listtempcustomerformfromcreate");
    		actionForm.setDetailsFlag("listtempcustomerformfromcreate");
    	}
    	
    	invocationContext.target=SCREENLOAD_SUCCESS;

    }


}
