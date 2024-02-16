/**
 * CreateCommand.java Created on Mar 30, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoicesettlement;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.InvoiceSettlementSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class CreateCommand extends BaseCommand{

	/*
	 * Logger for the Mailtracking Mra
	 */
	 private  Log log = LogFactory.getLogger("MAILTRACKING MRA");
	 
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID ="mailtracking.mra.gpabilling.invoicesettlement";      
	private static final String SCREENLOAD_SUCCESS ="screenload_success";
	private static final String SCREENLOAD_FAILURE ="screenload_failure";	
	private static final String EXIT ="exit";
	private static final String YES ="Y";
	/**
	 * @param invocationcontext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationcontext)
	throws CommandInvocationException {
		//InvoiceSettlementSession session=(InvoiceSettlementSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		InvoiceSettlementForm form=(InvoiceSettlementForm)invocationcontext.screenModel;
		/*LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		GPASettlementVO gpaSettlementFilterVO = new GPASettlementVO();
		Collection <GPASettlementVO> gpaSettlementVOsInSession = session.getGPASettlementVOs();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
*/
		/*ErrorVO errorVO=null;
		Page<GPASettlementVO> gpaSettlementVOs = null;		
		for(GPASettlementVO gpaSettlementVO: gpaSettlementVOsInSession){
			gpaSettlementFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			gpaSettlementFilterVO.setGpaCode(gpaSettlementVO.getGpaCode());
			gpaSettlementFilterVO.setDisplayPage(Integer.parseInt(form.getDisplayPage()));
		}
		try {
			gpaSettlementVOs =  new MailTrackingMRADelegate().findUnSettledInvoicesForGPAForSettlementCapture(gpaSettlementFilterVO);
		} catch (BusinessDelegateException e) {
			log.log(Log.FINE,  "BusinessDelegateException");
			
		}
		if(gpaSettlementVOs==null ||gpaSettlementVOs.size()<=0){
			errorVO=new ErrorVO("mailtracking.mra.gpabilling.msg.err.allinvoicessettled");
			errors.add(errorVO); 
			
		}
		if(errors!=null && errors.size()>0){
		
			invocationcontext.addAllError(errors);
			invocationcontext.target=SCREENLOAD_SUCCESS;
			form.setAvailableSettlement(EXIT);
			form.setCreateFlag(YES);
			return;
		}
		session.setSelectedGPASettlementVOs(gpaSettlementVOs);*/
		form.setAvailableSettlement(EXIT);
		form.setCreateFlag(YES);
		invocationcontext.target = SCREENLOAD_SUCCESS;

	}

}
