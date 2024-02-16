/*
 * AddCommand.java Created on Nov 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturerejectionmemo;



import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
//import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;


import java.util.ArrayList;
//import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoInInvoiceVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureRejectionMemoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureRejectionMemoForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * Command class to Add a row in Capture Rejection Memo
 *
 * Revision History
 *
 * Version      Date             Author          		 Description
 *
 *  0.1        Nov 22, 2007    Ruby Abraham     		Initial draft
 */
public class AddCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	
	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");
	
	private static final String CLASS_NAME = "AddCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturerejectionmemo";
	
	private static final String ADD_SUCCESS = "add_success";
	private static final String ADD_FAILURE = "add_failure";
		
	/**
	 * Execute method
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CaptureRejectionMemoSession  captureRejectionMemoSession = 
			(CaptureRejectionMemoSession)getScreenSession(MODULE_NAME, SCREEN_ID);
		
		CaptureRejectionMemoForm captureRejectionMemoForm=
			(CaptureRejectionMemoForm)invocationContext.screenModel;
		
		
		
		if(invocationContext.getErrors() != null &&
				invocationContext.getErrors().size()>0){
			//captureRejectionMemoForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = ADD_FAILURE;
			
		}
		else{	
		
			ArrayList<MemoInInvoiceVO> memoInInvoiceVOs = null;
			
			ArrayList<MemoInInvoiceVO> newMemoInInvoiceVOs	= new ArrayList<MemoInInvoiceVO>();
			
				
			ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
			LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
			String companyCode = logonAttributes.getCompanyCode();
			
					
			memoInInvoiceVOs = captureRejectionMemoSession.getMemoInInvoiceVOs();		
			log.log(Log.FINE, "MEMOVOS from Session ", memoInInvoiceVOs);
			/** if no collection of VO create new one and one empty VO to it*/	
			if(memoInInvoiceVOs == null || memoInInvoiceVOs.size() < 1){
				log.log(Log.FINE,"MEMOVOS null");
				newMemoInInvoiceVOs.add(createEmptyVO(companyCode));
				log.log(Log.FINE,
						"Only new added vos in the collection of vos",
						newMemoInInvoiceVOs);
					
			}
			/** 
			 * if hasRowsToShow is set updates VOs with values captured from form  
			 *  and adds records to updated collection of VOs
			 */
			else{
				newMemoInInvoiceVOs = memoInInvoiceVOs;
				newMemoInInvoiceVOs.add(createEmptyVO(companyCode));
			}
					
			log.log(Log.INFO, "\n \n ----The MemoVOs  existing  is--->>",
					newMemoInInvoiceVOs);
			captureRejectionMemoSession.setMemoInInvoiceVOs(newMemoInInvoiceVOs);	
			
			captureRejectionMemoForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
			invocationContext.target = ADD_SUCCESS;
			log.exiting(CLASS_NAME, "execute");
		}
	}
	
	
	
	/**
	 *
	 * creates an empty Vo object
	 * @return AccountingCalendarVO
	 * @param companyCode
	 */
	private MemoInInvoiceVO createEmptyVO( String companyCode ) {

		log.entering(CLASS_NAME, "createEmptyVO");
		MemoInInvoiceVO memoInInvoiceVO =  new MemoInInvoiceVO();
		memoInInvoiceVO.setOperationalFlag(OPERATION_FLAG_INSERT);
		memoInInvoiceVO.setCompanyCode(companyCode);
		memoInInvoiceVO.setMemoDate(new LocalDate(NO_STATION,NONE,false));
		memoInInvoiceVO.setProvisionalAmount(0.0);		
		memoInInvoiceVO.setReportedAmount(0.0);
		memoInInvoiceVO.setDifferenceAmount(0.0);
		memoInInvoiceVO.setRemarks("");	
				
		log.exiting(CLASS_NAME,"createEmptyVO");

		return memoInInvoiceVO;
	}

	
	
	
	
	

}
