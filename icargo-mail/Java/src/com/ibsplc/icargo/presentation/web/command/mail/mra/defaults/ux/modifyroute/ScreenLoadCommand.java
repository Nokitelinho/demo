/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.ux.modifyroute.ScreenLoadCommand.java
 *
 *	Created by	:	A-8061
 *	Created on	:	Dec 10, 2020
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.ux.modifyroute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.defaults.ModifyRouteModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.ux.modifyroute.ScreenLoadCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8061	:	Dec 10, 2020	:	Draft
 */
public class ScreenLoadCommand extends AbstractCommand {
	private Log log = LogFactory.getLogger("Modify Route");
	
	private static final String MODULE_NAME = "mail.mra";
	private static final String CLASS_NAME="ScreenLoadCommand";
	private static final String SUCCESS="success";
	private static final String ONETIME_BLOCKSPACE_TYPES="cra.proration.blockspacetype";
	private static final String ONTIME_AGREEMENT_TYPES="mailtracking.defaults.agreementtype";
	

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		this.log.entering(MODULE_NAME, CLASS_NAME);
		LogonAttributes logonAttributes = getLogonAttribute();
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		ModifyRouteModel modifyRouteModel = (ModifyRouteModel)actionContext.getScreenModel();
	
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
	     try
	     		{
	    	      oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
	    	        logonAttributes.getCompanyCode(), getOneTimeParameterTypes());
	    	    }
	    	    catch (BusinessDelegateException e)
	    	    {
	    	      actionContext.addAllError(handleDelegateException(e));
	    	    }	
	     modifyRouteModel.setOneTimeValues(oneTimeValues);

	     
	     List<ModifyRouteModel> results = new ArrayList<>();
	     results.add(modifyRouteModel);
		 ResponseVO responseVO = new ResponseVO();
		 responseVO.setStatus(SUCCESS);
		 responseVO.setResults(results); 
		 actionContext.setResponseVO(responseVO);
		this.log.exiting(MODULE_NAME, CLASS_NAME);
		
	}
	
	private Collection<String> getOneTimeParameterTypes() {
		Collection<String> oneTimeList = new ArrayList<>();
		oneTimeList.add(ONETIME_BLOCKSPACE_TYPES);
		oneTimeList.add(ONTIME_AGREEMENT_TYPES);
		return oneTimeList;
	}
	
	

}
