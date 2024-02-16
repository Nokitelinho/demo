/*
 * ScreenLoadListTempCustomerCommand.java Created on Dec 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listtempcustomer;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListtempCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListTempCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ScreenLoadListTempCustomerCommand  extends BaseCommand {

    private static final String SCREENLOAD_SUCCESS = "screenload_success";

    private static final String SCREENID = "customermanagement.defaults.listtempcustomerform";
    private static final String MODULE = "customermanagement.defaults";
    private static final String STATUS = "customer.RegisterCustomer.status";
    private Log log = LogFactory.getLogger(
	"ScreenLoadListTempCustomerCommand");
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
 	ListTempCustomerForm actionForm =
			(ListTempCustomerForm) invocationContext.screenModel;
 	ApplicationSessionImpl applicationSessionImpl = 
		getApplicationSession();
	LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
    String companyCode = logonAttributes.getCompanyCode();
 	ListtempCustomerSession listtempCustomerSession =
		getScreenSession(MODULE, SCREENID);
	actionForm.setScreenStatusFlag(
			ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);  
	actionForm.setValidFrom
	(new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP, false).
									toDisplayDateOnlyFormat());
	actionForm.setValidTo
	(new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP, false).
									toDisplayDateOnlyFormat());
	SharedDefaultsDelegate sharedDefaultsDelegate =
    	new SharedDefaultsDelegate();
	Map activeStatusHashMap = null;
	Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
	oneTimeActiveStatusList.add(STATUS);
	try {
		
		activeStatusHashMap = sharedDefaultsDelegate.
			findOneTimeValues(companyCode,oneTimeActiveStatusList);
		
	} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
		handleDelegateException(businessDelegateException);
	}
	Collection<OneTimeVO> status = 
		(Collection<OneTimeVO>)activeStatusHashMap.get(STATUS);
	listtempCustomerSession.setOneTimeValues(status);
	listtempCustomerSession.removeListTempCustomerDetails();	
	listtempCustomerSession.removeListCustomerRegistration();
	//if(("fromcreate").equals(actionForm.getCheckFlag())){
	listtempCustomerSession.setPageURL("");
	//else if(("fromdetails").equals(actionForm.getCheckFlag())){
	//listtempCustomerSession.setPageURL("details");	
	//}
 	invocationContext.target=SCREENLOAD_SUCCESS;

}


}
