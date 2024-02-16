/*
 * SaveCommand.java Created on Nov 22,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturerejectionmemo;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;


import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoInInvoiceVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureRejectionMemoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureRejectionMemoForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Command class for saving data captured through Capture Rejection Memo
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Nov 22,2007   Ruby Abraham			Initial draft
 */
public class SaveCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	
	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");
	
	
	private static final String CLASS_NAME = "SaveCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturerejectionmemo";
	
	private static final String SAVE_SUCCESS = "save_success";
	
	private static final String SAVE_FAILURE = "save_failure";
	
	
	
	
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
		
		CaptureRejectionMemoForm captureRejectionMemoForm=
			(CaptureRejectionMemoForm)invocationContext.screenModel;
		
		Collection<MemoInInvoiceVO> memoInInvoiceVOs = null;		
		
		if(invocationContext.getErrors() != null &&
				invocationContext.getErrors().size()>0){
			log.log(Log.FINE,"Errors");
			//log.log(Log.FINE,"Screen Status Flag"+captureRejectionMemoForm.getScreenStatusFlag());
			captureRejectionMemoForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
			//captureRejectionMemoSession.removeMemoInInvoiceVOs();
			invocationContext.target = SAVE_FAILURE;
			
		}
		else{	
		
			ErrorVO error = null;
			Collection<ErrorVO> errors=new ArrayList<ErrorVO>();	
					
			
			MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
			
			memoInInvoiceVOs = captureRejectionMemoSession.getMemoInInvoiceVOs();		
			
			Collection<MemoInInvoiceVO> updatedMemoInInvoiceVOs = memoInInvoiceVOs;
			
			Collection<MemoInInvoiceVO> newMemoInInvoiceVOs	= new ArrayList<MemoInInvoiceVO>();
			
			log.log(Log.FINE, "After Updation the MEMOININVOICEVOS",
					updatedMemoInInvoiceVOs);
			MemoFilterVO memoFilterVO = captureRejectionMemoSession.getMemoFilterVO();
			
			log.log(Log.FINE, "FilterVO", memoFilterVO);
			for(MemoInInvoiceVO memoInInvoiceVO:updatedMemoInInvoiceVOs){	
				
				if(memoFilterVO.getInvoiceNoFilter() != null &&
						memoFilterVO.getInvoiceNoFilter().trim().length() >0){				
					memoInInvoiceVO.setInvoiceNumber(memoFilterVO.getInvoiceNoFilter());
				}else{
					
					if(captureRejectionMemoForm.getInvoiceNo() != null &&
							captureRejectionMemoForm.getInvoiceNo().trim().length() >0){
						memoInInvoiceVO.setInvoiceNumber(captureRejectionMemoForm.getInvoiceNo().toUpperCase());						
					}
					
				}
				if(memoInInvoiceVO.getInvoiceNumber() == null || memoInInvoiceVO.getInvoiceNumber().trim().length() == 0){					
						log.log(Log.FINE,"The Invoice No  is null");
						error = new ErrorVO("mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.err.noinvoiceno");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);	
				}
				if(errors != null && errors.size() > 0){
					invocationContext.addAllError(errors);
					//captureRejectionMemoForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
					invocationContext.target=SAVE_FAILURE;
					return;
				}
				
				if(memoFilterVO.getClearancePeriod() != null &&
						memoFilterVO.getClearancePeriod().trim().length() >0){				
					memoInInvoiceVO.setClearancePeriod(memoFilterVO.getClearancePeriod());
				}else{
					
					if(captureRejectionMemoForm.getClearancePeriod() != null &&
							captureRejectionMemoForm.getClearancePeriod().trim().length() >0){
						memoInInvoiceVO.setClearancePeriod(captureRejectionMemoForm.getClearancePeriod().toUpperCase());						
					}
					
				}
				if(memoInInvoiceVO.getClearancePeriod() == null || 
						memoInInvoiceVO.getClearancePeriod().trim().length() == 0){					
						log.log(Log.FINE,"The Invoice No  is null");
						error = new ErrorVO("mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.err.noclrprd");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);	
					}
					if(errors != null && errors.size() > 0){
						invocationContext.addAllError(errors);
						invocationContext.target=SAVE_FAILURE;
						return;
					}
					
					if(memoFilterVO.getAirlineCodeFilter() != null && 
							memoFilterVO.getAirlineCodeFilter().trim().length()>0){
						memoInInvoiceVO.setAirlineCode(memoFilterVO.getAirlineCodeFilter());
					}
					
					if(memoFilterVO.getAirlineIdentifier() != 0){
						memoInInvoiceVO.setAirlineIdentifier(memoFilterVO.getAirlineIdentifier());
					}
					
					if(memoFilterVO.getInterlineBillingType()!= null && 
							memoFilterVO.getInterlineBillingType().trim().length()>0){
						memoInInvoiceVO.setInterlineBlgType(memoFilterVO.getInterlineBillingType());
					}
					/**
					 * for Optimistic Locking
					 */
					memoInInvoiceVO.setLastUpdatedUser(memoInInvoiceVO.getLastUpdatedUser());
					memoInInvoiceVO.setLastUpdatedTime(memoInInvoiceVO.getLastUpdatedTime());
					/*
					 * adding to final Collection
					 */
					newMemoInInvoiceVOs.add(memoInInvoiceVO);
						
				}							
				try{
					log.log(Log.FINE, "VOS to server", newMemoInInvoiceVOs);
					mailTrackingMRADelegate.saveMemo(newMemoInInvoiceVOs);
					
				}	
				catch(BusinessDelegateException businessDelegateException){
					log.log(Log.FINE,"inside try...caught businessDelegateException");
					businessDelegateException.getMessage();
				    errors = handleDelegateException(businessDelegateException);
				} 	
				
				captureRejectionMemoSession.removeMemoInInvoiceVOs();
				captureRejectionMemoSession.removeMemoFilterVO();
				captureRejectionMemoForm.setAirlineCode("");
				captureRejectionMemoForm.setInvoiceNo("");
				captureRejectionMemoForm.setClearancePeriod("");
				captureRejectionMemoForm.setInterlineBillingType("");
				captureRejectionMemoForm.setSelectAll(false);
				captureRejectionMemoForm.setLinkStatusFlag("disable");
				captureRejectionMemoForm.setStatusFlag("screenload");
				captureRejectionMemoForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
				log.log(Log.FINE, "Errors", errors.size());
				if(errors == null ||errors.size() ==0){	
					error = new ErrorVO("mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.info.datasavedsuccessfully");
					error.setErrorDisplayType(ErrorDisplayType.INFO);
					errors.add(error);
				}
				if (errors != null && errors.size() > 0) {
					invocationContext.addAllError(errors);
				}
				invocationContext.target = SAVE_SUCCESS;
							
			log.exiting(CLASS_NAME, "execute");
		}
	}
}
