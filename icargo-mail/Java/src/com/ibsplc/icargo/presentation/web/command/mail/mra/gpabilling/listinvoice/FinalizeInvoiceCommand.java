/*
 * FinalizeInvoiceCommand.java Created on JULY 8, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice;

/**
 * @author A-3447
 * 
 */

/**
 * Created For AirNZ CR_162
 * 
 */

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
 * This command class finalize Invoice
 * 
 */
/*
 * Revision History Revision Date Author  Description
 * ==============================================================================
 * 0.1	    	08-07-2008 	      A-3447    	For  Coding
 * 
 * =============================================================================
 */

public class FinalizeInvoiceCommand extends BaseCommand {

	/*
	 * /* The Module and ScreenID mappings
	 */

	private static final String SUCCESS = "finalize_success";
	private static final String FAILURE="finalize_failed";
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.listgpabillinginvoice";
	private static final String INVOICE_ALREADY_FINALIZED="mailtracking.mra.gpabilling.listgpabillinginvoice.invoiceFinalized";
	private static final String SYS_PARAM_HONGKONGPOST="mailtracking.mra.gpabilling.einvoicehongkongpost";
	private static final String INVOICE_FINALIZED="FINALIZED";

	/*
	 * Logger for the workflow message inbox
	 */
	private Log log = LogFactory
			.getLogger("ListGPABillingInvoice ScreenloadCommand");

	/**
	 * @author A-3447 execute method for finalize invoice
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("FinalizeInvoiceCommand", "execute");
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		ListGPABillingInvoiceForm listGPABillingInvoiceForm = (ListGPABillingInvoiceForm) invocationContext.screenModel;
		ListGPABillingInvoiceSession listGPABillingInvoiceSession = (ListGPABillingInvoiceSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String counter = listGPABillingInvoiceForm.getSelectedRow();
		/*String confirmHKGPost=listGPABillingInvoiceForm.getGpaHKGPostConfirmFinalize();
		log.log(Log.FINE, "\n\n<<<<---confirmHKGPost>>>>---------  ",
				confirmHKGPost);*/
		Collection<CN51SummaryVO> cN51SummaryVOs = listGPABillingInvoiceSession
				.getCN51SummaryVOs();
		ArrayList<CN51SummaryVO> cN51SummaryVOArraylist = new ArrayList<CN51SummaryVO>(
				cN51SummaryVOs);
		
		log.log(Log.FINE, "inside *****<<<<counter-->>>>----------  ", counter);
		String[] selectedRows=counter.split(",");
		//boolean containHKG=false;
		boolean containFinalized=false;
		Map<String, Collection<OneTimeVO>> oneTimeMap = null;
		oneTimeMap=	listGPABillingInvoiceSession.getOneTimeMap();
    	Collection<OneTimeVO> statusCollection = oneTimeMap.get("mra.gpabilling.invoicestatus");		
		log.log(Log.FINE, "Invoice Status-->", statusCollection);
		Collection<String> systemParameterCodes = new ArrayList<String>();

		/*//systemParameterCodes.add(SYS_PARAM_HONGKONGPOST);

		Map<String, String> systemParameters = null;

		try {

			systemParameters = new SharedDefaultsDelegate()
					.findSystemParameterByCodes(systemParameterCodes);

		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}
		String hongKongPostPA = null;
		if (systemParameters != null && systemParameters.size() > 0) {
			hongKongPostPA = (systemParameters.get(SYS_PARAM_HONGKONGPOST));
		}
		log.log(Log.INFO, "hongKongPostPA--->", hongKongPostPA);*/
		Collection<CN51SummaryVO> cn51SummaryVOsFinalize=new ArrayList<CN51SummaryVO>();
		
		for(int index=0;index<selectedRows.length;index++){
			
			CN51SummaryVO cn51SummaryVO=cN51SummaryVOArraylist.get(Integer.parseInt(selectedRows[index]));
			if((MRAConstantsVO.FINALIZED).equals(cn51SummaryVO.getInvoiceStatus())||(MRAConstantsVO.DIFFERENCED).equals(cn51SummaryVO.getInvoiceStatus())){
				containFinalized=true;
				break;
			}	
			/*if(cn51SummaryVO.getGpaCode().equals(hongKongPostPA)){
				containHKG=true;				
			}*/	
			
			cn51SummaryVOsFinalize.add(cn51SummaryVO);
		}
		
		
		for(CN51SummaryVO cn51SummaryVO:cn51SummaryVOsFinalize){
			
			for(OneTimeVO status :statusCollection){
			if(cn51SummaryVO.getInvoiceStatus().equals(status.getFieldDescription()) ){    				
				cn51SummaryVO.setInvoiceStatus(status.getFieldValue()); 
				}
			}
		}
		
		log.log(Log.FINE, "\n\n\n<--SELECTED INVOICES TO FINALIZE----->",
				cn51SummaryVOsFinalize);
		if(containFinalized){
			log.log(Log.FINE,"already Finalized---");											
			errors.add(new ErrorVO(INVOICE_ALREADY_FINALIZED));
			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE;
			return;
		}/*else
		if(containHKG && ("N").equals(confirmHKGPost)){
			
			log.log(Log.FINE,"----Contains HKG show yes/no---");											
			listGPABillingInvoiceForm.setGpaHKGPostConfirmFinalize("ASKCONFIRM");
			invocationContext.target = FAILURE;
			return;
			
		}*//*else //contains hongkong and confirmed yes
		if(("Y").equals(confirmHKGPost)){
			
			try {
				mailTrackingMRADelegate.finalizeandSendEInvoice(cn51SummaryVOsFinalize,"GENMSG");
			} catch (BusinessDelegateException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
		}*/else{
			
			try {
				//mailTrackingMRADelegate.finalizeandSendEInvoice(cn51SummaryVOsFinalize,"NOOP");
				mailTrackingMRADelegate.finalizeProformaInvoice(cn51SummaryVOsFinalize);
			} catch (BusinessDelegateException e) {
				// TODO Auto-generated catch block
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
    		listGPABillingInvoiceForm.setGpacode(cn51SummaryFilterVO.getGpaCode());
    		listGPABillingInvoiceForm.setInvoiceNo(cn51SummaryFilterVO.getInvoiceNumber());
    		listGPABillingInvoiceForm.setInvoiceStatus(cn51SummaryFilterVO.getInvoiceStatus());
    		listGPABillingInvoiceForm.setInvoiceFinalizedStatus(INVOICE_FINALIZED);
			log
					.log(Log.FINE, "----cn51SummaryFilterVO---",
							cn51SummaryFilterVO);
    		
    	}	
		invocationContext.target = SUCCESS;
				
	}
	
	
	
	
}
	


