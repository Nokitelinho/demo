/*
 * ScreenLoadRunningLoyaltyLOVCommand.java Created on May 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.maintainloyalty;


import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.MaintainLoyaltySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.RunningLoyaltyProgrammeLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2122
 *
 */
public class ScreenLoadRunningLoyaltyLOVCommand extends BaseCommand {

    private static final String SCREENLOAD_SUCCESS = "screenload_success";
   
    private static final String MODULE = "customermanagement.defaults";
	private static final String SCREENID ="customermanagement.defaults.maintainloyalty";
	
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	    	
    	ApplicationSessionImpl applicationSessionImpl=getApplicationSession();
    	LogonAttributes logonAttributes=applicationSessionImpl.getLogonVO();
    	MaintainLoyaltySession maintainLoyaltySession = 
			(MaintainLoyaltySession)getScreenSession(MODULE,SCREENID);
    	RunningLoyaltyProgrammeLovForm runningLoyaltyProgrammeLovForm = 
			(RunningLoyaltyProgrammeLovForm)invocationContext.screenModel;
    	String companyCode=logonAttributes.getCompanyCode().toUpperCase();
    	String displayPage = runningLoyaltyProgrammeLovForm.getDisplayPage();    	
    	int pageNumber=Integer.parseInt(displayPage);
    	LocalDate currentDate=new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
    	Page<LoyaltyProgrammeVO> loyaltyProgrammeVOs=null;
    	
    	CustomerMgmntDefaultsDelegate customerMgmntDefaultsDelegate = new CustomerMgmntDefaultsDelegate();
    	try {
    		loyaltyProgrammeVOs = customerMgmntDefaultsDelegate.
    						runningLoyaltyProgrammeLOV(companyCode,currentDate,pageNumber);

    				
    	}catch(BusinessDelegateException e){
    		handleDelegateException(e);
    	}
    	if(loyaltyProgrammeVOs!=null && loyaltyProgrammeVOs.size()>0)
    	{
    		maintainLoyaltySession.setRunningLoyaltyProgrammeLovVOs(loyaltyProgrammeVOs);
    	} else {
			maintainLoyaltySession.setLoyaltyProgrammeLovVOs(null);
		}
    	
    	invocationContext.target=SCREENLOAD_SUCCESS;

    }



}
