/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice.WithdrawInvoiceCommand.java
 *
 *	Created by	:	A-6991
 *	Created on	:	18-Sep-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListGPABillingInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListGPABillingInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice.WithdrawInvoiceCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-6991	:	18-Sep-2017	:	Draft
 */
public class WithdrawInvoiceCommand extends BaseCommand{

	private static final String SUCCESS = "withdraw_success";
	private static final String FAILURE="withdraw_failed";
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.listgpabillinginvoice";
	private static final String INVOICE_ALREADY_FINALIZED="mailtracking.mra.gpabilling.listgpabillinginvoice.invoiceFinalized";

	private Log log = LogFactory
			.getLogger("ListGPABillingInvoice WithdrawInvoiceCommand");
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("WithdrawInvoiceCommand", "execute");
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		ListGPABillingInvoiceForm listGPABillingInvoiceForm = (ListGPABillingInvoiceForm) invocationContext.screenModel;
		ListGPABillingInvoiceSession listGPABillingInvoiceSession = (ListGPABillingInvoiceSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String counter = listGPABillingInvoiceForm.getSelectedRow();
	
		Collection<CN51SummaryVO> cN51SummaryVOs = listGPABillingInvoiceSession
				.getCN51SummaryVOs();
		ArrayList<CN51SummaryVO> cN51SummaryVOArraylist = new ArrayList<CN51SummaryVO>(
				cN51SummaryVOs);
		
		boolean containFinalized=false;
		Map<String, Collection<OneTimeVO>> oneTimeMap = null;
		oneTimeMap=	listGPABillingInvoiceSession.getOneTimeMap();
    	Collection<OneTimeVO> statusCollection = oneTimeMap.get("mra.gpabilling.invoicestatus");		
		log.log(Log.FINE, "Invoice Status-->", statusCollection);


		CN51SummaryVO cn51SummaryVO=cN51SummaryVOArraylist.get(Integer.parseInt(counter));
	
			if((MRAConstantsVO.FINALIZED).equals(cn51SummaryVO.getInvoiceStatus())||
					(MRAConstantsVO.DIFFERENCED).equals(cn51SummaryVO.getInvoiceStatus())){
				containFinalized=true;
			}	
			
			for(OneTimeVO status :statusCollection){
			if(cn51SummaryVO.getInvoiceStatus().equals(status.getFieldDescription()) ){    				
				cn51SummaryVO.setInvoiceStatus(status.getFieldValue()); 
				}
			}
	
		
		log.log(Log.FINE, "\n\n\n<--SELECTED INVOICE TO WITHDRAW----->",
				cn51SummaryVO);
		if(containFinalized){
			log.log(Log.FINE,"already Finalized---");											
			errors.add(new ErrorVO(INVOICE_ALREADY_FINALIZED));
			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE;
			return;
		}
		else{
			
			try {
				
				mailTrackingMRADelegate.withdrawInvoice(cn51SummaryVO);
			} catch (BusinessDelegateException e) {
				e.getMessage();
			}
			
		}
		
    	CN51SummaryFilterVO cn51SummaryFilterVO = listGPABillingInvoiceSession.getCN51SummaryFilterVO();
    	if(cn51SummaryFilterVO != null){
    		if(cn51SummaryFilterVO.getFromDate() != null) {
    			listGPABillingInvoiceForm.setFromDate(cn51SummaryFilterVO.getFromDate().toDisplayDateOnlyFormat());
    		}
    		if(cn51SummaryFilterVO.getToDate() != null) {
    			listGPABillingInvoiceForm.setToDate(cn51SummaryFilterVO.getToDate().toDisplayDateOnlyFormat());
    		}
    		if(cn51SummaryFilterVO.getGpaCode() !=null && cn51SummaryFilterVO.getGpaCode().trim().length()>0){
    			listGPABillingInvoiceForm.setGpacode(cn51SummaryFilterVO.getGpaCode());	
    		}
    		if(cn51SummaryFilterVO.getInvoiceNumber() !=null && cn51SummaryFilterVO.getInvoiceNumber().trim().length() >0){
    			listGPABillingInvoiceForm.setInvoiceNo(cn51SummaryFilterVO.getInvoiceNumber());	
    		}
    		if(cn51SummaryFilterVO.getInvoiceStatus() !=null && cn51SummaryFilterVO.getInvoiceStatus().trim().length() >0){
    			listGPABillingInvoiceForm.setInvoiceStatus(cn51SummaryFilterVO.getInvoiceStatus());	
    		}
    		if(cn51SummaryFilterVO.getInvoiceStatus() !=null && cn51SummaryFilterVO.getInvoiceStatus().trim().length() >0){
    			listGPABillingInvoiceForm.setInvoiceStatus(cn51SummaryFilterVO.getInvoiceStatus());	
    		}
    		
			log
					.log(Log.FINE, "----cn51SummaryFilterVO---",
							cn51SummaryFilterVO);
    		
    	}	
		invocationContext.target = SUCCESS;
				
	}
}
