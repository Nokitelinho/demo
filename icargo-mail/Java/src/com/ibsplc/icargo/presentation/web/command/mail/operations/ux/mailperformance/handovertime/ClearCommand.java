/*
 * ClearCommand.java Created on Jul 05, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.handovertime;

//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;

//import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
//import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
//import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm;
//import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-6986
 *
 */
public class ClearCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	private static final String SUCCESS = "clear_success";
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		MailPerformanceForm mailPerformanceForm =
				(MailPerformanceForm)invocationContext.screenModel;
		
		MailPerformanceSession mailPerformanceSession = 
				getScreenSession(MODULE_NAME,SCREEN_ID);
		
		
		
		mailPerformanceForm.setHoPaCode("");
		mailPerformanceForm.setHoAirport("");
		mailPerformanceForm.setHoMailClass("");
		mailPerformanceForm.setHoExchangeOffice("");
		mailPerformanceForm.setHoMailSubClass("");

		//Modified by A-8399 as part of ICRD-293432
		
		/*SharedDefaultsDelegate defaultsDelegate = new SharedDefaultsDelegate();
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	String companyCode = logonAttributes.getCompanyCode();
    	Map<String, Collection<OneTimeVO>> hashMap = 
    		      new HashMap<String, Collection<OneTimeVO>>();
    	 Collection<String> oneTimeList = new ArrayList();
    	 oneTimeList.add("mail.operations.mailservicelevels");
    	 try
    	    {
    	      hashMap = defaultsDelegate.findOneTimeValues(companyCode, 
    	        oneTimeList);
    	    }
    	    catch (BusinessDelegateException localBusinessDelegateException3)
    	    {
    	      this.log.log(7, "onetime fetch exception");
    	    }
    	 
    	 Collection<OneTimeVO> serviceLevels = (Collection<OneTimeVO>)hashMap.get("mail.operations.mailservicelevels");
    	 mailPerformanceSession.setServiceLevel((ArrayList<OneTimeVO>) serviceLevels);
    	 */
    	 
    	 mailPerformanceSession.removeMailHandoverVOs();
    	 
    	 mailPerformanceForm.setStatusFlag("Clear_screen"); //Added by A-8399 as part of ICRD-293432
    	 mailPerformanceForm.setScreenFlag("hoRadiobtn");
		mailPerformanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SUCCESS;
	}
}
