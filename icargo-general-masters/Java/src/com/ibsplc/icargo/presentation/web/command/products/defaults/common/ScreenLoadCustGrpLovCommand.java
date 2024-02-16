/*
 * ScreenLoadCustGrpLovCommand.java Created on Oct 29, 2005
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
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.CustomerGroupLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1754
 *
 */

public class ScreenLoadCustGrpLovCommand extends BaseCommand {

	private static final String CUST_GROUP_ONETIME="shared.defaults.customergroup";
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
		log.entering("ScreenLoadCustGrpLovCommand","execute");
		CustomerGroupLovForm form= (CustomerGroupLovForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		Collection<String> fieldTypes = new ArrayList<String>();
		fieldTypes.add(CUST_GROUP_ONETIME);
		Map<String, Collection<OneTimeVO>> oneTimes =
			getCustGroupLov(getApplicationSession().getLogonVO().getCompanyCode(),fieldTypes) ;
		if(oneTimes!=null){
			session.setCustGrpLovVOs(oneTimes.get(CUST_GROUP_ONETIME));
			session.setNextAction(form.getNextAction());
		}
		invocationContext.target = "screenload_success";
		log.exiting("ScreenLoadCustGrpLovCommand","execute");
		
	}
	/**
	 * The method calls the controller and returns the onetime vos corresponding to tramsport mode
	 * @param companyCode
	 * @param fieldTypes
	 * @return Map<String, Collection<OneTimeVO>> 
	 */
	private Map<String, Collection<OneTimeVO>> 
			getCustGroupLov(String companyCode,Collection<String>fieldTypes) {
		log.entering("ScreenLoadCustGrpLovCommand","getCustGroupLov");
		Map<String,Collection<OneTimeVO>> tMode  = null;
		try{
			
		 tMode = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldTypes);
		
		}catch(BusinessDelegateException e){

		}
		log.exiting("ScreenLoadCustGrpLovCommand","getCustGroupLov");
		return tMode;
		
	}
}