/*
 * FinalizeInvoiceCommand.java Created on JULY 8, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoiceenquiry;



/**
 * Created For CR-AirNZ 846
 *
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingInvoiceEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingInvoiceEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-3108
 *
 */
public class FinalizeInvoiceCommand extends BaseCommand {

	/*
	 * /* The Module and ScreenID mappings
	 */
	private static final String MODULE = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.gpabillinginvoiceenquiry";

	private static final String SUCCESS = "finalize_success";
	private static final String FAILURE="finalize_failure";

	private static final String INVOICE_ALREADY_FINALIZED="mailtracking.mra.gpabilling.invoiceenquiry.invoiceFinalized";
	private static final String KEY_NO_RESULTS_FOUND = "mailtracking.mra.gpabilling.noresultsfound";
	private static final String REDIRECT_LOAD_E_INVOICE="load_eInvoice";
	private static final String LOAD_EINVOICE_YES="EINVOICE_YES";

	private static final String SYS_PARAM_HONGKONGPOST="mailtracking.mra.gpabilling.einvoicehongkongpost";

	private Log log = LogFactory
			.getLogger("FinalizeInvoiceCommand");

	/**
	 * @author A-3108 execute method for finalize invoice
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("FinalizeInvoiceCommand", "execute");
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		GPABillingInvoiceEnquiryForm form =(GPABillingInvoiceEnquiryForm)invocationContext.screenModel;
		GPABillingInvoiceEnquirySession session = (GPABillingInvoiceEnquirySession)getScreenSession(
				MODULE, SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		CN51SummaryVO cN51SummaryVO=null;
		if(session !=null && session.getCN51SummaryVO()!=null){
		cN51SummaryVO = session.getCN51SummaryVO();
		}
		log.log(Log.FINE,
				"Inside Finalize invoice command-b4 delegate call- >>",
				cN51SummaryVO);
		if(cN51SummaryVO!=null){

		Map<String, Collection<OneTimeVO>> oneTimeMap = null;
		oneTimeMap=	session.getOneTimeMap();
    	Collection<OneTimeVO> statusCollection = oneTimeMap.get("mra.gpabilling.invoicestatus");
		log.log(Log.FINE, "Invoice Status-->", statusCollection);
		for(OneTimeVO status :statusCollection){
			if(!(("F").equals(cN51SummaryVO.getInvoiceStatus()))){
				if(cN51SummaryVO.getInvoiceStatus().equals(status.getFieldDescription()) ){
					cN51SummaryVO.setInvoiceStatus(status.getFieldValue());
				}
			}
		}

		Collection<String> systemParameterCodes = new ArrayList<String>();

		systemParameterCodes.add(SYS_PARAM_HONGKONGPOST);

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
		log.log(Log.INFO, "hongKongPostPA--->", hongKongPostPA);
		if(!(("F").equals(cN51SummaryVO.getInvoiceStatus())||(("D").equals(cN51SummaryVO.getInvoiceStatus())))){
			if(hongKongPostPA.equals(cN51SummaryVO.getGpaCode())){
				form.setLoadEInvoice(LOAD_EINVOICE_YES);
				invocationContext.target = REDIRECT_LOAD_E_INVOICE;
				return;
			}

		}


		if(!(("F").equals(cN51SummaryVO.getInvoiceStatus())||(("D").equals(cN51SummaryVO.getInvoiceStatus())))){

					try {
						Collection<CN51SummaryVO> cn51SummaryVOS=new ArrayList<CN51SummaryVO>();
						cn51SummaryVOS.add(cN51SummaryVO);
						mailTrackingMRADelegate.finalizeandSendEInvoice(cn51SummaryVOS,"NOOP");

					} catch (BusinessDelegateException e) {
						errors = handleDelegateException(e);

					}
					//	display invoice finalized status for bug fix 35118 					
				    	form.setInvoiceFinalizedStatus("FINALIZED");
						invocationContext.target = SUCCESS;
						log.exiting("ListGPABillingInvoice", "execute");

		}
		else{

						log.log(Log.FINE,"already Finalized---");
						errors.add(new ErrorVO(INVOICE_ALREADY_FINALIZED));
						invocationContext.addAllError(errors);
						invocationContext.target = FAILURE;
						return;

			}
		}
	else{

    		errors.add(new ErrorVO(KEY_NO_RESULTS_FOUND));
    		invocationContext.addAllError(errors);
    		invocationContext.target = FAILURE;
			return;
	}
 }
}



