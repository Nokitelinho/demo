/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment.ScreenLoadCommand.java
 *
 *	Created by	:	A-4809
 *	Created on	:	18-Oct-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment;

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
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.receivablemanagement.AdvancePaymentModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment.ScreenLoadCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	18-Oct-2021	:	Draft
 */
public class ScreenLoadCommand extends AbstractCommand{
	
	private Log log = LogFactory.getLogger("Mail Mra AdvancePayment ");
	
	private static final String MODULE_NAME = "mail.mra";
	private static final String CLASS_NAME="ScreenLoadCommand";
	private static final String SCREENID = "mail.mra.receivablemanagement.ux.advancepayment";
	
	/**Onetime**/
	private static final String KEY_BATCH_STATUS_ONETIME = "mail.mra.receivablemanagement.batchstatus";
	
	/**System parameter**/
	private static final String SYS_PAR_MAX_PAGE_FETCH = "system.defaults.maxPagefetchSize";
	

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.spring.command.AbstractCommand#execute(com.ibsplc.icargo.framework.web.spring.controller.ActionContext)
	 *	Added by 			: A-4809 on 18-Oct-2021
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
	CommandInvocationException {
		this.log.entering(MODULE_NAME, CLASS_NAME);
		LogonAttributes logonAttributes = getLogonAttribute();
		String companyCode = logonAttributes.getCompanyCode();
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		AdvancePaymentModel advancePaymentModel = (AdvancePaymentModel)actionContext.getScreenModel();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Map<String, String> systemParameters = null;
		Collection<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(SYS_PAR_MAX_PAGE_FETCH); 
		
		oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(companyCode,getOneTimeParameterTypes());
		systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterTypes);
		
	     this.log.log(5, new Object[] { "oneTimeValues ---> ", oneTimeValues });
	     this.log.log(5, new Object[] { "systemParameters ---> ", systemParameters });
		
	     advancePaymentModel.setOneTimeValues(MailMRAModelConverter.constructOneTimeValues(oneTimeValues));
	     advancePaymentModel.setMaxPageCount(systemParameters.get(SYS_PAR_MAX_PAGE_FETCH));
	     List<AdvancePaymentModel> results = new ArrayList<AdvancePaymentModel>();
	     results.add(advancePaymentModel);
		 ResponseVO responseVO = new ResponseVO();
		 responseVO.setStatus("success");
		 responseVO.setResults(results); 
		 actionContext.setResponseVO(responseVO);
		this.log.exiting(MODULE_NAME, CLASS_NAME);
	}


	/**
	 * 	Method		:	ScreenLoadCommand.getOneTimeParameterTypes
	 *	Added by 	:	A-4809 on 18-Oct-2021
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<String>
	 */
	private Collection<String> getOneTimeParameterTypes() {
		Collection<String> parameterTypes = new ArrayList();
	    parameterTypes.add(KEY_BATCH_STATUS_ONETIME);
	    return parameterTypes;
	}

}
