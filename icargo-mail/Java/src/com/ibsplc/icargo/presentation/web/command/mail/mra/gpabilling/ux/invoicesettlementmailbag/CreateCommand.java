/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.CreateCommand.java
 *
 *	Created by	:	A-7531
 *	Created on	:	08-May-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag;

import java.util.ArrayList;
import java.util.Collection;



import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux.InvoiceSettlementMailbagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.InvoiceSettlementMailbagForm;

import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.CreateCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	08-May-2018	:	Draft
 */
public class CreateCommand extends BaseCommand{
	private Log log = LogFactory.getLogger(" CreateCommand");

	private static final String CLASS_NAME = "CreateCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel";
	private static final String ACTION_SUCCESS = "screenload_success";
	
	
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-7531 on 09-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@param invocationcontext
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(InvocationContext invocationcontext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("MRA_GPABILLING");
    	log.entering(CLASS_NAME, "execute");
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	InvoiceSettlementMailbagSession session = (InvoiceSettlementMailbagSession)getScreenSession(MODULE_NAME,SCREEN_ID);
    	InvoiceSettlementMailbagForm form=(InvoiceSettlementMailbagForm)invocationcontext.screenModel;
    	String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	InvoiceSettlementFilterVO filterVO=session.getInvoiceSettlementFilterVO();
    	
    	if(form.getSettlementCurrency()!=null&&(form.getSettlementCurrency().trim().length()>0)){
    		filterVO.setSettlementCurrency(form.getSettlementCurrency().toUpperCase());
    	}
    	
    	if(form.getSettlementReferenceNumber()!=null){
    		filterVO.setSettlementReferenceNumber(form.getSettlementReferenceNumber().toUpperCase());
    	}
    	if(form.getSettlementDate()!=null){
    		filterVO.setSettlementDate(form.getSettlementDate());
    	}
		form.setActionFlag("CREATE");
		form.setPopupFlag(false);
		
		//session.setSelectedGPASettlementVO(gpaSettlementVOs);
		
		invocationcontext.target = ACTION_SUCCESS;

	}

	}


