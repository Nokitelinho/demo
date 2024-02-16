/*
 * ScreenLoadTempIDLOVCommand.java Created on Dec 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listtempcustomer;

import com.ibsplc.icargo.business.shared.customer.vo.ListTempCustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.TempCustomerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListtempCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.TempIDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2135
 *
 */
public class ScreenLoadTempIDLOVCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("ScreenLoadTempIDLOVCommand");	
    private static final String SCREENLOAD_SUCCESS = "screenload_success";
    private static final String MODULE = "customermanagement.defaults";
	private static final String SCREENID ="customermanagement.defaults.listtempidlov";
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ScreenLoadTempIDLOVCommand", "execute"); 
    	TempIDForm actionForm =
			(TempIDForm) invocationContext.screenModel;
    	ApplicationSessionImpl applicationSessionImpl=getApplicationSession();
    	LogonAttributes logonAttributes=applicationSessionImpl.getLogonVO();
    	ListtempCustomerSession listtempCustomerSession = getScreenSession(MODULE, SCREENID);
    	    String companyCode=logonAttributes.getCompanyCode().toUpperCase();
    	    ListTempCustomerVO  listtempCustomerVO =new ListTempCustomerVO();
    	    listtempCustomerVO.setTempCustCode(actionForm.getTempid());
    	    listtempCustomerVO.setCompanyCode(companyCode);
    	    listtempCustomerVO.setDisplayPage(Integer.parseInt(actionForm.getDisplayPage()));
    	Page<TempCustomerVO> tempcustomercodes= null;
    	CustomerMgmntDefaultsDelegate customerMgmntDefaultsDelegate = new CustomerMgmntDefaultsDelegate();
    	try {
    		tempcustomercodes = customerMgmntDefaultsDelegate.listTempCustomerDetails(listtempCustomerVO);
    		log.log(Log.FINE, "&&&&&&&&&&&&&&&&&&&&tempcustomercodes&&&&&&&&&",
					tempcustomercodes);
    	}  catch(BusinessDelegateException e) {
//printStackTrrace()();
    		handleDelegateException(e);
    	}
    	listtempCustomerSession.setTempIdVOLovVOs(tempcustomercodes);
    	invocationContext.target=SCREENLOAD_SUCCESS;

        }


    	}

