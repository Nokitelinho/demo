/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.generatepassbillingfile.ScreenLoadCommand.java;
 *
 *	Created by	:	A-9084
 *	Created on	:	Jan 26, 2021
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.generatepassbillingfile;

import java.util.ArrayList;

import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.web.model.mail.mra.gpabilling.GeneratePASSBillingFileModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ScreenLoadCommand extends AbstractCommand{

	private Log log = LogFactory.getLogger("MRA MAIL");
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
	CommandInvocationException {
		this.log.entering("ScreenLoadCommand", "execute");
		GeneratePASSBillingFileModel generatepassbillingmodel = (GeneratePASSBillingFileModel)actionContext.getScreenModel();
		
		ResponseVO responseVO = new ResponseVO();
		ArrayList<GeneratePASSBillingFileModel> results = new ArrayList<>();
		results.add(generatepassbillingmodel);
		responseVO.setResults(results);
	    actionContext.setResponseVO(responseVO);
	    
		log.exiting("ScreenLoadCommand","execute");
	}

}
