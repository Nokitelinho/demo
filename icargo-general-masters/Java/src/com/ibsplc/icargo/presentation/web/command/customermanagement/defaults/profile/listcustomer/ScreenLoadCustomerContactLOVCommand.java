/*
 * ScreenLoadCustomerContactLOVCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;


import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2052
 *
 */
public class ScreenLoadCustomerContactLOVCommand extends BaseCommand {

    private static final String SCREENLOAD_SUCCESS = "screenload_success";
    private static final String MODULENAME = "customermanagement.defaults";
	private static final String SCREENID = "customermanagement.defaults.customerlisting";
	private Log log = LogFactory.getLogger("ScreenLoadCustomerContactLOVCommand");
/**
 * @param invocationContext
 * @throws CommandInvocationException
 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ScreenLoadCustomerContactLOVCommand","ENTER");
    	ListCustomerForm form = (ListCustomerForm)invocationContext.screenModel;
    	ApplicationSessionImpl applicationSessionImpl=getApplicationSession();
    	LogonAttributes logonAttributes=applicationSessionImpl.getLogonVO();
    	ListCustomerSession session = getScreenSession(MODULENAME,SCREENID);
    	    String companyCode=logonAttributes.getCompanyCode().toUpperCase();
    	Collection<CustomerContactVO> customerContactVOs= new ArrayList<CustomerContactVO>();
    	CustomerMgmntDefaultsDelegate delegate  = new CustomerMgmntDefaultsDelegate();
    	String customerCode = form.getCustomerCodeLov();
    	try {
    		log.log(Log.FINE,"before setting to delegate");
    		log.log(Log.FINE, "customerCode--------->>", customerCode);
			customerContactVOs = delegate.customerContactsLov(companyCode,customerCode);
    	}  catch(BusinessDelegateException e)
    		{
//printStackTrrace()();
    		handleDelegateException(e);
    		}
    	
    	session.setCustomerContactVOs(customerContactVOs);
    	log.log(Log.FINE, "getting from delegate", session.getCustomerContactVOs());
		log.exiting("ScreenLoadCustomerContactLOVCommand","EXIT");
    	invocationContext.target=SCREENLOAD_SUCCESS;
        }
}

