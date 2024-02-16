/*
 * ViewCustomerDetailsCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class ViewCustomerDetailsCommand extends BaseCommand {

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";

	private static final String SCREEN_ID = "customermanagement.defaults.maintainregcustomer";

	private static final String ACTION_SUCCESS = "list_success";
	private static final String CUSTOMER_STATUS = "customer.RegisterCustomer.status";
    private static final String CUSTOMER_TYPE_ONETIME = "shared.customer.customertype";
    private static final String AIRCRAFTTYPE_ONETIME = "shared.aircraft.flighttypes";
    private static final String CERTIFICATE_TYEP_ONETIME = "system.defaults.certificatetype";
    //Added by a-7804 as part of bug ICRD-220962
    private static final String BILLINGPERIOD_ONETIME = "cra.defaults.billingperiod";
    private static final String DATEOFEXCHANGE_ONETIME = "cra.defaults.dateofexchange";
  //Added by A-8329 as part of ICRD-319906
    private static final String ONETIME_CONTACTTYPE = "customermanagement.defaults.customercontact.contacttype";
    private static final String  ONETIME_CUSTOMERPREFERENCES = "customermanagement.defaults.customerpreferences";
    private static final String ONETIME_PAYMENTTYPE="shared.defaults.paymenttype";  
    private static final String ONETIME_LANGUAGE = "customermanagement.defaults.customercontact.language";
    private static final String ONETIME_FORMAT = "customermanagement.defaults.customercontact.format";
	 /**
     * Default Notification Mode
     * */
    private static final String DEF_NOT_MODE = "shared.customer.notificationmode";
    
    private static final String ADMIN_PRIV = "customermanagement.defaults.adminprivilege";
    private static final String ADMIN_PRIV_VALUE = "Y";
    private static final String STOCK_AUTOMATION_REQUIRED = "stockcontrol.defaults.stockautomationrequired";


	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("customerlisting");
		log.entering("customer", "view customer details command");
		MaintainRegCustomerForm form = (MaintainRegCustomerForm) invocationContext.screenModel;
		ListCustomerSession session = getScreenSession(MODULENAME, SCREENID);
		MaintainCustomerRegistrationSession maintainSession = getScreenSession(
				MODULENAME, SCREEN_ID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		String[] checked = form.getCheck();
		Map<String,Collection<OneTimeVO>> oneTimeValues = fetchScreenLoadDetails(companyCode);
		Map<String,String> systemParameterMap= fetchSystemParameterCodes();
		Collection<OneTimeVO> statusValues = oneTimeValues.get(CUSTOMER_STATUS);
       	Collection<OneTimeVO> notifyModes = oneTimeValues.get(DEF_NOT_MODE);
       	maintainSession.setCustomerStatus((ArrayList<OneTimeVO>)statusValues);
       	maintainSession.setDefaultNotifyModes((ArrayList<OneTimeVO>)notifyModes);
       	Collection<OneTimeVO> certificateTypes = oneTimeValues.get(CERTIFICATE_TYEP_ONETIME);    
       	maintainSession.setCertificateTypes((ArrayList<OneTimeVO>)certificateTypes);  
       	maintainSession.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>)oneTimeValues);
		Page<CustomerVO> customerVOs = session.getCustomerVOs();
		ArrayList<String> customerCodes = new ArrayList<String>();
		ArrayList<String> selectedCustomerCodes = new ArrayList<String>();
		for (CustomerVO customerVO : customerVOs) {
			customerCodes.add(customerVO.getCustomerCode());
		}

		for (int i = 0; i < checked.length; i++) {
			log.log(Log.FINE, "checked------------->" + checked[i]);
			selectedCustomerCodes.add(customerCodes.get(Integer
					.parseInt(checked[i])));
		}
		log.log(Log.FINE, "selected customer codes------->"
				+ selectedCustomerCodes);

		session.setCustomerCodes(selectedCustomerCodes);
		String customerCode = selectedCustomerCodes.get(0);
		maintainSession.setCustomerCodesFromListing(selectedCustomerCodes);
		//added by a-3045 for bug53620 on 20Jul09 starts
    	boolean hasPrivilege=false;
		try{
 			hasPrivilege = SecurityAgent.getInstance().checkBusinessPrivilege(ADMIN_PRIV);
 				
 		}catch(SystemException systemException){
//printStackTrrace()();
 	    }
 		if(hasPrivilege){
 			form.setAdminPrivilege(ADMIN_PRIV_VALUE);
 		}
		//added by a-3045 for bug53620 on 20Jul09 ends
		form.setCustomerCode(customerCode);
		form.setFromCustListing("Y");
		isAutomaticStockAllocationRequired(form,systemParameterMap);
		maintainSession.setSourcePage("LIST");
		invocationContext.target = ACTION_SUCCESS;
	}
	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Map<String,Collection<OneTimeVO>> fetchScreenLoadDetails(String companyCode){
		Map<String,Collection<OneTimeVO>> hashMap = new 
		HashMap<String,Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(CUSTOMER_STATUS);
		oneTimeList.add(DEF_NOT_MODE);
		oneTimeList.add(CUSTOMER_TYPE_ONETIME);
		oneTimeList.add(AIRCRAFTTYPE_ONETIME);
		oneTimeList.add(CERTIFICATE_TYEP_ONETIME);                        
		//Added by a-7804 as part of bug ICRD-220962
		oneTimeList.add(DATEOFEXCHANGE_ONETIME);
		oneTimeList.add(BILLINGPERIOD_ONETIME);                  
		oneTimeList.add("shared.customer.controlinglocationbillingapplicableto");
		 //Added by A-8329 as part of ICRD-319906
		oneTimeList.add(ONETIME_CUSTOMERPREFERENCES);
		oneTimeList.add(ONETIME_CONTACTTYPE);
		oneTimeList.add(ONETIME_PAYMENTTYPE);
		oneTimeList.add(ONETIME_LANGUAGE);
		oneTimeList.add(ONETIME_FORMAT);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		
		try{
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);
			
		}catch(BusinessDelegateException exception){
//printStackTrrace()();
			handleDelegateException(exception);
		}
		return hashMap;
	}

	private Map<String, String> fetchSystemParameterCodes() {
		Collection<String> systemParameterCodes = new ArrayList<String>();
		systemParameterCodes.add(STOCK_AUTOMATION_REQUIRED);
		Map<String, String> systemParametersMap = null;
		try {
			systemParametersMap = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);
		} catch (BusinessDelegateException ex) {
			handleDelegateException(ex);
		}
		return systemParametersMap;
	}
	private void isAutomaticStockAllocationRequired(MaintainRegCustomerForm form,
			Map<String, String> systemParameterMap) {
		if(systemParameterMap!=null && systemParameterMap.size() > 0){
			if(CustomerVO.FLAG_YES.equals(systemParameterMap.get(STOCK_AUTOMATION_REQUIRED) )){  
				form.setStockAutomationRequired(CustomerVO.FLAG_YES);
			} else{
				form.setStockAutomationRequired(CustomerVO.FLAG_NO);
			}
		}
	}
}
