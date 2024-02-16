/*
 * ScreenloadContainerDtlsCommand.java Created on Oct 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ScannedDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1876
 *
 */
public class ScreenloadContainerDtlsCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.uploadmail";	
	private static final String CONTAINERTYPE = "mailtracking.defaults.containertype";
	private static final String TARGET = "success";
	
		
	/** 
	 * The execute method for ScreenLoadCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
				
		log.entering("ScreenloadContainerDtlsCommand","execute");
		UploadMailForm uploadMailForm 
			= (UploadMailForm)invocationContext.screenModel;
		UploadMailSession uploadMailSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
		
		
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		ScannedDetailsVO scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
		
		String containers = uploadMailForm.getContinerDetails();
		log.log(Log.INFO, "containers.\n", containers);
		/*
        * Getting OneTime values
        */
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		Collection<OneTimeVO> ctVOs = new ArrayList<OneTimeVO>();
		if(oneTimes!=null){
		   ctVOs = oneTimes.get(CONTAINERTYPE);
		}
		uploadMailSession.setOneTimeContainerType(ctVOs);
		
	    log.log(Log.INFO, "scannedDetailsVO....after server.\n",
				scannedDetailsVO);
		uploadMailSession.setScannedDetailsVO(scannedDetailsVO);
		
		invocationContext.target = TARGET;
		log.exiting("ScreenloadContainerDtlsCommand","execute");
		
	}
	
	
	/**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add(CONTAINERTYPE);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}

}
