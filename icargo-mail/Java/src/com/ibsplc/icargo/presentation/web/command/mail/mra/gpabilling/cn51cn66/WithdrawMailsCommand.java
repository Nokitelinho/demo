/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51cn66.WithdrawMailsCommand.java
 *
 *	Created by	:	A-6991
 *	Created on	:	05-Sep-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51cn66;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
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
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51cn66.WithdrawMailsCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-6991	:	05-Sep-2017	:	Draft
 */
public class WithdrawMailsCommand extends BaseCommand{

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51cn66";
	private static final String ACTION_SUCCESS = "withdraw_success";
	private static final String ACTION_FAILURE = "withdraw_failure";
	private static final String EMPTYSTRING = "";
	
	private Log log = LogFactory
			.getLogger("CN51CN66 WithdrawMailsCommand");
	
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering("WithdrawMailsCommand", "execute");  
		ListCN51CN66Form listCN51CN66Form = (ListCN51CN66Form) invocationContext.screenModel;
		ListCN51CN66Session listCN51CN66Session = (ListCN51CN66Session) getScreenSession(
				MODULE_NAME, SCREENID);
		
		Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs = new ArrayList<>();
		try {
			
				String[] selectedRow=listCN51CN66Form.getSelectedRow().split(",");  
				for(int i=0;i<selectedRow.length;i++){			 										 
					 CN66DetailsVO cn66DetailsVO = listCN51CN66Session.getCN66VOs().get(Integer.parseInt(selectedRow[i]));
					 DocumentBillingDetailsVO documentBillingDetailsVO = new DocumentBillingDetailsVO();
					 documentBillingDetailsVO.setCompanyCode(cn66DetailsVO.getCompanyCode());
					 documentBillingDetailsVO.setBillingBasis(cn66DetailsVO.getBillingBasis());
					 documentBillingDetailsVO.setGpaCode(cn66DetailsVO.getGpaCode());
					 documentBillingDetailsVO.setInvoiceNumber(cn66DetailsVO.getInvoiceNumber());
					 documentBillingDetailsVO.setMailSequenceNumber(cn66DetailsVO.getMailSequenceNumber()); //added by A-7371 as part of ICRD-259054
					 documentBillingDetailsVOs.add(documentBillingDetailsVO);
			 }
				
			
			 new MailTrackingMRADelegate()
				.withdrawMailbags(documentBillingDetailsVOs);  
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			
		}
		
		CN51CN66FilterVO cn51CN66FilterVO = listCN51CN66Session.getCN51CN66FilterVO();
    	if(cn51CN66FilterVO != null){
    		if(cn51CN66FilterVO.getInvoiceNumber() != null && cn51CN66FilterVO.getInvoiceNumber().trim().length() > 0) {
    			listCN51CN66Form.setInvoiceNumber(cn51CN66FilterVO.getInvoiceNumber());
    		}
    		if(cn51CN66FilterVO.getGpaCode() != null && cn51CN66FilterVO.getGpaCode().trim().length() > 0) {
    			listCN51CN66Form.setGpaCode(cn51CN66FilterVO.getGpaCode());
    		}
    		if(cn51CN66FilterVO.getAirlineCode() !=null && cn51CN66FilterVO.getAirlineCode().trim().length()>0){
    			listCN51CN66Form.setAirlineCode(cn51CN66FilterVO.getAirlineCode());	
    		}
    		
    		log
			.log(Log.FINE, "----cn51SummaryFilterVO---",
					cn51CN66FilterVO);
		
	}

    	invocationContext.target = ACTION_SUCCESS;  
	
	
}
 }
