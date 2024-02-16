/*
 * ScreenLoadTransLovCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.common;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.TransportModeLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1754
 *
 */

public class ScreenLoadTransLovCommand extends BaseCommand {

	private static final String TRANSPORT_MODE_ONETIME="products.defaults.transportmode";
	//private static final String COMPANY_CODE="AV";
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	/**
	 * Overriding the execute method of BaseCommand
	 * @param invocationContext
	 * @author A-1754
	 * @throws CommandInvocationException
	 */	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ScreenLoadTransLovCommand","execute");
		TransportModeLovForm transportModeLovForm= (TransportModeLovForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		Collection<String> fieldTypes = new ArrayList<String>();
		fieldTypes.add(TRANSPORT_MODE_ONETIME);
		Map<String, Collection<OneTimeVO>> oneTimes =
			getTransportModeLov(getApplicationSession().getLogonVO().getCompanyCode(),fieldTypes) ;
		if(oneTimes!=null){
			session.setTransModeLovVOs(oneTimes.get(TRANSPORT_MODE_ONETIME));
			session.setNextAction(transportModeLovForm.getNextAction());
		}
		invocationContext.target = "screenload_success";
		log.exiting("ScreenLoadTransLovCommand","execute");
		
	}
	/**
	 * The method calls the controller and returns the onetime vos corresponding to tramsport mode
	 * @param companyCode
	 * @param fieldTypes
	 * @return Map<String, Collection<OneTimeVO>> 
	 */
	private Map<String, Collection<OneTimeVO>> 
			getTransportModeLov(String companyCode,Collection<String>fieldTypes) {
		log.entering("ScreenLoadTransLovCommand","getTransportModeLov");
		Map<String,Collection<OneTimeVO>> tMode  = null;
		try{
			
		 tMode = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldTypes);
		
		}catch(BusinessDelegateException e){			
		}
		log.exiting("ScreenLoadTransLovCommand","getTransportModeLov");
		return tMode;
		
	}
}