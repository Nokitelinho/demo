/*
 * UpdateSessionCommand.java Created on Feb 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturerejectionmemo;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureRejectionMemoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureRejectionMemoForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * Command class for updating session.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1			Feb 21, 2007	  Ruby Abraham				Initial draft
 */
public class UpdateSessionCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	
	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");
	
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturerejectionmemo";
	
	private static final String CLASS_NAME = "UpdateSessionCommand";
	
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		CaptureRejectionMemoSession  captureRejectionMemoSession = 
			(CaptureRejectionMemoSession)getScreenSession(MODULE_NAME, SCREEN_ID);
		
		CaptureRejectionMemoForm captureRejectionMemoForm=(CaptureRejectionMemoForm)invocationContext.screenModel;
			
		
		MemoFilterVO memoFilterVO = new MemoFilterVO();
		
		updateFilterVO(captureRejectionMemoForm,memoFilterVO);	
		
		captureRejectionMemoSession.setMemoFilterVO(memoFilterVO);

		log.exiting(CLASS_NAME, "execute");
	}
	
	
	/**
	 * 
	 * @param captureRejectionMemoForm
	 * @param memoFilterVO
	 */
	private void updateFilterVO(CaptureRejectionMemoForm captureRejectionMemoForm,MemoFilterVO memoFilterVO){
		
		    log.entering("UpdateSessionCommand","updateFilterVO");
		    ApplicationSessionImpl applicationSession = getApplicationSession();
	        LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
	        String companyCode=logonAttributes.getCompanyCode().toUpperCase();
	    	log.log(Log.FINE, "CompanyCode", companyCode);
			memoFilterVO.setCompanyCode(companyCode); 	
	    	
	    	
	    	
	    	String airlineCode = captureRejectionMemoForm.getAirlineCode();
	    	if(airlineCode != null && airlineCode.trim().length() > 0){
	    		memoFilterVO.setAirlineCodeFilter(airlineCode.toUpperCase());
		    }
			else {
				memoFilterVO.setAirlineCodeFilter("");
			}	    	
	    	
	    	
	    	String invoiceNo = captureRejectionMemoForm.getInvoiceNo();
	    	if(invoiceNo != null && invoiceNo.trim().length() > 0){
	    		
	    		memoFilterVO.setInvoiceNoFilter(invoiceNo.toUpperCase());
		    }
			else {
				memoFilterVO.setInvoiceNoFilter("");
			}
	    	
	    	String clearancePeriod = captureRejectionMemoForm.getClearancePeriod();
	    	if(clearancePeriod != null && clearancePeriod.trim().length() > 0){	    		
	    		memoFilterVO.setClearancePeriod(clearancePeriod.toUpperCase());
		    }
			else {
				memoFilterVO.setClearancePeriod("");
			}
	    	
	    	String interlineBillingType = captureRejectionMemoForm.getInterlineBillingType();
	    	if(interlineBillingType != null && interlineBillingType.trim().length() > 0){
	    		memoFilterVO.setInterlineBillingType(interlineBillingType);
	    	}
			else {
				memoFilterVO.setInterlineBillingType("");
			}
	    	
			
			log.log(Log.FINE, "MemoFilterVO---->", memoFilterVO);
			log.exiting("UpdateSessionCommand","updateFilterVO");    	
	    	
	}
}
