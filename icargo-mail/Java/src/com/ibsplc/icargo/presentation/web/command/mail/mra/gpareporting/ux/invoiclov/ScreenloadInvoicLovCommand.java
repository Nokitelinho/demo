/*
 * ScreenloadInvoicLovCommand.java Created on Dec 13, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.invoiclov;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
//import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicSummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.InvoicLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/** 
 * @author A-8464
 */
public class ScreenloadInvoicLovCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("MAIL MRA GPAREPORTING");

	
	private static final String SCREEN_SUCCESS = "screenload_success";
	

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ScreenloadInvoicLovCommand", "execute");
		
		InvoicLovForm invoicLovForm = (InvoicLovForm) invocationContext.screenModel;
		invocationContext.target = SCREEN_SUCCESS;
		Page<InvoicVO> invoicSummaryVOs = null;
		
		if(("invoicLovScreenLoad").equals(invoicLovForm.getLovActionType()) || ("invoicLovList").equals(invoicLovForm.getLovActionType()))
		{
			LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
			String companyCode = logonAttributes.getCompanyCode();
			InvoicFilterVO invoicFilterVO = new InvoicFilterVO();
			invoicFilterVO=populateinvoicFilterVO(invoicLovForm);
			invoicFilterVO.setCmpcod(companyCode);
			invoicFilterVO.setPageNumber(Integer.parseInt(invoicLovForm.getDisplayPage()));
			invoicFilterVO.setDefaultPageSize(Integer.parseInt(invoicLovForm.getDefaultPageSize()));
			MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
			try {
				if(!(("Y").equals(invoicLovForm.getMultiselect()))){
					invoicLovForm.setSelectedValues("");
				}
				invoicSummaryVOs=mailTrackingMRADelegate.listInvoic(invoicFilterVO, invoicFilterVO.getPageNumber()); 
			} catch (BusinessDelegateException e) {

				log.log(Log.FINE,  "BusinessDelegateException");
			}
		}
		invoicLovForm.setInvoicLovPage(invoicSummaryVOs);
		invocationContext.target=SCREEN_SUCCESS;
		}


	private InvoicFilterVO populateinvoicFilterVO(InvoicLovForm invoicLovForm) {
		InvoicFilterVO invoicFilterVO = new InvoicFilterVO();
		invoicFilterVO.setGpaCode(invoicLovForm.getGpaCodeFilter());
		invoicFilterVO.setFromDate(invoicLovForm.getFromDateFilter());
		invoicFilterVO.setToDate(invoicLovForm.getToDateFilter());
		return invoicFilterVO;
	}
}
