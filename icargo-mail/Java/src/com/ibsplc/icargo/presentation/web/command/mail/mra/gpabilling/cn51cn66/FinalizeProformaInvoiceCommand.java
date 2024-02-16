/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51cn66.FinalizeProformaInvoiceCommand.java
 *
 *	Created by	:	A-6991
 *	Created on	:	07-Sep-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51cn66;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66VO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListCN51CN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51CN66Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51cn66.FinalizeProformaInvoiceCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-6991	:	07-Sep-2017	:	Draft
 */
public class FinalizeProformaInvoiceCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MRA_GPABILLING");

	private static final String CLASS_NAME = "FinalizeProformaInvoiceCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51cn66";

	private static final String SAVE_SUCCESS = "invoicefinalizaion_success";
	
	private static final String SAVE_FAILURE = "invoicefinalizaion_failure";

	private static final String EMPTY_STRING= "";
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");  
		
  		ListCN51CN66Form listCN51CN66Form = (ListCN51CN66Form) invocationContext.screenModel;     
		ListCN51CN66Session listCN51CN66Session = (ListCN51CN66Session) getScreenSession(
				MODULE_NAME, SCREENID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		CN51SummaryVO cN51SummaryVO =new CN51SummaryVO();
		cN51SummaryVO.setCompanyCode(logonAttributes.getCompanyCode());
		cN51SummaryVO.setGpaCode(listCN51CN66Form.getGpaCode());
		cN51SummaryVO.setInvoiceNumber(listCN51CN66Form.getInvoiceNumber());
		CN51CN66VO vo = listCN51CN66Session.getCN51CN66VO();
		//DocumentBillingDetailsVO documentBillingDetailsVO = new DocumentBillingDetailsVO();
		Collection<CN51SummaryVO> summaryVOs =new ArrayList<CN51SummaryVO>();
		summaryVOs.add(cN51SummaryVO);
			
			try {
				new MailTrackingMRADelegate()
				.finalizeProformaInvoice(summaryVOs);
				listCN51CN66Form.setInvoiceStatus("F");
				vo.setInvoiceStatus("Finalized");
				listCN51CN66Form.setInvStatusDesc("Finalized");
			}
	
		
		 catch (BusinessDelegateException businessDelegateException) {  
			handleDelegateException(businessDelegateException);
			
		}
			invocationContext.target = SAVE_SUCCESS;
	}
}
