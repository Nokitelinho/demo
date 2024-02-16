/*
 * InvoiceLovListCommand.java Created on Feb 02, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51cn66;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceLovVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 * InvoiceLovListCommand
 */
public class InvoiceLovListCommand extends BaseCommand{
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private Log log = LogFactory.getLogger(	"MRA RateCardLOV ListCommand");

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 log.entering("ListAccountLOVCommand","execute");
		 ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			InvoiceLovForm form=(InvoiceLovForm)invocationContext.screenModel;

			Page<InvoiceLovVO> invoiceLovVOs=null;
			InvoiceLovVO invoiceLovVO=new InvoiceLovVO();
			invoiceLovVO.setCompanyCode(logonAttributes.getCompanyCode());

			if(form.getCode()!=null && form.getCode().trim().length()>0){
				invoiceLovVO.setInvoiceNumber(form.getCode().toUpperCase());
			}
			if(form.getGpaCodeFilter()!=null && form.getGpaCodeFilter().trim().length()>0){
				invoiceLovVO.setGpaCode(form.getGpaCodeFilter().toUpperCase());
			}

			invoiceLovVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));

			MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
			try{
				invoiceLovVOs=delegate.findInvoiceLov(invoiceLovVO);
			}
			catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				log.log(Log.FINE, "caught");
			}
			//System.out.println("RATECARDs"+invoiceLovVOs);

			form.setInvoiceLovPage(invoiceLovVOs);




		/* form.setFormCount("1");
		 form.setMultiselect("N");
		 form.setSelectedValues("1");
		 form.setIndex("0");*/
		 invocationContext.target=SCREENLOAD_SUCCESS;
			log.exiting("ListAccountLOVCommand","execute");

	 }
}