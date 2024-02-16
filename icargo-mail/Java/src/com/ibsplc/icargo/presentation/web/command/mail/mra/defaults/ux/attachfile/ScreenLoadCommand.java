/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.ux.attachfile.ScreenLoadCommand.java
 *
 *	Created by	:	A-4809
 *	Created on	:	01-Nov-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.ux.attachfile;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.web.model.mail.mra.defaults.AttachFileModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.ux.attachfile.ScreenLoadCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	01-Nov-2021	:	Draft
 */
public class ScreenLoadCommand extends AbstractCommand{
	
	private Log log = LogFactory.getLogger("Mail Mra AttachFile ");
	
	private static final String MODULE_NAME = "mail.mra";
	private static final String CLASS_NAME="ScreenLoadCommand";
	private static final String SCREENID = "mail.mra.defaults.ux.attachfile";

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.spring.command.AbstractCommand#execute(com.ibsplc.icargo.framework.web.spring.controller.ActionContext)
	 *	Added by 			: A-4809 on 01-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		this.log.entering(MODULE_NAME, CLASS_NAME);
		LogonAttributes logonAttributes = getLogonAttribute();
		String companyCode = logonAttributes.getCompanyCode();
		AttachFileModel attachFileModel = (AttachFileModel)actionContext.getScreenModel();
	     List<AttachFileModel> results = new ArrayList<AttachFileModel>();
	     results.add(attachFileModel);
		 ResponseVO responseVO = new ResponseVO();
		 responseVO.setStatus("success");
		 responseVO.setResults(results); 
		 actionContext.setResponseVO(responseVO);
		this.log.exiting(MODULE_NAME, CLASS_NAME);
		
	}

}
