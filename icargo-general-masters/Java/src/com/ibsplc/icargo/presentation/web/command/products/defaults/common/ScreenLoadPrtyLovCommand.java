/*
 * ScreenLoadPrtyLovCommand.java Created on Jun 27, 2005
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
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.PriorityLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1754
 *
 */

public class ScreenLoadPrtyLovCommand extends BaseCommand {

	private static final String PRIORITY_ONETIME="products.defaults.priority";
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
		log.entering("ScreenLoadPrtyLovCommand","execute");
		PriorityLovForm priorityLovForm= (PriorityLovForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		Collection<String> fieldTypes = new ArrayList<String>();
		fieldTypes.add(PRIORITY_ONETIME);
		Map<String, Collection<OneTimeVO>> oneTimes =
			getTransportModeLov(getApplicationSession().getLogonVO().getCompanyCode(),fieldTypes) ;
		
		
		/*Collection<OneTimeVO> newOneTimeCollection1 = new ArrayList<OneTimeVO>(); 
		Collection<OneTimeVO> newOneTimeCollection2 = new ArrayList<OneTimeVO>(); */
		
		
		/*if(oneTimes!=null){
			
 			for(Collection<OneTimeVO> oneTimeColl : oneTimes.values()){
				newOneTimeCollection1 = oneTimeColl;
				break;
			}
			
			for(OneTimeVO oneTim : newOneTimeCollection1){
				if("H".equals(oneTim.getFieldValue())){
					newOneTimeCollection2.add(oneTim);
				}
			}
			for(OneTimeVO oneTim : newOneTimeCollection1){
				if("M".equals(oneTim.getFieldValue())){
					newOneTimeCollection2.add(oneTim);
				}
			}
			for(OneTimeVO oneTim : newOneTimeCollection1){
				if("L".equals(oneTim.getFieldValue())){
					newOneTimeCollection2.add(oneTim);
				}
			}
		  oneTimes.clear();
		  oneTimes.put(PRIORITY_ONETIME,newOneTimeCollection2);
		  
		}*/
		
		
		
		
		if(oneTimes!=null){
			session.setPriorityLovVOs(oneTimes.get(PRIORITY_ONETIME));
			session.setNextAction(priorityLovForm.getNextAction());
		}
		invocationContext.target = "screenload_success";
		log.exiting("ScreenLoadPrtyLovCommand","execute");
		
	}
	/**
	 * The method calls the controller and returns the onetime vos corresponding to tramsport mode
	 * @param companyCode
	 * @param fieldTypes
	 * @author A-1754
	 * @return Map<String, Collection<OneTimeVO>> 
	 */
	private Map<String, Collection<OneTimeVO>> 
			getTransportModeLov(String companyCode,Collection<String>fieldTypes) {
		log.entering("ScreenLoadPrtyLovCommand","getTransportModeLov");
		Map<String,Collection<OneTimeVO>> tMode  = null;
		try{
			
		 tMode = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldTypes);
		
		}catch(BusinessDelegateException e){
			
		}
		log.exiting("ScreenLoadPrtyLovCommand","getTransportModeLov");
		return tMode;
		
	}
}