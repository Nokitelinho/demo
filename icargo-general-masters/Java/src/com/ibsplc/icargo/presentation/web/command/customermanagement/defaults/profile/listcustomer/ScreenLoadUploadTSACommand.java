/*
 * ScreenLoadUploadTSACommand.java Created on Jun 22, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.UploadTSAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3045
 *
 */
public class ScreenLoadUploadTSACommand extends BaseCommand {

	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	
	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";
	
	private static final String UPLOAD_FILETYPE = "shared.customer.uploadfiletype";
	
	private Log log = LogFactory.getLogger("ScreenLoadUPloadTSACommand");
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException{
			log.entering("ScreenLoadUPloadTSACommand","ENTER");
			UploadTSAForm form = (UploadTSAForm)invocationContext.screenModel;
	    	String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
	    	ListCustomerSession session = getScreenSession(MODULENAME,SCREENID);
	    	CustomerMgmntDefaultsDelegate delegate  = new CustomerMgmntDefaultsDelegate();	    	
	    	SharedDefaultsDelegate sharedDefaultsDelegate =
				new SharedDefaultsDelegate();
			Map<String, Collection<OneTimeVO>> oneTimeValues = null;			
			try {
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					companyCode, getOneTimeParameterTypes());
			} catch (BusinessDelegateException e) {
//printStackTrrace()();
			handleDelegateException(e);
			}			
			Collection<OneTimeVO> fileTypes = oneTimeValues.get(UPLOAD_FILETYPE);
			log.log(Log.FINE, "****fileTypes  OneTime******", fileTypes);
			session.setTSAFiletype((ArrayList<OneTimeVO>)fileTypes);		
			invocationContext.target=SCREENLOAD_SUCCESS;
	}
	
	/**
	 * Method to populate the collection of onetime parameters to be obtained
	 * 
	 * @return parameterTypes
	 */
	private Collection<String> getOneTimeParameterTypes() {
		log.entering("ScreenLoadCommand", "getOneTimeParameterTypes");
		ArrayList<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(UPLOAD_FILETYPE);
		log.exiting("ScreenLoadCommand", "getOneTimeParameterTypes");
		return parameterTypes;
	}
}
